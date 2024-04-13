package com.studyhub.modules.member.response;

import lombok.Builder;

@Builder
public record MemberResponse(
        Long id,
        String username,
        String nickname
) {

}
