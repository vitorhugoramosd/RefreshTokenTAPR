package com.example.authservice.application.auth;

import com.example.authservice.application.port.TokenService;
import com.example.authservice.interfaces.rest.dto.auth.LogoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutHandler {
    private final TokenService tokenService;

    public void handle(LogoutRequest request) {
        tokenService.revokeRefreshToken(request.refreshToken());
    }
}
