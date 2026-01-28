package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoleDTO {

    private Long userId;

    private Long roleId;

    private String roleCode;

    private Boolean activeStatus;

    @Builder
    public UserRoleDTO(Long userId, Long roleId, String roleCode,Boolean activeStatus) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.activeStatus = activeStatus;
    }
}
