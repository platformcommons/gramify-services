package com.platformcommons.platform.service.iam.dto;

public class RoleHierarchyStructureDTO extends AuthBaseDTO {

    private Long id;
    private String function;
    private RoleDTO role;
    private RoleDTO parentRole;

}
