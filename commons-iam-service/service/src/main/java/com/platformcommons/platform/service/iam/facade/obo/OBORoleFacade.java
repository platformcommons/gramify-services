package com.platformcommons.platform.service.iam.facade.obo;

import com.platformcommons.platform.service.iam.dto.RoleDTO;

import java.util.Set;

public interface OBORoleFacade {

    Set<RoleDTO> getRolesFromLinkedSystem();

    void addRoleAndHierarchy(RoleDTO roleDTO, String parentRoleCode, String functionName);
}
