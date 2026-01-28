package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PairDTO<F,S> {

    private final F first;
    private final S second;

    @Builder
    public PairDTO(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
