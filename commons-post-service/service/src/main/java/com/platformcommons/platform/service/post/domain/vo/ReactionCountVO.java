package com.platformcommons.platform.service.post.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReactionCountVO {

    private Long count;

    private Long entityId;

    private String entityType;

    @Builder
    public ReactionCountVO(Long count, Long entityId, String entityType) {
        this.count = count;
        this.entityId = entityId;
        this.entityType = entityType;
    }
}
