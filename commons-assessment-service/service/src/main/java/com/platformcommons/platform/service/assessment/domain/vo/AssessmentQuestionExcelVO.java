package com.platformcommons.platform.service.assessment.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class AssessmentQuestionExcelVO extends BaseExcelVO {


    @ExcelColumn(name = "DOMAIN_CODE")
    private String domain;

    @ExcelColumn(name = "SUB_DOMAIN_CODE")
    private String subDomain;

    @ExcelColumn(name = "ASSESSMENT_CODE")
    private String assessmentCode;

    @ExcelColumn(name = "SECTION")
    private String section;
    @ExcelColumn(name = "SECTION_TAM")
    private String sectionTamil;
    @ExcelColumn(name = "SECTION_TEL")
    private String sectionTelugu;
    @ExcelColumn(name = "SECTION_KAN")
    private String sectionKannada;
    @ExcelColumn(name = "SECTION_MAR")
    private String sectionMarathi;
    @ExcelColumn(name = "SECTION_HIN")
    private String sectionHindi;
    @ExcelColumn(name = "SECTION_GUJU")
    private String sectionGujarati;
    @ExcelColumn(name = "SECTION_ODIA")
    private String sectionOdia;

    @ExcelColumn(name = "SECTION_SEQUENCE")
    private Integer sectionSequence;

    @ExcelColumn(name = "QUESTION_SEQUENCE")
    private Integer questionSequence;

    @ExcelColumn(name = "QUESTION_CODE")
    private String questionCode;

    @ExcelColumn(name = "QUESTION_TYPE")
    private String questionType;

    @ExcelColumn(name = "QUESTION_SUB_TYPE")
    private String questionSubType;


    @ExcelColumn(name = "QUESTION_MANDATORY")
    private String mandatory;

    @ExcelColumn(name = "HINT_TEXT")
    private String hintText;

    @ExcelColumn(name = "QUESTION_DESC")
    private String questionDescription;

    @ExcelColumn(name = "QUESTION_NAME")
    private String questionName;

    @ExcelColumn(name = "QUESTION_NAME_TAM")
    private String questionNameTamil;

    @ExcelColumn(name = "QUESTION_NAME_TEL")
    private String questionNameTelugu;

    @ExcelColumn(name = "QUESTION_NAME_KAN")
    private String questionNameKannada;

    @ExcelColumn(name = "QUESTION_NAME_MAR")
    private String questionNameMarathi;

    @ExcelColumn(name = "QUESTION_NAME_HIN")
    private String questionNameHindi;

    @ExcelColumn(name = "QUESTION_NAME_ODIA")
    private String questionNameOdia;

    @ExcelColumn(name = "QUESTION_NAME_GUJU")
    private String questionNameGujarati;

    @ExcelColumn(name = "WEIGHT")
    private Double weight;

    @ExcelColumn(name = "CHILD_DEPTH")
    private Integer depth;

    @ExcelColumn(name = "SKILL")
    private String skill;
    @ExcelColumn(name = "SUB_SKILL")
    private String subSkill;

    @ExcelColumn(name = "OPTION_SOURCE_VALUE")
    private String optionSourceValue;

    @ExcelColumn(name = "OPTION_SOURCE_TYPE")
    private String optionSourceType;


    @Builder
    public AssessmentQuestionExcelVO(Long rowNumber, String domain, String subDomain, String assessmentCode, String section,
                                     String sectionHindi, String sectionGujarati, String sectionOdia, Integer sectionSequence,
                                     Integer questionSequence, String questionCode, String questionType, String questionSubType,
                                     String questionName, String questionNameHindi, String questionNameOdia,
                                     String questionNameGujarati, Double weight, Integer depth, String sectionTamil,
                                     String sectionTelugu, String sectionKannada, String sectionMarathi, String questionNameTamil,
                                     String questionNameTelugu, String questionNameKannada, String questionNameMarathi, String questionDescription,
                                     String hintText, String mandatory, String skill, String subSkill, Integer isMandatory) {
        super(rowNumber);
        this.domain = domain;
        this.subDomain = subDomain;
        this.assessmentCode = assessmentCode;
        this.section = section;
        this.sectionHindi = sectionHindi;
        this.sectionGujarati = sectionGujarati;
        this.sectionOdia = sectionOdia;
        this.sectionSequence = sectionSequence;
        this.questionSequence = questionSequence;
        this.questionCode = questionCode;
        this.questionType = questionType;
        this.questionSubType = questionSubType;
        this.questionName = questionName;
        this.questionNameHindi = questionNameHindi;
        this.questionNameOdia = questionNameOdia;
        this.questionNameGujarati = questionNameGujarati;
        this.weight = weight;
        this.depth = depth;
        this.sectionTamil = sectionTamil;
        this.sectionTelugu = sectionTelugu;
        this.sectionKannada = sectionKannada;
        this.sectionMarathi = sectionMarathi;
        this.questionNameTamil = questionNameTamil;
        this.questionNameTelugu = questionNameTelugu;
        this.questionNameKannada = questionNameKannada;
        this.questionNameMarathi = questionNameMarathi;
        this.questionDescription = questionDescription;
        this.hintText = hintText;
        this.mandatory = mandatory;
        this.skill = skill;
        this.subSkill = subSkill;
        this.optionSourceValue = optionSourceValue;
        this.optionSourceType = optionSourceType;
        init();
    }

    public void init() {
        if (Objects.nonNull(this.domain) && this.domain.isEmpty()) this.domain = null;
        if (Objects.nonNull(this.subDomain) && this.subDomain.isEmpty()) this.subDomain = null;
        if (Objects.nonNull(this.assessmentCode) && this.assessmentCode.isEmpty()) this.assessmentCode = null;
        if (Objects.nonNull(this.section) && this.section.isEmpty()) this.section = null;
        if (Objects.nonNull(this.sectionTamil) && this.sectionTamil.isEmpty()) this.sectionTamil = null;
        if (Objects.nonNull(this.sectionTelugu) && this.sectionTelugu.isEmpty()) this.sectionTelugu = null;
        if (Objects.nonNull(this.sectionKannada) && this.sectionKannada.isEmpty()) this.sectionKannada = null;
        if (Objects.nonNull(this.sectionMarathi) && this.sectionMarathi.isEmpty()) this.sectionMarathi = null;
        if (Objects.nonNull(this.sectionHindi) && this.sectionHindi.isEmpty()) this.sectionHindi = null;
        if (Objects.nonNull(this.sectionGujarati) && this.sectionGujarati.isEmpty()) this.sectionGujarati = null;
        if (Objects.nonNull(this.sectionOdia) && this.sectionOdia.isEmpty()) this.sectionOdia = null;
        if (Objects.nonNull(this.questionCode) && this.questionCode.isEmpty()) this.questionCode = null;
        if (Objects.nonNull(this.questionType) && this.questionType.isEmpty()) this.questionType = null;
        if (Objects.nonNull(this.questionSubType) && this.questionSubType.isEmpty()) this.questionSubType = null;
        if (Objects.nonNull(this.mandatory) && this.mandatory.isEmpty()) this.mandatory = null;
        if (Objects.nonNull(this.hintText) && this.hintText.isEmpty()) this.hintText = null;
        if (Objects.nonNull(this.questionDescription) && this.questionDescription.isEmpty()) this.questionDescription = null;
        if (Objects.nonNull(this.questionName) && this.questionName.isEmpty()) this.questionName = null;
        if (Objects.nonNull(this.questionNameTamil) && this.questionNameTamil.isEmpty()) this.questionNameTamil = null;
        if (Objects.nonNull(this.questionNameTelugu) && this.questionNameTelugu.isEmpty()) this.questionNameTelugu = null;
        if (Objects.nonNull(this.questionNameKannada) && this.questionNameKannada.isEmpty()) this.questionNameKannada = null;
        if (Objects.nonNull(this.questionNameMarathi) && this.questionNameMarathi.isEmpty()) this.questionNameMarathi = null;
        if (Objects.nonNull(this.questionNameHindi) && this.questionNameHindi.isEmpty()) this.questionNameHindi = null;
        if (Objects.nonNull(this.questionNameOdia) && this.questionNameOdia.isEmpty()) this.questionNameOdia = null;
        if (Objects.nonNull(this.questionNameGujarati) && this.questionNameGujarati.isEmpty()) this.questionNameGujarati = null;
        if (Objects.nonNull(this.skill) && this.skill.isEmpty()) this.skill = null;
        if (Objects.nonNull(this.subSkill) && this.subSkill.isEmpty()) this.subSkill = null;
        if (Objects.nonNull(this.optionSourceValue) && this.optionSourceValue.isEmpty()) this.optionSourceValue = null;
        if (Objects.nonNull(this.optionSourceType) && this.optionSourceType.isEmpty()) this.optionSourceType = null;
        if (Objects.isNull(sectionSequence)) this.sectionSequence = 0;
        if (Objects.isNull(questionSequence)) this.questionSequence = 0;
        if (Objects.isNull(depth)) this.depth = 0;
        if (Objects.isNull(weight)) this.weight = 0.0;

    }

    public boolean getMandatory() {
        return this.mandatory == null ||
                this.mandatory.equalsIgnoreCase("true") ||
                this.mandatory.equals("1") ||
                this.mandatory.equalsIgnoreCase("yes") || this.mandatory.equalsIgnoreCase("y");
    }

}
