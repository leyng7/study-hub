package com.studyhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyhub.config.RestDocSetupTest;
import com.studyhub.repository.MemberRepository;
import com.studyhub.request.SignUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest extends RestDocSetupTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
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


}