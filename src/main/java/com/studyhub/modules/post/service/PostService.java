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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }


    public PagingResponse<PostResponse> searchPosts(PostSearch postSearch) {
        Page<Post> postPage = postRepository.searchPosts(postSearch);
        return new PagingResponse<>(postPage, PostResponse.class);
    }

}
