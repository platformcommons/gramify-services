package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.RoleService;
import com.platformcommons.platform.service.iam.domain.Authority;
import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.domain.RoleHierarchyStructure;
import com.platformcommons.platform.service.iam.domain.repo.RoleHierarchyStructureRepository;
import com.platformcommons.platform.service.iam.domain.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.platformcommons.platform.service.iam.domain.Role.RoleType.APP_ROLE;
import static com.platformcommons.platform.service.iam.domain.Role.RoleType.PROCESS;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleHierarchyStructureRepository roleHierarchyStructureRepository;

    @Override
    public Role addRole(Role role) {
        role.init();
        return roleRepository.save(role);
    }

    @Override
    public RoleHierarchyStructure addRoleHierarchy(Role role, Role parentRole, String function) {
        RoleHierarchyStructure roleHierarchyStructure = RoleHierarchyStructure.builder()
                .role(role)
                .parentRole(parentRole)
                .function(function)
                .build();
        roleHierarchyStructure.init();
        return roleHierarchyStructureRepository.save(roleHierarchyStructure);
    }

    @Override
    public void addRoles(List<Role> roles) {
        roles.forEach(Role::init);
        roleRepository.saveAll(roles);
    }

    @Override
    public void updateRole(Role role) {
        Role roleFetched = getRoleByCode(role.getCode());
        roleFetched.update(role);
        if(role.getRoleType().equals(PROCESS) ||  role.getRoleType().equals(APP_ROLE)) {
            role.updateAuthorities(role.getAuthorities());
        }
        roleRepository.save(role);
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAllRoles(PlatformSecurityUtil.getCurrentTenantId()));
    }

    @Override
    public Role getRoleByCode(String roleCode) {
        return roleRepository.findByCode(roleCode,PlatformSecurityUtil.getCurrentTenantId()).orElseThrow(() ->
                new NotFoundException(String.format("Role with code %s not found", roleCode)));
    }


    @Override
    public Role getRoleReferenceByCode(String roleCode) {
        return roleRepository.getRoleReferenceByCode(roleCode,PlatformSecurityUtil.getCurrentTenantId()).orElseThrow(() ->
                new NotFoundException(String.format("Role with code %s not found", roleCode)));
    }

    @Override
    public Set<RoleHierarchyStructure> getRoleHierarchyStructure(String function) {
        return roleHierarchyStructureRepository.findByFunction(function);
    }

    @Override
    public void addAuthorities(String roleCode, Set<String> authorities) {
        Role roleFetched = getRoleByCode(roleCode);
        authorities.forEach(code -> roleFetched.addAuthority(Authority.builder()
                .code(code).build()));
        roleRepository.save(roleFetched);
    }

    @Override
    public void copyAuthorityFrom(Role role, String authorityFromRoleCode) {
        Role roleFetched = roleRepository.findByCode(authorityFromRoleCode,
                        PlatformSecurityUtil.getCurrentTenantId())
                .orElseThrow(() -> new NotFoundException
                        (String.format("Role with code %s not found", authorityFromRoleCode)));
        roleFetched.getAuthorities().forEach(role::addAuthority);
        roleRepository.save(role);
    }

    @Override
    public Role addRoleInherit(Role role) {
        if (role.getInheritedRolePermissions() != null && !role.getInheritedRolePermissions().isEmpty()) {
            Set<String> inheritedRolePermissions = role.getInheritedRolePermissions().stream().map(Role::getCode).collect(Collectors.toSet());
            Set<Role> roles = roleRepository.findByCodeIn(inheritedRolePermissions,PlatformSecurityUtil.getCurrentTenantId());
            if (roles.size() != inheritedRolePermissions.size()) {
                throw new NotFoundException("Some inherited role permission not found");
            }
            role.setInheritedRolePermissions(roles);
        }
        role.inheritCheck();
        role.initV2();
        return roleRepository.save(role);
    }
}
