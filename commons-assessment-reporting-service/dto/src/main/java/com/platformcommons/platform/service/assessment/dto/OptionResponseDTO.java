package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
public class OptionResponseDTO {
    private Long optionId;
    private Long responseCount;
    @Builder
    public OptionResponseDTO(Long optionId, Long responseCount) {
        this.optionId = optionId;
        this.responseCount = responseCount;
    }

}