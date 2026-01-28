package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "assessment_instance_dim")
@Getter @Setter
@NoArgsConstructor
public class AssessmentInstanceDim extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessment_instance_id")
    private Long assessmentInstanceId;

    @Column(name = "assessment_id")
    private Long assessmentId;

    @Column(name = "question_paper_id")
    private Long questionPaperId;

    @Column(name = "assessment_created_at")
    private Long assessmentCreatedAt;

    @Column(name = "assessment_instance_created_at")
    private Long assessmentInstanceCreatedAt;

    @Column(name = "question_paper_id_created_at")
    private Long questionPaperIdCreatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "in_active_at")
    private Long inActiveAt;

    @Column(name = "in_active_reason")
    private String inActiveReason;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "assessment_code")
    private String assessmentCode;

    @Column(name = "context")
    private String context;

    @Column(name = "domain")
    private String domain;

    @Column(name = "assessment_name",columnDefinition = "TEXT")
    private String assessmentName;

    @Column(name = "assessment_desc",columnDefinition = "TEXT")
    private String assessmentDesc;

    @Column(name = "sub_domain")
    private String subDomain;

    @Column(name = "assessment_mode")
    private String assessmentMode;

    @Column(name = "assessment_type")
    private String assessmentType;

    @Column(name = "assessment_subtype")
    private String assessmentSubtype;

    @Column(name = "base_language")
    private String baseLanguage;

    @Column(name = "duplicated_count")
    private Long duplicatedCount;

    @Column(name = "duplicated_from")
    private Long duplicatedFrom;

    @Column(name = "schedule_status")
    private String scheduleStatus;

    @Column(name = "assessment_instance_end_date")
    private Date assessmentInstanceEndDate;

    @Column(name = "assessment_instance_start_date")
    private Date assessmentInstanceStartDate;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "is_specific_visibility")
    private Boolean isSpecificVisibility;

    @Column(name = "monitered_by_actor_id")
    private String moniteredByActorId;

    @Column(name = "monitered_by_actor_type")
    private String moniteredByActorType;

    @Column(name = "conducted_by_actor_id")
    private String conductedByActorId;

    @Column(name = "conducted_by_actor_type")
    private String conductedByActorType;

    @Column(name = "for_entity_id")
    private String forEntityId;

    @Column(name = "for_entity_type")
    private String forEntityType;

    @Column(name = "assessment_instance_name",columnDefinition = "TEXT")
    private String assessmentInstanceName;

    @Column(name = "question_paper_code")
    private String questionPaperCode;

    @Column(name = "question_paper_name",columnDefinition = "TEXT")
    private String questionPaperName;

    @Column(name = "question_paper_description",columnDefinition = "TEXT")
    private String questionPaperDescription;

    @Column(name = "total_weight")
    private Double totalWeight;

    @Column(name = "response_count")
    private Long responseCount;

    @Column(name = "total_question")
    private Long totalQuestion;

    @Column(name = "language")
    private String language;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "dim_type")
    private String dimType;

    @Column
    private Long sequence;

    @Builder
    public AssessmentInstanceDim(Long id, Long assessmentInstanceId, Long assessmentId, Long questionPaperId, Long assessmentCreatedAt, Long assessmentInstanceCreatedAt, Long questionPaperIdCreatedAt, Long createdBy, Long inActiveAt, String inActiveReason, Boolean isActive, String assessmentCode, String context, String domain, String assessmentName, String assessmentDesc, String subDomain, String assessmentMode, String assessmentType, String assessmentSubtype, String baseLanguage, Long duplicatedCount, Long duplicatedFrom, String scheduleStatus, Date assessmentInstanceEndDate, Date assessmentInstanceStartDate, Boolean isPublic, Boolean isSpecificVisibility, String moniteredByActorId, String moniteredByActorType, String conductedByActorId, String conductedByActorType, String forEntityId, String forEntityType, String assessmentInstanceName, String questionPaperCode, String questionPaperName, String questionPaperDescription, Double totalWeight, Long responseCount, Long totalQuestion, String language, Long tenantId, String dimType, Long sequence) {
        this.id = id;
        this.assessmentInstanceId = assessmentInstanceId;
        this.assessmentId = assessmentId;
        this.questionPaperId = questionPaperId;
        this.assessmentCreatedAt = assessmentCreatedAt;
        this.assessmentInstanceCreatedAt = assessmentInstanceCreatedAt;
        this.questionPaperIdCreatedAt = questionPaperIdCreatedAt;
        this.createdBy = createdBy;
        this.inActiveAt = inActiveAt;
        this.inActiveReason = inActiveReason;
        this.isActive = isActive;
        this.assessmentCode = assessmentCode;
        this.context = context;
        this.domain = domain;
        this.assessmentName = assessmentName;
        this.assessmentDesc = assessmentDesc;
        this.subDomain = subDomain;
        this.assessmentMode = assessmentMode;
        this.assessmentType = assessmentType;
        this.assessmentSubtype = assessmentSubtype;
        this.baseLanguage = baseLanguage;
        this.duplicatedCount = duplicatedCount;
        this.duplicatedFrom = duplicatedFrom;
        this.scheduleStatus = scheduleStatus;
        this.assessmentInstanceEndDate = assessmentInstanceEndDate;
        this.assessmentInstanceStartDate = assessmentInstanceStartDate;
        this.isPublic = isPublic;
        this.isSpecificVisibility = isSpecificVisibility;
        this.moniteredByActorId = moniteredByActorId;
        this.moniteredByActorType = moniteredByActorType;
        this.conductedByActorId = conductedByActorId;
        this.conductedByActorType = conductedByActorType;
        this.forEntityId = forEntityId;
        this.forEntityType = forEntityType;
        this.assessmentInstanceName = assessmentInstanceName;
        this.questionPaperCode = questionPaperCode;
        this.questionPaperName = questionPaperName;
        this.questionPaperDescription = questionPaperDescription;
        this.totalWeight = totalWeight;
        this.responseCount = responseCount;
        this.totalQuestion = totalQuestion;
        this.language = language;
        this.tenantId = tenantId;
        this.dimType = dimType;
        this.sequence = sequence;
    }

    public void updateForInstanceEvent(AssessmentInstanceDim assessmentInstanceDim) {

    }

    public void updateForQuestionPaperEvent(AssessmentInstanceDim assessmentInstanceDim) {
        this.assessmentId = assessmentInstanceDim.getAssessmentId();
        this.questionPaperId = assessmentInstanceDim.getQuestionPaperId();
        this.assessmentCreatedAt = assessmentInstanceDim.getAssessmentCreatedAt();
        this.questionPaperIdCreatedAt = assessmentInstanceDim.getQuestionPaperIdCreatedAt();
        this.assessmentCode = assessmentInstanceDim.getAssessmentCode();
        this.context = assessmentInstanceDim.getContext();
        this.domain = assessmentInstanceDim.getDomain();
        this.assessmentName = assessmentInstanceDim.getAssessmentName();
        this.assessmentDesc = assessmentInstanceDim.getAssessmentDesc();
        this.subDomain = assessmentInstanceDim.getSubDomain();
        this.assessmentMode = assessmentInstanceDim.getAssessmentMode();
        this.assessmentType = assessmentInstanceDim.getAssessmentType();
        this.assessmentSubtype = assessmentInstanceDim.getAssessmentSubtype();
        this.baseLanguage = assessmentInstanceDim.getBaseLanguage();
        this.questionPaperCode = assessmentInstanceDim.getQuestionPaperCode();
        this.questionPaperName = assessmentInstanceDim.getQuestionPaperName();
        this.questionPaperDescription = assessmentInstanceDim.getQuestionPaperDescription();
        this.totalWeight = assessmentInstanceDim.getTotalWeight();
        this.responseCount = assessmentInstanceDim.getResponseCount();
        this.totalQuestion = assessmentInstanceDim.getTotalQuestion();
        this.language = assessmentInstanceDim.getLanguage();
    }

    public void updateForAssessmentEvent(AssessmentInstanceDim assessmentDTO){
        this.assessmentId = assessmentDTO.getId();
        this.assessmentSubtype=assessmentDTO.getAssessmentSubtype();
        this.assessmentMode=assessmentDTO.getAssessmentMode();
        this.assessmentType=assessmentDTO.getAssessmentType();
        this.assessmentCode = assessmentDTO.getAssessmentCode();
        this.context = assessmentDTO.getContext();
        this.domain = assessmentDTO.getDomain();
        this.subDomain=assessmentDTO.getSubDomain();
        this.assessmentDesc=assessmentDTO.getAssessmentDesc();
        this.assessmentName = assessmentDTO.getAssessmentName();
        this.baseLanguage=assessmentDTO.getBaseLanguage();
    }
}
