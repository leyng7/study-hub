package com.studyhub.infra.request;

import jakarta.validation.constraints.NotBlank;

public record ReissueJwt(

        @NotBlank(message = "토큰은 필수 값입니다.")
        String refreshToken

) {

    public static ReissueJwt of(String refreshToken) {
        return new ReissueJwt(refreshToken);
    }

}
