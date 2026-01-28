package com.platformcommons.platform.service.assessment.application.cache;

import com.platformcommons.platform.cache.CacheUtil;
import com.platformcommons.platform.cache.CachedMap;
import com.platformcommons.platform.exception.generic.DataAccessException;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.utility.SecurityContextUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentContextCachedDTO;
import com.platformcommons.platform.service.assessment.facade.AssessmentFacade;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceFacade;
import com.platformcommons.platform.service.assessment.facade.AssessmentQuestionPaperFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssessmentContextCacheManger {

    private static final int TIMEOUT_SECONDS = 120;
    @Value("${commons.platform.service.commons-assessment-service.cache.assessment-context.ttl:600}")
    private long cacheTTL;
    private final String ASSESSMENT_CONTEXT_CACHE_NAME = "COMMONS_ASSESSMENT_SERVICE.ASSESSMENT_CONTEXT";
    private final Map<String, CompletableFuture<AssessmentContextCachedDTO>> ongoingUpdates = new ConcurrentHashMap<>();

    private final Optional<CacheUtil<String, AssessmentContextCachedDTO>> assessmentContextCache;

    private AssessmentQuestionPaperFacade assessmentQuestionPaperFacade;
    private AssessmentInstanceFacade assessmentInstanceFacade;

    private Optional<CachedMap<String, AssessmentContextCachedDTO>> getCache() {
        return this.assessmentContextCache.flatMap(cache -> Optional.ofNullable(cache.getMap(ASSESSMENT_CONTEXT_CACHE_NAME, this.cacheTTL)));
    }

    public AssessmentContextCachedDTO getAssessmentContext(Long assessmentInstanceId) {
        try {
            CompletableFuture<AssessmentContextCachedDTO> future = ongoingUpdates.get(assessmentInstanceId.toString());
            if (future != null) {
                return future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            }

            if (!getCache().isPresent()) {
                return getCacheDTOInternal(assessmentInstanceId);
            } else {
                if (getCache().get().containsKey(assessmentInstanceId.toString())) {
                    return getCache().get().get(assessmentInstanceId.toString());
                } else {
                    return addToCacheInternal(assessmentInstanceId)
                            .thenApplyAsync(contextCachedDTO -> {
                                ongoingUpdates.remove(assessmentInstanceId.toString());
                                return contextCachedDTO;
                            })
                            .get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
                }
            }
        } catch (TimeoutException e) {
            log.error("Cache operation timed out for assessment {}", assessmentInstanceId);
            throw new DataAccessException("Cached Operation timed out");
        } catch (Exception e) {
            log.error("Error fetching assessment context from cache for assessment {}", assessmentInstanceId, e);
            throw new DataAccessException("Something went wrong");
        }
    }

    public void addToCache(Long assessmentInstanceId) {
        if (getCache().isPresent()) {
            addToCacheInternal(assessmentInstanceId)
                    .thenRun(() -> ongoingUpdates.remove(assessmentInstanceId.toString()));
        }
    }

    public void addToCacheByAssessmentId(Long assessmentId) {
        if (getCache().isPresent()) {
            Long instanceId = assessmentInstanceFacade.getAssessmentInstanceIdByAssessmentId(assessmentId);
            if(instanceId!=null) addToCache(instanceId);
        }
    }

    @Async
    public CompletableFuture<AssessmentContextCachedDTO> addToCacheInternal(Long assessmentInstanceId) {
        CompletableFuture<AssessmentContextCachedDTO> existingUpdate = ongoingUpdates.get(assessmentInstanceId.toString());
        if (existingUpdate != null) {
            return existingUpdate;
        }
        CompletableFuture<AssessmentContextCachedDTO> future = SecurityContextUtil.runWithContext(() -> {
            try {
                getCache()
                        .ifPresent(cache -> {
                            AssessmentContextCachedDTO contextCachedDTO = getCacheDTOInternal(assessmentInstanceId);
                            cache.put(assessmentInstanceId.toString(), contextCachedDTO);
                        });
                return getCache().map(cache -> cache.get(assessmentInstanceId.toString())).orElse(null);
            } catch (Exception e) {
                ongoingUpdates.remove(assessmentInstanceId.toString());
                log.error("Error adding assessment context to cache -> ", e);
                throw new DataAccessException("Failed adding data to cache");
            }
        });
        ongoingUpdates.put(assessmentInstanceId.toString(), future);
        return future;
    }

    private AssessmentContextCachedDTO getCacheDTOInternal(Long assessmentInstanceId) {
        return assessmentInstanceFacade.getContextCache(assessmentInstanceId);
    }

    @PreDestroy
    public void cleanup() {
        ongoingUpdates.forEach((key, future) -> {
            if (!future.isDone()) {
                future.cancel(true);
            }
        });
        ongoingUpdates.clear();
    }


    public void addToCacheByQuestionId(Long id) {
        if (getCache().isPresent()) {
            PlatformToken token = PlatformSecurityUtil.getContext();
            getAssessmentInstanceIdQuestionId(id)
                    .thenAccept(instanceId -> {
                        try {
                            SecurityContextHolder.getContext().setAuthentication(token);
                            addToCache(instanceId);
                        } finally {
                            SecurityContextHolder.clearContext();
                        }
                    });
        }
    }

    @Async
    public CompletableFuture<Long> getAssessmentInstanceIdQuestionId(Long id) {
        return SecurityContextUtil.runWithContext(() -> assessmentQuestionPaperFacade.getAssessmentInstanceIdByQuestionId(id));
    }

    public void evictCacheContext(Long assessmentInstanceId) {
        getCache().ifPresent(cache->{
            if(assessmentInstanceId==null){
                cache.removeAll();
            }
            else{
                cache.remove(assessmentInstanceId.toString());
            }
        });
    }

    @Autowired
    @Lazy
    public void setAssessmentQuestionPaperFacade(AssessmentQuestionPaperFacade assessmentQuestionPaperFacade) {
        this.assessmentQuestionPaperFacade = assessmentQuestionPaperFacade;
    }


    @Autowired
    @Lazy
    public void setAssessmentInstanceFacade(AssessmentInstanceFacade assessmentInstanceFacade) {
        this.assessmentInstanceFacade = assessmentInstanceFacade;
    }
}
