package com.example.authservice.interfaces.rest.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank(message = "Refresh token é obrigatório")
    String refreshToken
) {}
