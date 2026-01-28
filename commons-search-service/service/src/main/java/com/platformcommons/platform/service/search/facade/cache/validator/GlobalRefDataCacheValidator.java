package com.platformcommons.platform.service.search.facade.cache.validator;

import com.platformcommons.platform.service.search.dto.GlobalRefDataDTO;
import com.platformcommons.platform.service.search.facade.cache.GlobalRefDataCacheManager;
import com.platformcommons.platform.service.search.facade.client.utility.CommonsReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Slf4j
public class GlobalRefDataCacheValidator {

    @Autowired(required = false)
    private GlobalRefDataCacheManager cacheManager;

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    public GlobalRefDataDTO getValueForCode(String code){
        GlobalRefDataDTO globalRefDataDTO = null;
        if(code != null) {
            if(cacheManager != null) {
                globalRefDataDTO = cacheManager.getValueForKey(code);
            }
            else {
                globalRefDataDTO = commonsReportUtil.getGlobalRefDataFromCode(code);
            }
        }
        return globalRefDataDTO;
    }
}
