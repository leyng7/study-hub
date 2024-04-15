package com.studyhub.modules.post.response;

import com.studyhub.modules.post.domain.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final Long createdBy;
    private final LocalDate createdAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdBy = post.getCreatedBy();
        this.createdAt = post.getCreatedAt().toLocalDate();
    }

}
