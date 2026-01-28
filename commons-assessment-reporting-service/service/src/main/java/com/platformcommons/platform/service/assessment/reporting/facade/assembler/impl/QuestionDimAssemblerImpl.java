package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.MimicMLTextDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.QuestionDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class QuestionDimAssemblerImpl implements QuestionDimAssembler {

    @Autowired
    private UtilityAssembler utilityAssembler;
    @Override
    public Set<QuestionDim> toQuestionDim(QuestionDTO questionDTO) {

        if(questionDTO==null){
            return new HashSet<>();
        }

        Set<QuestionDim> set = new HashSet<>();

        Long correctOptions = utilityAssembler.getCorrectOptions(questionDTO);
        Long options        = utilityAssembler.getOptionCount(questionDTO);
        String subtype      = utilityAssembler.getRefDataCode(questionDTO.getQuestionSubtype());
        String type         = utilityAssembler.getRefDataCode(questionDTO.getQuestionType());

        if(questionDTO.getQuestionText()==null || questionDTO.getQuestionText().isEmpty()) {
            set.add(toQuestionDimBuilder(questionDTO,subtype,type,options,correctOptions).build());
            return set;
        }
        else{
            for (MimicMLTextDTO mimicMLTextDTO : questionDTO.getQuestionText()) {
                set.add(toQuestionDimBuilder(questionDTO,subtype,type,options,correctOptions)
                        .questionText(mimicMLTextDTO.getText())
                        .language(mimicMLTextDTO.getLanguage()==null?null:mimicMLTextDTO.getLanguage().getCode())
                        .build());
            }
        }
        return set;
    }

    @Override
    public void updateQuestionDim(QuestionDTO questionDTO, QuestionDim questionDim) {
        questionDim.setQuestionCode(questionDTO.getQuestionCode());
        questionDim.setQuestionDomain(questionDTO.getDomain());
        questionDim.setQuestionSubDomain(questionDTO.getSubDomain());
        questionDim.setQuestionType(utilityAssembler.getRefDataCode(questionDTO.getQuestionType()));
        questionDim.setQuestionSubtype(utilityAssembler.getRefDataCode(questionDTO.getQuestionSubtype()));
        questionDim.setQuestionText(utilityAssembler.filterMLTexts(questionDTO.getQuestionText(),questionDim.getLanguage()));
        questionDim.setQuestionWeight(questionDTO.getWeight());
        questionDim.setOptions(utilityAssembler.getOptionCount(questionDTO));
        questionDim.setCorrectOptions(utilityAssembler.getCorrectOptions(questionDTO));
        questionDim.setSkillIds(questionDTO.getLtldskillList());
        questionDim.setSkillCode(questionDTO.getSkillCode());
        questionDim.setSubSkillCode(questionDTO.getSubSkillCode());

    }

    @Override
    public QuestionDim toQuestionDim(QuestionDTO questionDTO, String language) {
        Long correctOptions = utilityAssembler.getCorrectOptions(questionDTO);
        Long options        = utilityAssembler.getOptionCount(questionDTO);
        String subtype      = utilityAssembler.getRefDataCode(questionDTO.getQuestionSubtype());
        String type         = utilityAssembler.getRefDataCode(questionDTO.getQuestionType());
        return toQuestionDimBuilder(questionDTO,subtype,type,options,correctOptions)
                .questionText(utilityAssembler.filterMLTexts(questionDTO.getQuestionText(),language))
                .language(null)
                .build();
    }

    @Override
    public QuestionDimDTO toDTO(QuestionDim questionDim) {
        return QuestionDimDTO.builder()
                .id(questionDim.getId())
                .createAt(questionDim.getCreateAt())
                .createBy(questionDim.getCreateBy())
                .questionId(questionDim.getQuestionId())
                .questionCode(questionDim.getQuestionCode())
                .questionDomain(questionDim.getQuestionDomain())
                .questionSubDomain(questionDim.getQuestionSubDomain())
                .questionType(questionDim.getQuestionType())
                .questionSubtype(questionDim.getQuestionSubtype())
                .questionText(questionDim.getQuestionText())
                .questionWeight(questionDim.getQuestionWeight())
                .options(questionDim.getOptions())
                .correctOptions(questionDim.getCorrectOptions())
                .language(questionDim.getLanguage())
                .tenantId(questionDim.getTenantId())
                .dimType(questionDim.getDimType())
                .skillIds(questionDim.getSkillIds())
                .parentQuestionId(questionDim.getParentQuestionId())
                .childDefaultOptionId(questionDim.getChildDefaultOptionId())
                .subSkillCode(questionDim.getSubSkillCode())
                .skillCode(questionDim.getSkillCode())
                .build();
    }

    private QuestionDim.QuestionDimBuilder toQuestionDimBuilder(QuestionDTO questionDTO, String subtype, String type, Long options, Long correctOptions){
        return QuestionDim.builder()
                .questionId(questionDTO.getId())
                .questionCode(questionDTO.getQuestionCode())
                .questionSubtype(subtype)
                .questionType(type)
                .questionDomain(questionDTO.getDomain())
                .questionSubDomain(questionDTO.getSubDomain())
                .createBy(PlatformSecurityUtil.getCurrentUserId())
                .questionWeight(questionDTO.getWeight())
                .options(options)
                .correctOptions(correctOptions)
                .tenantId(PlatformSecurityUtil.getCurrentTenantId())
                .skillIds(questionDTO.getLtldskillList())
                .createAt(questionDTO.getCreatedAt())
                .createBy(questionDTO.getCreatedBy())
                .subSkillCode(questionDTO.getSubSkillCode())
                .skillCode(questionDTO.getSkillCode())
                .dimType(DimType.NONE);
    }

}