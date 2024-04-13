package com.studyhub.modules.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.request.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.studyhub.modules.post.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> searchPosts(PostSearch postSearch) {
        long totalCount = jpaQueryFactory.select(post.count())
                .from(post)
                .fetchFirst();

        List<Post> items = jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();

        return new PageImpl<>(items, postSearch.getPageable(), totalCount);
    }

}
