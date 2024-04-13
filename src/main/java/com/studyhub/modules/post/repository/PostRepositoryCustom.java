package com.studyhub.modules.post.repository;

import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> searchPosts(PostSearch postSearch);

}
