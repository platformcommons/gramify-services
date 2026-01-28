package com.platformcommons.platform.service.search.domain;

import com.platformcommons.platform.service.search.domain.base.ElasticBaseEntity;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "opportunity_applicant")
public class OpportunityApplicant extends ElasticBaseEntity {

    @Field
    private Long id;

    @Field
    private Long marketUserId;

    @Field
    private Long linkedUserId;

    @Field
    private Long opportunityId;

    @Field
    private String tenantLogin;

    @Field
    private String applicationStatus;

    @Field
    private String currentStepStatus;

    @Field
    private String currentStepSubStatus;

    @Field
    private String currentStepCode;

    @Field
    private Long currentSupervisorLinkedUserId;

    @Field
    private Long forEntityId;

    @Field
    private String applicantEmail;

    @Field
    private String name;

    @Field
    private String contactNumber;

    @Field
    private Long currentOwnerEntityId;

    @Field
    private String currentOwnerEntityType;

    @Field
    private String attendanceStatus;

    @Field
    private String retentionStatus;

    @Field
    private String forRole;

    @Field
    private String candidateType;

    @Field
    private Boolean isVerified;

    @Field
    private String applicationType;

    @Field
    private Long assessmentSubStepId;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date applicationDateTime;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date applicationSubmitDateTime;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date applicationCompleteDateTime;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date interviewDate;

    @Field
    private String rsvpStatus;

    @Field
    private String rsvpNotes;

    @Field
    private String currentStateCode;

    @Field
    private String currentCityCode;

    @Field
    private String currentCountryCode;

    @Field
    private Set<String> roleCodes;

    @Field
    private Set<String> pathwayCodes;

    @Field
    private String cohort;

    @Field
    private String placementCity;

    @Field
    private String applicantCampaign;

    @Field
    private Long applicantReferrer;

    @Field
    private String applicantSource;

    @Field
    private String applicantMedium;

    @Field
    private String applicantOnboardingStatus;

    @Field
    private Boolean policyAccepted;

    @Field
    private Boolean cocAccepted;

    @Field
    private Boolean loiAccepted;

    @Field
    private String areaOfResidence;

    @Field
    private String currentUserType;

    @Field
    private Set<String> eligibilityRuleCodes;

    @Field
    private Set<RuleResult> ruleResults;

    @Field
    private Set<ApplicantOwnerResult> applicantOwnerResults;

    @Field
    private Double totalYearOfExperience;

    @Field
    private Set<String> educationQualificationCodes;

    @Field
    private Set<String> relocationPreferenceSet;

    @Field
    private Set<String> selfDeclarationCodes;

    @Field
    private Set<String> educationDegreeCodes;

    @Field
    private Long appliedToEntityId;

    @Field
    private Long applicantVersion;

    @Field
    private Set<OpportunityApplicantTask> opportunityApplicantTasks;

    @Field
    private Set<ApplicantAssessmentResponse> applicantAssessmentResponses;

