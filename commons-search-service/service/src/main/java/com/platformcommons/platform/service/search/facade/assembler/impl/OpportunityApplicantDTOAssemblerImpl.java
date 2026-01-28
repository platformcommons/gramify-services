package com.platformcommons.platform.service.search.facade.assembler.impl;

import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantDTO;
import com.platformcommons.platform.service.search.facade.assembler.OpportunityApplicantDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpportunityApplicantDTOAssemblerImpl implements OpportunityApplicantDTOAssembler {

    @Override
    public OpportunityApplicantDTO toDTO(OpportunityApplicant entity) {
        if ( entity == null ) {
            return null;
        }

        OpportunityApplicantDTO.OpportunityApplicantDTOBuilder opportunityApplicantDTO = OpportunityApplicantDTO.builder();

        opportunityApplicantDTO.id( entity.getId() );
        opportunityApplicantDTO.isActive(entity.getIsActive());
        opportunityApplicantDTO.linkedUserId(entity.getLinkedUserId());
        opportunityApplicantDTO.marketUserId( entity.getMarketUserId() );
        opportunityApplicantDTO.tenantLogin( entity.getTenantLogin() );
        opportunityApplicantDTO.opportunityId( entity.getOpportunityId() );
        opportunityApplicantDTO.applicationStatus( entity.getApplicationStatus() );
        opportunityApplicantDTO.currentStepStatus( entity.getCurrentStepStatus() );
        opportunityApplicantDTO.currentStepSubStatus( entity.getCurrentStepSubStatus() );
        opportunityApplicantDTO.currentSupervisorLinkedUserId( entity.getCurrentSupervisorLinkedUserId() );
        opportunityApplicantDTO.forEntityId( entity.getForEntityId() );
        opportunityApplicantDTO.currentStepCode( entity.getCurrentStepCode() );
        opportunityApplicantDTO.currentOwnerEntityId( entity.getCurrentOwnerEntityId() );
        opportunityApplicantDTO.currentOwnerEntityType(entity.getCurrentOwnerEntityType());
        opportunityApplicantDTO.attendanceStatus( entity.getAttendanceStatus() );
        opportunityApplicantDTO.retentionStatus( entity.getRetentionStatus() );
        opportunityApplicantDTO.forRole( entity.getForRole() );
        opportunityApplicantDTO.candidateType( entity.getCandidateType() );
        opportunityApplicantDTO.isVerified( entity.getIsVerified() );
        opportunityApplicantDTO.applicationType( entity.getApplicationType() );
        opportunityApplicantDTO.assessmentSubStepId( entity.getAssessmentSubStepId() );
        opportunityApplicantDTO.rsvpStatus( entity.getRsvpStatus() );
        opportunityApplicantDTO.rsvpNotes( entity.getRsvpNotes() );
        opportunityApplicantDTO.applicationDateTime( entity.getApplicationDateTime() );
        opportunityApplicantDTO.applicationSubmitDateTime( entity.getApplicationSubmitDateTime() );
        opportunityApplicantDTO.applicationCompleteDateTime( entity.getApplicationCompleteDateTime() );
        opportunityApplicantDTO.interviewDate( entity.getInterviewDate() );
        opportunityApplicantDTO.cocAccepted(entity.getCocAccepted());
        opportunityApplicantDTO.loiAccepted(entity.getLoiAccepted());
        opportunityApplicantDTO.currentUserType(entity.getCurrentUserType());
        opportunityApplicantDTO.policyAccepted(entity.getPolicyAccepted());
        opportunityApplicantDTO.appliedToEntityId(entity.getAppliedToEntityId());
        opportunityApplicantDTO.applicantCampaign(entity.getApplicantCampaign());
        opportunityApplicantDTO.applicantReferrer(entity.getApplicantReferrer());
        opportunityApplicantDTO.name(entity.getName());
        opportunityApplicantDTO.applicantEmail(entity.getApplicantEmail());
        opportunityApplicantDTO.contactNumber(entity.getContactNumber());
        opportunityApplicantDTO.applicantCampaign(entity.getApplicantCampaign());
        opportunityApplicantDTO.applicantReferrer(entity.getApplicantReferrer());
        opportunityApplicantDTO.applicantSource(entity.getApplicantSource());
        opportunityApplicantDTO.applicantMedium(entity.getApplicantMedium());

        return opportunityApplicantDTO.build();
    }

    @Override
    public OpportunityApplicantDTO toDTOForChangeMaker(OpportunityApplicant entity) {
        if ( entity == null ) {
            return null;
        }

        OpportunityApplicantDTO.OpportunityApplicantDTOBuilder opportunityApplicantDTO = OpportunityApplicantDTO.builder();

        opportunityApplicantDTO.id( entity.getId() );
        opportunityApplicantDTO.isActive(entity.getIsActive());
        opportunityApplicantDTO.marketUserId( entity.getMarketUserId() );
        opportunityApplicantDTO.tenantLogin( entity.getTenantLogin() );
        opportunityApplicantDTO.opportunityId( entity.getOpportunityId() );
        opportunityApplicantDTO.applicationStatus( entity.getApplicationStatus() );
        opportunityApplicantDTO.currentStepStatus( entity.getCurrentStepStatus() );
        opportunityApplicantDTO.currentStepSubStatus( entity.getCurrentStepSubStatus() );
        opportunityApplicantDTO.currentSupervisorLinkedUserId( entity.getCurrentSupervisorLinkedUserId() );
        opportunityApplicantDTO.forEntityId( entity.getForEntityId() );
        opportunityApplicantDTO.currentStepCode( entity.getCurrentStepCode() );
        opportunityApplicantDTO.currentOwnerEntityId( entity.getCurrentOwnerEntityId() );
        opportunityApplicantDTO.attendanceStatus( entity.getAttendanceStatus() );
        opportunityApplicantDTO.retentionStatus( entity.getRetentionStatus() );
        opportunityApplicantDTO.forRole( entity.getForRole() );
        opportunityApplicantDTO.candidateType( entity.getCandidateType() );
        opportunityApplicantDTO.isVerified( entity.getIsVerified() );
        opportunityApplicantDTO.applicationType( entity.getApplicationType() );
        opportunityApplicantDTO.assessmentSubStepId( entity.getAssessmentSubStepId() );
        opportunityApplicantDTO.rsvpStatus( entity.getRsvpStatus() );
        opportunityApplicantDTO.rsvpNotes( entity.getRsvpNotes() );
        opportunityApplicantDTO.applicationDateTime( entity.getApplicationDateTime() );
        opportunityApplicantDTO.applicationSubmitDateTime( entity.getApplicationSubmitDateTime() );
        opportunityApplicantDTO.applicationCompleteDateTime( entity.getApplicationCompleteDateTime() );
        opportunityApplicantDTO.interviewDate( entity.getInterviewDate() );
        opportunityApplicantDTO.cocAccepted(entity.getCocAccepted());
        opportunityApplicantDTO.loiAccepted(entity.getLoiAccepted());
        opportunityApplicantDTO.currentUserType(entity.getCurrentUserType());
        opportunityApplicantDTO.policyAccepted(entity.getPolicyAccepted());
        opportunityApplicantDTO.name(entity.getName());

        return opportunityApplicantDTO.build();
    }
}
