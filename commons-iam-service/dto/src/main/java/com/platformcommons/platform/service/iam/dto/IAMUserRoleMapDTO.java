package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class IAMUserRoleMapDTO {
    private Long id;
    private Long userId;
    private String roleCode;
    private String roleName;

    @Builder
    public IAMUserRoleMapDTO(Long id, Long userId, String roleCode, String roleName) {
        this.id = id;
        this.userId = userId;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

}
