package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.service.assessment.application.cache.AssessmentContextCacheManger;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import com.platformcommons.platform.service.assessment.facade.SectionQuestionFacade;
import com.platformcommons.platform.service.assessment.messaging.producer.AssessmentQuestionPaperEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.assessment.facade.AssessmentQuestionPaperFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.assessment.application.AssessmentQuestionPaperService;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentQuestionPaperDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;

@Component
@Transactional
@RequiredArgsConstructor
public class AssessmentQuestionPaperFacadeImpl implements AssessmentQuestionPaperFacade {


    private final AssessmentQuestionPaperService service;
    private final AssessmentQuestionPaperDTOAssembler assembler;
    private final AssessmentQuestionPaperEventProducer producer;
    private AssessmentContextCacheManger cacheManger;
    private final SectionQuestionFacade sectionQuestionFacade;


    @Override
    public Long createAssessmentQuestionPaper(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        assessmentQuestionPaperDTO=assembler.toDTO(service.createAssessmentQuestionPaper(assembler.fromDTO(assessmentQuestionPaperDTO)));
        producer.assessmentQuestionPaperCreated(assessmentQuestionPaperDTO);
        cacheManger.addToCacheByAssessmentId(assessmentQuestionPaperDTO.getAssessment().getId());
        return assessmentQuestionPaperDTO.getId();
    }


    @Override
    public Set<AssessmentQuestionPaperDTO> getAllAssessmentQuestionPapersOfInstance(Long instanceId) {
        return service.getAllAssessmentQuestionPapersOfInstance(instanceId).stream().map(assembler::toDTO).collect(Collectors.toSet());
    }

    @Override
    public AssessmentQuestionPaperDTO updateAssessmentQuestionPaperV3(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        assessmentQuestionPaperDTO=assembler.toDTO(service.updateAssessmentQuestionPaperV3(assembler.fromDTO(assessmentQuestionPaperDTO)));
        producer.assessmentQuestionPaperUpdated(assessmentQuestionPaperDTO);
        cacheManger.addToCacheByAssessmentId(assessmentQuestionPaperDTO.getAssessment().getId());
        return assessmentQuestionPaperDTO;
    }

    @Override
    public Set<AssessmentQuestionPaperDTO> getAllAssessmentQuestionPapersByAssessmentId(Long id) {
        return service.getAllAssessmentQuestionPapersByAssessmentId(id).stream().map(assembler::toDTO).collect(Collectors.toSet());
    }

    @Override
    public Long getAssessmentInstanceIdByQuestionId(Long id) {
        return service.getAssessmentInstanceIdByQuestionId(id);
    }

    @Override
    public Map<Long, QuestionContextCacheDTO> getSectionQuestions(Long assessmentInstanceId) {
        return sectionQuestionFacade.getSectionQuestions(assessmentInstanceId);
    }


    @Autowired
    public void setCacheManger(AssessmentContextCacheManger cacheManger) {
        this.cacheManger = cacheManger;
    }
}
