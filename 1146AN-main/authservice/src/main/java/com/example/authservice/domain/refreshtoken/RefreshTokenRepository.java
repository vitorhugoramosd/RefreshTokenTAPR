package com.example.authservice.domain.refreshtoken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    
    Optional<RefreshToken> findActiveByHash(String tokenHash);
    
    void revoke(UUID tokenId);
    
    void deleteById(UUID tokenId);
    
    void revokeAllByUser(UUID userId);
}
