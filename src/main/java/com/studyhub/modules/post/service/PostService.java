package com.studyhub.modules.post.service;

import com.studyhub.infra.response.PagingResponse;
import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.repository.PostRepository;
import com.studyhub.modules.post.request.PostCreate;
import com.studyhub.modules.post.request.PostSearch;
import com.studyhub.modules.post.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void write(Long memberId, PostCreate postCreate) {

        Optional<Post> post = postRepository.getPost(memberId, LocalDate.now());

        if (post.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 게시글이 있습니다.");
        }

        Post newPost = Post.builder()
                .title(postCreate.title())
                .content(postCreate.content())
                .build();

        postRepository.save(newPost);
    }


    public PagingResponse<PostResponse> searchPosts(PostSearch postSearch) {
        Page<Post> postPage = postRepository.searchPosts(postSearch);
        return new PagingResponse<>(postPage, PostResponse.class);
    }

}
