package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "assesse_dim")
@Getter @Setter
@NoArgsConstructor
public class AssesseDim extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assesse_id",nullable = false)
    private Long assesseId;
    @Column(name = "is_active",nullable = false)
    private Boolean isActive;
    @Column(name = "assesse_created_at",nullable = false)
    private Long assessmentInstanceAssesseCreatedAt;
    @Column(name = "assessment_instance_id")
    private Long assessmentInstanceId;
    @Column(name = "assessment_id")
    private Long assessmentId;
    @Column(name = "in_active_at")
    private Long inActiveAt;
    @Column(name = "in_active_reason")
    private String inActiveReason;
    @Column(name = "assessed_for_entity_id")
    private String assessedForEntityId;
    @Column(name = "assessed_for_entity_type")
    private String assessedForEntityType;
    @Column(name = "assessee_actor_id",columnDefinition = "TEXT")
    private String assesseeActorId;
    @Column(name = "assessee_actor_type",columnDefinition = "TEXT")
    private String assesseeActorType;
    @Column(name = "assessee_actor_name",columnDefinition = "TEXT")
    private String assesseeActorName;
    @Column(name = "assessment_taken_on")
    private Date assessmentTakenOn;
    @Column(name = "assessor_actor_id")
    private String assessorActorId;
    @Column(name = "assessor_actor_name")
    private String assessorActorName;
    @Column(name = "assessor__actor_type")
    private String assessorActorType;
    @Column(name = "name")
    private String name;
    @Column(name = "score",columnDefinition = "")
    private Double score;
    @Column(name = "correct_question",columnDefinition = "")
    private Long correctQuestion;
    @Column(name = "total_question",columnDefinition = "")
    private Long totalQuestion;

    @Column(name = "l1")
    private String l1;

    @Column(name = "l2")
    private String l2;

    @Column(name = "l3")
    private String l3;

    @Column(name = "l4")
    private String l4;

    @Column(name = "l5")
    private String l5;

    @Builder
    public AssesseDim(Long id, Long assesseId, Boolean isActive, Long assessmentInstanceAssesseCreatedAt, Long assessmentInstanceId, Long assessmentId, Long inActiveAt, String inActiveReason, String assessedForEntityId, String assessedForEntityType, String assesseeActorId, String assesseeActorType, Date assessmentTakenOn, String assessorActorId, String assessorActorType, String name, Double score, Long correctQuestion, Long totalQuestion, String assesseeActorName, String assessorActorName,
                      String l1,String l2,String l3,String l4,String l5) {
        this.id = id;
        this.assesseId = assesseId;
        this.isActive = isActive == null || isActive;
        this.assessmentInstanceAssesseCreatedAt = assessmentInstanceAssesseCreatedAt;
        this.assessmentInstanceId = assessmentInstanceId;
        this.assessmentId = assessmentId;
        this.inActiveAt = inActiveAt;
        this.inActiveReason = inActiveReason;
        this.assessedForEntityId = assessedForEntityId;
        this.assessedForEntityType = assessedForEntityType;
        this.assesseeActorId = assesseeActorId;
        this.assesseeActorType = assesseeActorType;
        this.assessmentTakenOn = assessmentTakenOn;
        this.assessorActorId = assessorActorId;
        this.assessorActorType = assessorActorType;
        this.name = name;
        this.score = score;
        this.correctQuestion = correctQuestion;
        this.totalQuestion = totalQuestion;
        this.assesseeActorName=assesseeActorName;
        this.assessorActorName=assessorActorName;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.l5 = l5;
    }
}
