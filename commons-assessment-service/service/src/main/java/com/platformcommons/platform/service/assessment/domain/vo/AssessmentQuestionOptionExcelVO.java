package com.platformcommons.platform.service.assessment.domain.vo;

import com.platformcommons.platform.service.sdk.bulkupload.domain.model.BaseExcelVO;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.ExcelColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssessmentQuestionOptionExcelVO extends BaseExcelVO {

    @ExcelColumn(name = "QUESTION_CODE")
    private String questionCode;


    @ExcelColumn(name = "OPTION_CODE")
    private String optionCode;


    @ExcelColumn(name = "SEQUENCE")
    private Long sequence;


    @ExcelColumn(name = "OPTION_TEXT")
    private String optionText;

    @ExcelColumn(name = "OPTION_TEXT_TAM")
    private String optionTextTamil;

    @ExcelColumn(name = "OPTION_TEXT_TEL")
    private String optionTextTelugu;

    @ExcelColumn(name = "OPTION_TEXT_KAN")
    private String optionTextKannada;

    @ExcelColumn(name = "OPTION_TEXT_MAR")
    private String optionTextMarathi;

    @ExcelColumn(name = "OPTION_TEXT_HIN")
    private String optionTextHindi;

    @ExcelColumn(name = "OPTION_TEXT_GUJ")
    private String optionTextGujarati;

    @ExcelColumn(name = "OPTION_TEXT_ODIA")
    private String optionTextOdia;

    @ExcelColumn(name = "OPTION_WEIGHT")
    private Double optionWeight;


    @ExcelColumn(name = "CHILD_QUESTION_CODE")
    private String childQuestionCode;


    @ExcelColumn(name = "IS_CORRECT")
    private Boolean isCorrect;

    @ExcelColumn(name = "REMARKS_REQUIRED")
    private Boolean remarksRequired;

    @ExcelColumn(name = "IS_CHILD_OPTION")
    private Boolean isChildOption;

    @ExcelColumn(name = "PARENT_OPTION_CODE")
    private String parentOptionCode;

    @Builder
    public AssessmentQuestionOptionExcelVO(Long rowNumber, String questionCode, String optionCode, Long sequence,
                                           String optionText, String optionTextTamil, String optionTextTelugu,
                                           String optionTextKannada, String optionTextMarathi, String optionTextHindi,
                                           String optionTextGujarati, String optionTextOdia, Double optionWeight,
                                           String childQuestionCode, Boolean isCorrect, Boolean remarksRequired,
                                           Boolean isChildOption, String parentOptionCode) {
        super(rowNumber);
        this.questionCode = questionCode;
        this.optionCode = optionCode;
        this.sequence = sequence;
        this.optionText = optionText;
        this.optionTextTamil = optionTextTamil;
        this.optionTextTelugu = optionTextTelugu;
        this.optionTextKannada = optionTextKannada;
        this.optionTextMarathi = optionTextMarathi;
        this.optionTextHindi = optionTextHindi;
        this.optionTextGujarati = optionTextGujarati;
        this.optionTextOdia = optionTextOdia;
        this.optionWeight = optionWeight;
        this.childQuestionCode = childQuestionCode;
        this.isCorrect = isCorrect;
        this.remarksRequired = remarksRequired;
        this.isChildOption = isChildOption;
        this.parentOptionCode = parentOptionCode;
        init();
    }

    public void init() {
        if (questionCode!=null && questionCode.isEmpty()) this.questionCode = null;
        if (optionCode!=null && optionCode.isEmpty()) this.optionCode = null;
        if (optionText!=null && optionText.isEmpty()) this.optionText = null;
        if (optionTextTamil!=null && optionTextTamil.isEmpty()) this.optionTextTamil = null;
        if (optionTextTelugu!=null && optionTextTelugu.isEmpty()) this.optionTextTelugu = null;
        if (optionTextKannada!=null && optionTextKannada.isEmpty()) this.optionTextKannada = null;
        if (optionTextMarathi!=null && optionTextMarathi.isEmpty()) this.optionTextMarathi = null;
        if (optionTextHindi!=null && optionTextHindi.isEmpty()) this.optionTextHindi = null;
        if (optionTextGujarati!=null && optionTextGujarati.isEmpty()) this.optionTextGujarati = null;
        if (optionTextOdia!=null && optionTextOdia.isEmpty()) this.optionTextOdia = null;
        if (childQuestionCode!=null && childQuestionCode.isEmpty()) this.childQuestionCode = null;
        if (sequence == null) this.sequence = 0L;
        if (isCorrect == null) this.isCorrect = false;
        if (remarksRequired == null) this.remarksRequired = false;
        if (optionWeight == null) this.optionWeight = 0.0;
    }
}
