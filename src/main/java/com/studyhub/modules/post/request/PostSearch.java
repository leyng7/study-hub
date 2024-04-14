package com.studyhub.modules.post.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    private final int page;

    private final int size;

    public PostSearch() {
        this.page = 1;
        this.size = 20;
    }

    @Builder
    public PostSearch(int page, int size) {
        this.page = max(1, page);
        this.size = min(size, 2000);
    }

    public long getOffset() {
        return (long) (page - 1) * size;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }

}
