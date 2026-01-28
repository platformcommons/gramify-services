package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;

import java.util.Optional;
import java.util.Map;
import java.util.Set;

public interface TenantMetaConfigFacade {

    TenantMetaConfigDTO getTenantMetaConfig(Long tenantId, String tenantLogin);

    Optional<TenantMetaConfigDTO> getTenantMetaConfigOptional(Long tenantId, String tenantLogin);

    void addTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalPropertyDTO> body);

    void patchTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalPropertyDTO> body);

    TenantMetaConfigDTO patchTenantMetaConfigForLoggedInTenant(TenantMetaConfigDTO tenantMetaConfigDTO );

    Long postTenantMetaConfigForLoggedInTenant(TenantMetaConfigDTO tenantMetaConfigDTO );

    void deleteTenantMetaConfig(Long id,String reason);

    TenantMetaConfigDTO getById(Long id);

    TenantMetaConfigDTO getForLoggedInTenant();

    TenantMetaConfigDTO getByTenantLoginFromCacheOrDB(String tenantLogin);

    void deleteAllTenantMetaConfigCache();

    TenantMetaConfigDTO getTenantMetaConfigByTenantLoginAndLoadCache(String tenantLogin);

    void deleteTenantMetaConfigCacheByTenantLogin(String tenantLogin);

    String getMetaPropertyValueByMetaKeyAndHierarchy(String metaKey, String tenantLogin,String appContext);

    TenantMetaAdditionalPropertyDTO getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(String metaKey, String tenantLogin,String appContext);

    Set<String> getMetaValueForMultiValuedMetaByConfigDTO(TenantMetaConfigDTO tenantMetaConfigDTO, String metaKey, String appContext);

    void createTenantMetaConfigOnTenantSignupVMS(TenantDTO tenantDTO, String appContext);

    PageDTO<TenantMetaConfigDTO> getAllTenantMetaConfig(String metaKey, String metaValue, Integer page, Integer size, String sortBy, String direction);

    Set<TenantMetaConfigDTO> getAllTenantMetaConfigSet(String metaKey, String metaValue);

    Set<TenantMetaAdditionalPropertyDTO> getTenantMetaAdditionalPropertiesByMetaKeysAndTenantLogin(Set<String> metaKeys,
                                                                                                   String tenantLogin,String appContext);
}
