package com.platformcommons.platform.service.assessment.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Builder
public class SectionQuestionVO {

    private Long assessmentId;
    private Long assessmentQuestionPaperId;
    private Long sectionId;
    private Long sectionQuestionId;
    private Long questionId;
    private String questionType;
    private Boolean isMandatory;

    @Builder
    public SectionQuestionVO(Long assessmentId, Long assessmentQuestionPaperId, Long sectionId, Long sectionQuestionId, Long questionId, String questionType, Boolean isMandatory) {
        this.assessmentId = assessmentId;
        this.assessmentQuestionPaperId = assessmentQuestionPaperId;
        this.sectionId = sectionId;
        this.sectionQuestionId = sectionQuestionId;
        this.questionId = questionId;
        this.questionType = questionType;
        this.isMandatory = isMandatory;
    }
}
