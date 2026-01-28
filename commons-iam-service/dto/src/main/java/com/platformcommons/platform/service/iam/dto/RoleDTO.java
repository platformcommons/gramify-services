package com.platformcommons.platform.service.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@NoArgsConstructor
public class RoleDTO extends AuthBaseDTO {

    private Integer id;
    private String code;
    private String roleName;

    private Set<AuthorityDTO> authorities;
    @Schema(allowableValues = {"APP_ROLE","PROCESS","TENANT"})
    @Pattern(regexp = "^(APP_ROLE|PROCESS|TENANT)$")
    private String roleType;
    private Set<RoleDTO> inheritedRolePermissions;

    @Builder
    public RoleDTO(Integer id,String code, String roleName,String roleType,Set<RoleDTO> inheritedRolePermissions,Set<AuthorityDTO> authorities) {
        this.id = id;
        this.code = code;
        this.roleName = roleName;
        this.roleType=roleType;
        this.inheritedRolePermissions=inheritedRolePermissions;
        this.authorities = authorities;
    }
}
