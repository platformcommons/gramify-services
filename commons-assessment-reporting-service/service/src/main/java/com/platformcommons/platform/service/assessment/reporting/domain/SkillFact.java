package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "skill_fact")
public class SkillFact extends BaseReportEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long skillId;
    private Long assessmentId;
    private Long assessmentInstanceId;
    private Long questionPaperId;
    private Long questionPaperSectionId;
    private Long sectionQuestionId;
    private Long questionId;
    private Double totalWeightage;
    private String dimType;
    private Long childDefaultOptionId;
    private Long parentQuestionId;


    @Builder
    public SkillFact(Long id, Long skillId, Long assessmentId, Long assessmentInstanceId,
                     Long questionPaperId, Long questionPaperSectionId, Long sectionQuestionId,
                     Long questionId, Double totalWeightage, String dimType, Long childDefaultOptionId, Long parentQuestionId) {
        this.id = id;
        this.skillId = skillId;
        this.assessmentId = assessmentId;
        this.assessmentInstanceId = assessmentInstanceId;
        this.questionPaperId = questionPaperId;
        this.questionPaperSectionId = questionPaperSectionId;
        this.sectionQuestionId = sectionQuestionId;
        this.questionId = questionId;
        this.totalWeightage = totalWeightage;
        this.dimType = dimType;
        this.childDefaultOptionId = childDefaultOptionId;
        this.parentQuestionId = parentQuestionId;
    }
}
