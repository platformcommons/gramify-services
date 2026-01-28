package com.platformcommons.platform.service.assessment.reporting.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "question_paper_section_dim")
public class QuestionPaperSectionDim extends BaseReportEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_paper_id")
    private Long questionPaperId;

    @Column(name = "question_paper_section_id")
    private Long questionPaperSectionId;

    @Column(name = "assessment_id")
    private Long assessmentId;
    @Column(name = "assessment_instance_id")
    private Long assessmentInstanceId;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "name",columnDefinition = "TEXT")
    private String name;

    @Column(name = "text",columnDefinition = "TEXT")
    private String text;

    @Column(name = "total_weight")
    private Double totalWeight;

    @Column(name = "total_questions")
    private Long totalQuestions;

    @Column(name = "mandatory_questions")
    private Long mandatoryQuestions;

    @Column(name = "language")
    private String language;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "dim_type")
    private String dimType;

    @Builder
    public QuestionPaperSectionDim(Long id, Long questionPaperId, Long questionPaperSectionId, Long assessmentId, Long assessmentInstanceId, Long createdAt, Long createdBy, Long sequence, String description, String name, String text, Double totalWeight, Long totalQuestions, Long mandatoryQuestions, String language, Long tenantId, String dimType) {
        this.id = id;
        this.questionPaperId = questionPaperId;
        this.questionPaperSectionId = questionPaperSectionId;
        this.assessmentId = assessmentId;
        this.assessmentInstanceId = assessmentInstanceId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.sequence = sequence;
        this.description = description;
        this.name = name;
        this.text = text;
        this.totalWeight = totalWeight;
        this.totalQuestions = totalQuestions;
        this.mandatoryQuestions = mandatoryQuestions;
        this.language = language;
        this.tenantId = tenantId;
        this.dimType = dimType;
    }
}
