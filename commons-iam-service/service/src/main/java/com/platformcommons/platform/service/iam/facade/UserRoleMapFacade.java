package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.dto.IAMUserRoleMapDTO;

import java.util.List;
import java.util.Set;

public interface UserRoleMapFacade {

    void addUserRoles(List<IAMUserRoleMapDTO> userRoleMapDTOList, Long adminRoleId,String tenantLogin);

    void assignRolesToUser(Long userId, String userLogin, Set<String> roleCodes);
}
