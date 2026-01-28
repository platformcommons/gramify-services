package com.platformcommons.platform.service.assessment.reporting.facade.cache;

import com.platformcommons.platform.service.assessment.dto.CachedSectionQuestionResponse;
import com.platformcommons.platform.service.assessment.dto.OptionResponseDTO;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionResponseDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.OptionsDimFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.cache.manager.SectionQuestionResponseCacheManager;
import lombok.Synchronized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SectionQuestionResponseCache {

    private final OptionsDimFacade facade;


    private final Optional<SectionQuestionResponseCacheManager> cacheManager;

    public SectionQuestionResponseCache(@Autowired(required = false) SectionQuestionResponseCacheManager cacheManager,@Autowired OptionsDimFacade facade){
        this.cacheManager = cacheManager==null?Optional.empty():Optional.of(cacheManager);
        this.facade = facade;
    }

    ThreadLocal<Boolean> responseFromDB = ThreadLocal.withInitial(()->false);

    private Map<Long, CachedSectionQuestionResponse> getCachedSectionQuestionResponse(Long assessmentInstanceID) {
        Map<Long, CachedSectionQuestionResponse> cachedSectionQuestionResponse;
        if (cacheManager.isPresent() && cacheManager.get().contains(assessmentInstanceID)) {
            cachedSectionQuestionResponse = cacheManager.get().get(assessmentInstanceID);
        } else {
            responseFromDB.set(true);
            cachedSectionQuestionResponse = facade.getOptionResponseByAssessmentId(assessmentInstanceID);
            cacheManager.ifPresent(sectionQuestionResponseCacheManager -> sectionQuestionResponseCacheManager.add(cachedSectionQuestionResponse, assessmentInstanceID));
        }
        return cachedSectionQuestionResponse;
    }

    public void evictCache(Long id) {
        cacheManager.ifPresent(responseCacheManager -> responseCacheManager.evict(id));
    }

    @Synchronized
    public void updateResponses(List<SectionQuestionResponseDTO> responses, Long instanceId) {

        Map<Long, CachedSectionQuestionResponse> cachedSectionQuestionResponse = getCachedSectionQuestionResponse(instanceId);
        if(responseFromDB.get()) return;
        for (SectionQuestionResponseDTO response : responses) {
            if(cachedSectionQuestionResponse.containsKey(response.getQuestionId())) {
                cachedSectionQuestionResponse.get(response.getQuestionId())
                        .increaseOptionResponseCount(response.getOptionResponseDTOList());
            }
            else {
                cachedSectionQuestionResponse.put(response.getQuestionId(),
                                                  CachedSectionQuestionResponse.builder()
                                                     .assessmentInstanceId(response.getAssessmentInstanceId())
                                                     .sectionQuestionId(response.getSectionQuestionId())
                                                     .questionId(response.getQuestionId())
                                                     .childQuestion(response.getChildQuestion())
                                                     .optionresponse(
                                                             response.getOptionResponseDTOList().stream()
                                                                     .collect(Collectors.toMap(OptionResponseDTO::getOptionId,OptionResponseDTO::getResponseCount))
                                                     )
                                                     .build()
                );
            }
        }
        cacheManager.ifPresent(responseCacheManager -> responseCacheManager.evictAndRebuildCache(instanceId,cachedSectionQuestionResponse));

    }

    public List<SectionQuestionResponseDTO> getResponses(Long instanceId) {
        return getCachedSectionQuestionResponse(instanceId)
                .values()
                .stream()
                .map(cachedResponses ->
                        SectionQuestionResponseDTO.builder()
                        .assessmentInstanceId(cachedResponses.getAssessmentInstanceId())
                        .sectionQuestionId(cachedResponses.getSectionQuestionId())
                        .questionId(cachedResponses.getQuestionId())
                        .childQuestion(cachedResponses.getChildQuestion())
                        .optionResponseDTOList(
                            cachedResponses.getOptionResponse()
                                    .entrySet()
                                    .stream()
                                    .map(entry -> OptionResponseDTO.builder()
                                                        .optionId(entry.getKey())
                                                        .responseCount(entry.getValue())
                                                        .build())
                                    .collect(Collectors.toList())
                        )
                        .build()
                ).collect(Collectors.toList());
    }

}