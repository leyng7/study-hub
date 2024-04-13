package com.studyhub.modules.member.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEditor {

    private final String nickname;
    private final boolean removed;

    @Builder
    public MemberEditor(String nickname, boolean removed) {
        this.nickname = nickname;
        this.removed = removed;
    }

}
