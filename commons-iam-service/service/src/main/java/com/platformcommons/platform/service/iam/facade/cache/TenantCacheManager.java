package com.platformcommons.platform.service.iam.facade.cache;

import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.facade.TenantFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteClientDisconnectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = {"commons.platform.cache.enabled"},
        havingValue = "true",
        matchIfMissing = false
)
@Slf4j
public class TenantCacheManager {

    private static final Long CACHE_TENANT_TTL = 360000L;

    @Autowired
    private CommonsCacheManager<Long, TenantDTO> cacheManagerForTenant;

    @Autowired
    private TenantFacade tenantFacade;

    private CachedMap<Long, TenantDTO> getCache() {
        log.debug("--- Get Tenant CacheMap");
        return cacheManagerForTenant.getCache("tenant_iam", CACHE_TENANT_TTL);
    }

    private void setDataToCache(CachedMap<Long, TenantDTO> cachedMap, Long key, TenantDTO value) {
        log.debug(String.format("-> Set Tenant %s ", value));
        cachedMap.put(key, value, CACHE_TENANT_TTL);
    }

    public TenantDTO getValueForKey(Long key) {
        log.debug(String.format("<-- Getting Tenant value for key %s from cache", key));

        TenantDTO tenantDTO;
        try {
            CachedMap<Long, TenantDTO> cachedMap = this.getCache();
            tenantDTO = cachedMap.get(key);
            if (tenantDTO == null) {
                tenantDTO = getTenantFromDB(key);
                if (tenantDTO != null) {
                    this.setDataToCache(cachedMap, key, tenantDTO);
                }
            }
        } catch (IgniteClientDisconnectedException var4) {
            tenantDTO = this.getTenantFromDB(key);
        }

        log.debug(String.format("<-- Got Tenant for key %s", key));
        return tenantDTO;
    }


    private TenantDTO getTenantFromDB(Long key) {
        log.debug(String.format("<-- Getting Tenant for tenantId %s from DB", key));
        return tenantFacade.getTenantByIdOptional(key).orElse(null);
    }

    public void setValueToCache(TenantDTO tenantDTO) {
        try {
            if (tenantDTO != null) {
                CachedMap<Long, TenantDTO> cachedMap = getCache();
                setDataToCache(cachedMap, tenantDTO.getId(), tenantDTO);
            }
        } catch (IgniteClientDisconnectedException var4) {
            log.debug(var4.getMessage());
        }
    }

    public void clearCache(Long key) {
        if (key != null) {
            this.getCache().remove(key);
        } else {
            this.getCache().removeAll();
        }
    }

    public void clearAllCache() {
        this.getCache().removeAll();
    }
}
