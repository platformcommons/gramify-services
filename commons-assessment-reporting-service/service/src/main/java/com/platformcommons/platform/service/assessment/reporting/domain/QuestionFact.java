package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "question_fact")
@Getter
@Setter
@NoArgsConstructor
public class QuestionFact extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long assessmentId;
    private Long assessmentInstanceId;
    private Long assessmentQuestionPaperId;
    private Long questionPaperSectionId;
    private Long sectionQuestionId;
    private Long questionId;
    private String questionSkills;
    private Long tenantId;
    private Long respondedCount;
    private Long correctCount;
    private Long incorrectCount;
    private Long skippedCount;
    private Long linkedDefaultOptionId;
    private Long linkedQuestionId;
    private String dimType;
    private Long parentQuestionId;
    private Long childDefaultOptionId;

    @Builder
    public QuestionFact(Long id, Long assessmentId, Long assessmentInstanceId, Long assessmentQuestionPaperId, Long questionPaperSectionId, Long sectionQuestionId, Long questionId, String questionSkills, Long tenantId, Long respondedCount, Long correctCount, Long incorrectCount, Long skippedCount, String dimType,
                        Long linkedDefaultOptionId, Long linkedQuestionId, Long parentQuestionId, Long childDefaultOptionId) {
        this.id = id;
        this.assessmentId = assessmentId;
        this.assessmentQuestionPaperId = assessmentQuestionPaperId;
        this.questionPaperSectionId = questionPaperSectionId;
        this.sectionQuestionId = sectionQuestionId;
        this.assessmentInstanceId = assessmentInstanceId;
        this.tenantId = tenantId;
        this.respondedCount = respondedCount;
        this.correctCount = correctCount;
        this.incorrectCount = incorrectCount;
        this.skippedCount = skippedCount;
        this.dimType = dimType;
        this.questionId = questionId;
        this.questionSkills = questionSkills;
        this.linkedDefaultOptionId = linkedDefaultOptionId;
        this.linkedQuestionId = linkedQuestionId;
        this.parentQuestionId = parentQuestionId;
        this.childDefaultOptionId = childDefaultOptionId;
    }

    public QuestionFact duplicate() {
        return QuestionFact.builder()
                .id(0L)
                .assessmentId(this.assessmentId)
                .assessmentQuestionPaperId(this.assessmentQuestionPaperId)
                .questionPaperSectionId(this.questionPaperSectionId)
                .sectionQuestionId(this.sectionQuestionId)
                .assessmentInstanceId(this.assessmentInstanceId)
                .tenantId(this.tenantId)
                .respondedCount(this.respondedCount)
                .correctCount(this.correctCount)
                .incorrectCount(this.incorrectCount)
                .skippedCount(this.skippedCount)
                .dimType(this.dimType)
                .build();
    }
}
