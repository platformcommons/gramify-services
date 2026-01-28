package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.changemaker.dto.OpportunityApplicantDTO;
import com.platformcommons.platform.service.changemaker.dto.OpportunityApplicantMetaDTO;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OpportunityApplicantEventMapper {

    @Autowired
    private RuleResultEventMapper ruleResultEventMapper;

    @Autowired
    private ApplicantOwnerResultEventMapper applicantOwnerResultEventMapper;

    @Autowired
    private OpportunityApplicantTaskEventMapper opportunityApplicantTaskEventMapper;

    @Autowired
    private ApplicantAssessmentResponseEventMapper applicantAssessmentResponseEventMapper;

    public OpportunityApplicant fromEventDTO(OpportunityApplicantDTO opportunityApplicantDTO) {
        if(opportunityApplicantDTO == null) {
            return null;
        }
        OpportunityApplicant.OpportunityApplicantBuilder opportunityApplicantBuilder =  OpportunityApplicant.builder()
                .id(opportunityApplicantDTO.getId())
                .isActive(opportunityApplicantDTO.getIsActive() == null ? Boolean.TRUE : opportunityApplicantDTO.getIsActive())
                .tenantId(opportunityApplicantDTO.getTenantId())
                .linkedUserId(opportunityApplicantDTO.getLinkedUserId())
                .marketUserId(opportunityApplicantDTO.getMarketUserId())
                .opportunityId(opportunityApplicantDTO.getOpportunityId())
                .applicationStatus(opportunityApplicantDTO.getApplicationStatus())
                .currentStepStatus(opportunityApplicantDTO.getCurrentStepStatus())
                .currentStepSubStatus(opportunityApplicantDTO.getCurrentStepSubStatus())
                .currentStepCode(opportunityApplicantDTO.getCurrentStepCode())
                .currentSupervisorLinkedUserId(opportunityApplicantDTO.getCurrentSupervisorLinkedUserId())
                .forEntityId(opportunityApplicantDTO.getForEntityId())
                .currentOwnerEntityId(opportunityApplicantDTO.getCurrentOwnerEntityId())
                .currentOwnerEntityType(opportunityApplicantDTO.getCurrentOwnerEntityType())
                .attendanceStatus(opportunityApplicantDTO.getAttendanceStatus())
                .retentionStatus(opportunityApplicantDTO.getRetentionStatus())
                .forRole(opportunityApplicantDTO.getForRole())
                .candidateType(opportunityApplicantDTO.getCandidateType())
                .isVerified(opportunityApplicantDTO.getIsVerified())
                .applicantCampaign(opportunityApplicantDTO.getApplicantCampaign())
                .applicantReferrer(opportunityApplicantDTO.getApplicantReferrer() != null ? Long.valueOf(opportunityApplicantDTO.getApplicantReferrer())
                        : null )
                .applicantSource(opportunityApplicantDTO.getApplicantSource())
                .applicantMedium(opportunityApplicantDTO.getApplicantMedium())
                .applicationType(opportunityApplicantDTO.getApplicationType())
                .assessmentSubStepId(opportunityApplicantDTO.getAssessmentSubStepId())
                .applicationDateTime(opportunityApplicantDTO.getApplicationDateTime())
                .applicationSubmitDateTime(opportunityApplicantDTO.getApplicationSubmitDateTime())
                .applicationCompleteDateTime(opportunityApplicantDTO.getApplicationCompleteDateTime())
                .interviewDate(opportunityApplicantDTO.getInterviewDate())
                .rsvpStatus(MapperUtil.getCodeFromRefDataDTO(opportunityApplicantDTO.getRsvpStatus()))
                .rsvpNotes(opportunityApplicantDTO.getRsvpNotes())
                .applicantOnboardingStatus(opportunityApplicantDTO.getApplicantOnboardingStatus())
                .ruleResults( ruleResultEventMapper.fromEventDTOSet(opportunityApplicantDTO.getRuleResultList()) )
                .applicantOwnerResults( applicantOwnerResultEventMapper.fromEventDTOSet(opportunityApplicantDTO.getApplicantOwnerResultList()) )
                .opportunityApplicantTasks( opportunityApplicantTaskEventMapper.fromCustomEventDTOSet(opportunityApplicantDTO.getCustomOpportunityApplicantTaskList()))
                .isVerified(opportunityApplicantDTO.getIsVerified())
                .applicantVersion(opportunityApplicantDTO.getVersion())
                .applicantAssessmentResponses(applicantAssessmentResponseEventMapper.fromEventDTOSet(opportunityApplicantDTO.getApplicantAssessmentResponse()));

        OpportunityApplicant opportunityApplicant = opportunityApplicantBuilder.build();
        appendOpportunityApplicantMeta(opportunityApplicant,opportunityApplicantDTO.getOpportunityApplicantMeta());
        return opportunityApplicant;
    }


    public void appendOpportunityApplicantMeta(OpportunityApplicant opportunityApplicant, OpportunityApplicantMetaDTO opportunityApplicantMetaDTO) {
        if(opportunityApplicantMetaDTO != null) {
            opportunityApplicant.setPolicyAccepted(opportunityApplicantMetaDTO.getPolicyAccepted());
            opportunityApplicant.setCocAccepted(opportunityApplicantMetaDTO.getCocAccepted());
            opportunityApplicant.setLoiAccepted(opportunityApplicantMetaDTO.getLoiAccepted());
            opportunityApplicant.setAreaOfResidence(opportunityApplicantMetaDTO.getAreaOfResidence());
            opportunityApplicant.setCurrentUserType(MapperUtil.getCodeFromRefDataDTO(opportunityApplicantMetaDTO.getCurrentUserType()));
        }
    }


}