package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.UserRoleMap;

import java.util.List;

public interface UserRoleMapService {
    List<UserRoleMap> getUserRoleMapByUserId(Long userId);

    UserRoleMap addUserRole(UserRoleMap userRoleMap);

}
