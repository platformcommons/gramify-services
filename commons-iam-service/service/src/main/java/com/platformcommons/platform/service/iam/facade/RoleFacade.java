package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.dto.RoleDTO;
import com.platformcommons.platform.service.iam.dto.RoleHierarchyStructureDTO;

import java.util.Set;

public interface RoleFacade {
    Long addRole(RoleDTO roleDTO, String parentRoleCode, String functionName, String authorityFromRoleCode);

    void updateRole(RoleDTO roleDTO);

    Set<RoleDTO> getAllRoles();

    RoleDTO getRoleByCode(String roleCode);

    Role getRoleReferenceByCode(String roleCode);

    Set<RoleHierarchyStructureDTO> getRoleHierarchyStructure(String functionName);

    void addAuthorities(String roleCode, Set<String> authorities);
}
