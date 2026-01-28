package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@Getter @Setter
public class AssesseResponseFact extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long assesseCreatedAt;
    private Long responseCreatedAt;
    private Long responseUpdatedAt;
    private Long inActiveAt;
    private String inActiveReason;
    private Boolean isActive;
    private Long assessmentId;
    private Long assessmentInstanceId;
    private Long assessmentInstanceAssesseId;
    private Long sectionQuestionsId;
    private Long questionId;
    private String assessedForEntityId;
    @Column(columnDefinition = "TEXT")
    private String assesseeActorId;
    @Column(columnDefinition = "TEXT")
    private String assesseeActorName;
    private String assessedForEntityType;
    @Column(columnDefinition = "TEXT")
    private String assesseeActorType;
    private Date assessmentTakenOn;
    @Column(columnDefinition = "TEXT")
    private String assessorId;
    @Column(columnDefinition = "TEXT")
    private String assessorType;
    @Column(columnDefinition = "TEXT")
    private String assessorName;
    @Column(columnDefinition = "TEXT")
    private String questionText;
    private Long aiaDefaultResponseId;
    private String multiSelectResponseIds;
    private Long optionId;
    @Column(columnDefinition = "TEXT")
    private String objectiveResponse;
    @Column(columnDefinition = "TEXT")
    private String multiSelectResponse;
    private String subjectiveResponseIds;
    @Column(columnDefinition = "LONGTEXT")
    private String subjectiveResponses;
    @Column(columnDefinition = "LONGTEXT")
    private String questionResponse;
    private Long correctQuestion;
    private Double percentage;
    private Double score;
    private String userDisplayName;
    private String userLogin;
    private String language;
    private String dimType;
    private Long childDefaultOptionId;
    private Long parentQuestionId;
    private Long mtfOptionId;

    @Builder
    public AssesseResponseFact(Long id, Long assesseCreatedAt, Long responseCreatedAt, Long responseUpdatedAt, Long inActiveAt, String inActiveReason, Boolean isActive, Long assessmentId, Long assessmentInstanceId, Long assessmentInstanceAssesseId, Long sectionQuestionsId, Long questionId, String assessedForEntityId, String assesseeActorId, String assessedForEntityType, String assesseeActorType, Date assessmentTakenOn, String assessorId, String assessorType, String questionText, Long aiaDefaultResponseId, String multiSelectResponseIds, Long optionId, String objectiveResponse, String multiSelectResponse, String subjectiveResponseIds, String subjectiveResponses, String questionResponse, Long correctQuestion, Double percentage, Double score, String userDisplayName, String userLogin, String language, String dimType, Long childDefaultOptionId, Long parentQuestionId, Long mtfOptionId, String assesseeActorName, String assessorName) {
        this.id = id;
        this.assesseCreatedAt = assesseCreatedAt;
        this.responseCreatedAt = responseCreatedAt;
        this.responseUpdatedAt = responseUpdatedAt;
        this.inActiveAt = inActiveAt;
        this.inActiveReason = inActiveReason;
        this.isActive = isActive;
        this.assessmentId = assessmentId;
        this.assessmentInstanceId = assessmentInstanceId;
        this.assessmentInstanceAssesseId = assessmentInstanceAssesseId;
        this.sectionQuestionsId = sectionQuestionsId;
        this.questionId = questionId;
        this.assessedForEntityId = assessedForEntityId;
        this.assesseeActorId = assesseeActorId;
        this.assessedForEntityType = assessedForEntityType;
        this.assesseeActorType = assesseeActorType;
        this.assessmentTakenOn = assessmentTakenOn;
        this.assessorId = assessorId;
        this.assessorType = assessorType;
        this.questionText = questionText;
        this.aiaDefaultResponseId = aiaDefaultResponseId;
        this.multiSelectResponseIds = multiSelectResponseIds;
        this.optionId = optionId;
        this.objectiveResponse = objectiveResponse;
        this.multiSelectResponse = multiSelectResponse;
        this.subjectiveResponseIds = subjectiveResponseIds;
        this.subjectiveResponses = subjectiveResponses;
        this.questionResponse = questionResponse;
        this.correctQuestion = correctQuestion;
        this.percentage = percentage;
        this.score = score;
        this.userDisplayName = userDisplayName;
        this.userLogin = userLogin;
        this.language = language;
        this.dimType = dimType;
        this.childDefaultOptionId = childDefaultOptionId;
        this.parentQuestionId = parentQuestionId;
        this.mtfOptionId = mtfOptionId;
        this.assesseeActorName=assesseeActorName;
        this.assessorName=assessorName;
    }
}
