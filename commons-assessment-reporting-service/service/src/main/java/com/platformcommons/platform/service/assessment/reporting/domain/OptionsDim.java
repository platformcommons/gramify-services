package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "options_dim")
@NoArgsConstructor
@Getter @Setter
public class OptionsDim extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_question",columnDefinition = "TEXT")
    private String childQuestion;

    @Column(name = "default_option_id")
    private Long defaultOptionId;

    @Column
    private Long sequence;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "option_code")
    private String optionCode;

    @Column(name = "option_text", columnDefinition = "TEXT")
    private String optionText;

    @Column(name = "option_weight")
    private Double optionWeight;

    @Column(name = "options_id")
    private Long optionsId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "language")
    private String language;

    @Column(name = "response_count")
    private Long responseCount;

    @Column(name = "dim_type")
    private String dimType;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "mtf_option_id")
    private Long mtfOptionId;

    @Column(name = "parent_question_id")
    private Long parentQuestionId;

    @Column(name = "child_default_option_id")
    private Long childDefaultOptionId;

    @Builder
    public OptionsDim(Long id, String childQuestion, Long defaultOptionId, Boolean isCorrect, String optionCode, String optionText, Double optionWeight, Long optionsId, Long questionId, Long tenantId, String language, Long responseCount, String dimType, Long createdAt, Long createdBy, Long mtfOptionId, Long parentQuestionId,Long childDefaultOptionId,Long sequence) {
        this.id = id;
        this.childQuestion = childQuestion;
        this.defaultOptionId = defaultOptionId;
        this.isCorrect = isCorrect;
        this.optionCode = optionCode;
        this.optionText = optionText;
        this.optionWeight = optionWeight;
        this.optionsId = optionsId;
        this.questionId = questionId;
        this.tenantId = tenantId;
        this.language = language;
        this.responseCount = responseCount;
        this.dimType = dimType;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.mtfOptionId = mtfOptionId;
        this.childDefaultOptionId = childDefaultOptionId;
        this.parentQuestionId = parentQuestionId;
        this.sequence = sequence;
    }

    public void init(){
        this.id = 0L;
    }
    public void update(OptionsDim dim){
        setOptionsId(dim.getOptionsId());
        setQuestionId(dim.getQuestionId());
        setMtfOptionId(dim.getMtfOptionId());
        setSequence(dim.getSequence());
        setOptionText(dim.getOptionText());
        setOptionWeight(dim.getOptionWeight());
        setOptionCode(dim.getOptionCode());
        setIsCorrect(dim.getIsCorrect());
        setChildQuestion(dim.getChildQuestion());
    }

}
