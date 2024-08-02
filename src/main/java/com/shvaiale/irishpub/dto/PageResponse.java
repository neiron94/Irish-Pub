package com.shvaiale.irishpub.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(List<T> content, Metadata metadata) {

    public static <K> PageResponse<K> of(Page<K> page) {
        Metadata metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
        return new PageResponse<K>(page.getContent(), metadata);
    }

    public record Metadata(int page, int size, long totalElements) {
    }
}
