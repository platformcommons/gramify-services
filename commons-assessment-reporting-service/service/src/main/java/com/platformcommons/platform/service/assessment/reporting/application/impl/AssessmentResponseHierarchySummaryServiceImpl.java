package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssesseDimDTO;
import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.dto.SummaryKey;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentResponseHierarchySummaryService;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentResponseHierarchySummary;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssessmentResponseHierarchySummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AssessmentResponseHierarchySummaryServiceImpl implements AssessmentResponseHierarchySummaryService {

    private final AssessmentResponseHierarchySummaryRepository repository;


    @Override
    public void createOrUpdateSummariesInBatch(List<AssesseDimHierarchyResolvedEventDTO> events) {
        if (events == null || events.isEmpty()) {
            return;
        }
        Map<SummaryKey, Long> aggregationMap = new HashMap<>();
        for (AssesseDimHierarchyResolvedEventDTO event : events) {
            AssesseDimDTO assesseDim = event.getAssesseDim();
            if (assesseDim == null) continue;
            Map<Long, String> resolvedValues = getResolvedValuesMap(assesseDim);
            event.getAssesseHierarchyResolutionConfiguration()
                    .getHierarchyResolvers()
                    .forEach(resolver -> {
                        Long level = resolver.getHierarchyLevel();
                        String value = resolvedValues.get(level);
                        if (StringUtils.hasText(value)) {
                            SummaryKey key = new SummaryKey(assesseDim.getAssessmentInstanceId(), assesseDim.getAssesseeActorType(), level, value,assesseDim.getAssessmentId());
                            aggregationMap.merge(key, 1L, Long::sum);
                        }
                    });
        }
        if (aggregationMap.isEmpty()) {
            return;
        }
        batchUpdateSummaries(aggregationMap);
    }

    @Override
    public void resetAll(Long assessment) {
        repository.deleteByAssessmentId(assessment);
    }

    private void batchUpdateSummaries(Map<SummaryKey, Long> aggregationMap) {
        List<AssessmentResponseHierarchySummary> summariesToSave = new ArrayList<>();
        for (Map.Entry<SummaryKey, Long> entry : aggregationMap.entrySet()) {
            SummaryKey key = entry.getKey();
            Long count = entry.getValue();
            Optional<AssessmentResponseHierarchySummary> existingSummaryOpt = repository.findByAssessmentInstanceIdAndActorTypeAndHierarchyLevelAndResolvedHierarchyValue(
                    key.getAssessmentInstanceId(),
                    key.getActorType(),
                    key.getHierarchyLevel(),
                    key.getResolvedValue()
            );
            if (existingSummaryOpt.isPresent()) {
                repository.incrementResponseCount(key.getAssessmentInstanceId(),key.getActorType(),key.getHierarchyLevel(),key.getResolvedValue(),count);
            } else {
                AssessmentResponseHierarchySummary newSummary = buildAssessmentResponseHierarchySummary(key, count);
                summariesToSave.add(newSummary);
            }
        }
        if (!summariesToSave.isEmpty()) {
            repository.saveAll(summariesToSave);
            log.debug("Processed and saved {} summary updates/inserts.", summariesToSave.size());
        }
    }

    private static @NotNull AssessmentResponseHierarchySummary buildAssessmentResponseHierarchySummary(SummaryKey key, Long count) {
        AssessmentResponseHierarchySummary newSummary = new AssessmentResponseHierarchySummary();
        newSummary.setAssessmentId(key.getAssessmentId());
        newSummary.setAssessmentInstanceId(key.getAssessmentInstanceId());
        newSummary.setActorType(key.getActorType());
        newSummary.setHierarchyLevel(key.getHierarchyLevel());
        newSummary.setResolvedHierarchyValue(key.getResolvedValue());
        newSummary.setResponseCount(count);
        return newSummary;
    }

    private Map<Long, String> getResolvedValuesMap(AssesseDimDTO assesseDim) {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, assesseDim.getL1());
        map.put(2L, assesseDim.getL2());
        map.put(3L, assesseDim.getL3());
        map.put(4L, assesseDim.getL4());
        map.put(5L, assesseDim.getL5());
        return map;
    }
}