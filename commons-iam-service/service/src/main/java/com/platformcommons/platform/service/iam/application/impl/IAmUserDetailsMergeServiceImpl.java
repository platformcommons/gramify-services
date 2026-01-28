package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.iam.application.IAmUserDetailsMergeService;
import com.platformcommons.platform.service.iam.application.UserRoleMapService;
import com.platformcommons.platform.service.iam.domain.Authority;
import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.domain.UserRoleMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class IAmUserDetailsMergeServiceImpl implements IAmUserDetailsMergeService {

    @Autowired
    private UserRoleMapService userRoleMapService;

    @Override
    @Transactional
    public void addUserContext(PlatformToken platformToken) {
        List<UserRoleMap> userRoleMaps = userRoleMapService.getUserRoleMapByUserId(platformToken.getUserContext().getUserId());
        Set<String> authorities = platformToken.getUserContext().getAuthorities();
        userRoleMaps.forEach(userRoleMap -> {
            Role role = userRoleMap.getRole();
            authorities.add(role.getCode());
            for (Role inheritedRolePermission : role.getInheritedRolePermissions()) {
                authorities.add(inheritedRolePermission.getCode());
                for (Authority authority : inheritedRolePermission.getAuthorities()) {
                    authorities.add(authority.getCode());
                }
            }
            for (Authority authority : role.getAuthorities()) {
                authorities.add(authority.getCode());
            }
        });
    }
}
