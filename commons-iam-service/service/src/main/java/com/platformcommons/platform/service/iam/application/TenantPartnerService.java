package com.platformcommons.platform.service.iam.application;

public interface TenantPartnerService {
    Long addTenantPartner(Long currentTenantId, Long parentTenantId, String metaValue, Boolean isPrimary);
    Long getTenantPartner(Long tenantId, Long parentTenantId);
}
