package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.service.assessment.application.DuplicateEntityService;
import com.platformcommons.platform.service.assessment.application.QuestionService;
import com.platformcommons.platform.service.assessment.application.cache.AssessmentContextCacheManger;
import com.platformcommons.platform.service.assessment.dto.OptionCacheDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.facade.OptionFacade;
import com.platformcommons.platform.service.assessment.facade.QuestionFacade;
import com.platformcommons.platform.service.assessment.facade.assembler.QuestionDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Transactional
public class QuestionFacadeImpl implements QuestionFacade {

    private final QuestionService service;
    private final QuestionDTOAssembler assembler;
    private final DuplicateEntityService duplicateEntityService;
    private final AssessmentContextCacheManger cacheManger;
    private final OptionFacade optionFacade;
    private final QuestionService questionService;

    @Override
    public Boolean checkQuestionResponseExists(Long questionId) {
        return service.checkQuestionResponseExists(questionId);
    }

    @Override
    public QuestionDTO createQuestionWithResponse(QuestionDTO questionDTO) {
        return assembler.toDTO(service.createQuestionWithResponse(assembler.fromDTO(questionDTO)));
    }

    @Override
    public QuestionDTO updateQuestionWithResponse(QuestionDTO questionDTO) {
        questionDTO = assembler.toDTO(service.updateQuestionWithResponse(assembler.fromDTO(questionDTO)));
        cacheManger.addToCacheByQuestionId(questionDTO.getId());
        return questionDTO;
    }

    @Override
    public Set<QuestionDTO> getQuestionByIdsAndInstanceId(Set<Long> questionIds, Long instanceId) {
        return service.getQuestionByIdsAndInstanceId(questionIds, instanceId)
                .stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Set<QuestionDTO> getChildQuestion(Set<Long> questionIds, Long instanceId) {
        return service.getChildQuestion(questionIds, instanceId)
                .stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public PageDTO<QuestionDTO> searchByQuestionByQuestionText(String domain, String subDomain, String questionText, String questionType, String questionSubType, String sortBy, String sortOrder, String language, Integer page, Integer size) {
        return service.searchByQuestionByQuestionText(domain, subDomain, questionText, questionType, questionSubType, sortBy, sortOrder, language, page, size);
    }

    @Override
    public QuestionDTO duplicateQuestion(Long questionId) {
        return assembler.toDTO(duplicateEntityService.duplicateQuestion(questionId));
    }

    @Override
    public String createAssessmentQuestionAttachment(MultipartFile file, Long entityId) {
        return service.createAssessmentAttachment(file, entityId);
    }

    @Override
    public Set<QuestionDTO> getQuestionByIds(Set<Long> ids) {
        return service.getQuestionByIds(ids).stream().map(assembler::toDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<QuestionDTO> getQuestionByIdsV2(Set<Long> questionIds) {
        return service.getQuestionByIdsV2(questionIds).stream().map(assembler::toDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<QuestionDTO> getQuestionByIdsV3(Set<Long> questionIds) {
        return service.getQuestionByIdsV3(questionIds).stream().map(assembler::toDTO).collect(Collectors.toSet());
    }

    @Override
    public Map<Long, Set<OptionCacheDTO>> getOptions(Set<Long> questionIds) {
        return optionFacade.getOptions(questionIds)
                .stream()
                .map(optionVO -> Pair.of(optionVO.getQuestionId(), OptionCacheDTO.builder()
                        .optionId(optionVO.getOptionId())
                        .mtfOptionId(optionVO.getMtfOptionId())
                        .defaultOptionId(optionVO.getDefaultOptionId())
                        .build()))
                .collect(Collectors.groupingBy(Pair::getLeft, HashMap::new, Collectors.mapping(Pair::getRight,Collectors.toSet())));
    }

    @Override
    public Set<Long> getChildQuestionIds(Set<Long> questionIds) {
        return questionService.getChildQuestionIds(questionIds);
    }


}
