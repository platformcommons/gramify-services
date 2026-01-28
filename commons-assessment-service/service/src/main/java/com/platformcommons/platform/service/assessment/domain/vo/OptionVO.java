package com.platformcommons.platform.service.assessment.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OptionVO {

    private Long questionId;
    private Long optionId;
    private Long mtfOptionId;
    private Long defaultOptionId;

    @Builder
    public OptionVO(Long questionId, Long optionId, Long mtfOptionId, Long defaultOptionId) {
        this.questionId = questionId;
        this.optionId = optionId;
        this.mtfOptionId = mtfOptionId;
        this.defaultOptionId = defaultOptionId;
    }
}
