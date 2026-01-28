package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.iam.application.UserRoleMapService;
import com.platformcommons.platform.service.iam.application.utility.RoleUtil;
import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.UserRoleMap;
import com.platformcommons.platform.service.iam.dto.IAMUserRoleMapDTO;
import com.platformcommons.platform.service.iam.facade.RoleFacade;
import com.platformcommons.platform.service.iam.facade.UserFacade;
import com.platformcommons.platform.service.iam.facade.UserRoleMapFacade;
import com.platformcommons.platform.service.iam.facade.assembler.IAMUserDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRoleMapFacadeImpl implements UserRoleMapFacade {

    private final UserRoleMapService userRoleMapService;
    private final UserFacade userFacade;
    private final RoleFacade roleFacade;
    private final IAMUserDTOAssembler IAMUserDTOAssembler;
    private final PolicyEvaluator policyEvaluator;


    @Override
    public void addUserRoles(List<IAMUserRoleMapDTO> userRoleMapDTOList, Long adminRoleId, String tenantLogin) {
        userRoleMapDTOList.forEach(iamUserRoleMapDTO -> {
            User user = User.builder().id(iamUserRoleMapDTO.getUserId()).build();
            Role role = Role.builder().id(adminRoleId).code(RoleUtil.adminRoleCode(tenantLogin)).build();
            UserRoleMap userRoleMap = UserRoleMap.builder().id(iamUserRoleMapDTO.getId()).user(user).role(role).build();
            userRoleMapService.addUserRole(userRoleMap);
        });
    }

    @Override
    public void assignRolesToUser(Long userId, String userLogin, Set<String> roleCodes) {
       // policyEvaluator.evaluate(AuthConstant.PERMISSION_MANAGE_USER_ROLE);
        User user = validateUserDetail(userId, userLogin);
        roleCodes.forEach(roleCode -> {
            Role role = roleFacade.getRoleReferenceByCode(roleCode);
            userRoleMapService.addUserRole(IAMUserDTOAssembler.buildUserRoleMap(user, role));
        });
    }

    private User validateUserDetail(Long userId, String userLogin) {
        if (userId == null && userLogin == null) {
            throw new InvalidInputException("User Id or User Login should not be null");
        }
        return userId != null ?
                userFacade.getUserReferenceById(userId) :
                userFacade.getUserReferenceByLogin(userLogin);
    }
}
