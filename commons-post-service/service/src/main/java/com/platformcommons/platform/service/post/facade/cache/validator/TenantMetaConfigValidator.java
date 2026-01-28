package com.platformcommons.platform.service.post.facade.cache.validator;

import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.post.application.utility.PlatformUtil;
import com.platformcommons.platform.service.post.facade.client.CommonsIamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Slf4j
public class TenantMetaConfigValidator {

    private static final Long CACHE_TENANT_META_CONFIG_TTL = 360000L;

    @Autowired(required = false)
    private CommonsCacheManager<String, TenantMetaConfigDTO> cacheManagerForTenantMetaConfig;

    @Autowired
    private CommonsIamClient commonsIamClient;

    @Autowired
    @Lazy
    private PlatformUtil platformUtil;

    public TenantMetaConfigDTO getByTenantLogin(String tenantLogin) {
        TenantMetaConfigDTO tenantMetaConfigDTO = null;
        if(tenantLogin != null) {
            if (cacheManagerForTenantMetaConfig != null) {
                log.debug("-->>> TenantMetaConfig : Getting TenantMetaConfig from cache..");
                tenantMetaConfigDTO = cacheManagerForTenantMetaConfig.getCache("tenant_meta_config_iam", CACHE_TENANT_META_CONFIG_TTL)
                        .get(tenantLogin);
                if (tenantMetaConfigDTO == null) {
                    tenantMetaConfigDTO = getTenantMetaConfigDTOFromFeignCall(tenantLogin);
                }
            }
            else{
                tenantMetaConfigDTO = getTenantMetaConfigDTOFromFeignCall(tenantLogin);
            }
        }
        return tenantMetaConfigDTO;
    }

    private  TenantMetaConfigDTO getTenantMetaConfigDTOFromFeignCall(String tenantLogin) {
        log.debug("-->>> TenantMetaConfig : Not in Cache !!  Getting TenantMetaConfig from feign call..");
        return commonsIamClient.getTenantMetaConfigByTenantLoginAndLoadCache(tenantLogin,platformUtil.getPlatformApiKey()).getBody();
    }
}