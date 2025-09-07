package com.example.authservice.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authservice.application.port.TokenService;
import com.example.authservice.domain.refreshtoken.RefreshToken;
import com.example.authservice.domain.refreshtoken.RefreshTokenRepository;
import com.example.authservice.domain.user.User;
import com.example.authservice.infrastructure.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {
    private final JwtProperties props;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public TokenPair issue(User user) {
        if (props.getSecret() == null || props.getSecret().isBlank()) {
            throw new IllegalStateException("jwt.secret deve ser definido");
        }

        Instant now = Instant.now();
        Algorithm alg = Algorithm.HMAC256(props.getSecret().getBytes(StandardCharsets.UTF_8));

        // Criar access token
        Instant accessExp = now.plusSeconds(props.getAccessTtlSeconds());
        String access = JWT.create()
                .withIssuer(props.getIssuer())
                .withAudience(props.getAudience())
                .withSubject(user.getId().toString())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(accessExp))
                .withClaim("type", "access")
                .withClaim("email", user.getEmail().getValue())
                .withClaim("role", user.getRole().getValue().name())
                .withClaim("level", user.getRole().getValue().getLevel())
                .sign(alg);

        // Criar refresh token
        String refreshToken = generateRefreshToken();
        String refreshTokenHash = hashRefreshToken(refreshToken);
        Instant refreshExp = now.plusSeconds(props.getRefresTtlSeconds());
        
        RefreshToken refreshTokenEntity = new RefreshToken(refreshTokenHash, refreshExp, user);
        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenPair(access, refreshToken, props.getAccessTtlSeconds());
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        String tokenHash = hashRefreshToken(refreshToken);
        
        return refreshTokenRepository.findActiveByHash(tokenHash)
                .map(token -> {
                    // Revogar o token atual
                    token.revoke();
                    refreshTokenRepository.save(token);
                    
                    // Criar novos tokens
                    return issue(token.getUser());
                })
                .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido ou expirado"));
    }

    @Override
    public void revokeRefreshToken(String refreshToken) {
        String tokenHash = hashRefreshToken(refreshToken);
        refreshTokenRepository.findActiveByHash(tokenHash)
                .ifPresent(token -> refreshTokenRepository.revoke(token.getId()));
    }

    private String generateRefreshToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private String hashRefreshToken(String refreshToken) {
        return Base64.getEncoder().encodeToString(refreshToken.getBytes(StandardCharsets.UTF_8));
    }
}
