package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionsDTO;
import com.platformcommons.platform.service.assessment.reporting.application.SectionQuestionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.SectionQuestionDimRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.SectionQuestionDimAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionQuestionDimServiceImpl implements SectionQuestionDimService {


    private final SectionQuestionDimRepository sectionQuestionDimRepository;
    private final SectionQuestionDimAssembler sectionQuestionDimAssembler;


    @Override
    public void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        sectionQuestionDimRepository.saveAll(sectionQuestionDimAssembler.assessmentQuestionPaperDTOToSectionQuestionDim(assessmentQuestionPaperDTO));
    }

    @Override
    public Set<SectionQuestionDim> getByAssessmentId(Long assessmentId) {
        return sectionQuestionDimRepository.findByAssessmentId(assessmentId, EventContext.getSystemEvent());
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {

        List<SectionQuestionDim> sectionQuestionDims = sectionQuestionDimRepository.findByQuestionPaperId(assessmentQuestionPaperDTO.getId(), EventContext.getSystemEvent());

        List<SectionQuestionDim> toSave   = new ArrayList<>();
        List<SectionQuestionDim> toUpdate = new ArrayList<>();
        List<SectionQuestionDim> toDelete = new ArrayList<>();

        Map<Long, QuestionPaperSectionDTO> questionPaperSectionDTOMap = new HashMap<>();
        for (QuestionPaperSectionDTO paperSectionDTO : assessmentQuestionPaperDTO.getQuestionPaperSectionList()) {
            for (SectionQuestionsDTO sectionQuestionsDTO : paperSectionDTO.getSectionQuestionsList()) {
                questionPaperSectionDTOMap.put(sectionQuestionsDTO.getId(),paperSectionDTO);
            }
        }

        Map<Long, SectionQuestionsDTO> sectionQuestionMap = assessmentQuestionPaperDTO.getQuestionPaperSectionList()
                .stream()
                .flatMap(sectionDTO -> sectionDTO.getSectionQuestionsList().stream())
                .collect(Collectors.toMap(SectionQuestionsDTO::getId, Function.identity()));

        for (SectionQuestionDim sectionQuestionDim : sectionQuestionDims) {
            Long sectionQuestionId = sectionQuestionDim.getSectionQuestionId();
            if (sectionQuestionMap.containsKey(sectionQuestionId)) {
                SectionQuestionsDTO sectionQuestions = sectionQuestionMap.get(sectionQuestionId);
                sectionQuestionMap.remove(sectionQuestionId);
                sectionQuestionDimAssembler.updateSectionQuestion(sectionQuestionDim, assessmentQuestionPaperDTO, sectionQuestions);
                toUpdate.add(sectionQuestionDim);
            } else {
                toDelete.add(sectionQuestionDim);
            }

        }

        sectionQuestionMap.values().forEach(sectionQuestion -> toSave.add(sectionQuestionDimAssembler.toQuestionPaperSectionDim(assessmentQuestionPaperDTO, questionPaperSectionDTOMap.get(sectionQuestion.getId()), sectionQuestion)));

        if (!toSave.isEmpty()) sectionQuestionDimRepository.saveAll(toSave);
        if (!toUpdate.isEmpty()) sectionQuestionDimRepository.saveAll(toUpdate);
        if (!toDelete.isEmpty()) sectionQuestionDimRepository.deleteBySectionQuestionIdIn(toDelete.stream().map(SectionQuestionDim::getSectionQuestionId).collect(Collectors.toSet()), EventContext.getSystemEvent());


    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        sectionQuestionDimRepository.deleteByAssessmentId(id, EventContext.getSystemEvent());
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        sectionQuestionDimRepository.deleteByAssessmentId(syncContext.getAssessment().getId(), EventContext.getSystemEvent());
        assessmentQuestionPaperCreate(syncContext.getAssessmentQuestionPaperDTO());
    }

    @Override
    public List<Long> getSequencedSectionQuestion(Set<Long> id) {
        return sectionQuestionDimRepository.getSequencedSectionQuestion(id,EventContext.getSystemEvent());
    }

}
