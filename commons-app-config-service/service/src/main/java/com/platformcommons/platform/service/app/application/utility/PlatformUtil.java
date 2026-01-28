package com.platformcommons.platform.service.app.application.utility;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import java.util.Objects;

public class PlatformUtil {

    public static  void validateTenantAdmin(){
        if(!PlatformSecurityUtil.isTenantAdmin()){
            throw  new UnAuthorizedAccessException("Only Tenant Admin can perform this operation");
        }
    }

    public static void   validateLoginTenant(Long createdTenantId){
        if( !(PlatformSecurityUtil.isPlatformAdmin()) ){
            if(!createdTenantId.equals(PlatformSecurityUtil.getCurrentTenantId())) {
                throw new UnAuthorizedAccessException("Only PlatformAdmin or created Tenant Can update");
            }
        }
    }

    public static void   validateLoginTenantAndAdmin(Long tenantId){
        if( !PlatformSecurityUtil.isPlatformAdmin() ){
            if(!PlatformSecurityUtil.isTenantAdmin() || !tenantId.equals(PlatformSecurityUtil.getCurrentTenantId())) {
                throw new UnAuthorizedAccessException("Only PlatformAdmin or created Tenant Can update");
            }
        }
    }

    public static  void validateIfTenantAdminOrPlatformAdmin(){
        if(!PlatformSecurityUtil.isTenantAdmin() &&  !PlatformSecurityUtil.isPlatformAdmin()){
            throw  new UnAuthorizedAccessException("Only Tenant Admin Or Platform Admin can perform this operation");
        }
    }

    public static  void validatePlatformAdmin(){
        if(!PlatformSecurityUtil.isPlatformAdmin()){
            throw  new UnAuthorizedAccessException("Only Platform Admin can perform this operation");
        }
    }

    public static void deactivateAnObject(NonMultiTenantBaseTransactionalEntity persistedObject, Boolean isActive, String reason) {
        if(Objects.equals(isActive,Boolean.FALSE)) {
            persistedObject.deactivate(reason);
        }
    }

    public static void deactivateAnObject(BaseTransactionalEntity persistedObject, Boolean isActive, String reason) {
        if(Objects.equals(isActive,Boolean.FALSE)) {
            persistedObject.deactivate(reason);
        }
    }
}
