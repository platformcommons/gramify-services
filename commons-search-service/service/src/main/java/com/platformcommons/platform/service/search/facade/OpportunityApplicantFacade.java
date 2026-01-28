package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.changemaker.dto.CustomApplicantOnboardingDTO;
import com.platformcommons.platform.service.changemaker.dto.RecruiterAndCommitteeAssignDTO;
import com.platformcommons.platform.service.search.domain.ApplicantOwnerResult;
import com.platformcommons.platform.service.changemaker.dto.OpportunityApplicantMetaDTO;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.domain.OpportunityApplicantTask;
import com.platformcommons.platform.service.search.domain.RuleResult;
import com.platformcommons.platform.service.search.dto.AssessmentFilterDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantDTO;
import com.platformcommons.platform.service.search.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Set;

public interface OpportunityApplicantFacade {

    FacetPageDTO<OpportunityApplicantDTO> getApplicantsByFilter(String searchText, Long opportunityId, String applicationStatus,
                                                                Set<String> stepCodeSet, Set<String> stepStatusCodeSet, Long entityId,
                                                                Date startDateTime, Date endDateTime, Integer page, Integer size,
                                                                String excludeApplicationStatus, Long supervisorId,
                                                                Set<String> attendanceStatusSet, String sortFieldName,
                                                                String sortFieldOrder, String candidateType, Boolean isVerified,
                                                                Long currentOwnerEntityId, String forRole, String retentionStatus,
                                                                Long assessmentSubStepId, String currentCountry, String currentState,
                                                                String currentCity, Set<String> facetFields, Set<String> rsvpStatusSet,
                                                                Set<String> userAttributeList, String eligibilityCriteriaCode,
                                                                Boolean isActive, Date startInterviewDateTime, Date endInterviewDateTime,
                                                                Set<String> pathwayCodes, Set<String> roleCodes, Set<String> cohorts,
                                                                Set<String> placementCities, Set<String> applicantCampaignCodes,
                                                                Set<Long> applicantReferrerIds, Set<String> failedRuleCodes,
                                                                Set<String> areaOfResidenceSet, String ownerResponseStepCode, String ownerResponseResultCode,
                                                                Double minimumTotalYearOfExperience, Double maximumTotalYearOfExperience,
                                                                Set<String> educationQualificationCodes, Set<String> educationDegreeCodes,
                                                                Set<String> currentCitySet, Set<String> relocationPreferenceSet, Set<String> selfDeclarationCodes,
                                                                String applicantOnboardingStatus, Set<String> applicantSourceSet, Set<String> applicantMediumSet,
                                                                Set<Long> includeApplicantIds, Set<Long> opportunityTaskIds,
                                                                Set<String> opportunityApplicantTaskStatusSet, Set<Long> selectionStepIds,
                                                                AssessmentFilterDTO assessmentFilterDTO);

    FacetPageDTO<OpportunityApplicantDTO> getUserIdsOfApplicantsByFilter(Set<Long> opportunityIds, Set<String> stepCodeSet, Set<String> stepStatusCodeSet, Pageable pageable);

    FacetPageDTO<OpportunityApplicantDTO> getApplicantsByFilterForChangeMaker(String searchText, Long opportunityId, String applicationStatus,
                                                                              Set<String> stepCodeSet, Set<String> stepStatusCodeSet,
                                                                              Integer page, Integer size, String excludeApplicationStatus,
                                                                              String sortFieldName, String sortFieldOrder,
                                                                              String currentCountry, String currentState,
                                                                              String currentCity, Set<String> facetFields,
                                                                              Set<String> rsvpStatusSet, Set<String> userAttributeList);

    void updateUserFieldsInApplicant(UserDTO userDTO);

    void createOrUpdateOpportunityApplicant(OpportunityApplicant opportunityApplicant);

    void syncOpportunityApplicant(OpportunityApplicant opportunityApplicant);

    void addRuleResultInApplicant(RuleResult ruleResult);

    void updateAttendanceStatus(Long opportunityApplicantId, String attendanceStatus);

    void appendOpportunityApplicantMetaDetails(OpportunityApplicantMetaDTO opportunityApplicantMetaDTO);

    void addOrUpdateApplicantOwnerResultInApplicant(ApplicantOwnerResult applicantOwnerResult);

    void addApplicantOnboardingDetailsInApplicant(CustomApplicantOnboardingDTO customApplicantOnboardingDTO);

    void updateRecruiterOrCommitteeAssignedInBulk(RecruiterAndCommitteeAssignDTO recruiterAndCommitteeAssignDTO);

    Set<Long> getApplicantIdsByFilter(Date startDateTime, Date endDateTime);

}
