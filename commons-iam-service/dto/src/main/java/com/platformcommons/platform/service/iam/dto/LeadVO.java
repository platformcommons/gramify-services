package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class LeadVO {
    private Long id;
    private String key;
    private String organizationName;
    private String activationStatus;

    @Builder
    public LeadVO(Long id, String key, String organizationName, String activationStatus) {
        this.id = id;
        this.key = key;
        this.organizationName = organizationName;
        this.activationStatus = activationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeadVO leadVO = (LeadVO) o;
        return Objects.equals(id, leadVO.id) && Objects.equals(key, leadVO.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key);
    }
}
