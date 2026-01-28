package com.platformcommons.platform.service.iam.dto.brbase;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class TLDRoleHierarchyDTO {
    @NotNull
    private Integer id;

    // roleCode of RoleHierarchy
    @Valid
    private TLDRoleDTO roleCode;

    // parentRoleCode of RoleHierarchy
    @Valid
    private TLDRoleDTO parentRoleCode;
}
