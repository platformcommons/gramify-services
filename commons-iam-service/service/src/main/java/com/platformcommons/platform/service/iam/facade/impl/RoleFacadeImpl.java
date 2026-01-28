package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.iam.application.RoleService;
import com.platformcommons.platform.service.iam.application.constant.AuthConstant;
import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.dto.RoleDTO;
import com.platformcommons.platform.service.iam.dto.RoleHierarchyStructureDTO;
import com.platformcommons.platform.service.iam.facade.RoleFacade;
import com.platformcommons.platform.service.iam.facade.assembler.RoleDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.RoleHierarchyStructureDTOAssembler;
import com.platformcommons.platform.service.iam.facade.obo.OBORoleFacade;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
public class RoleFacadeImpl implements RoleFacade {


    private final PolicyEvaluator policyEvaluator;
    private final RoleDTOAssembler roleDTOAssembler;
    private final RoleHierarchyStructureDTOAssembler roleHierarchyStructureDTOAssembler;
    private final RoleService roleService;

    public RoleFacadeImpl(PolicyEvaluator policyEvaluator, RoleDTOAssembler roleDTOAssembler, RoleHierarchyStructureDTOAssembler roleHierarchyStructureDTOAssembler,
                          RoleService roleService, OBORoleFacade oboRoleFacade) {
        this.policyEvaluator = policyEvaluator;
        this.roleDTOAssembler = roleDTOAssembler;
        this.roleHierarchyStructureDTOAssembler = roleHierarchyStructureDTOAssembler;
        this.roleService = roleService;
    }

    @Override
    public Long addRole(RoleDTO roleDTO, String parentRoleCode, String functionName,
                        String authorityFromRoleCode) {
        //policyEvaluator.evaluate(AuthConstant.PERMISSION_MANAGE_ROLE);
        Role role = roleService.addRole(roleDTOAssembler.fromDTO(roleDTO));
        if (parentRoleCode != null) {
            Role parentRole = roleService.getRoleByCode(parentRoleCode);
            roleService.addRoleHierarchy(role, parentRole, functionName);
        }
        if (authorityFromRoleCode != null) {
            roleService.copyAuthorityFrom(role, authorityFromRoleCode);
        }
        return role.getId();
    }

    @Override
    public void updateRole(RoleDTO roleDTO) {
        policyEvaluator.evaluate(AuthConstant.PERMISSION_MANAGE_ROLE);
        roleService.updateRole(roleDTOAssembler.fromDTO(roleDTO));
    }

    @Override
    public Set<RoleDTO> getAllRoles() {
        Set<RoleDTO> roleDTOS;
        Set<Role> roles = roleService.getAllRoles();
        roleDTOS = roleDTOAssembler.toDTO(roles);
        return roleDTOS;
    }

    @Override
    public RoleDTO getRoleByCode(String roleCode) {
        return roleDTOAssembler.
                toDTO(roleService.getRoleByCode(roleCode));
    }

    @Override
    public Role getRoleReferenceByCode(String roleCode) {
        return roleService.getRoleReferenceByCode(roleCode);
    }

    @Override
    public Set<RoleHierarchyStructureDTO> getRoleHierarchyStructure(String functionName) {
        return roleHierarchyStructureDTOAssembler.toDTO(roleService.getRoleHierarchyStructure(functionName));
    }

    @Override
    public void addAuthorities(String roleCode, Set<String> authorities) {
        policyEvaluator.evaluate(AuthConstant.PERMISSION_MANAGE_ROLE);
        roleService.addAuthorities(roleCode, authorities);
    }


}
