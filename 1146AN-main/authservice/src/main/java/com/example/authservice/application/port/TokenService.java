package com.example.authservice.application.port;

import com.example.authservice.domain.user.User;

public interface TokenService {
    record TokenPair(
        String accessToken,
        String refreshToken,
        long expiresInSeconds
    ) {}

    TokenPair issue(User user);
    
    TokenPair refresh(String refreshToken);
    
    void revokeRefreshToken(String refreshToken);
}
