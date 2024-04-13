package com.studyhub.modules.post.controller;

import com.studyhub.config.RestDocSetupTest;
import com.studyhub.config.StudyHubMockUser;
import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
    @DisplayName("공부 기록 페이지 가져오기")
    void posts() throws Exception {

        // given
        Post post = Post.builder()
                .title("제목1")
                .content("링크1")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(get("/api/posts?page=1&size=20")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalCount").value(1))
                .andExpect(jsonPath("$.items[0].title").value("제목1"))
                .andExpect(jsonPath("$.items[0].content").value("링크1"))
                .andDo(document("posts/page",
                        queryParameters(
                                parameterWithName("page").description("가져올 페이지"),
                                parameterWithName("size").description("가져올 사이즈")
                        )
                ));
    }
}