package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.TenantMetaAdditionalProperty;
import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface TenantMetaConfigService {

    void save(TenantMetaConfig tenantMetaConfig);

    TenantMetaConfig getTenantMetaConfig(Long tenantId, String tenantLogin);

    Optional<TenantMetaConfig> getTenantMetaConfigOptional(Long tenantId, String tenantLogin);

    TenantMetaConfig getById(Long id);

    void addTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalProperty> tenantMetaAdditionalProperties);

    void patchTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalProperty> tenantMetaAdditionalProperties);

    TenantMetaConfig patchTenantMetaConfigForLoggedInTenant(TenantMetaConfig tenantMetaConfig);

    TenantMetaConfig postTenantMetaConfigForLoggedInTenant(TenantMetaConfig tenantMetaConfig);

    TenantMetaConfig deleteTenantMetaConfig(Long id, String reason);

    Page<TenantMetaConfig> getAllTenantMetaConfig(String metaKey, String metaValue, Pageable pageable);

    Set<TenantMetaAdditionalProperty> getTenantMetaAdditionalPropertiesByMetaKeysAndTenantLogin(Set<String> metaKeys, String tenantLogin, String appContext, String defaultAppContext);

    Set<TenantMetaConfig> getAllTenantMetaConfigSet(String metaKey, String metaValue);
}
