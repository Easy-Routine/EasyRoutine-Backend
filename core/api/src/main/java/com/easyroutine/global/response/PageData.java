package com.easyroutine.global.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageData <T> {
    private final int total;
    private final List<T> contents;

    @Builder
    private PageData(int total, List<T> contents) {
        this.total = total;
        this.contents = contents;
    }

    public static <T> PageData<T> of(int total, List<T> contents) {
        return PageData.<T>builder()
                .total(total)
                .contents(contents)
                .build();
    }
}
