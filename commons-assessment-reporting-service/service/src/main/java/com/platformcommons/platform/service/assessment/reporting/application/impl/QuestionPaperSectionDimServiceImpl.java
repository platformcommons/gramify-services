package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionPaperSectionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionPaperSectionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.QuestionPaperSectionRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.QuestionPaperSectionDimAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionPaperSectionDimServiceImpl implements QuestionPaperSectionDimService {

    private final QuestionPaperSectionRepository questionPaperSectionRepository;
    private final QuestionPaperSectionDimAssembler questionPaperSectionDimAssembler;

    @Override
    public void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        questionPaperSectionRepository.saveAll(questionPaperSectionDimAssembler.toQuestionPaperSectionDim(assessmentQuestionPaperDTO));
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        List<QuestionPaperSectionDim> questionPaperSectionDimList = questionPaperSectionRepository.findByQuestionPaperId(assessmentQuestionPaperDTO.getId(), EventContext.getSystemEvent());

        List<QuestionPaperSectionDim> toUpdate = new ArrayList<>();
        List<QuestionPaperSectionDim> toSave   = new ArrayList<>();
        List<QuestionPaperSectionDim> toDelete = new ArrayList<>();

        Map<Long,QuestionPaperSectionDTO> sectionMap = assessmentQuestionPaperDTO.getQuestionPaperSectionList().stream().collect(Collectors.toMap(QuestionPaperSectionDTO::getId, Function.identity()));
        for (QuestionPaperSectionDim questionPaperSectionDim : questionPaperSectionDimList) {
            Long sectionId = questionPaperSectionDim.getQuestionPaperSectionId();
            QuestionPaperSectionDTO section = sectionMap.get(sectionId);
            if(sectionMap.containsKey(sectionId)){
                sectionMap.remove(sectionId);
                questionPaperSectionDimAssembler.updateQuestionPaperSection(questionPaperSectionDim,assessmentQuestionPaperDTO,section);
                toUpdate.add(questionPaperSectionDim);
            }
            else{
                toDelete.add(questionPaperSectionDim);
            }

        }

        sectionMap.values().forEach(section -> toSave.add(questionPaperSectionDimAssembler.toQuestionPaperSectionDim(assessmentQuestionPaperDTO,section)));

        if(!toSave.isEmpty())   questionPaperSectionRepository.saveAll(toSave);
        if(!toUpdate.isEmpty()) questionPaperSectionRepository.saveAll(toUpdate);
        if(!toDelete.isEmpty()) questionPaperSectionRepository.deleteByQuestionPaperSectionIdIn(toUpdate.stream().map(QuestionPaperSectionDim::getQuestionPaperSectionId).collect(Collectors.toSet()), EventContext.getSystemEvent());

    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        questionPaperSectionRepository.deleteByAssessmentId(id, EventContext.getSystemEvent());
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        questionPaperSectionRepository.deleteByQuestionPaperId(syncContext.getAssessmentQuestionPaperDTO().getId(), EventContext.getSystemEvent());
        assessmentQuestionPaperCreate(syncContext.getAssessmentQuestionPaperDTO());
    }

}
