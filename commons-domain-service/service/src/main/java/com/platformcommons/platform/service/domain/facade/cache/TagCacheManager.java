package com.platformcommons.platform.service.domain.facade.cache;

import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.facade.TagFacade;
import com.platformcommons.platform.service.facade.cache.RefDataCacheManager;
import org.apache.ignite.IgniteClientDisconnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@ConditionalOnProperty(
        name = {"commons.platform.cache.enabled"},
        matchIfMissing = false
)
public class TagCacheManager {

    @Value("${spring.application.name}")
    private String serviceName;
    private static final Logger LOG = LoggerFactory.getLogger(RefDataCacheManager.class);
    private static final Long CACHE_REF_DATA_TTL = 360000L;

    @Autowired
    private TagFacade tagFacade;
    @Autowired
    private CommonsCacheManager<String, TagDTO> cacheManagerForTag;

    public TagCacheManager() {
    }

    private CachedMap<String, TagDTO> getCache() {
        LOG.debug("--- Get Tag CacheMap");
        return this.cacheManagerForTag.getCache(this.serviceName + "_domain_tag", CACHE_REF_DATA_TTL);
    }

    private void setDataToCache(CachedMap<String, TagDTO> cachedMap, TagDTO value) {
        LOG.debug(String.format("-> setDataToCache %s ", value));
        cachedMap.put(value.getCode(), value, CACHE_REF_DATA_TTL);
    }

    public TagDTO getValueForKey(String code) {
        LOG.debug(String.format("<-- Getting  value for code  %s  from cache", code));

        TagDTO tag;
        try {
            CachedMap<String, TagDTO> cachedMap = this.getCache();
            tag = (TagDTO) cachedMap.get(code);
            if (null == tag) {
                tag = this.getTagFromDB(code);
                this.setDataToCache(cachedMap, tag);
            }
        } catch (IgniteClientDisconnectedException var4) {
            tag = this.getTagFromDB(code);
        }

        LOG.debug(String.format("<-- Got  value for code  %s ", tag.getCode()));
        return tag;
    }

    private @NotNull TagDTO getTagFromDB(String code) {
        LOG.debug(String.format("<-- Getting value for code  %s  from cache", code));
        return this.tagFacade.getTagByCode(code);
    }

    public void clearCache(String key) {
        if (key != null) {
            this.getCache().remove(key);
        } else {
            this.getCache().removeAll();
        }
    }

}
