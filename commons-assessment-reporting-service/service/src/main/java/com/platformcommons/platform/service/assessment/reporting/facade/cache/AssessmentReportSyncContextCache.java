package com.platformcommons.platform.service.assessment.reporting.facade.cache;

import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.cache.manager.CommonsCacheManager;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@ConditionalOnBean(name = "commonsCacheManager")
public class AssessmentReportSyncContextCache {

    @Value("${assessment-report-sync-context-cache.ttl:3600000}")
    private Long ttl;

    private static final String ASSESSMENT_SERVICE_ASSESSMENT_REPORT_SYNC_CONTEXT_CACHE_MAP = "ASSESSMENT_SERVICE_ASSESSMENT_REPORT_SYNC_CONTEXT_CACHE_MAP";
    private final CommonsCacheManager<String, AssessmentReportSyncContext> platformCacheClient;

    private CachedMap<String, AssessmentReportSyncContext> getSectionQuestionResponseCachedMap() {
        return this.platformCacheClient.getCache(ASSESSMENT_SERVICE_ASSESSMENT_REPORT_SYNC_CONTEXT_CACHE_MAP);
    }

    public void add(AssessmentReportSyncContext syncContext, Long instanceId) {
        if (null == syncContext) return;
        getSectionQuestionResponseCachedMap()
                .put(getKey(instanceId),syncContext,ttl);
    }

    public AssessmentReportSyncContext get(Long id) {
        return getSectionQuestionResponseCachedMap()
                .get(getKey(id));
    }

    public boolean contains(Long id) {
        return getSectionQuestionResponseCachedMap()
                .containsKey(getKey(id));
    }

    public void evict(Long id) {
        if(id!=null) {
            getSectionQuestionResponseCachedMap()
                    .remove(getKey(id));
        }
        else{
            getSectionQuestionResponseCachedMap()
                    .removeAll();
        }
    }

    private String getKey(Long id){
        return String.format("%s-%s",id, EventContext.getEvent());
    }
}
