package com.platformcommons.platform.service.iam.dto;


import java.util.Set;

public class PageAuthDTO <T extends AuthBaseDTO> {
    private Set<T> elements;
    private boolean hasNext;
    private long totalElements;

    public PageAuthDTO(Set<T> elements, boolean hasNext) {
        this.elements = elements;
        this.hasNext = hasNext;
    }

    public PageAuthDTO(Set<T> elements, boolean hasNext, long totalElements) {
        this.elements = elements;
        this.hasNext = hasNext;
        this.totalElements = totalElements;
    }

    public Set<T> getElements() {
        return this.elements;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public PageAuthDTO() {
    }
}
