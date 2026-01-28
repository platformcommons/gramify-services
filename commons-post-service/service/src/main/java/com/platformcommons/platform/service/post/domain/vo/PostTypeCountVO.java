package com.platformcommons.platform.service.post.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostTypeCountVO extends BaseDTO {

    private Long userId;

    private String postType;

    private String postSubType;

    private Long postCount;

    @Builder
    public PostTypeCountVO(Long userId, String postType, String postSubType, Long postCount) {
        this.userId = userId;
        this.postType = postType;
        this.postSubType = postSubType;
        this.postCount = postCount;
    }
}
