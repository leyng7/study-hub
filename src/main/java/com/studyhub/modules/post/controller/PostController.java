package com.studyhub.modules.post.controller;

import com.studyhub.infra.config.UserPrincipal;
import com.studyhub.infra.response.PagingResponse;
import com.studyhub.modules.post.request.PostCreate;
import com.studyhub.modules.post.request.PostSearch;
import com.studyhub.modules.post.response.PostResponse;
import com.studyhub.modules.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public void write(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid PostCreate request
    ) {
        request.validate();
        postService.write(principal.getMemberId(), request);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public PagingResponse<PostResponse> posts(
            @AuthenticationPrincipal UserPrincipal principal,
            PostSearch postSearch
    ) {

        return postService.searchPosts(postSearch);
    }

}
