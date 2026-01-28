package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.exception.generic.DataAccessException;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceService;
import com.platformcommons.platform.service.assessment.application.AssessmentService;
import com.platformcommons.platform.service.assessment.application.DuplicateEntityService;
import com.platformcommons.platform.service.assessment.application.constant.OwnedBy;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceFacade;
import com.platformcommons.platform.service.assessment.facade.AssessmentQuestionPaperFacade;
import com.platformcommons.platform.service.assessment.facade.QuestionFacade;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceDTOAssembler;
import com.platformcommons.platform.service.assessment.messaging.producer.AssessmentInstanceEventProducer;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class AssessmentInstanceFacadeImpl implements AssessmentInstanceFacade {

    private final AssessmentInstanceService service;
    private final AssessmentInstanceDTOAssembler assembler;
    private final DuplicateEntityService duplicateEntityService;
    private final AssessmentInstanceEventProducer producer;
    private final AssessmentInstanceService assessmentInstanceService;
    private final AssessmentQuestionPaperFacade assessmentQuestionPaperFacade;
    private final QuestionFacade questionFacade;
    private final AssessmentService assessmentService;

    @Override
    @Transactional
    public Long createAssessmentInstance(AssessmentInstanceDTO assessmentInstanceDTO) {
        assessmentInstanceDTO = assembler.toDTO(service.createAssessmentInstance(assembler.fromDTO(assessmentInstanceDTO)), null);
        producer.assessmentInstanceCreated(assessmentInstanceDTO);
        return assessmentInstanceDTO.getId();
    }

    @Override
    public PageDTO<AssessmentInstanceDTO> getAllAssessmentInstances(Integer page, Integer size) {
        Page<AssessmentInstance> assessmentInstancePage = service.getAllAssessmentInstances(page, size);
        return new PageDTO<>(assessmentInstancePage.get()
                .map((el) -> assembler.toDTO(el, null))
                .collect(Collectors.toCollection(LinkedHashSet::new)),
                assessmentInstancePage.hasNext());
    }

    @Override
    public AssessmentInstanceDTO getAssessmentInstanceByIdv2(Long assessmentInstanceId) {
        AssessmentInstance instance = service.getAssessmentInstanceByIdv2(assessmentInstanceId);
        return assembler.toDTO(instance, service.getAssessmentInstanceCreatedByName(Collections.singleton(instance.getCreatedByUser().toString())).get(instance.getCreatedByUser()));
    }

    @Override
    public PageDTO<AssessmentInstanceDetailVO> getAssessmentInstanceInfo(String domain, OwnedBy ownedBy, String subdomain,
                                                                         String status, String sortBy, String sortOrder,
                                                                         Integer page, Integer size, String language, String text, String context) {
        PageDTO<AssessmentInstanceDetailVO> result = service.getAssessmentInstanceInfo(domain, ownedBy, subdomain, status, sortBy, sortOrder, page, size, language, text, context);
        Set<AssessmentInstanceDetailVO> resultSet = result.getElements();
        Map<Long, String> voSet = service.getAssessmentInstanceCreatedByName(resultSet.stream().map(AssessmentInstanceDetailVO::getAuthor).collect(Collectors.toCollection(LinkedHashSet::new)));
        resultSet.forEach(el -> el.setAuthor(voSet.get(Long.parseLong(el.getAuthor()))));
        return new PageDTO<>(resultSet, result.hasNext(), result.getTotalElements());
    }


    @Override
    public void updateAssessmentInstanceV2(AssessmentInstanceDTO assessmentInstanceDTO) {
        assessmentInstanceDTO = assembler.toDTO(service.updateAssessmentInstanceV2(assembler.fromDTO(assessmentInstanceDTO)), null);
        producer.assessmentInstanceUpdated(assessmentInstanceDTO);
    }

    @Override
    public void deleteAssessmentInstanceV2(Long assessmentInstanceId, String reason) {
        producer.assessmentInstanceDeleted(service.deleteAssessmentInstanceV2(assessmentInstanceId, reason));
    }

    @Override
    public AssessmentInstanceDTO duplicateAssessmentInstance(Long assessmentInstanceId, String name) {
        return duplicateEntityService.duplicateAssessmentInstance(assessmentInstanceId, name);
    }

    @Override
    public AssessmentInstanceDTO getAssessmentInstanceByAssessmentId(Long asssessmentId) {
        return assembler.toDTO(service.getAssessmentInstanceByAssessmentId(asssessmentId), null);
    }

    @Override
    public Set<AssessmentInstanceDTO> getAssessmentInstanceByAssessmentType(String assessmentType, Boolean assessmentIsActive, Boolean isActive) {
        return service.getAssessmentInstanceByAssessmentType(assessmentType, assessmentIsActive, isActive).stream().map((el) -> assembler.toDTO(el, null)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public PageDTO<AssessmentInstanceSearchDTO> getAllAssessmentInstanceSearchDTO(Integer page, Integer size) {
        Page<AssessmentInstanceSearchDTO> assessmentInstancePage = service.getAllAssessmentInstanceSearchDTO(page, size);
        return new PageDTO<>(assessmentInstancePage.get().collect(Collectors.toSet()), assessmentInstancePage.hasNext());
    }

    @Override
    public PageDTO<AssessmentInstanceDTO> getAssessmentInstanceByAssessmentTypes(Set<String> assessmentTypes, Set<String> assessmentKind, String assessmentSubType, Boolean assessmentIsActive, String context, Boolean isActive, Integer page, Integer size, String forEntityId, String forEntityType) {
        return service.getAssessmentInstanceByAssessmentTypes(assessmentTypes, assessmentKind, assessmentSubType, assessmentIsActive, context, isActive, page, size,forEntityId,forEntityType);
    }

    @Override
    public Long getAssessmentInstanceIdByAssessmentId(Long assessmentId) {
        return assessmentInstanceService.getAssessmentInstanceIdByAssessmentId(assessmentId);
    }

    @Override
    public AssessmentContextCachedDTO getContextCache(Long assessmentInstanceId) {
        Map<String, String> config = assessmentService.getConfigs(assessmentInstanceId);
        Map<Long, QuestionContextCacheDTO> childQuestionMap = new HashMap<>();

        Map<Long, QuestionContextCacheDTO> sectionQuestionsMap = assessmentQuestionPaperFacade.getSectionQuestions(assessmentInstanceId);

        Map<Long, QuestionContextCacheDTO> questionMap = sectionQuestionsMap.values().stream().collect(Collectors.toMap(QuestionContextCacheDTO::getQuestionId, Function.identity()));
        Map<Long, Set<OptionCacheDTO>> options = questionFacade.getOptions(questionMap.keySet());
        for (Map.Entry<Long, Set<OptionCacheDTO>> questionOptions : options.entrySet()) {
            QuestionContextCacheDTO questionContextCacheDTO = questionMap.get(questionOptions.getKey());
            questionContextCacheDTO.setOptions(questionOptions.getValue());
        }
        int limitLevels = 100;
        Set<Long> childQuestionIds = questionMap.keySet();
        do {
            childQuestionIds = questionFacade.getChildQuestionIds(childQuestionIds);
            limitLevels--;
            if (childQuestionIds.isEmpty()) {
                break;
            }
            if (limitLevels == 0) {
                throw new DataAccessException("Child Question hierarchy too deep");
            }
            Map<Long, Set<OptionCacheDTO>> childQuestionOptions = questionFacade.getOptions(childQuestionIds);
            for (Long childQuestion : childQuestionIds) {
                childQuestionMap.put(childQuestion, QuestionContextCacheDTO.builder()
                        .questionId(childQuestion)
                        .isMandatory(Boolean.FALSE)
                        .isChildQuestion(Boolean.TRUE)
                        .options(childQuestionOptions.get(childQuestion))
                        .build());
            }
        } while (true);

        return AssessmentContextCachedDTO.builder()
                .assessmentInstanceId(assessmentInstanceId)
                .sectionQuestionsMap(sectionQuestionsMap)
                .childQuestionMap(childQuestionMap)
                .config(config)
                .build();
    }
}

