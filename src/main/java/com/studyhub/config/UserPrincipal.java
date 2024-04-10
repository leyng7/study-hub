package com.studyhub.config;

import com.studyhub.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserPrincipal extends User {

    private final Long memberId;

    public UserPrincipal(Member member) {
        super(String.valueOf(member.getId()), member.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(member.getRole().name())
                ));
        this.memberId = member.getId();
    }
}
