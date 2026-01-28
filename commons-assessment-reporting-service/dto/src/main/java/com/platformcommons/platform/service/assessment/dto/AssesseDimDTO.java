package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AssesseDimDTO {

    private Long id;

    private Long assesseId;
    private Boolean isActive;
    private Long assessmentInstanceAssesseCreatedAt;
    private Long assessmentInstanceId;
    private Long assessmentId;
    private Long inActiveAt;
    private String inActiveReason;
    private String assessedForEntityId;
    private String assessedForEntityType;
    private String assesseeActorId;
    private String assesseeActorType;
    private String assesseeActorName;
    private Date assessmentTakenOn;
    private String assessorActorId;
    private String assessorActorName;
    private String assessorActorType;
    private String name;
    private Double score;
    private Long correctQuestion;
    private Long totalQuestion;

    private String l1;

    private String l2;

    private String l3;

    private String l4;

    private String l5;

    @Builder
    public AssesseDimDTO(Long id, Long assesseId, Boolean isActive, Long assessmentInstanceAssesseCreatedAt, Long assessmentInstanceId, Long assessmentId, Long inActiveAt, String inActiveReason, String assessedForEntityId, String assessedForEntityType, String assesseeActorId, String assesseeActorType, String assesseeActorName, Date assessmentTakenOn, String assessorActorId, String assessorActorName, String assessorActorType, String name, Double score, Long correctQuestion, Long totalQuestion, String l1, String l2, String l3, String l4, String l5) {
        this.id = id;
        this.assesseId = assesseId;
        this.isActive = isActive;
        this.assessmentInstanceAssesseCreatedAt = assessmentInstanceAssesseCreatedAt;
        this.assessmentInstanceId = assessmentInstanceId;
        this.assessmentId = assessmentId;
        this.inActiveAt = inActiveAt;
        this.inActiveReason = inActiveReason;
        this.assessedForEntityId = assessedForEntityId;
        this.assessedForEntityType = assessedForEntityType;
        this.assesseeActorId = assesseeActorId;
        this.assesseeActorType = assesseeActorType;
        this.assesseeActorName = assesseeActorName;
        this.assessmentTakenOn = assessmentTakenOn;
        this.assessorActorId = assessorActorId;
        this.assessorActorName = assessorActorName;
        this.assessorActorType = assessorActorType;
        this.name = name;
        this.score = score;
        this.correctQuestion = correctQuestion;
        this.totalQuestion = totalQuestion;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.l5 = l5;
    }
}
