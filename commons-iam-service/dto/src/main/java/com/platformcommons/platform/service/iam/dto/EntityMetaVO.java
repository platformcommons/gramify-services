package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntityMetaVO {

    private String entityId;
    private String entityType;

    @Builder
    public EntityMetaVO(String entityId, String entityType) {
        this.entityId = entityId;
        this.entityType = entityType;
    }
}
