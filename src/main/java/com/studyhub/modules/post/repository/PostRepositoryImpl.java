package com.studyhub.modules.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.studyhub.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> searchPosts(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
