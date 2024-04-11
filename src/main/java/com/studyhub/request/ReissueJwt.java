package com.studyhub.request;

public record ReissueJwt(
        String refreshToken
) {

    public static ReissueJwt of(String refreshToken) {
        return new ReissueJwt(refreshToken);
    }

}
