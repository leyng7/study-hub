package com.studyhub.response;

import lombok.Builder;

public record JwtResponse(
        String tokenType,
        String accessToken,
        long expiresIn,
        String refreshToken,
        long refreshTokenExpiresIn
) {

    @Builder
    public JwtResponse {
    }

}
