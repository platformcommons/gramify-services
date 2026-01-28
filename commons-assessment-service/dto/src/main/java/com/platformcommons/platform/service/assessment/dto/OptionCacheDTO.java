package com.platformcommons.platform.service.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@Builder
public class OptionCacheDTO {

    private Long optionId;
    private Long mtfOptionId;
    private Long defaultOptionId;

}
