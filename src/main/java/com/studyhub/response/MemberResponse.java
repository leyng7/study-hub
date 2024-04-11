package com.studyhub.response;

import lombok.Builder;

@Builder
public record MemberResponse(
        Long id,
        String username,
        String nickname
) {

}
