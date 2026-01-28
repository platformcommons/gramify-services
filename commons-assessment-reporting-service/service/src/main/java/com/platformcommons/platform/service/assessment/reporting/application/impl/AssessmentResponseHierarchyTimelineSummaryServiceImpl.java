package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssesseDimDTO;
import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.dto.TimelineSummaryKey;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentResponseHierarchyTimelineSummaryService;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentResponseHierarchyTimelineSummary;
import com.platformcommons.platform.service.assessment.reporting.domain.enums.TimelineType;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssessmentResponseHierarchyTimelineSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AssessmentResponseHierarchyTimelineSummaryServiceImpl implements AssessmentResponseHierarchyTimelineSummaryService {

    private final AssessmentResponseHierarchyTimelineSummaryRepository repository;

    @Override
    public void createAssessmentResponseHierarchySummaryTimeline(List<AssesseDimHierarchyResolvedEventDTO> events) {
        if (events == null || events.isEmpty()) {
            return;
        }

        Map<TimelineSummaryKey, Long> aggregationMap = aggregateTimelineEvents(events);
        if (aggregationMap.isEmpty()) {
            return;
        }

        batchUpdateSummaries(aggregationMap);
    }

    @Override
    public void resetAll(Long assessment) {
        repository.deleteByAssessmentId(assessment);
    }


    private Map<TimelineSummaryKey, Long> aggregateTimelineEvents(List<AssesseDimHierarchyResolvedEventDTO> events) {
        Map<TimelineSummaryKey, Long> aggregationMap = new HashMap<>();
        for (AssesseDimHierarchyResolvedEventDTO event : events) {
            AssesseDimDTO assesseDim = event.getAssesseDim();
            if (assesseDim == null || assesseDim.getAssessmentInstanceAssesseCreatedAt() == null) continue;

            ZonedDateTime timestamp = Instant.ofEpochMilli(assesseDim.getAssessmentInstanceAssesseCreatedAt()).atZone(ZoneOffset.UTC);
            Map<Long, String> resolvedValues = getResolvedValuesMap(assesseDim);

            for (TimelineType type : TimelineType.values()) {
                String timelineValue = timestamp.format(type.getFormatter());
                event.getAssesseHierarchyResolutionConfiguration().getHierarchyResolvers().forEach(resolver -> {
                    Long level = resolver.getHierarchyLevel();
                    String value = resolvedValues.get(level);
                    if (StringUtils.hasText(value)) {
                        TimelineSummaryKey key = new TimelineSummaryKey(
                                assesseDim.getAssessmentInstanceId(), assesseDim.getAssesseeActorType(),
                                level, value, assesseDim.getAssessmentId(), type.name(), timelineValue
                        );
                        aggregationMap.merge(key, 1L, Long::sum);
                    }
                });
            }
        }
        return aggregationMap;
    }

    private void batchUpdateSummaries(Map<TimelineSummaryKey, Long> aggregationMap) {
        List<AssessmentResponseHierarchyTimelineSummary> summariesToSave = new ArrayList<>();
        for (Map.Entry<TimelineSummaryKey, Long> entry : aggregationMap.entrySet()) {
            TimelineSummaryKey key = entry.getKey();
            Long count = entry.getValue();

            Optional<AssessmentResponseHierarchyTimelineSummary> existingSummaryOpt = repository.findByAssessmentIdAndActorTypeAndActorIdAndHierarchyLevelAndHierarchyResolvedValueAndTimelineTypeAndTimelineValue(
                    key.getAssessmentInstanceId(), key.getActorType(), key.getHierarchyLevel(),
                    key.getResolvedValue(), key.getTimelineType(), key.getTimelineValue()
            );

            if (existingSummaryOpt.isPresent()) {
                repository.incrementResponseCount(
                        key.getAssessmentInstanceId(), key.getActorType(), key.getHierarchyLevel(),
                        key.getResolvedValue(), key.getTimelineType(),
                        key.getTimelineValue(), count
                );
            } else {
                AssessmentResponseHierarchyTimelineSummary newSummary = buildNewTimelineSummary(key, count);
                summariesToSave.add(newSummary);
            }
        }

        if (!summariesToSave.isEmpty()) {
            repository.saveAll(summariesToSave);
            log.debug("Processed and saved {} new timeline summary inserts.", summariesToSave.size());
        }
    }

    private AssessmentResponseHierarchyTimelineSummary buildNewTimelineSummary(TimelineSummaryKey key, Long count) {
        AssessmentResponseHierarchyTimelineSummary newSummary = new AssessmentResponseHierarchyTimelineSummary();
        newSummary.setAssessmentId(key.getAssessmentId());
        newSummary.setAssessmentInstanceId(key.getAssessmentInstanceId());
        newSummary.setActorType(key.getActorType());
        newSummary.setHierarchyLevel(key.getHierarchyLevel());
        newSummary.setHierarchyResolvedValue(key.getResolvedValue());
        newSummary.setResponseCount(count);
        newSummary.setTimelineType(key.getTimelineType());
        newSummary.setTimelineValue(key.getTimelineValue());
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