package com.studyhub.domain;

import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private Member(String username, String password, String nickname, Role role) {
        Assert.notNull(role, "권한은 필수입니다.");
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public MemberEditor.MemberEditorBuilder toEditor() {
        return MemberEditor.builder()
                .nickname(nickname);
    }

    public void edit(MemberEditor memberEditor) {
        this.nickname = memberEditor.getNickname();
    }

}