package com.studyhub.modules.member.controller;

import com.studyhub.config.RestDocSetupTest;
import com.studyhub.config.StudyHubMockUser;
import com.studyhub.infra.config.UserPrincipal;
import com.studyhub.modules.member.domain.Member;
import com.studyhub.modules.member.repository.MemberRepository;
import com.studyhub.modules.member.request.MemberEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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


    @Test
    @StudyHubMockUser
    @DisplayName("닉네임 수정")
    void edit() throws Exception {
        // given
        MemberEdit memberEdit = MemberEdit.builder()
                .nickname("친몽")
                .build();

        // expected
        mockMvc.perform(patch("/api/members/me")
                        .content(objectMapper.writeValueAsString(memberEdit))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members/edit",
                        requestFields(
                                fieldWithPath("nickname").description("닉네임")
                        )
                ));

        // TODO 서비스 쪽으로 테스트 이동 필요
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findById(principal.getMemberId()).orElseThrow();
        assertEquals(member.getNickname(), "친몽");
    }

    @Test
    @StudyHubMockUser
    @DisplayName("탈퇴")
    void remove() throws Exception {
        // given

        // expected
        mockMvc.perform(delete("/api/members/me")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members/remove"));

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findById(principal.getMemberId()).orElseThrow();
        assertTrue(member.isRemoved());
    }

}