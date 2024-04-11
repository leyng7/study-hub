package com.studyhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyhub.config.RestDocSetupTest;
import com.studyhub.config.StudyHubMockUser;
import com.studyhub.repository.MemberRepository;
import com.studyhub.request.Login;
import com.studyhub.request.ReissueJwt;
import com.studyhub.request.SignUp;
import com.studyhub.response.JwtResponse;
import com.studyhub.service.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest extends RestDocSetupTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        // given
        SignUp signup = SignUp.builder()
                .username("leyng7")
                .password("1q2w3e4r!")
                .nickname("몽친")
                .build();

        // expected
        mockMvc.perform(post("/api/auth/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("sign-up",
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("nickname").description("닉네임")
                        )
                ));
    }

    @Test
    @StudyHubMockUser
    @DisplayName("로그인")
    void login() throws Exception {
        // given
        Login login = Login.builder()
                .username("leyng7")
                .password("1q2w3e4r!")
                .build();

        // expected
        mockMvc.perform(post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("login",
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("패스워드")
                        ),
                        responseFields(
                                fieldWithPath("tokenType").description("토큰 구분 (Bearer)"),
                                fieldWithPath("accessToken").description("accessToken"),
                                fieldWithPath("expiresIn").description("accessToken 만료시간(초), 30분"),
                                fieldWithPath("refreshToken").description("refreshToken"),
                                fieldWithPath("refreshTokenExpiresIn").description("refreshToken 만료시간(초), 7일")
                        )
                ));
    }

    @Test
    @StudyHubMockUser
    @DisplayName("토큰 재발급")
    void reissue() throws Exception {
        // given
        Login login = Login.builder()
                .username("leyng7")
                .password("1q2w3e4r!")
                .build();

        JwtResponse jwtResponse = authService.login(login);
        ReissueJwt reissueJwt = ReissueJwt.of(jwtResponse.refreshToken());

        // expected
        mockMvc.perform(post("/api/auth/reissue")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtResponse.accessToken())
                        .content(objectMapper.writeValueAsString(reissueJwt))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("reissue",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("tokenType accessToken")
                        ),
                        requestFields(
                                fieldWithPath("refreshToken").description("refreshToken")
                        ),
                        responseFields(
                                fieldWithPath("tokenType").description("토큰 구분 (Bearer)"),
                                fieldWithPath("accessToken").description("accessToken"),
                                fieldWithPath("expiresIn").description("accessToken 만료시간(초), 30분"),
                                fieldWithPath("refreshToken").description("refreshToken"),
                                fieldWithPath("refreshTokenExpiresIn").description("refreshToken 만료시간(초), 7일")
                        )
                ));

    }

    @Test
    @StudyHubMockUser
    @DisplayName("refreshToken 없이 토큰 재발급")
    void reissue2() throws Exception {
        // given
        Login login = Login.builder()
                .username("leyng7")
                .password("1q2w3e4r!")
                .build();

        JwtResponse jwtResponse = authService.login(login);

        // expected
        mockMvc.perform(post("/api/auth/reissue")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtResponse.accessToken())
                        .content(objectMapper.writeValueAsString(ReissueJwt.of("")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.refreshToken").value("토큰은 필수 값입니다."))
                .andDo(print());

    }


}