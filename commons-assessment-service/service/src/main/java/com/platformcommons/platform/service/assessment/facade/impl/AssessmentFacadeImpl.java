package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.service.assessment.application.cache.AssessmentContextCacheManger;
import com.platformcommons.platform.service.assessment.dto.AssessmentContextDTO;
import com.platformcommons.platform.service.assessment.application.constant.ServiceConstants;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceFacade;
import com.platformcommons.platform.service.assessment.facade.AssessmentQuestionPaperFacade;
import com.platformcommons.platform.service.assessment.facade.QuestionFacade;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.ConfigDTOAssembler;
import com.platformcommons.platform.service.assessment.messaging.producer.AssessmentEventProducer;
import com.platformcommons.platform.service.notification.resolver.dto.FCMRRNotificationEventRequestDTO;
import com.platformcommons.platform.service.notification.resolver.producer.FCMRREventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.assessment.facade.AssessmentFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.assessment.application.AssessmentService;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentDTOAssembler;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class AssessmentFacadeImpl implements AssessmentFacade {

    private final AssessmentService service;
    private final AssessmentDTOAssembler assembler;
    private final ConfigDTOAssembler configDTOAssembler;

    private final AssessmentInstanceFacade assessmentInstanceFacade;
    private final AssessmentQuestionPaperFacade questionPaperFacade;
    private final QuestionFacade questionFacade;

    private final AssessmentEventProducer producer;
    private final AssessmentContextCacheManger cacheManger;
    private final FCMRREventProducer fcmrrEventProducer;




    @Override
    public Long createAssessment(AssessmentDTO assessmentDTO) {
        AssessmentDTO dto = assembler.toDTO(service.createAssessment(assembler.fromDTO(assessmentDTO)));
        producer.assessmentCreated(dto);
        return dto.getId();
    }

    @Override
    public void updateAssessmentV2(AssessmentDTO assessmentDTO) {
        AssessmentDTO dto = assembler.toDTO(service.updateAssessmentV2(assembler.fromDTO(assessmentDTO)));
        producer.assessmentUpdated(dto);
    }

    @Override
    public AssessmentContextDTO getContext(Long asssessmentId) {

        AssessmentInstanceDTO assessmentInstance = assessmentInstanceFacade.getAssessmentInstanceByAssessmentId(asssessmentId);
        AssessmentQuestionPaperDTO papersByAssessmentId = questionPaperFacade.getAllAssessmentQuestionPapersByAssessmentId(asssessmentId).stream().findAny().get();
        Set<QuestionDTO> questionDTOS = questionFacade.getQuestionByIds(papersByAssessmentId.getQuestionPaperSectionList()
                .stream()
                .flatMap(sectionDTO -> sectionDTO.getSectionQuestionsList()
                        .stream()
                        .map(SectionQuestionsDTO::getQuestionId))
                .collect(Collectors.toSet()));
        AssessmentDTO assessmentDTO = assessmentInstance.getAssessment();
        return AssessmentContextDTO.builder()
                .assessment(assessmentDTO)
                .assessmentInstanceDTO(assessmentInstance)
                .assessmentQuestionPaperDTO(papersByAssessmentId)
                .questionDTOMap(questionDTOS)
                .build();
    }

    @Override
    public void evictCacheContext(Long assessmentInstanceId) {
        cacheManger.evictCacheContext(assessmentInstanceId);
    }

    @Override
    public Set<AssessmentContextDTO> getContexts(Set<String> uuids, Set<String> codes) {
        Set<Long> assessmentIds = service.getAssessmentIdsByUUIDsAndCodes(uuids,codes);
        return assessmentIds.stream().map(this::getContext).collect(Collectors.toSet());
    }

    @Override
    public AssessmentDTO addAssessmentConfig(Long assessmentId, ConfigDTO configDTO) {
        return assembler.toDTO(service.addAssessmentConfig(assessmentId, configDTOAssembler.fromDTO(configDTO)));
    }

    @Override
    public AssessmentDTO getAssessmentById(Long assessmentId) {
        return assembler.toDTO(service.getAssessmentById(assessmentId));
    }

    @Override
    public void triggerReviewAssessmentReminder() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put(ServiceConstants.TYPE, ServiceConstants.NOTIFICATION_TYPE);
        metadata.put(ServiceConstants.SUB_TYPE, ServiceConstants.REVIEW_ASSESSMENT_SUB_TYPE);
        metadata.put(ServiceConstants.CONSUMER, ServiceConstants.CONSUMER_TYPE);
        FCMRRNotificationEventRequestDTO fcmrrNotificationEventRequestDTO =
                FCMRRNotificationEventRequestDTO.builder()
                        .eventCode(ServiceConstants.REVIEW_ASSESSMENT_EVENT_CODE)
                        .recipientKind(FCMRRNotificationEventRequestDTO.RecipientKind.TOPIC)
                        .recipientValues(new HashSet<>(Collections.singletonList(ServiceConstants.TOPIC)))
                        .metaData(metadata)
                        .appContext(ServiceConstants.APP_CONTEXT)
                        .build();
        fcmrrEventProducer.triggerTopic(fcmrrNotificationEventRequestDTO);
    }
}
