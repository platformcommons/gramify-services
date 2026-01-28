package com.platformcommons.platform.service.post.application.utility;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.post.application.PostTenantConfigService;
import com.platformcommons.platform.service.post.domain.PostTenantConfig;
import com.platformcommons.platform.service.post.facade.cache.validator.TenantMetaConfigValidator;
import feign.FeignException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Slf4j
@Transactional(noRollbackFor = FeignException.class)
public class PlatformUtil {

    @Getter
    @Value("${commons.platform.api-key}")
    private String platformApiKey;

    @Autowired
    private TenantMetaConfigValidator tenantMetaConfigValidator;

    public static  void validatePlatformAdmin(){
        if(!PlatformSecurityUtil.isPlatformAdmin()){
            throw  new UnAuthorizedAccessException("Only Platform Admin can perform this operation");
        }
    }

    public static  void validateIfTenantAdminOrPlatformAdmin(){
        if(!PlatformSecurityUtil.isTenantAdmin() &&  !PlatformSecurityUtil.isPlatformAdmin()){
            throw  new UnAuthorizedAccessException("Only Tenant Admin Or Platform Admin can perform this operation");
        }
    }

    public static  void validateTenantAdmin(){
        if(!PlatformSecurityUtil.isTenantAdmin()){
            throw  new UnAuthorizedAccessException("Only Tenant Admin can perform this operation");
        }
    }

    public Long getTenantIdByContext(String tenantLogin) {
        Long tenantId = null;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigValidator.getByTenantLogin(tenantLogin);
            tenantId = tenantMetaConfigDTO.getTenantId();
        }
        else {
            tenantId = PlatformSecurityUtil.getCurrentTenantId();
        }
        return tenantId;
    }

    public void registerTransactionSynchronization(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    // This code will execute only if the transaction commits successfully
                    try {
                        log.info("Transaction committed. Executing post-commit task.");
                        runnable.run();
                    } catch (Exception e) {
                        log.error("Error in post-commit task", e);
                    }
                }
            };
            TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
        }
    }
}
