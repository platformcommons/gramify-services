package com.platformcommons.platform.service.assessment.reporting.facade.cache.manager;

import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.service.assessment.dto.CachedSectionQuestionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@ConditionalOnBean(name = "commonsCacheManager")
public class SectionQuestionResponseCacheManager {

    private static final String ASSESSMENT_SERVICE_SECTION_QUESTION_RESPONSE_CACHE_MAP = "ASSESSMENT_SERVICE_SECTION_QUESTION_RESPONSE_CACHE_MAP";
    @Autowired
    private CommonsCacheManager<Long,Map<Long,CachedSectionQuestionResponse>> platformCacheClient;

    private CachedMap<Long, Map<Long, CachedSectionQuestionResponse>> get() {
        return this.platformCacheClient.getCache(ASSESSMENT_SERVICE_SECTION_QUESTION_RESPONSE_CACHE_MAP);
    }

    public void add(Map<Long,CachedSectionQuestionResponse> sectionQuestionResponse, Long assessmentId) {
        if (null == sectionQuestionResponse || sectionQuestionResponse.isEmpty()) return;
        get().put(assessmentId,sectionQuestionResponse);
    }

    public Map<Long, CachedSectionQuestionResponse> get(Long id) {
        return get().get(id);
    }

    public boolean contains(Long cacheCode) {
        return get().containsKey(cacheCode);
    }

    public void evict(Long id) {
        if(id!=null) get().remove(id);
        else         get().removeAll();

    }

    public void evictAndRebuildCache(Long instanceId, Map<Long, CachedSectionQuestionResponse> cachedSectionQuestionResponse) {
        get().put(instanceId,cachedSectionQuestionResponse);
    }
}