package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadedAssessmentInfo {

    private Long assessmentId;
    private Long assessmentInstanceId;
    private Long questionPaperId;

    @Builder
    public UploadedAssessmentInfo(Long assessmentId, Long assessmentInstanceId, Long questionPaperId) {
        this.assessmentId = assessmentId;
        this.assessmentInstanceId = assessmentInstanceId;
        this.questionPaperId = questionPaperId;
    }

}
