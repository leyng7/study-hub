package com.studyhub.modules.post.repository;

import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studyhub.modules.post.domain.Post;
import com.studyhub.modules.post.request.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.studyhub.modules.post.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private  final DateTemplate<LocalDate> postCreatedAt = Expressions.dateTemplate(LocalDate.class, "to_date({0})", post.createdAt);

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

    @Override
    public Optional<Post> getPost(Long createdBy, LocalDate createdAt) {

        return Optional.ofNullable(
                jpaQueryFactory
                    .selectFrom(post)
                    .where(
                            post.createdBy.eq(createdBy),
                            postCreatedAt.eq(createdAt)
                    ).fetchOne()
        );
    }

}
