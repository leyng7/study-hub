package com.studyhub.modules.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@NoArgsConstructor
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    private int page = 1;

    private int size = 20;

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
