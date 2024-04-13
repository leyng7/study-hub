package com.studyhub.modules.post.controller;

import com.studyhub.infra.config.UserPrincipal;
import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.repository.PostRepository;
import com.studyhub.modules.post.request.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public void write() {

    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public List<Post> posts(
            @AuthenticationPrincipal UserPrincipal principal
    ) {

        return postRepository.searchPosts(new PostSearch());
    }

}
