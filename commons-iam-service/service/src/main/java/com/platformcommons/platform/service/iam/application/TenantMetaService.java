package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.TenantMeta;

public interface TenantMetaService {

    void addTenantMetaData(String tenantLogin, TenantMeta tenantMeta);

    TenantMeta getTenantMetaData(String tenantLogin, String metaCode);

    String getMetaData(String code, Long tenantId);
}
