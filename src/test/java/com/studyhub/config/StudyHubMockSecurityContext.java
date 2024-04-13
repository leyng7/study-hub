package com.studyhub.config;

import com.studyhub.infra.config.UserPrincipal;
import com.studyhub.modules.member.domain.Member;
import com.studyhub.modules.member.domain.Role;
import com.studyhub.modules.member.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class StudyHubMockSecurityContext implements WithSecurityContextFactory<StudyHubMockUser> {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public StudyHubMockSecurityContext(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SecurityContext createSecurityContext(StudyHubMockUser annotation) {
        var member = Member.builder()
                .username(annotation.username())
                .password(passwordEncoder.encode(annotation.password()))
                .nickname(annotation.nickname())
                .role(Role.ROLE_USER)
                .build();

        memberRepository.save(member);

        var principal = new UserPrincipal(member.getId(), member.getPassword(), member.getRole());

        var authenticationToken = new UsernamePasswordAuthenticationToken(principal,
                member.getPassword(),
                List.of(new SimpleGrantedAuthority(member.getRole().name())));

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
