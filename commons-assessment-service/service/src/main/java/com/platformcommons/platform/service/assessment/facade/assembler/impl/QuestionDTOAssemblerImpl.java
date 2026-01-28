package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.*;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionDTOAssemblerImpl implements QuestionDTOAssembler {

    private final MimicMLTextDTOAssembler mLTextDTOAssembler;
    private final DefaultOptionsDTOAssembler defaultOptionsDTOAssembler;
    private final MimicRefDataDTOAssembler refDataDTOAssembler;
    private final MTFOptionDTOAssembler mtfOptionDTOAssembler;
    private final ConfigDTOAssembler configDTOAssembler;
    private final RefDataDTOValidator refDataDTOValidator;

    @Override
    public Question fromDTO(QuestionDTO questionDTO) {
        if (questionDTO == null) return null;
        return Question.builder()
                .uuid(questionDTO.getUuid())
                .appCreatedTimestamp(questionDTO.getAppCreatedAt())
                .appLastModifiedTimestamp(questionDTO.getAppLastModifiedAt())
                .id(questionDTO.getId())
                .questionType(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(questionDTO.getQuestionType())))
                .questionSubtype(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(questionDTO.getQuestionSubtype())))
                .complexityLevel(questionDTO.getComplexityLevel())
                .weight(questionDTO.getWeight())
                .domain(questionDTO.getDomain())
                .tenant(questionDTO.getTenant())
                .questionCode(questionDTO.getQuestionCode())
                .questionClass(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(questionDTO.getQuestionClass())))
                .frequencyindays(questionDTO.getFrequencyINDays())
                .timeToRectifyInDays(questionDTO.getTimeToRectifyInDays())
                .questionName(mLTextDTOAssembler.fromDTOs(questionDTO.getQuestionName()))
                .questionText(mLTextDTOAssembler.fromDTOs(questionDTO.getQuestionText()))
                .questionSubText(mLTextDTOAssembler.fromDTOs(questionDTO.getQuestionSubText()))
                .questionMeta(questionDTO.getQuestionMeta())
                .questionImageurl(questionDTO.getQuestionImageURL())
                .ltldSkillList(questionDTO.getLtldskillList())
                .skill(questionDTO.getSkill())
                .validationMessage(questionDTO.getValidationMessage())
                .responseValidation(questionDTO.getResponseValidation())
                .subSkill(questionDTO.getSubSkill())
                .hintText(questionDTO.getHintText())
                .optionSourceType(questionDTO.getOptionSourceType())
                .optionSourceValue(questionDTO.getOptionSourceValue())
                .defaultParamForOptionSource(questionDTO.getDefaultParamForOptionSource())
                .defaultOptionsList(questionDTO.getDefaultOptionsList() != null ?
                        questionDTO.getDefaultOptionsList().stream()
                                .map(defaultOptionsDTOAssembler::fromDTO)
                                .collect(Collectors.toCollection(LinkedHashSet::new)) : null)
                .mtfoptionList(questionDTO.getMtfoptionList()!=null?
                        questionDTO.getMtfoptionList().stream()
                                .map(mtfOptionDTOAssembler::fromDTO)
                                .collect(Collectors.toSet()):null)
                .questionConfigs(!CollectionUtils.isEmpty(questionDTO.getQuestionConfigs())?
                        questionDTO.getQuestionConfigs().stream()
                                .map(configDTOAssembler::fromDTOToQuestionConfig)
                                .collect(Collectors.toCollection(LinkedHashSet::new)):new LinkedHashSet<>()
                        )
                .subDomain(questionDTO.getSubDomain())
                .subSkillCode(questionDTO.getSubSkillCode())
                .skillCode(questionDTO.getSkillCode())
                .isMandatory(questionDTO.getIsMandatory())
                .build();
    }

    @Override
    public QuestionDTO toDTO(Question question) {
        if (question == null) {
            return null;
        }
        Set<Long> set1 = question.getLtldSkillList() == null ? new HashSet<>() : question.getLtldSkillList();
        return QuestionDTO.builder()
                .uuid(question.getUuid())
                .appCreatedAt(question.getAppCreatedTimestamp())
                .appLastModifiedAt(question.getAppLastModifiedTimestamp())
                .tenant(question.getTenantId())
                .id(question.getId())
                .complexityLevel(question.getComplexityLevel())
                .domain(question.getDomain())
                .frequencyINDays(question.getFrequencyindays())
                .questionClass(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(question.getQuestionClass())))
                .questionCode(question.getQuestionCode())
                .questionName(mLTextDTOAssembler.toDTOs(question.getQuestionName()))
                .questionSubtype(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(question.getQuestionSubtype())))
                .questionText(mLTextDTOAssembler.toDTOs(question.getQuestionText()))
                .questionSubText(mLTextDTOAssembler.toDTOs(question.getQuestionSubText()))
                .questionType(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(question.getQuestionType())))
                .responseUOMCode(question.getResponseuomcode())
                .tenant(question.getTenant())
                .timeToRectifyInDays(question.getTimeToRectifyInDays())
                .weight(question.getWeight())
                .questionMeta(question.getQuestionMeta())
                .questionImageURL(question.getQuestionImageurl())
                .skill(question.getSkill())
                .subSkill(question.getSubSkill())
                .subDomain(question.getSubDomain())
                .duplicatedCount(question.getDuplicatedCount())
                .duplicatedFrom(question.getDuplicatedFrom())
                .isQuestionModified(question.getIsQuestionModified())
                .ltldskillList(set1)
                .hintText(question.getHintText())
                .optionSourceType(question.getOptionSourceType())
                .optionSourceValue(question.getOptionSourceValue())
                .defaultParamForOptionSource(question.getDefaultParamForOptionSource())
                .validationMessage(question.getValidationMessage())
                .responseValidation(question.getResponseValidation())
                .defaultOptionsList(question.getDefaultOptionsList() != null ?
                        question.getDefaultOptionsList().stream()
                                .map(defaultOptionsDTOAssembler::toDTO)
                                .collect(Collectors.toCollection(LinkedHashSet::new)) : null)
                .mtfoptionList(question.getMtfoptionList() != null ?
                        question.getMtfoptionList().stream()
                                .map(mtfOptionDTOAssembler::toDTO)
                                .collect(Collectors.toCollection(LinkedHashSet::new)) : null)
                .questionConfigs(!CollectionUtils.isEmpty(question.getQuestionConfigs())?
                        question.getQuestionConfigs().stream()
                                .map(configDTOAssembler::toDTO)
                                .collect(Collectors.toCollection(LinkedHashSet::new)):new LinkedHashSet<>()
                        )
                .subSkillCode(question.getSubSkillCode())
                .skillCode(question.getSkillCode())
                .createdAt(question.getCreatedTimestamp())
                .createdBy(question.getCreatedByUser())
                .tenantId(question.getTenantId())
                .isMandatory(question.getIsMandatory())
                .build();
    }
}
