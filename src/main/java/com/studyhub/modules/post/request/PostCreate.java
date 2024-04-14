package com.studyhub.modules.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostCreate(

        @NotBlank(message = "타이틀을 입력해주세요.")
        String title,

        @NotBlank(message = "링크를 입력해주세요.")
        String content

) {

    public void validate() {

    }

}
