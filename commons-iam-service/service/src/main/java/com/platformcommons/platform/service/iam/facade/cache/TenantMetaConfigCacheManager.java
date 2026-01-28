package com.platformcommons.platform.service.iam.facade.cache;

import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteClientDisconnectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@ConditionalOnProperty(
        name = {"commons.platform.cache.enabled"},
        havingValue = "true",
        matchIfMissing = false
)
@Slf4j
public class TenantMetaConfigCacheManager {

    private static final Long CACHE_TENANT_META_CONFIG_TTL = 360000L;

    @Autowired
    private CommonsCacheManager<String, TenantMetaConfigDTO> cacheManagerForTenantMetaConfig;

    @Autowired
    private TenantMetaConfigFacade tenantMetaConfigFacade;

    private CachedMap<String, TenantMetaConfigDTO> getCache() {
        log.debug("--- Get Tenant Meta Config CacheMap");
        return cacheManagerForTenantMetaConfig.getCache("tenant_meta_config_iam", CACHE_TENANT_META_CONFIG_TTL);
    }

    private void setDataToCache(CachedMap<String, TenantMetaConfigDTO> cachedMap, String key, TenantMetaConfigDTO value) {
        log.debug(String.format("-> Set Tenant Meta Config %s ", value));
        cachedMap.put(key, value, CACHE_TENANT_META_CONFIG_TTL);
    }

    public TenantMetaConfigDTO getValueForKey(String tenantLogin) {
        log.debug(String.format("<-- Getting Tenant Meta Config value for tenantLogin %s from cache", tenantLogin));

        TenantMetaConfigDTO tenantMetaConfigDTO;
        try {
            CachedMap<String, TenantMetaConfigDTO> cachedMap = this.getCache();
            tenantMetaConfigDTO = cachedMap.get(tenantLogin);
            if (tenantMetaConfigDTO == null) {
                tenantMetaConfigDTO = getTenantMetaConfigFromDB(tenantLogin);
                if (tenantMetaConfigDTO != null) {
                    this.setDataToCache(cachedMap, tenantLogin, tenantMetaConfigDTO);
                }
            }
        } catch (IgniteClientDisconnectedException var4) {
            tenantMetaConfigDTO = this.getTenantMetaConfigFromDB(tenantLogin);
        }

        log.debug(String.format("<-- Got Tenant Meta Config for tenantLogin %s", tenantLogin));
        return tenantMetaConfigDTO;
    }

    public TenantMetaConfigDTO getValueFromDBAndPutIntoCache(String tenantLogin) {
        log.debug(String.format("<-- Getting Tenant Meta Config value for tenantLogin %s from Database and loading into cache", tenantLogin));
        TenantMetaConfigDTO tenantMetaConfigDTO = getTenantMetaConfigFromDB(tenantLogin);
        try {
            if (tenantMetaConfigDTO != null) {
                CachedMap<String, TenantMetaConfigDTO> cachedMap = this.getCache();
                setDataToCache(cachedMap, tenantLogin, tenantMetaConfigDTO);
            }
        } catch (IgniteClientDisconnectedException var4) {
            log.debug(var4.getMessage());
        }

        log.debug(String.format("<-- Got Tenant Meta Config for tenantLogin %s", tenantLogin));
        return tenantMetaConfigDTO;
    }

    public void setValueToCache(TenantMetaConfigDTO tenantMetaConfigDTO) {
        try {
            if (tenantMetaConfigDTO != null) {
                CachedMap<String, TenantMetaConfigDTO> cachedMap = this.getCache();
                setDataToCache(cachedMap, tenantMetaConfigDTO.getTenantLogin(), tenantMetaConfigDTO);
            }
        } catch (IgniteClientDisconnectedException var4) {
            log.debug(var4.getMessage());
        }
    }

    private TenantMetaConfigDTO getTenantMetaConfigFromDB(String tenantLogin) {
        log.debug(String.format("<-- Getting Tenant Meta Config for tenantLogin %s from DB", tenantLogin));
        return tenantMetaConfigFacade.getTenantMetaConfigOptional(null,tenantLogin).orElse(null);
    }

    public void clearCache(String tenantLogin) {
        if (tenantLogin != null) {
            this.getCache().remove(tenantLogin);
        } else {
            this.getCache().removeAll();
        }
    }

    public void clearAllCache() {
        this.getCache().removeAll();
    }

}
