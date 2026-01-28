package com.platformcommons.platform.service.assessment.reporting.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentOptionResponseHierarchySummaryService;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentResponseHierarchySummaryService;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentResponseHierarchyTimelineSummaryService;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssesseHierarchyResolutionConsumer {

    private final AssessmentResponseHierarchySummaryService assessmentResponseHierarchySummaryService;
    private final AssessmentResponseHierarchyTimelineSummaryService assessmentResponseHierarchyTimelineSummaryService;
    private final AssessmentOptionResponseHierarchySummaryService assessmentOptionResponseHierarchySummaryService;

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_HIERARCHY_SYNCED_TOPIC,
            groupId = MessagingConstant.GROUP_ID_ASSESSE_HIERARCHY_OVERALL,
            containerFactory = "batchKafkaListenerContainerFactory")
    public void createAssessmentResponseHierarchySummary(@Payload List<AssesseDimHierarchyResolvedEventDTO> payload) {
        log.debug("Processing batch of {} events for overall hierarchy summary.", payload.size());
        try {
            assessmentResponseHierarchySummaryService.createOrUpdateSummariesInBatch(payload);
        }
        catch (Exception e) {
            log.error("Error Processing Batch createAssessmentResponseHierarchySummaryTimeline ",e);
        }
    }


    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_HIERARCHY_SYNCED_TOPIC,
            groupId = MessagingConstant.GROUP_ID_ASSESSE_HIERARCHY_TIMELINE,
            containerFactory = "batchKafkaListenerContainerFactory")
    public void createAssessmentResponseHierarchySummaryTimeline(@Payload List<AssesseDimHierarchyResolvedEventDTO> payload) {
        log.debug("Processing batch of {} events for hierarchy timeline summary.", payload.size());
        try {
            assessmentResponseHierarchyTimelineSummaryService.createAssessmentResponseHierarchySummaryTimeline(payload);
        }
        catch (Exception e) {
            log.error("Error Processing Batch createAssessmentResponseHierarchySummaryTimeline ",e);
        }

    }

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_HIERARCHY_SYNCED_TOPIC,
            groupId = MessagingConstant.GROUP_ID_ASSESSE_OPTION_HIERARCHY_OVERALL,
            containerFactory = "batchKafkaListenerContainerFactory")
    public void createAssessmentOptionResponseHierarchySummary(@Payload List<AssesseDimHierarchyResolvedEventDTO> payload) {
        log.debug("Processing batch of {} events for overall option hierarchy summary.", payload.size());
        try {
            assessmentOptionResponseHierarchySummaryService.createAssessmentOptionResponseHierarchySummary(payload);
        }
        catch (Exception e) {
            log.error("Error Processing Batch createAssessmentResponseHierarchySummaryTimeline ",e);
        }
    }


}
