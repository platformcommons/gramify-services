package com.platformcommons.platform.service.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReactionCountDTO {

    private Long count;

    private Long entityId;

    private String entityType;

    private Boolean isReactedByMe;

    @Builder
    public ReactionCountDTO(Long count, Long entityId, String entityType, Boolean isReactedByMe) {
        this.count = count;
        this.entityId = entityId;
        this.entityType = entityType;
        this.isReactedByMe = isReactedByMe;
    }
}
