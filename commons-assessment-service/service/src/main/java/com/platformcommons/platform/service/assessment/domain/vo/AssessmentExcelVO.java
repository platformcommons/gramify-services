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
public class AssessmentExcelVO extends BaseExcelVO {

    @ExcelColumn(name = "DOMAIN_CODE")
    private String domain;

    @ExcelColumn(name = "SUB_DOMAIN_CODE")
    private String subDomain;

    @ExcelColumn(name = "ASSESSMENT_CODE")
    private String assessmentCode;

    @ExcelColumn(name = "LANGUAGE")
    private String baseLanguage;

    @ExcelColumn(name = "ASSESSMENT_TYPE")
    private String assessmentType;

    @ExcelColumn(name = "ASSESSMENT_SUB_TYPE")
    private String assessmentSubType;

    @ExcelColumn(name = "SUBJECT_CODE")
    private String subjectCode;

    @ExcelColumn(name = "ASSESSMENT_NAME")
    private String assessmentName;

    @ExcelColumn(name = "ASSESSMENT_KIND")
    private String assessmentKind;

    @ExcelColumn(name = "ASSESSMENT_SUB_KIND")
    private String assessmentSubKind;

    @ExcelColumn(name = "ASSESSMENT_NAME_TAM")
    private String assessmentNameTamil;

    @ExcelColumn(name = "ASSESSMENT_NAME_TEL")
    private String assessmentNameTelugu;

    @ExcelColumn(name = "ASSESSMENT_NAME_KAN")
    private String assessmentNameKannada;

    @ExcelColumn(name = "ASSESSMENT_NAME_MAR")
    private String assessmentNameMarathi;

    @ExcelColumn(name = "ASSESSMENT_NAME_HIN")
    private String assessmentNameHindi;

    @ExcelColumn(name = "ASSESSMENT_NAME_GUJU")
    private String assessmentNameGujarati;

    @ExcelColumn(name = "ASSESSMENT_NAME_ODIA")
    private String assessmentNameOdia;

    @ExcelColumn(name = "ASSESSMENT_DESC")
    private String assessmentDesc;

    @ExcelColumn(name = "ASSESSMENT_DESC_TAM")
    private String assessmentDescTamil;

    @ExcelColumn(name = "ASSESSMENT_DESC_TEL")
    private String assessmentDescTelugu;

    @ExcelColumn(name = "ASSESSMENT_DESC_KAN")
    private String assessmentDescKannada;

    @ExcelColumn(name = "ASSESSMENT_DESC_MAR")
    private String assessmentDescMarathi;

    @ExcelColumn(name = "ASSESSMENT_DESC_HIN")
    private String assessmentDescHindi;

    @ExcelColumn(name = "ASSESSMENT_DESC_GUJU")
    private String assessmentDescGujarati;

    @ExcelColumn(name = "ASSESSMENT_DESC_ODIA")
    private String assessmentDescOdia;

    @ExcelColumn(name = "TOTAL_MARKS")
    private Double totalMarks;

    @ExcelColumn(name = "CONTEXT")
    private String context;


    @Builder
    public AssessmentExcelVO(Long rowNumber, String domain, String subDomain, String assessmentCode, String baseLanguage,
                             String assessmentType, String subjectCode, String assessmentName,
                             String assessmentNameHindi, String assessmentNameGujarati, String assessmentNameOdia,
                             String assessmentDesc, String assessmentDescHindi, String assessmentDescGujarati,
                             String assessmentDescOdia, Double totalMarks, String context,
                             String assessmentDescTamil, String assessmentDescTelugu, String assessmentDescKannada,
                             String assessmentDescMarathi,  String assessmentNameTamil, String assessmentNameTelugu,
                             String assessmentNameKannada, String assessmentNameMarathi,String assessmentKind,String assessmentSubKind,
                             String assessmentSubType) {
        super(rowNumber);
        this.domain = domain;
        this.subDomain = subDomain;
        this.assessmentCode = assessmentCode;
        this.baseLanguage = baseLanguage;
        this.assessmentType = assessmentType;
        this.subjectCode = subjectCode;
        this.assessmentName = assessmentName;
        this.assessmentNameHindi = assessmentNameHindi;
        this.assessmentNameGujarati = assessmentNameGujarati;
        this.assessmentNameOdia = assessmentNameOdia;
        this.assessmentDesc = assessmentDesc;
        this.assessmentDescHindi = assessmentDescHindi;
        this.assessmentDescGujarati = assessmentDescGujarati;
        this.assessmentDescOdia = assessmentDescOdia;
        this.assessmentDescTamil = assessmentDescTamil;
        this.assessmentDescTelugu = assessmentDescTelugu;
        this.assessmentDescKannada = assessmentDescKannada;
        this.assessmentDescMarathi = assessmentDescMarathi;
        this.totalMarks = totalMarks;
        this.assessmentNameTamil = assessmentNameTamil;
        this.assessmentNameTelugu = assessmentNameTelugu;
        this.assessmentNameKannada = assessmentNameKannada;
        this.assessmentNameMarathi = assessmentNameMarathi;
        this.context = context;
        this.assessmentKind = assessmentKind;
        this.assessmentSubKind = assessmentSubKind;
        this.assessmentSubType=assessmentSubType;
        init();
    }

