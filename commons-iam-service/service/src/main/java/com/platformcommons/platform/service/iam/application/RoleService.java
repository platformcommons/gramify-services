package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.domain.RoleHierarchyStructure;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Role addRole(Role role);

    RoleHierarchyStructure addRoleHierarchy(Role role,Role parentRole,String functionName);
    void addRoles(List<Role> roles);

    void updateRole(Role roleDTO);

    Set<Role> getAllRoles();

    Role getRoleByCode(String roleCode);

    Set<RoleHierarchyStructure> getRoleHierarchyStructure(String functionName);

    void addAuthorities(String roleCode, Set<String> authorities);

    void copyAuthorityFrom(Role role, String authorityFromRoleCode);

    Role addRoleInherit(Role role);

    Role getRoleReferenceByCode(String roleCode);
}
