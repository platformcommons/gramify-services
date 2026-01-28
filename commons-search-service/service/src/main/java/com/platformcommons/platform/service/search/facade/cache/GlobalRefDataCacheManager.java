package com.platformcommons.platform.service.search.facade.cache;

import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.service.search.dto.GlobalRefDataDTO;
import com.platformcommons.platform.service.search.facade.client.utility.CommonsReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;


@Component
@ConditionalOnProperty(name = "commons.platform.cache.enabled",havingValue = "true")
public class GlobalRefDataCacheManager {

    @Autowired
    private CommonsReportUtil commonsReportUtil;
    private static final Logger LOG = LoggerFactory.getLogger(GlobalRefDataCacheManager.class);
    private static final Long CACHE_REF_DATA_TTL = 36000 * 10L;

    @Autowired
    private CommonsCacheManager<String,GlobalRefDataDTO> cacheManagerForRefData;

    private CachedMap<String,GlobalRefDataDTO> getCache() {
        LOG.debug("--- Get RefData CacheMap");
        return cacheManagerForRefData.getCache("tld_global_ref_data",CACHE_REF_DATA_TTL);
    }

    private void setDataToCache(CachedMap<String,GlobalRefDataDTO> cachedMap, GlobalRefDataDTO value) {
        LOG.debug(String.format("-> setDataToCache %s ", value));
        cachedMap.put(value.getDataCode(), value, CACHE_REF_DATA_TTL);
    }

    public GlobalRefDataDTO getValueForKey(String code) {
        LOG.debug(String.format("<-- Getting ref data value for code  %s  from cache", code));
        GlobalRefDataDTO refData;
        try {
            CachedMap<String,GlobalRefDataDTO> cachedMap = getCache();
            refData = cachedMap.get(code);
            if (null == refData) {
                refData = getRefDataFromDB(code);
                setDataToCache(cachedMap, refData);
            }
        } catch (org.apache.ignite.IgniteClientDisconnectedException ex) {
            refData = getRefDataFromDB(code);
        }
        LOG.debug(String.format("<-- Got ref data value for code  %s ", refData.getDataCode()));
        return refData;
    }

    public GlobalRefDataDTO getValueForKey(CachedMap<String,GlobalRefDataDTO> cachedMap,String code) {
        LOG.debug(String.format("<-- Getting ref data value for code  %s  from cache", code));
        GlobalRefDataDTO refData;
        try {
            refData = cachedMap.get(code);
            if (null == refData) {
                refData = getRefDataFromDB(code);
                setDataToCache(cachedMap, refData);
            }
        } catch (org.apache.ignite.IgniteClientDisconnectedException ex) {
            refData = getRefDataFromDB(code);
        }
        LOG.debug(String.format("<-- Got ref data value for code  %s ", refData.getDataCode()));
        return refData;
    }

    public GlobalRefDataDTO getRefDataFromDB(String code) {
        LOG.debug(String.format("<-- Getting ref data value for code  %s  from cache", code));
        return commonsReportUtil.getGlobalRefDataFromCode(code);
    }

    public List<GlobalRefDataDTO> getValuesInBulk(Set<String> codes) {
        List<GlobalRefDataDTO> globalRefDataDTOList = new ArrayList<>();
        CachedMap<String,GlobalRefDataDTO> cachedMap = getCache();
        for(String code : codes) {
            GlobalRefDataDTO globalRefDataDTO = getValueForKey(cachedMap,code);
            globalRefDataDTOList.add(globalRefDataDTO);
        }
        return globalRefDataDTOList;
    }
    public void  clearCache(String key){
        if(key!=null){
            this.getCache().remove(key);
        }
        else {
            this.getCache().removeAll();
        }
    }

    public void clearAllCache() {
        getCache().removeAll();
        LOG.debug("Cleared All Cache Of RefData");
    }
}