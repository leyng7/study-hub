package com.studyhub.service;

import com.studyhub.domain.Member;
import com.studyhub.domain.RefreshToken;
import com.studyhub.domain.Role;
import com.studyhub.repository.RefreshTokenRepository;
import com.studyhub.request.Login;
import com.studyhub.response.JwtResponse;
import com.studyhub.jwt.TokenProvider;
import com.studyhub.repository.MemberRepository;
import com.studyhub.request.SignUp;
import lombok.RequiredArgsConstructor;
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
                .role(Role.ROLE_USER)
                .build();

        memberRepository.save(member);
    }


    @Transactional
    public JwtResponse login(Login login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtResponse jwtResponse = tokenProvider.generateJwt(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(Long.valueOf(authentication.getName()))
                .token(jwtResponse.refreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return jwtResponse;
    }

    public JwtResponse reissue(String accessToken, String refreshToken) {

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        RefreshToken findRefreshToken = refreshTokenRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

         if (!findRefreshToken.getToken().equals(refreshToken)) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        JwtResponse jwtResponse = tokenProvider.generateJwt(authentication);
        findRefreshToken.updateToken(jwtResponse.refreshToken());

        return jwtResponse;
    }
}
