package com.studyhub.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberEdit(

        @NotBlank(message = "닉네임은 필수 값입니다.")
        String nickname

) {

}
