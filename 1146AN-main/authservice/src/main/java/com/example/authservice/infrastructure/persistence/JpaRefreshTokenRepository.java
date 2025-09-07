package com.example.authservice.infrastructure.persistence;

import com.example.authservice.domain.refreshtoken.RefreshToken;
import com.example.authservice.domain.refreshtoken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaRefreshTokenRepository implements RefreshTokenRepository {
    private final SpringDataRefreshTokenJpa springDataRefreshTokenJpa;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return springDataRefreshTokenJpa.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findActiveByHash(String tokenHash) {
        return springDataRefreshTokenJpa.findByTokenHashAndRevokedFalseAndExpiresAtAfter(tokenHash, java.time.Instant.now());
    }

    @Override
    public void revoke(UUID tokenId) {
        springDataRefreshTokenJpa.findById(tokenId)
                .ifPresent(token -> {
                    token.revoke();
                    springDataRefreshTokenJpa.save(token);
                });
    }

    @Override
    public void deleteById(UUID tokenId) {
        springDataRefreshTokenJpa.deleteById(tokenId);
    }

    @Override
    public void revokeAllByUser(UUID userId) {
        springDataRefreshTokenJpa.revokeAllByUser(userId);
    }
}