    @Builder
    public OpportunityApplicant(Long tenantId, Boolean isActive, SeqNoPrimaryTerm SeqNoPrimaryTerm,
                                Long id, Long marketUserId, Long linkedUserId, Long opportunityId, String tenantLogin, String applicationStatus,
                                String currentStepStatus, String currentStepSubStatus, String currentStepCode, Long currentSupervisorLinkedUserId,
                                Long forEntityId, String applicantEmail, String name, String contactNumber, Long currentOwnerEntityId,
                                String currentOwnerEntityType, String attendanceStatus, String retentionStatus, String forRole,
                                String candidateType, Boolean isVerified, String applicationType, Long assessmentSubStepId,
                                Date applicationDateTime, Date applicationSubmitDateTime, Date applicationCompleteDateTime,
                                Date interviewDate, String rsvpStatus, String rsvpNotes, String currentStateCode, String currentCityCode,
                                String currentCountryCode, Boolean policyAccepted, Boolean cocAccepted, Boolean loiAccepted,
                                String currentUserType, Set<String> roleCodes, Set<String> pathwayCodes, String cohort, String placementCity,
                                String applicantCampaign, Long applicantReferrer, Long appliedToEntityId, Set<RuleResult> ruleResults,
                                String areaOfResidence, Set<ApplicantOwnerResult> applicantOwnerResults,Long applicantVersion,
                                Double totalYearOfExperience, Set<String> educationQualificationCodes, Set<String> educationDegreeCodes,
                                Set<String> relocationPreferenceSet, Set<String> selfDeclarationCodes, String applicantOnboardingStatus,
                                String applicantSource,String applicantMedium, Set<OpportunityApplicantTask> opportunityApplicantTasks,
                                Set<ApplicantAssessmentResponse> applicantAssessmentResponses) {
        super(tenantId, isActive, SeqNoPrimaryTerm);
        this.id = id;
        this.marketUserId = marketUserId;
        this.linkedUserId = linkedUserId;
        this.opportunityId = opportunityId;
        this.tenantLogin = tenantLogin;
        this.applicationStatus = applicationStatus;
        this.currentStepStatus = currentStepStatus;
        this.currentStepSubStatus = currentStepSubStatus;
        this.currentStepCode = currentStepCode;
        this.currentSupervisorLinkedUserId = currentSupervisorLinkedUserId;
        this.forEntityId = forEntityId;
        this.applicantEmail = applicantEmail;
        this.name = name;
        this.contactNumber = contactNumber;
        this.currentOwnerEntityId = currentOwnerEntityId;
        this.currentOwnerEntityType = currentOwnerEntityType;
        this.attendanceStatus = attendanceStatus;
        this.retentionStatus = retentionStatus;
        this.forRole = forRole;
        this.candidateType = candidateType;
        this.isVerified = isVerified;
        this.applicationType = applicationType;
        this.assessmentSubStepId = assessmentSubStepId;
        this.applicationDateTime = applicationDateTime;
        this.applicationSubmitDateTime = applicationSubmitDateTime;
        this.applicationCompleteDateTime = applicationCompleteDateTime;
        this.interviewDate = interviewDate;
        this.rsvpStatus = rsvpStatus;
        this.rsvpNotes = rsvpNotes;
        this.currentStateCode = currentStateCode;
        this.currentCityCode = currentCityCode;
        this.currentCountryCode = currentCountryCode;
        this.policyAccepted = policyAccepted;
        this.cocAccepted = cocAccepted;
        this.loiAccepted = loiAccepted;
        this.currentUserType = currentUserType;
        this.roleCodes = roleCodes;
        this.pathwayCodes = pathwayCodes;
        this.cohort = cohort;
        this.placementCity = placementCity;
        this.appliedToEntityId = appliedToEntityId;
        this.applicantCampaign = applicantCampaign;
        this.applicantReferrer = applicantReferrer;
        this.ruleResults = ruleResults;
        this.applicantOwnerResults = applicantOwnerResults;
        this.areaOfResidence = areaOfResidence;
        this.totalYearOfExperience = totalYearOfExperience;
        this.educationQualificationCodes = educationQualificationCodes;
        this.educationDegreeCodes = educationDegreeCodes;
        this.applicantVersion = applicantVersion;
        this.relocationPreferenceSet = relocationPreferenceSet;
        this.selfDeclarationCodes = selfDeclarationCodes;
        this.applicantOnboardingStatus =  applicantOnboardingStatus;
        this.applicantSource = applicantSource;
        this.applicantMedium = applicantMedium;
        this.opportunityApplicantTasks = opportunityApplicantTasks;
        this.applicantAssessmentResponses = applicantAssessmentResponses;
    }

    /**
     * This method is only to update the OpportunityApplicant root fields,ignoring the ones which gets copied from User Core
     * For the user specific fields, it gets synced automatically in the com.platformcommons.platform.service.search.facade.impl.updateUserFieldsInApplicant
     */
    public void putUpdate(OpportunityApplicant applicant) {
        this.marketUserId = applicant.getMarketUserId();
        this.linkedUserId = applicant.getLinkedUserId();
        this.opportunityId = applicant.getOpportunityId();
        this.tenantLogin = applicant.getTenantLogin();
        this.applicationStatus = applicant.getApplicationStatus();
        this.currentStepStatus = applicant.getCurrentStepStatus();
        this.currentStepSubStatus = applicant.getCurrentStepSubStatus();
        this.currentStepCode = applicant.getCurrentStepCode();
        this.currentSupervisorLinkedUserId = applicant.getCurrentSupervisorLinkedUserId();
        this.forEntityId = applicant.getForEntityId();
        this.currentOwnerEntityId = applicant.getCurrentOwnerEntityId();
        this.currentOwnerEntityType = applicant.getCurrentOwnerEntityType();
        this.attendanceStatus = applicant.getAttendanceStatus();
        this.retentionStatus = applicant.getRetentionStatus();
        this.forRole = applicant.getForRole();
        this.candidateType = applicant.getCandidateType();
        this.isVerified = applicant.getIsVerified();
        this.applicationType = applicant.getApplicationType();
        this.assessmentSubStepId = applicant.getAssessmentSubStepId();
        this.applicationDateTime = applicant.getApplicationDateTime();
        this.applicationSubmitDateTime = applicant.getApplicationSubmitDateTime();
        this.applicationCompleteDateTime = applicant.getApplicationCompleteDateTime();
        this.interviewDate = applicant.getInterviewDate();
        this.rsvpStatus = applicant.getRsvpStatus();
        this.rsvpNotes = applicant.getRsvpNotes();
        this.applicantCampaign = applicant.getApplicantCampaign();
        this.applicantReferrer = applicant.getApplicantReferrer();
        this.applicantSource = applicant.getApplicantSource();
        this.applicantMedium = applicant.getApplicantMedium();
        this.policyAccepted = applicant.getPolicyAccepted();
        this.cocAccepted = applicant.getCocAccepted();
        this.loiAccepted = applicant.getLoiAccepted();
        this.areaOfResidence = applicant.getAreaOfResidence();
        this.currentUserType = applicant.getCurrentUserType();
        this.appliedToEntityId = applicant.getAppliedToEntityId();
        this.applicantVersion = applicant.getApplicantVersion();
        this.applicantOnboardingStatus = applicant.getApplicantOnboardingStatus();
    }

}
