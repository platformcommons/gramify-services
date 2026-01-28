package com.platformcommons.platform.service.assessment.reporting.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "assesse_skill_dim")
@Getter @Setter
@NoArgsConstructor
@Schema(description = "Dimension Table for Assessment Instance Assesse's Skill Report",name = "Assesse Skill Dimension")
public class AssesseSkillDim extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Auto Generated Primary Key serves no purpose other than giving unique id to record",name = "id")
    private Long id;

    @Column(name = "skill_id")
    @Schema(description = "Skill Id",name = "skillId")
    private Long skillId;

    @Schema(description = "Represents skill report belongs to an assessment instance assesse",name = "assesseId")
    @Column(name = "assesse_id")
    private Long assesseId;

    @Schema(description = "Represents user type of assesse",name = "assesseType", example = "ACTOR_TYPE.AUTH_USER,ACTOR_TYPE.BRIDGE_USER,ACTOR_TYPE.IE")
    @Column(name = "assesse_actor_type")
    private String assesseActorType;

    @Column(name = "assesse_actor_id")
    private String assesseActorId;

    @Column(name = "correct_question_ids",columnDefinition = "TEXT")
    private String correctQuestionIds;

    @Column(name = "incorrect_question_ids",columnDefinition = "TEXT")
    private String incorrectQuestionIds;

    @Column(name = "correct_questions")
    private Long correctQuestions;

    @Column(name = "total_questions")
    private Long totalQuestions;

    @Column(name = "scored")
    private Double scored;

    @Column(name = "total_weight",columnDefinition = "")
    private Double totalWeight;

    @Column(name = "skill_code")
    private String skillCode;

    @Builder
    public AssesseSkillDim(Long id, Long skillId, Long assesseId, String assesseActorType, String assesseActorId, String correctQuestionIds, String incorrectQuestionIds, Long correctQuestions, Long totalQuestions, Double scored, Double totalWeight, String skillCode) {
        this.id = id;
        this.skillId = skillId;
        this.assesseId = assesseId;
        this.assesseActorType = assesseActorType;
        this.assesseActorId = assesseActorId;
        this.correctQuestionIds = correctQuestionIds;
        this.incorrectQuestionIds = incorrectQuestionIds;
        this.correctQuestions = correctQuestions;
        this.totalQuestions = totalQuestions;
        this.scored = scored;
        this.totalWeight = totalWeight;
        this.skillCode = skillCode;
    }

    public void init(){

    }

    public void update(){

    }

}
