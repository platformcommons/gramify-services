package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionFactService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionFact;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.QuestionFactRepository;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.QuestionFactAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionFactServiceImpl implements QuestionFactService {

    private final QuestionFactRepository questionFactRepository;
    private final UtilityAssembler utilityAssembler;
    private final QuestionFactAssembler questionFactAssembler;
    private final QuestionDimService questionDimService;


    @Override
    public void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context) {
        Set<QuestionFact> questionFacts = questionFactAssembler.toQuestionFactAssembler(context);
        questionFactRepository.saveAll(questionFacts);

    }

    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        List<QuestionFact> facts = questionFactRepository.findByAssessmentInstanceId(assesseDTO.getAssessmentInstance().getId(), EventContext.getSystemEvent());
        Map<Long, AiaDefaultResponseDTO> map = assesseDTO.getAiadefaultResponseList()
                .stream()
                .collect(Collectors.toMap(aiaDefaultResponseDTO -> aiaDefaultResponseDTO.getSectionQuestion() == null ? aiaDefaultResponseDTO.getChildQuestionId() : aiaDefaultResponseDTO.getSectionQuestion().getQuestionId(), Function.identity()));

        for (QuestionFact fact : facts) {
            boolean responded = map.containsKey(fact.getQuestionId());

            final long respondentCount = responded ? 1L : 0L;
            final long correctCount = responded && map.get(fact.getSectionQuestionId()) != null ? utilityAssembler.getCorrectQuestions(map.get(fact.getSectionQuestionId()), context) : 0L;
            final long incorrectCount = responded && correctCount == 1L ? 0L : 1L;
            final long skippedCount = responded ? 0L : 1L;
            questionFactRepository.updateQuestionFact(respondentCount, correctCount, incorrectCount, skippedCount, fact.getId(), EventContext.getSystemEvent());
        }

    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext) {
        List<QuestionFact> dbQuestionFacts = questionFactRepository.findByAssessmentInstanceId(syncContext.getAssessmentInstanceDim().getAssessmentInstanceId(), EventContext.getSystemEvent());

        Set<Long> scSectionQuestionIds = syncContext.getSectionQuestionDims().stream().map(SectionQuestionDimDTO::getSectionQuestionId).collect(Collectors.toSet());
        Set<Long> qfSectionQuestionIds = dbQuestionFacts.stream().map(QuestionFact::getSectionQuestionId).collect(Collectors.toSet());

        questionFactRepository.deleteBySectionQuestionIdIn(filterNonIntersectingIds(qfSectionQuestionIds,scSectionQuestionIds), EventContext.getSystemEvent());

        Set<QuestionFact> toSave = new HashSet<>();
        for (SectionQuestionDimDTO sectionQuestionDim : syncContext.getSectionQuestionDims()) {
            if (!qfSectionQuestionIds.contains(sectionQuestionDim.getSectionQuestionId())) {
                QuestionFact fact = questionFactAssembler.toQuestionFactAssembler(sectionQuestionDim, syncContext.getQuestionDim(sectionQuestionDim.getQuestionId()), syncContext.getAssessmentInstanceDim());
                toSave.add(fact);
                List<QuestionFact> getChildFacts = getChildFact(sectionQuestionDim.getQuestionId(),syncContext,fact);
                if(!getChildFacts.isEmpty()) toSave.addAll(getChildFacts);
            }
        }

        if (!toSave.isEmpty()) questionFactRepository.saveAll(toSave);

    }

    private List<QuestionFact> getChildFact(Long questionId, AssessmentReportSyncContext syncContext, QuestionFact fact) {
        Set<QuestionDimDTO> questionDimDTOS = syncContext.getChildQuestions(questionId);
        if(questionDimDTOS==null || questionDimDTOS.isEmpty()) return new ArrayList<>();
        List<QuestionFact> questionFacts = new ArrayList<>();
        for (QuestionDimDTO childQuestion : questionDimDTOS) {
            QuestionFact questionFact = fact.duplicate();
            questionFact.setQuestionId(childQuestion.getQuestionId());
            questionFact.setParentQuestionId(childQuestion.getParentQuestionId());
            questionFact.setChildDefaultOptionId(childQuestion.getChildDefaultOptionId());
            StringJoiner join = new StringJoiner(",");
            for (Long skillId : childQuestion.getSkillIds()) join.add(skillId.toString());
            questionFact.setQuestionSkills(join.toString());
            questionFacts.add(questionFact);
        }
        return questionFacts;
    }

    private Set<Long> filterNonIntersectingIds(Set<Long> qfSectionQuestionIds, Set<Long> scSectionQuestionIds) {
        Set<Long> toBeDeletedSectionQuestions = new HashSet<>(qfSectionQuestionIds);
        toBeDeletedSectionQuestions.removeAll(scSectionQuestionIds);
        return toBeDeletedSectionQuestions;
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        questionFactRepository.deleteByAssessmentId(id, EventContext.getSystemEvent());
    }

    @Override
    public void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext) {
        questionFactRepository.deleteByAssessmentId(assessmentInstanceDTO.getAssessment().getId(), EventContext.getSystemEvent());
        assessmentInstanceEvent(assessmentInstanceDTO, reportSyncContext);
    }

    @Override
    public Set<Long> getAssessmentIds(QuestionDTO questionDTO) {
        return questionFactRepository.findAssessmentIdByQuestionId(questionDTO.getId(), EventContext.getSystemEvent());
    }

    @Override
    public void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext) {

        Map<Long, QuestionFact> factMap = getQuestionFacts(questionDTO.getId())
                .stream()
                .collect(Collectors.toMap(QuestionFact::getQuestionId, Function.identity()));
        QuestionFact fact = factMap.remove(questionDTO.getId());
        questionFactRepository.deleteByQuestionIdIn(factMap.keySet(), EventContext.getSystemEvent());
        Set<QuestionFact> toSave = new HashSet<>();
        createChildHierarchy(questionDTO,fact.duplicate(),toSave);
        if (!toSave.isEmpty()) questionFactRepository.saveAll(toSave);
    }

    private void createChildHierarchy(QuestionDTO questionDTO, QuestionFact questionFact,Set<QuestionFact> toSave) {
        if(questionDTO.getDefaultOptionsList()==null) return;
        Set<QuestionDim>  questionDims = questionDimService.getChildQuestionsDimById(questionDTO.getId());

        while(!questionDims.isEmpty()){
            Set<QuestionDim> dims = new HashSet<>();
            for (QuestionDim dim : questionDims) {
                QuestionFact childFact = questionFact.duplicate();
                childFact.setQuestionId(dim.getQuestionId());
                childFact.setParentQuestionId(dim.getParentQuestionId());
                childFact.setChildDefaultOptionId(dim.getChildDefaultOptionId());
                toSave.add(childFact);
                dims.addAll(questionDimService.getChildQuestionsDimById(dim.getQuestionId()));
            }
            questionDims.clear();
            questionDims.addAll(dims);
        }

    }

    private Set<QuestionFact> getQuestionFacts(Long id) {
        Set<Long> ids = Collections.singleton(id);
        Set<QuestionFact> questionFacts = questionFactRepository.findByQuestionIds(ids, EventContext.getSystemEvent());
        while (!ids.isEmpty()) {
            Set<QuestionFact> facts = questionFactRepository.findByParentQuestionId(ids, EventContext.getSystemEvent());
            ids = facts.stream().map(QuestionFact::getQuestionId).collect(Collectors.toSet());
            questionFacts.addAll(facts);
        }
        return questionFacts;
    }

}
