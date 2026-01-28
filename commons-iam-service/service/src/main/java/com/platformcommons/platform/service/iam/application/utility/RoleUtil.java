package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.domain.Role;
import com.platformcommons.platform.service.iam.dto.RoleDTO;

import java.util.Locale;

public class RoleUtil {

    public static RoleDTO role(String roleName) {
        String roleCode = roleName.toLowerCase(Locale.ROOT).replace("\\s", "_");
        return RoleDTO.builder()
                .code(roleCode)
                .roleName(roleName)
                .build();
    }

    public static RoleDTO createAdminRole(String tenantLogin) {
        return RoleDTO.builder()
                .code(adminRoleCode(tenantLogin))
                .roleName(IAMConstant.ADMIN_ROLE_NAME)
                .roleType(Role.RoleType.TENANT.name())
                .build();
    }

    public static String adminRoleCode(String tenantLogin) {
        return "role."+tenantLogin+".admin";
    }

}
