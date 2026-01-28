package com.platformcommons.platform.service.search.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public final class FacetPageDTO<T> {

    private final Set<T> elements;
    private final boolean hasNext;

    private final Map<String, Map<String,Long>> facetResult;

    private long totalElements;


    @Builder
    public FacetPageDTO(Set<T> elements, boolean hasNext, Map<String, Map<String, Long>> facetResult,long totalElements) {
        this.elements = elements;
        this.hasNext = hasNext;
        this.facetResult = facetResult;
        this.totalElements=totalElements;
    }

    public FacetPageDTO(Set<T> elements, boolean hasNext, Map<String, Map<String, Long>> facetResult) {
        this.elements = elements;
        this.hasNext = hasNext;
        this.facetResult = facetResult;
    }
}
