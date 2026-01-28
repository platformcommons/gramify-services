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
public class PostVO extends BaseDTO {

    private Long id;

    private String linkedEntityCode;

    private String status;

    @Builder
    public PostVO(Long postId, String linkedEntityCode, String status) {
        this.id = postId;
        this.linkedEntityCode = linkedEntityCode;
        this.status = status;
    }
}
