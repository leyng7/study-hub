package com.studyhub.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEditor {

    private final String nickname;

    @Builder
    public MemberEditor(String nickname) {
        this.nickname = nickname;
    }

}
