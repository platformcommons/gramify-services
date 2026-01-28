package com.platformcommons.platform.service.assessment.reporting.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "section_question_dim")
@Getter @Setter
@NoArgsConstructor
public class SectionQuestionDim extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long sectionQuestionId;

    @Column
    private Long questionId;

    @Column
    private Long questionPaperId;

    @Column
    private Long questionPaperSectionId;

    @Column
    private Long assessmentId;

    @Column
    private Long createdAt;

    @Column
    private Long createdBy;

    @Column
    private Long sequence;

    @Column
    private String description;

    @Column
    private Double totalWeight;

    @Column
    private Long totalQuestions;

    @Column
    private Boolean mandatoryQuestion;

    @Column
    private String language;

    @Column
    private Long tenantId;

    @Column
    private String dimType;

    @Builder
    public SectionQuestionDim(Long id, Long sectionQuestionId, Long questionId, Long questionPaperId, Long questionPaperSectionId, Long assessmentId, Long createdAt, Long createdBy, Long sequence, String description, Double totalWeight, Long totalQuestions, Boolean mandatoryQuestion, String language, Long tenantId, String dimType) {
        this.id = id;
        this.sectionQuestionId = sectionQuestionId;
        this.questionId = questionId;
        this.questionPaperId = questionPaperId;
        this.questionPaperSectionId = questionPaperSectionId;
        this.assessmentId = assessmentId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.sequence = sequence;
        this.description = description;
        this.totalWeight = totalWeight;
        this.totalQuestions = totalQuestions;
        this.mandatoryQuestion = mandatoryQuestion;
        this.language = language;
        this.tenantId = tenantId;
        this.dimType = dimType;
    }
}