    public void init() {
        if(Objects.nonNull(this.domain) && this.domain.isEmpty())                                 this.domain = null;
        if(Objects.nonNull(this.subDomain) && this.subDomain.isEmpty())                           this.subDomain = null;
        if(Objects.nonNull(this.assessmentCode) && this.assessmentCode.isEmpty())                 this.assessmentCode = null;
        if(Objects.nonNull(this.baseLanguage) && this.baseLanguage.isEmpty())                     this.baseLanguage = null;
        if(Objects.nonNull(this.assessmentType) && this.assessmentType.isEmpty())                 this.assessmentType = null;
        if(Objects.nonNull(this.assessmentSubType) && this.assessmentSubType.isEmpty())           this.assessmentSubType = null;
        if(Objects.nonNull(this.subjectCode) && this.subjectCode.isEmpty())                       this.subjectCode = null;
        if(Objects.nonNull(this.assessmentName) && this.assessmentName.isEmpty())                 this.assessmentName = null;
        if(Objects.nonNull(this.assessmentKind) && this.assessmentKind.isEmpty())                 this.assessmentKind = null;
        if(Objects.nonNull(this.assessmentSubKind) && this.assessmentSubKind.isEmpty())           this.assessmentSubKind = null;
        if(Objects.nonNull(this.assessmentNameTamil) && this.assessmentNameTamil.isEmpty())       this.assessmentNameTamil = null;
        if(Objects.nonNull(this.assessmentNameTelugu) && this.assessmentNameTelugu.isEmpty())     this.assessmentNameTelugu = null;
        if(Objects.nonNull(this.assessmentNameKannada) && this.assessmentNameKannada.isEmpty())   this.assessmentNameKannada = null;
        if(Objects.nonNull(this.assessmentNameMarathi) && this.assessmentNameMarathi.isEmpty())   this.assessmentNameMarathi = null;
        if(Objects.nonNull(this.assessmentNameHindi) && this.assessmentNameHindi.isEmpty())       this.assessmentNameHindi = null;
        if(Objects.nonNull(this.assessmentNameGujarati) && this.assessmentNameGujarati.isEmpty()) this.assessmentNameGujarati = null;
        if(Objects.nonNull(this.assessmentNameOdia) && this.assessmentNameOdia.isEmpty())         this.assessmentNameOdia = null;
        if(Objects.nonNull(this.assessmentDesc) && this.assessmentDesc.isEmpty())                 this.assessmentDesc = null;
        if(Objects.nonNull(this.assessmentDescTamil) && this.assessmentDescTamil.isEmpty())       this.assessmentDescTamil = null;
        if(Objects.nonNull(this.assessmentDescTelugu) && this.assessmentDescTelugu.isEmpty())     this.assessmentDescTelugu = null;
        if(Objects.nonNull(this.assessmentDescKannada) && this.assessmentDescKannada.isEmpty())   this.assessmentDescKannada = null;
        if(Objects.nonNull(this.assessmentDescMarathi) && this.assessmentDescMarathi.isEmpty())   this.assessmentDescMarathi = null;
        if(Objects.nonNull(this.assessmentDescHindi) && this.assessmentDescHindi.isEmpty())       this.assessmentDescHindi = null;
        if(Objects.nonNull(this.assessmentDescGujarati) && this.assessmentDescGujarati.isEmpty()) this.assessmentDescGujarati = null;
        if(Objects.nonNull(this.assessmentDescOdia) && this.assessmentDescOdia.isEmpty())         this.assessmentDescOdia = null;
        if(Objects.nonNull(this.context) && this.context.isEmpty())                               this.context = null;
        if(Objects.nonNull(this.totalMarks) && this.totalMarks == 0)                              this.totalMarks = 0.0;
    }
}
