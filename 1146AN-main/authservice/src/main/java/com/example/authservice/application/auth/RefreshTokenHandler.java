package com.example.authservice.application.auth;

import com.example.authservice.application.port.TokenService;
import com.example.authservice.interfaces.rest.dto.auth.RefreshTokenRequest;
import com.example.authservice.interfaces.rest.dto.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenHandler {
    private final TokenService tokenService;

    public TokenResponse handle(RefreshTokenRequest request) {
        var tokenPair = tokenService.refresh(request.refreshToken());
        return new TokenResponse(
            tokenPair.accessToken(),
            tokenPair.refreshToken(),
            tokenPair.expiresInSeconds()
        );
    }
}
