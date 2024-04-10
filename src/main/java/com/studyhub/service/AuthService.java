package com.studyhub.service;

import com.studyhub.config.UserPrincipal;
import com.studyhub.domain.Member;
import com.studyhub.domain.RefreshToken;
import com.studyhub.repository.RefreshTokenRepository;
import com.studyhub.request.Login;
import com.studyhub.response.JwtResponse;
import com.studyhub.jwt.TokenProvider;
import com.studyhub.repository.MemberRepository;
import com.studyhub.request.SignUp;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signUp(SignUp signUp) {

        if (memberRepository.existsByUsername(signUp.getUsername())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        String encryptedPassword = passwordEncoder.encode(signUp.getPassword());

        Member member = Member.builder()
                .username(signUp.getUsername())
                .password(encryptedPassword)
                .nickname(signUp.getNickname())
                .build();

        memberRepository.save(member);
    }


    @Transactional
    public JwtResponse login(Login login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(),  login.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtResponse jwtResponse = tokenProvider.generateTokenDto(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(principal.getMemberId())
                .token(jwtResponse.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return jwtResponse;
    }

}
