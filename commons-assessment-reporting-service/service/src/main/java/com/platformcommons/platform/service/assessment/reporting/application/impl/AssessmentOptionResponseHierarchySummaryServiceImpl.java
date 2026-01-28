package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentOptionResponseHierarchySummaryService;
import com.platformcommons.platform.service.assessment.reporting.application.constant.ApplicationConstants;
import com.platformcommons.platform.service.assessment.reporting.domain.*;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AssessmentOptionResponseHierarchySummaryServiceImpl implements AssessmentOptionResponseHierarchySummaryService {
    private final AssessmentOptionResponseHierarchySummaryRepository summaryRepository;
    private final AssesseResponseFactRepository responseFactRepository;
    private final OptionsDimRepository optionsDimRepository;



    @Override
    public void createAssessmentOptionResponseHierarchySummary(List<AssesseDimHierarchyResolvedEventDTO> events) {
        if (events == null || events.isEmpty()) {
            log.warn("Received empty or null list of events. No summaries to process.");
            return;
        }

        Map<OptionSummaryKey, Long> aggregationMap = new HashMap<>();

        Set<Long> assesseInstanceAssesseIds = events.stream()
                .map(event -> event.getAssesseDim().getAssesseId())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, List<AssesseResponseFact>> responsesByAssesseId = responseFactRepository.findByAssessmentInstanceAssesseIdIn(assesseInstanceAssesseIds)
                .stream()
                .collect(Collectors.groupingBy(AssesseResponseFact::getAssessmentInstanceAssesseId));

        Set<Long> allOptionIds = responsesByAssesseId.values().stream()
                .flatMap(List::stream)
                .flatMap(this::extractOptionIdsFromFact)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, OptionsDim> optionsDimMap = optionsDimRepository.findByOptionsIdIn(allOptionIds)
                .stream()
                .collect(Collectors.toMap(OptionsDim::getOptionsId, Function.identity(), (existing, replacement) -> existing));

        for (AssesseDimHierarchyResolvedEventDTO event : events) {
            AssesseDimDTO assesseDim = event.getAssesseDim();
            if (assesseDim == null || assesseDim.getAssesseId() == null) {
                log.warn("Skipping event due to missing AssesseDim or AssesseId.");
                continue;
            }

            List<AssesseResponseFact> responses = responsesByAssesseId.getOrDefault(assesseDim.getAssesseId(), Collections.emptyList());
            if (responses.isEmpty()) {
                continue;
            }

            Map<Long, String> resolvedHierarchyValues = getResolvedValuesMap(assesseDim);
            Long maxLevel = event.getAssesseHierarchyResolutionConfiguration().getMaxHierarchyLevel();

            Set<Long> selectedOptionIds = responses.stream()
                    .flatMap(this::extractOptionIdsFromFact)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            for (Long optionId : selectedOptionIds) {
                OptionsDim optionDim = optionsDimMap.get(optionId);
                Long defaultOptionId = (optionDim != null) ? optionDim.getDefaultOptionId() : null;

                for (long level = 1; level <= maxLevel; level++) {
                    String hierarchyValue = resolvedHierarchyValues.get(level);
                    if (StringUtils.hasText(hierarchyValue)) {
                        OptionSummaryKey key = OptionSummaryKey.builder()
                                .assessmentInstanceId(assesseDim.getAssessmentInstanceId())
                                .actorType(assesseDim.getAssesseeActorType())
                                .hierarchyLevel(level)
                                .resolvedValue(hierarchyValue)
                                .optionId(optionId)
                                .defaultOptionId(defaultOptionId)
                                .assessmentId(assesseDim.getAssessmentId())
                                .build();
                        aggregationMap.merge(key, 1L, Long::sum);
                    }
                }
            }
        }

        if (aggregationMap.isEmpty()) {
            log.debug("Aggregation map is empty after processing events. No summary updates needed.");
            return;
        }

        batchUpdateSummaries(aggregationMap);
    }

    @Override
    public void resetAll(Long assessment) {
        summaryRepository.deleteByAssessmentId(assessment);
    }

    private Stream<Long> extractOptionIdsFromFact(AssesseResponseFact fact) {
        Stream<Long> ids = Stream.empty();
        if (fact.getOptionId() != null) {
            ids = Stream.concat(ids, Stream.of(fact.getOptionId()));
        }
        if (StringUtils.hasText(fact.getMultiSelectResponseIds())) {
            try {
                Stream<Long> multiSelectIds = Arrays.stream(fact.getMultiSelectResponseIds().split(ApplicationConstants.PRIMARY_DELIMITER))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::parseLong);
                ids = Stream.concat(ids, multiSelectIds);
            } catch (NumberFormatException e) {
                log.error("Failed to parse multiSelectResponseIds '{}' for fact id {}", fact.getMultiSelectResponseIds(), fact.getId(), e);
            }
        }
        return ids;
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

    private void batchUpdateSummaries(Map<OptionSummaryKey, Long> aggregationMap) {
        List<AssessmentOptionResponseHierarchySummary> summariesToSave = new ArrayList<>();
        Set<OptionSummaryKey> keysToIncrement = new HashSet<>();

        for (OptionSummaryKey key : aggregationMap.keySet()) {
            Optional<AssessmentOptionResponseHierarchySummary> existingOpt = summaryRepository.findByKeyFields(
                    key.getAssessmentInstanceId(),
                    key.getActorType(),
                    key.getHierarchyLevel(),
                    key.getResolvedValue(),
                    key.getOptionId(),
                    key.getDefaultOptionId()
            );

            if (existingOpt.isPresent()) {
                keysToIncrement.add(key);
            } else {
                summariesToSave.add(buildNewSummary(key, aggregationMap.get(key)));
            }
        }

        if (!keysToIncrement.isEmpty()) {
            keysToIncrement.forEach(key -> {
                int updatedRows = summaryRepository.incrementResponseCount(
                        key.getAssessmentInstanceId(),
                        key.getActorType(),
                        key.getHierarchyLevel(),
                        key.getResolvedValue(),
                        key.getOptionId(),
                        key.getDefaultOptionId(),
                        aggregationMap.get(key)
                );
                if (updatedRows == 0) {
                    log.warn("Failed to increment count for key {}, record might have been deleted. Creating new.", key);
                    summariesToSave.add(buildNewSummary(key, aggregationMap.get(key)));
                }
            });
            log.debug("Incremented counts for {} existing summary records.", keysToIncrement.size());
        }

        if (!summariesToSave.isEmpty()) {
            summaryRepository.saveAll(summariesToSave);
            log.debug("Inserted {} new summary records.", summariesToSave.size());
        }
    }

    private AssessmentOptionResponseHierarchySummary buildNewSummary(OptionSummaryKey key, Long count) {
        AssessmentOptionResponseHierarchySummary newSummary = new AssessmentOptionResponseHierarchySummary();
        newSummary.setAssessmentInstanceId(key.getAssessmentInstanceId());
        newSummary.setActorType(key.getActorType());
        newSummary.setHierarchyLevel(key.getHierarchyLevel());
        newSummary.setResolvedHierarchyValue(key.getResolvedValue());
        newSummary.setOptionId(key.getOptionId());
        newSummary.setDefaultOptionId(key.getDefaultOptionId());
        newSummary.setResponseCount(count);
        newSummary.setAssessmentId(key.getAssessmentId());
        return newSummary;
    }
}
