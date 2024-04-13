package com.studyhub.infra.config;

import com.studyhub.modules.member.domain.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
public class UserPrincipal extends User {

    private final Long memberId;

    public UserPrincipal(Long memberId, String password, Role role) {
        super(String.valueOf(memberId), password, List.of(new SimpleGrantedAuthority(role.name())));
        this.memberId = memberId;
    }

    public UserPrincipal(String memberId, Collection<? extends GrantedAuthority> authorities) {
        super(memberId, "", authorities);
        this.memberId = Long.parseLong(memberId);
    }

}
