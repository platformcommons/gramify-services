package com.platformcommons.platform.service.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDTO {
    private Long id;
    private String code;
    private String tenantId;
    private String roleName;
    private String description;
    private Boolean isActive;
    private String notes;
    private String roleType;

    @Builder
    public RoleDTO(Long id, String code, String tenantId, String roleName, String description, Boolean isActive, String notes) {
        this.id = id;
        this.code = code;
        this.tenantId = tenantId;
        this.roleName = roleName;
        this.description = description;
        this.isActive = isActive;
        this.notes = notes;
    }
}
