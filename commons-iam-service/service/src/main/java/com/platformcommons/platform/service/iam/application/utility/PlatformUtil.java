package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.iam.domain.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Objects;

@Component
@Slf4j
public class PlatformUtil {

    @Value("${commons.platform.cm-admin.appkey:Appkey YXBwS2V5OjdiMDNlMWJlLTkwMjctNDMwNC05YjRjLTlkMTdiZmI4NTM2NyxhcHBDb2RlOkNPTU1PTlNfQVBQLkNIQU5HRU1BS0VS}")
    private String adminAppKey;

    public String getAdminAppKey() {
        return adminAppKey;
    }

    public static  void validateTenantAdmin(){
        if(!PlatformSecurityUtil.isTenantAdmin()){
            throw  new UnAuthorizedAccessException("Only Tenant Admin can perform this operation");
        }
    }

    public static void   validateLoginTenantAndAdmin(String tenantLogin){
        if( !PlatformSecurityUtil.isPlatformAdmin() &&
                (!PlatformSecurityUtil.isTenantAdmin() ||
                        !tenantLogin.equals(PlatformSecurityUtil.getCurrentTenantLogin()))) {
                throw new UnAuthorizedAccessException("Only PlatformAdmin or created Tenant Can update");
            }

    }

    public static void validatePlatformAdmin() {
        if (!PlatformSecurityUtil.isPlatformAdmin()){
            throw new UnAuthorizedAccessException("Only Platform Admin can delete Tenant Meta Config");
        }
    }

    public static void validateLoginTenantAndAdminByTenantId(Long tenantId) {
        if (!PlatformSecurityUtil.isTenantAdmin() || !PlatformSecurityUtil.getCurrentTenantId().equals(tenantId)) {
            throw new UnAuthorizedAccessException("Only Tenant Admin Can update");
        }
    }

    public static void validateLoginTenantAndAdminByTenantLogin(String tenantLogin) {
        if (!PlatformSecurityUtil.isTenantAdmin() || !PlatformSecurityUtil.getCurrentTenantLogin().equals(tenantLogin)) {
            throw new UnAuthorizedAccessException("Only Tenant Admin Can update");
        }
    }

    public static  void validateIfTenantAdminOrPlatformAdmin(){
        if(!PlatformSecurityUtil.isTenantAdmin() &&  !PlatformSecurityUtil.isPlatformAdmin()){
            throw  new UnAuthorizedAccessException("Only Tenant Admin Or Platform Admin can perform this operation");
        }
    }

    public static void deactivateAnObject(NonMultiTenantBaseTransactionalEntity nonMultiTenantBaseTransactionalEntity,
                                          Boolean isActive, String reason) {
        if(Objects.equals(isActive,Boolean.FALSE)) {
            nonMultiTenantBaseTransactionalEntity.deactivate(reason);
        }
    }

    public static boolean isTenantAdmin(Long tenantId){
        return PlatformSecurityUtil.getCurrentTenantId().equals(tenantId)
                && PlatformSecurityUtil.isTenantAdmin();
    }

    public static boolean isSession() {
        try {
            return PlatformSecurityUtil.isSession();
        }
        catch(Exception e) {
            return Boolean.FALSE;
        }
    }

    public static void registerTransactionSynchronization(Runnable runnable) {
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