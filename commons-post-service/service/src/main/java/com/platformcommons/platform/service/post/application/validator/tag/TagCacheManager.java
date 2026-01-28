package com.platformcommons.platform.service.post.application.validator.tag;


import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.domain.client.TagClientV2;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteClientDisconnectedException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
@ConditionalOnBean(CommonsCacheManager.class)
@RequiredArgsConstructor
@Slf4j
public class TagCacheManager {

    public static final String COMMONS_DOMAIN_TAG_CACHE = "COMMONS_DOMAIN_SERVICE_TAG";
    private static final Long CACHE_TAG_DATA_TTL = 360000L;


    private final TagClientV2 tagClient;
    private final CommonsCacheManager<String, TagDTO> cacheManagerForTag;



    private CachedMap<String, TagDTO> getCache() {
        log.debug("--- Get Tag CacheMap");
        return this.cacheManagerForTag.getCache(COMMONS_DOMAIN_TAG_CACHE, CACHE_TAG_DATA_TTL);
    }

    private void setDataToCache(CachedMap<String, TagDTO> cachedMap, TagDTO value) {
        log.debug(String.format("-> setDataToCache %s ", value));
        cachedMap.put(value.getCode(), value, CACHE_TAG_DATA_TTL);
    }

    public TagDTO getValueForKey(String code) {
        log.debug(String.format("<-- Getting  value for code  %s  from cache", code));
        TagDTO tag;
        try {
            CachedMap<String, TagDTO> cachedMap = this.getCache();
            tag = (TagDTO) cachedMap.get(code);
            if (null == tag) {
                tag = this.getTagFromClient(code);
                this.setDataToCache(cachedMap, tag);
            }
        } catch (IgniteClientDisconnectedException var4) {
            tag = this.getTagFromClient(code);
        }
        log.debug(String.format("<-- Got  value for code  %s ", tag.getCode()));
        return tag;
    }

    private TagDTO getTagFromClient(String code){
        try {
            Set<TagDTO> tagDTOPage = tagClient.getTagByCodes(Collections.singleton(code)).getBody();
            if(tagDTOPage==null){
               throw  new RuntimeException("Tag is Null or empty");
            }
            return tagDTOPage.stream().findFirst()
                    .orElseThrow(()-> new NotFoundException(String.format("Tag with code %s not found",code)));

        }catch (FeignException feignException){
            throw  new RuntimeException(feignException.getMessage());
        }
    }

}
