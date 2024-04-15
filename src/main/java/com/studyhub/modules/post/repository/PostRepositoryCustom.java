package com.studyhub.modules.post.repository;

import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.request.PostSearch;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface PostRepositoryCustom {

    Page<Post> searchPosts(PostSearch postSearch);

    Optional<Post> getPost(Long createdBy, LocalDate createdAt);

}
