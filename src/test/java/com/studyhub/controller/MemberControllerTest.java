package com.studyhub.controller;

import com.studyhub.config.RestDocSetupTest;
import com.studyhub.config.StudyHubMockUser;
import com.studyhub.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends RestDocSetupTest {


    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @StudyHubMockUser
    @DisplayName("로그인 사용자 정보 가져오기")
    void me() throws Exception {
        // given

        // expected
        mockMvc.perform(get("/api/members/me")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members/me",
                        responseFields(
                                fieldWithPath("id").description("테이블 아이디(PK)"),
                                fieldWithPath("username").description("사용자 아이디"),
                                fieldWithPath("nickname").description("닉네임")
                        )
                ));
    }
}