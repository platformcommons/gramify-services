package com.platformcommons.platform.service.search.application.utility;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;

public class PlatformUtil {

    public static  void validateIfTenantAdminOrPlatformAdmin(){
        if(!PlatformSecurityUtil.isTenantAdmin() &&  !PlatformSecurityUtil.isPlatformAdmin()){
            throw  new UnAuthorizedAccessException("Only Tenant Admin Or Platform Admin can perform this operation");
        }
    }

    public static boolean isSession() {
        try {
            return PlatformSecurityUtil.isSession();
        }
        catch(Exception e) {
            return Boolean.FALSE;
        }
    }
}
