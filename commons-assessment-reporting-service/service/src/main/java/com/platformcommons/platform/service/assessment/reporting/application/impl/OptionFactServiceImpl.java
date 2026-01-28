package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.OptionFactService;
import com.platformcommons.platform.service.assessment.reporting.application.OptionsDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionFact;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.OptionFactRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.OptionFactAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OptionFactServiceImpl implements OptionFactService {

    private final OptionFactRepository repository;
    private final OptionFactAssembler assembler;
    private final OptionsDimService optionsDimService;

    @Override
    public void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context) {
        repository.saveAll(assembler.toOptionFacts(context));
    }

    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        List<OptionFact> optionFacts = repository.findByAssessmentInstanceId(context.getAssessmentInstanceDim().getAssessmentInstanceId(),EventContext.getSystemEvent());
        Map<Long, Long> map = assesseDTO.getAiadefaultResponseList()
                .stream()
                .flatMap(value -> {
                    if (value.getOptionId() != null) {
                        return Stream.of(value.getOptionId().getId());
                    }
                    if (value.getDrobjectiveresponseList() != null && !value.getDrobjectiveresponseList().isEmpty()) {
                        return value.getDrobjectiveresponseList().stream().filter(dr -> dr.getDefaultOption()!=null).map(dr -> dr.getDefaultOption().getOptions().getId());
                    }
                    return Stream.<Long>empty();
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Long,Long> mtfOptionMap = assesseDTO.getAiadefaultResponseList()
                .stream()
                .flatMap(value -> {
                    if (value.getDrobjectiveresponseList() != null && !value.getDrobjectiveresponseList().isEmpty())
                        return value.getDrobjectiveresponseList().stream().filter(dr -> dr.getMtfOptionId()!=null).map(dr -> dr.getMtfOptionId().getId());
                    return Stream.<Long>empty();
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (OptionFact optionFact : optionFacts) {
            Long count = optionFact.getOptionsId()==null || map.get(optionFact.getOptionsId())==null?0L:map.get(optionFact.getOptionsId());
            Long mtfOptionCount = optionFact.getMtfOptionId()==null || mtfOptionMap.get(optionFact.getMtfOptionId())==null ? 0L : map.get(optionFact.getMtfOptionId());
            if(count!=0L)repository.updateOptionFactOptionResponseCount(count,optionFact.getId(),EventContext.getSystemEvent());
            if(mtfOptionCount!=0L)repository.updateOptionFactMTFOptionResponseCount(mtfOptionCount,optionFact.getId(),EventContext.getSystemEvent());
        }
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext) {
        repository.deleteByAssessmentInstanceId(syncContext.getAssessmentInstanceDim().getAssessmentInstanceId(), EventContext.getSystemEvent());
        repository.saveAll(assembler.toOptionFacts(syncContext));
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        repository.deleteByAssessmentId(id, EventContext.getSystemEvent());
    }

    @Override
    public void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext) {
        repository.deleteByAssessmentId(assessmentInstanceDTO.getAssessment().getId(), EventContext.getSystemEvent());
        assessmentInstanceEvent(assessmentInstanceDTO,reportSyncContext);
    }

    @Override
    public void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext) {
        deleteHierarchy(questionDTO.getId());
        List<OptionFact> facts = repository.saveAll(assembler.toOptionFacts(questionDTO,syncContext));
    }

    private void deleteHierarchy(Long questionId) {
        Set<Long> ids = repository.findByParentQuestionId(Collections.singleton(questionId),EventContext.getSystemEvent());
        repository.deleteByQuestionId(questionId,EventContext.getSystemEvent());
        while(!ids.isEmpty()){
            Set<Long> temp = repository.findByChildDefaultOptionId(ids,EventContext.getSystemEvent());
            repository.deleteByDefaultOptionIdIn(ids,EventContext.getSystemEvent());
            ids = temp;
        }
    }
}
