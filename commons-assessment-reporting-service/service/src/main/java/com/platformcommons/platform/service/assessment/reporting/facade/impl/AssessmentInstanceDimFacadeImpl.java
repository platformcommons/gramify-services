package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.token.PlatformAppToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentInstanceDimService;
import com.platformcommons.platform.service.assessment.reporting.application.constant.LinkedSystem;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.facade.*;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssessmentInstanceDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.client.AssessmentClient;
import com.platformcommons.platform.service.assessment.reporting.facade.client.CommonsAssessmentClient;
import com.platformcommons.platform.service.assessment.reporting.facade.exception.NotImplementedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AssessmentInstanceDimFacadeImpl implements AssessmentInstanceDimFacade {

    private final AssessmentInstanceDimService service;
    private final AssessmentInstanceDimAssembler assembler;

    private final LTLDFacade ltldFacade;
    private final QuestionPaperSectionDimFacade questionPaperSectionDimFacade;
    private final SectionQuestionDimFacade sectionQuestionDimFacade;
    private final QuestionFactFacade questionFactFacade;
    private final OptionFactFacade optionFactFacade;
    private final SkillFactFacade skillFactFacade;

    private final AssessmentClient assessmentClient;
    private final CommonsAssessmentClient commonsAssessmentClient;
    private final AssesseSkillDimFacade assesseSkillDimFacade;

    @Override
    public void assessmentCreateEvent(AssessmentDTO assessmentDTO) {
        service.assessmentCreateEvent(assembler.assessmentDTOToAssessmentInstanceDim(assessmentDTO));
    }

    @Override
    public void assessmentInstanceCreateEvent(AssessmentInstanceDTO dto) {
        service.assessmentInstanceCreateEvent(dto);
    }

    @Override
    public void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        service.assessmentQuestionPaperCreate(assessmentQuestionPaperDTO);
    }

    @Override
    @Transactional
    public AssessmentReportSyncContext getSyncContext(AssessmentDTO assessmentDTO, boolean cacheDisable) {
        return service.getSyncContext(assessmentDTO,cacheDisable);
    }

    @Override
    public void assessmentUpdateEvent(AssessmentDTO assessmentDTO) {
        service.assessmentUpdateEvent(assessmentDTO);
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        service.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO);
    }

    @Override
    public void assessmentInstanceUpdateEvent(AssessmentInstanceDTO assessmentInstanceDTO) {
        service.assessmentInstanceUpdateEvent(assessmentInstanceDTO);
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        service.assessmentInstanceDeleteEvent(id);
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        service.syncAssessmentData(syncContext);
    }

    @Override
    public Set<AssessmentReportSyncContext> getSyncContext(QuestionDTO questionDTO) {
        return service.getSyncContext(questionDTO);
    }
    @Override
    public void updateSyncContext(Long assessmentId){
        service.updateSyncContext(assessmentId);
    }

    @Override
    public void updateSyncContext(QuestionDTO questionDTO) {
        service.updateSyncContext(questionDTO);
    }

    @Override
    @Transactional
    public void cleanUpAssessmentData(Long assessmentId) {
        AssessmentReportSyncContext syncContext = service.getSyncContext(assessmentId);
        assessmentInstanceDeleteEvent(syncContext.getAssessmentInstanceDim().getAssessmentInstanceId());
        questionPaperSectionDimFacade.assessmentInstanceDeleteEvent(assessmentId);
        sectionQuestionDimFacade.assessmentInstanceDeleteEvent(assessmentId);
        questionFactFacade.assessmentInstanceDeleteEvent(assessmentId);
        optionFactFacade.assessmentInstanceDeleteEvent(assessmentId);
        skillFactFacade.assessmentInstanceDeleteEvent(assessmentId);
    }


    @Override
    public AssessmentSyncContext getAssessmentContext(Long assessment) {
        AssessmentSyncContext syncContext;
        if(EventContext.getSystemEvent().equals(LinkedSystem.LTLD)){
            syncContext = ltldFacade.getAssessmentContext(assessment, PlatformSecurityUtil.getToken());
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.COMMONS)){
            syncContext = commonsAssessmentClient.getContext(assessment, PlatformSecurityUtil.getToken());
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.IGX)){
            syncContext = assessmentClient.getAssessmentContext(assessment, PlatformSecurityUtil.getToken());
        }
        else {
            throw new InvalidInputException("Invalid linked system");
        }
        return syncContext;
    }

    @Override
    public AssessmentInstanceDTO getAssessmentInstanceById(Long assessmentInstanceId) {
        AssessmentInstanceDTO assessmentDTO;
        if(EventContext.getSystemEvent().equals(LinkedSystem.LTLD)){
            throw new NotImplementedException("No Implementation Found");
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.COMMONS)){
            if(SecurityContextHolder.getContext().getAuthentication() instanceof PlatformAppToken){
                PlatformAppToken token = (PlatformAppToken)SecurityContextHolder.getContext().getAuthentication();
                String appKey = String.format("Appkey %s",Base64.getEncoder().encodeToString(String.format("appKey:%s,appCode:%s",token.getAppKey(),token.getAppCode()).getBytes(StandardCharsets.UTF_8)));
                assessmentDTO = commonsAssessmentClient.getAssessmentInstanceByIdV3(assessmentInstanceId, appKey);
            }
            else{
                assessmentDTO = commonsAssessmentClient.getAssessmentInstanceByIdV2(assessmentInstanceId, PlatformSecurityUtil.getToken());
            }
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.IGX)){
            throw new NotImplementedException("No Implementation Found");
        }
        else {
            throw new InvalidInputException("Invalid linked system");
        }
        return assessmentDTO;
    }

    @Override
    public Set<AssessmentReportSyncContext> getAssessmentReportSyncContextByAssessmentInstanceIds(Set<Long> assessmentInstanceIds) {
        return service.getAssessmentReportSyncContextByAssessmentInstanceIds(assessmentInstanceIds);
    }
}
