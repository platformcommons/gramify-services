package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.DefaultOptionsDTO;
import com.platformcommons.platform.service.assessment.dto.MTFOptionDTO;
import com.platformcommons.platform.service.assessment.dto.MimicMLTextDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.OptionsDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OptionsDimAssemblerImpl implements OptionsDimAssembler {

    private final UtilityAssembler utilityAssembler;

    @Override
    public Set<OptionsDim> toOptionDims(QuestionDTO questionDTO) {
        Set<OptionsDim> optionsDims = new HashSet<>();
        if ((questionDTO.getDefaultOptionsList() == null || questionDTO.getDefaultOptionsList().isEmpty()) &&
                (questionDTO.getMtfoptionList() == null || questionDTO.getMtfoptionList().isEmpty())
        ) return optionsDims;

        if (questionDTO.getDefaultOptionsList() != null && !questionDTO.getDefaultOptionsList().isEmpty()) {
            for (DefaultOptionsDTO defaultOptionsDTO : questionDTO.getDefaultOptionsList()) {
                optionsDims.addAll(toOptionDims(questionDTO, defaultOptionsDTO));
            }
        }
        if (questionDTO.getMtfoptionList() != null && !questionDTO.getMtfoptionList().isEmpty()) {
            for (MTFOptionDTO mtfOptionDTO : questionDTO.getMtfoptionList()) {
                optionsDims.add(toOptionDims(questionDTO, mtfOptionDTO));
            }
        }
        return optionsDims;
    }

    private OptionsDim toOptionDims(QuestionDTO questionDTO, MTFOptionDTO mtfOptionDTO) {
        return OptionsDim.builder()
                .id(0L)
                .childQuestion(null)
                .defaultOptionId(null)
                .isCorrect(null)
                .optionCode(null)
                .optionWeight(mtfOptionDTO.getWeight())
                .optionsId(null)
                .questionId(questionDTO.getId())
                .tenantId(mtfOptionDTO.getTenantId())
                .responseCount(0L)
                .createdAt(mtfOptionDTO.getCreatedAt())
                .createdBy(mtfOptionDTO.getCreatedBy())
                .dimType(DimType.NONE).build();
    }


    @Override
    public Set<OptionsDim> toOptionDims(QuestionDTO questionDTO, DefaultOptionsDTO defaultOptionsDTO) {
        Set<OptionsDim> optionsDims = new HashSet<>();
        String childQuestionIds = utilityAssembler.getChildQuestionId(defaultOptionsDTO);
        if (defaultOptionsDTO.getOptions().getOptionName() == null || defaultOptionsDTO.getOptions().getOptionName().isEmpty()) {
            optionsDims.add(toOptionsDimBuilder(defaultOptionsDTO, questionDTO.getId(), childQuestionIds).build());
        } else {
            for (MimicMLTextDTO mimicMLTextDTO : defaultOptionsDTO.getOptions().getOptionName()) {
                optionsDims.add(
                        toOptionsDimBuilder(defaultOptionsDTO, questionDTO.getId(), childQuestionIds)
                                .optionText(mimicMLTextDTO.getText())
                                .sequence(defaultOptionsDTO.getOrderNo())
                                .language(utilityAssembler.getLanguageCode(mimicMLTextDTO))
                                .build()
                );
            }
        }
        return optionsDims;
    }

    @Override
    public OptionsDim toOptionDim(QuestionDTO questionDTO, DefaultOptionsDTO defaultOptionsDTO, String language) {
        return null;
    }

    @Override
    public void updateOptionDim(QuestionDTO questionDTO, DefaultOptionsDTO defaultOptionsDTO, OptionsDim dim) {
        dim.setChildQuestion(utilityAssembler.getChildQuestionId(defaultOptionsDTO));
        dim.setIsCorrect(defaultOptionsDTO.getIsCorrect());
        dim.setOptionCode(defaultOptionsDTO.getOptions().getOptionCode());
        dim.setOptionText(utilityAssembler.getOptionText(defaultOptionsDTO.getOptions(), dim.getLanguage()));
        dim.setOptionWeight(defaultOptionsDTO.getWeight());
    }

    private OptionsDim.OptionsDimBuilder toOptionsDimBuilder(DefaultOptionsDTO defaultOptionsDTO, Long questionId, String childQuestionIds) {
        return OptionsDim.builder()
                .id(0L)
                .childQuestion(childQuestionIds)
                .defaultOptionId(defaultOptionsDTO.getId())
                .isCorrect(defaultOptionsDTO.getIsCorrect())
                .optionCode(defaultOptionsDTO.getOptions().getOptionCode())
                .optionWeight(defaultOptionsDTO.getWeight())
                .optionsId(defaultOptionsDTO.getOptions().getId())
                .questionId(questionId)
                .tenantId(defaultOptionsDTO.getTenantId())
                .responseCount(0L)
                .createdAt(defaultOptionsDTO.getCreatedAt())
                .createdBy(defaultOptionsDTO.getCreatedBy())
                .dimType(DimType.NONE);
    }

}
