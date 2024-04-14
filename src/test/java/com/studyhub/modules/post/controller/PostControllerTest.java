package com.studyhub.modules.post.controller;

import com.studyhub.config.RestDocSetupTest;
import com.studyhub.config.StudyHubMockUser;
import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.repository.PostRepository;
import com.studyhub.modules.post.request.PostCreate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends RestDocSetupTest {

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @StudyHubMockUser
    @DisplayName("공부 등록")
    void write() throws Exception {

        // given
        PostCreate newPost = PostCreate.builder()
                .title("제목1")
                .content("링크1")
                .build();

        // expected
        mockMvc.perform(post("/api/posts")
                        .content(objectMapper.writeValueAsString(newPost))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("posts/write",
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("링크").optional()
                        )
                ));


        assertEquals(postRepository.count(), 1);
    }


    @Test
    @StudyHubMockUser
    @DisplayName("작성자마다 하루에 글 1개만 등록")
    void write2() throws Exception {

        // given
        Post newPost1 = Post.builder()
                .title("제목1")
                .content("링크1")
                .build();
        postRepository.save(newPost1);

        PostCreate newPost = PostCreate.builder()
                .title("제목1")
                .content("링크1")
                .build();

        // expected
        mockMvc.perform(post("/api/posts")
                        .content(objectMapper.writeValueAsString(newPost))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("이미 등록된 게시글이 있습니다."))
                .andExpect(jsonPath("$.validation").exists())
                .andDo(document("posts/write2"
                ))
        ;

    }


    @Test
    @StudyHubMockUser
    @DisplayName("공부 기록 페이지 가져오기")
    void posts() throws Exception {

        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("링크" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/api/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalCount").value(30))
                .andExpect(jsonPath("$.items[0].title").value("제목30"))
                .andExpect(jsonPath("$.items[0].content").value("링크30"))
                .andDo(document("posts/page",
                        queryParameters(
                                parameterWithName("page").description("가져올 페이지"),
                                parameterWithName("size").description("가져올 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("page").description("현재 페이지"),
                                fieldWithPath("size").description("현재 사이즈"),
                                fieldWithPath("totalCount").description("총 개수"),
                                fieldWithPath("items").description("공부 기록 목록"),
                                fieldWithPath("items[].id").description("공부기록 아이디"),
                                fieldWithPath("items[].title").description("공부기록 제목"),
                                fieldWithPath("items[].content").description("공부기록 링크"),
                                fieldWithPath("items[].createdBy").description("등록자(PK)"),
                                fieldWithPath("items[].createdAt").description("등록일")
                        )
                ));
    }

    @Test
    @StudyHubMockUser
    @DisplayName("공부 기록 페이지 파라미터 없이 가져오기")
    void posts2() throws Exception {

        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("링크" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/api/posts?page=2")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(2))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalCount").value(30))
                .andExpect(jsonPath("$.items[0].title").value("제목10"))
                .andExpect(jsonPath("$.items[0].content").value("링크10"));
    }

}