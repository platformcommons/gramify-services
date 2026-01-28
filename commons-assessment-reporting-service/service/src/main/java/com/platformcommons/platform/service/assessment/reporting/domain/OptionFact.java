package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "option_fact")
@NoArgsConstructor
@Getter @Setter
public class OptionFact extends BaseReportEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long assessmentInstanceId;
    private Long assessmentId;
    private Long questionPaperId;
    private Long questionPaperSectionId;
    private Long sectionQuestionId;
    private Long questionId;
    private Long defaultOptionId;
    private Long optionsId;
    private Long tenantId;
    private Long responseCount;
    private String dimType;
    private Long parentQuestionId;
    private Long childDefaultOptionId;
    private Long mtfOptionId;

    @Builder
    public OptionFact(Long id, Long assessmentInstanceId, Long assessmentId, Long questionPaperId, Long questionPaperSectionId, Long sectionQuestionId, Long questionId, Long defaultOptionId, Long optionsId, Long tenantId, Long responseCount, String dimType,Long parentQuestionId, Long childDefaultOptionId,Long mtfOptionId) {
        this.id = id;
        this.assessmentInstanceId = assessmentInstanceId;
        this.assessmentId = assessmentId;
        this.questionPaperId = questionPaperId;
        this.questionPaperSectionId = questionPaperSectionId;
        this.sectionQuestionId = sectionQuestionId;
        this.questionId = questionId;
        this.defaultOptionId = defaultOptionId;
        this.optionsId = optionsId;
        this.tenantId = tenantId;
        this.responseCount = responseCount;
        this.dimType = dimType;
        this.childDefaultOptionId = childDefaultOptionId;
        this.parentQuestionId = parentQuestionId;
        this.mtfOptionId = mtfOptionId;
    }

    public OptionFact duplicate() {
        return OptionFact.builder()
                .id(0L)
                .assessmentInstanceId(this.assessmentInstanceId)
                .assessmentId(this.assessmentId)
                .questionPaperId(this.questionPaperId)
                .questionPaperSectionId(this.questionPaperSectionId)
                .sectionQuestionId(this.sectionQuestionId)
                .tenantId(this.tenantId)
                .responseCount(this.responseCount)
                .dimType(this.dimType)
                .build();
    }
}
