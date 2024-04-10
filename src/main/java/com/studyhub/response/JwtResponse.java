package com.studyhub.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponse {

    private final String tokenType;
    private final String accessToken;
    private final long expiresIn;
    private final String refreshToken;
    private final long refreshTokenExpiresIn;

    @Builder
    public JwtResponse(String tokenType, String accessToken, long expiresIn, String refreshToken, long refreshTokenExpiresIn) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }



}
