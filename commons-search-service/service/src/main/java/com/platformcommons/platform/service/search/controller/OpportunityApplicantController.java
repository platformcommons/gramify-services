package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.AssessmentFilterDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantDTO;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@RestController
@Tag(name="OpportunityApplicant")
public class OpportunityApplicantController {

    @Autowired
    private OpportunityApplicantFacade facade;

    @Deprecated
    @ApiOperation(value = "Get Applicants By Filter", notes = "", response = OpportunityApplicantDTO.class, tags = {"OpportunityApplicant"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityApplicantDTO.class)})
    @RequestMapping(value = "/api/v1/opportunity-applicants/filter" ,
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<OpportunityApplicantDTO>> getApplicantsByFilter(@RequestParam(value = "searchText", required = false) String searchText,
                                                                                @RequestParam(value = "opportunityId") Long opportunityId,
                                                                                @RequestParam(value = "applicationStatus", required = false) String applicationStatus,
                                                                                @RequestParam(value = "stepCodeSet", required = false) Set<String> stepCodeSet,
                                                                                @RequestParam(value = "stepStatusCodeSet", required = false) Set<String> stepStatusCodeSet,
                                                                                @RequestParam(value = "entityId", required = false) Long entityId,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "startDateTime", required = false) Date startDateTime,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "endDateTime", required = false) Date endDateTime,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "startInterviewDateTime", required = false) Date startInterviewDateTime,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "endInterviewDateTime", required = false) Date endInterviewDateTime,
                                                                                @RequestParam(value = "page") Integer page,
                                                                                @RequestParam(value = "size") Integer size,
                                                                                @RequestParam(value = "excludeApplicationStatus", required = false) String excludeApplicationStatus,
                                                                                @RequestParam(value = "supervisorId", required = false) Long supervisorId,
                                                                                @RequestParam(value = "attendanceStatusSet", required = false) Set<String> attendanceStatusSet,
                                                                                @RequestParam(value = "eligibilityCriteriaCode", required = false) String eligibilityCriteriaCode,
                                                                                @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
                                                                                @RequestParam(value = "sortFieldOrder", required = false) String sortFieldOrder,
                                                                                @RequestParam(value = "candidateType", required = false) String candidateType,
                                                                                @RequestParam(value = "isVerified", required = false) Boolean isVerified,
                                                                                @RequestParam(value = "failedRuleCodes", required = false) Set<String> failedRuleCodes,
                                                                                @RequestParam(value = "currentOwnerEntityId", required = false) Long currentOwnerEntityId,
                                                                                @RequestParam(value = "forRole", required = false) String forRole,
                                                                                @RequestParam(value = "retentionStatus", required = false) String retentionStatus,
                                                                                @RequestParam(value = "applicantOnboardingStatus", required = false) String applicantOnboardingStatus,
                                                                                @RequestParam(value = "assessmentSubStepId", required = false) Long assessmentSubStepId,
                                                                                @RequestParam(value = "currentCountry", required = false) String currentCountry,
                                                                                @RequestParam(value = "currentState", required = false) String currentState,
                                                                                @RequestParam(value = "currentCity", required = false) String currentCity,
                                                                                @RequestParam(value = "currentCitySet", required = false) Set<String> currentCitySet,
                                                                                @RequestParam(value = "areaOfResidenceSet", required = false) Set<String> areaOfResidenceSet,
                                                                                @RequestParam(value = "rsvpStatusSet", required = false) Set<String> rsvpStatusSet,
                                                                                @RequestParam(value = "pathwayCodes", required = false) Set<String> pathwayCodes,
                                                                                @RequestParam(value = "roleCodes", required = false) Set<String> roleCodes,
                                                                                @RequestParam(value = "cohorts", required = false) Set<String> cohorts,
                                                                                @RequestParam(value = "ownerResponseStepCode", required = false) String ownerResponseStepCode,
                                                                                @RequestParam(value = "ownerResponseResultCode", required = false) String ownerResponseResultCode,
                                                                                @RequestParam(value = "placementCities", required = false) Set<String> placementCities,
                                                                                @RequestParam(value = "educationQualificationCodes", required = false) Set<String> educationQualificationCodes,
                                                                                @RequestParam(value = "educationDegreeCodes", required = false) Set<String> educationDegreeCodes,
                                                                                @RequestParam(value = "relocationPreferenceSet", required = false ) Set<String> relocationPreferenceSet,
                                                                                @RequestParam(value = "selfDeclarationCodes", required = false ) Set<String> selfDeclarationCodes,
                                                                                @RequestParam(value = "minimumTotalYearOfExperience", required = false) Double minimumTotalYearOfExperience,
                                                                                @RequestParam(value = "maximumTotalYearOfExperience", required = false) Double maximumTotalYearOfExperience,
                                                                                @RequestParam(value = "applicantCampaignCodes", required = false) Set<String> applicantCampaignCodes,
                                                                                @RequestParam(value = "applicantReferrerIds", required = false) Set<Long> applicantReferrerIds,
                                                                                @RequestParam(value = "applicantSourceSet", required = false) Set<String> applicantSourceSet,
                                                                                @RequestParam(value = "applicantMediumSet", required = false) Set<String> applicantMediumSet,
                                                                                @RequestParam(value = "facetFields",required = false) Set<String> facetFields,
                                                                                @RequestParam(value = "userAttributeList",required = false) Set<String> userAttributeList,
                                                                                @RequestParam(value = "includeApplicantIds",required = false) Set<Long> includeApplicantIds,
                                                                                @RequestParam(value = "opportunityTaskIds",required = false) Set<Long> opportunityTaskIds,
                                                                                @RequestParam(value = "selectionStepIds",required = false) Set<Long> selectionStepIds,
                                                                                @RequestParam(value = "opportunityApplicantTaskStatusSet",required = false) Set<String> opportunityApplicantTaskStatusSet,
                                                                                @RequestParam(value = "isActive",required = false) Boolean isActive) {
        FacetPageDTO<OpportunityApplicantDTO> result = facade.getApplicantsByFilter(searchText, opportunityId, applicationStatus,
                stepCodeSet, stepStatusCodeSet, entityId, startDateTime, endDateTime,
                page, size, excludeApplicationStatus, supervisorId, attendanceStatusSet, sortFieldName, sortFieldOrder,
                candidateType, isVerified, currentOwnerEntityId, forRole, retentionStatus, assessmentSubStepId,
                currentCountry, currentState, currentCity,facetFields,rsvpStatusSet,userAttributeList,eligibilityCriteriaCode,
                isActive, startInterviewDateTime, endInterviewDateTime,pathwayCodes, roleCodes, cohorts, placementCities,
                applicantCampaignCodes, applicantReferrerIds, failedRuleCodes, areaOfResidenceSet, ownerResponseStepCode,
                ownerResponseResultCode, minimumTotalYearOfExperience, maximumTotalYearOfExperience, educationQualificationCodes,
                educationDegreeCodes, currentCitySet, relocationPreferenceSet, selfDeclarationCodes,applicantOnboardingStatus,
                applicantSourceSet,applicantMediumSet,includeApplicantIds, opportunityTaskIds, opportunityApplicantTaskStatusSet,
                selectionStepIds, null);
        return ResponseEntity.status(result.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get Applicants By Filter", notes = "", response = OpportunityApplicantDTO.class, tags = {"OpportunityApplicant"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityApplicantDTO.class)})
    @RequestMapping(value = "/api/v1/opportunity-applicants/filter" ,
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<FacetPageDTO<OpportunityApplicantDTO>> getApplicantsByAllFilter(@RequestParam(value = "searchText", required = false) String searchText,
                                                                                @RequestParam(value = "opportunityId") Long opportunityId,
                                                                                @RequestParam(value = "applicationStatus", required = false) String applicationStatus,
                                                                                @RequestParam(value = "stepCodeSet", required = false) Set<String> stepCodeSet,
                                                                                @RequestParam(value = "stepStatusCodeSet", required = false) Set<String> stepStatusCodeSet,
                                                                                @RequestParam(value = "entityId", required = false) Long entityId,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "startDateTime", required = false) Date startDateTime,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "endDateTime", required = false) Date endDateTime,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "startInterviewDateTime", required = false) Date startInterviewDateTime,
                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") @Valid @RequestParam(value = "endInterviewDateTime", required = false) Date endInterviewDateTime,
                                                                                @RequestParam(value = "page") Integer page,
                                                                                @RequestParam(value = "size") Integer size,
                                                                                @RequestParam(value = "excludeApplicationStatus", required = false) String excludeApplicationStatus,
                                                                                @RequestParam(value = "supervisorId", required = false) Long supervisorId,
                                                                                @RequestParam(value = "attendanceStatusSet", required = false) Set<String> attendanceStatusSet,
                                                                                @RequestParam(value = "eligibilityCriteriaCode", required = false) String eligibilityCriteriaCode,
                                                                                @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
                                                                                @RequestParam(value = "sortFieldOrder", required = false) String sortFieldOrder,
                                                                                @RequestParam(value = "candidateType", required = false) String candidateType,
                                                                                @RequestParam(value = "isVerified", required = false) Boolean isVerified,
                                                                                @RequestParam(value = "failedRuleCodes", required = false) Set<String> failedRuleCodes,
                                                                                @RequestParam(value = "currentOwnerEntityId", required = false) Long currentOwnerEntityId,
                                                                                @RequestParam(value = "forRole", required = false) String forRole,
                                                                                @RequestParam(value = "retentionStatus", required = false) String retentionStatus,
                                                                                @RequestParam(value = "applicantOnboardingStatus", required = false) String applicantOnboardingStatus,
                                                                                @RequestParam(value = "assessmentSubStepId", required = false) Long assessmentSubStepId,
                                                                                @RequestParam(value = "currentCountry", required = false) String currentCountry,
                                                                                @RequestParam(value = "currentState", required = false) String currentState,
                                                                                @RequestParam(value = "currentCity", required = false) String currentCity,
                                                                                @RequestParam(value = "currentCitySet", required = false) Set<String> currentCitySet,
                                                                                @RequestParam(value = "areaOfResidenceSet", required = false) Set<String> areaOfResidenceSet,
                                                                                @RequestParam(value = "rsvpStatusSet", required = false) Set<String> rsvpStatusSet,
                                                                                @RequestParam(value = "pathwayCodes", required = false) Set<String> pathwayCodes,
                                                                                @RequestParam(value = "roleCodes", required = false) Set<String> roleCodes,
                                                                                @RequestParam(value = "cohorts", required = false) Set<String> cohorts,
                                                                                @RequestParam(value = "ownerResponseStepCode", required = false) String ownerResponseStepCode,
                                                                                @RequestParam(value = "ownerResponseResultCode", required = false) String ownerResponseResultCode,
                                                                                @RequestParam(value = "placementCities", required = false) Set<String> placementCities,
                                                                                @RequestParam(value = "educationQualificationCodes", required = false) Set<String> educationQualificationCodes,
                                                                                @RequestParam(value = "educationDegreeCodes", required = false) Set<String> educationDegreeCodes,
                                                                                @RequestParam(value = "relocationPreferenceSet", required = false ) Set<String> relocationPreferenceSet,
                                                                                @RequestParam(value = "selfDeclarationCodes", required = false ) Set<String> selfDeclarationCodes,
                                                                                @RequestParam(value = "minimumTotalYearOfExperience", required = false) Double minimumTotalYearOfExperience,
                                                                                @RequestParam(value = "maximumTotalYearOfExperience", required = false) Double maximumTotalYearOfExperience,
                                                                                @RequestParam(value = "applicantCampaignCodes", required = false) Set<String> applicantCampaignCodes,
                                                                                @RequestParam(value = "applicantReferrerIds", required = false) Set<Long> applicantReferrerIds,
                                                                                @RequestParam(value = "applicantSourceSet", required = false) Set<String> applicantSourceSet,
                                                                                @RequestParam(value = "applicantMediumSet", required = false) Set<String> applicantMediumSet,
                                                                                @RequestParam(value = "facetFields",required = false) Set<String> facetFields,
                                                                                @RequestParam(value = "userAttributeList",required = false) Set<String> userAttributeList,
                                                                                @RequestParam(value = "includeApplicantIds",required = false) Set<Long> includeApplicantIds,
                                                                                @RequestParam(value = "isActive",required = false) Boolean isActive,
                                                                                   @RequestParam(value = "opportunityTaskIds",required = false) Set<Long> opportunityTaskIds,
                                                                                   @RequestParam(value = "selectionStepIds",required = false) Set<Long> selectionStepIds,
                                                                                   @RequestParam(value = "opportunityApplicantTaskStatusSet",required = false) Set<String> opportunityApplicantTaskStatusSet,
                                                                                @RequestBody(required = false) AssessmentFilterDTO assessmentFilterDTO) {
        FacetPageDTO<OpportunityApplicantDTO> result = facade.getApplicantsByFilter(searchText, opportunityId, applicationStatus,
                stepCodeSet, stepStatusCodeSet, entityId, startDateTime, endDateTime,
                page, size, excludeApplicationStatus, supervisorId, attendanceStatusSet, sortFieldName, sortFieldOrder,
                candidateType, isVerified, currentOwnerEntityId, forRole, retentionStatus, assessmentSubStepId,
                currentCountry, currentState, currentCity,facetFields,rsvpStatusSet,userAttributeList,eligibilityCriteriaCode,
                isActive, startInterviewDateTime, endInterviewDateTime,pathwayCodes, roleCodes, cohorts, placementCities,
                applicantCampaignCodes, applicantReferrerIds, failedRuleCodes, areaOfResidenceSet, ownerResponseStepCode,
                ownerResponseResultCode, minimumTotalYearOfExperience, maximumTotalYearOfExperience, educationQualificationCodes,
                educationDegreeCodes, currentCitySet, relocationPreferenceSet, selfDeclarationCodes,applicantOnboardingStatus,
                applicantSourceSet,applicantMediumSet,includeApplicantIds, opportunityTaskIds, opportunityApplicantTaskStatusSet,
                selectionStepIds, assessmentFilterDTO);
        return ResponseEntity.status(result.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get Applicants By Filter For ChangeMaker App", notes = "", response = OpportunityApplicantDTO.class, tags = {"OpportunityApplicant"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityApplicantDTO.class)})
    @RequestMapping(value = "/api/v1/opportunity-applicants/change-maker/filter",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<OpportunityApplicantDTO>> getApplicantsByFilterForChangeMaker(@RequestParam(value = "searchText", required = false) String searchText,
                                                                                           @RequestParam(value = "opportunityId") Long opportunityId,
                                                                                           @RequestParam(value = "applicationStatus", required = false) String applicationStatus,
                                                                                           @RequestParam(value = "stepCodeSet", required = false) Set<String> stepCodeSet,
                                                                                           @RequestParam(value = "stepStatusCodeSet", required = false) Set<String> stepStatusCodeSet,
                                                                                           @RequestParam(value = "page") Integer page,
                                                                                           @RequestParam(value = "size") Integer size,
                                                                                           @RequestParam(value = "excludeApplicationStatus", required = false) String excludeApplicationStatus,
                                                                                           @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
                                                                                           @RequestParam(value = "sortFieldOrder", required = false) String sortFieldOrder,
                                                                                           @RequestParam(value = "currentCountry", required = false) String currentCountry,
                                                                                           @RequestParam(value = "currentState", required = false) String currentState,
                                                                                           @RequestParam(value = "currentCity", required = false) String currentCity,
                                                                                           @RequestParam(value = "rsvpStatusSet", required = false) Set<String> rsvpStatusSet,
                                                                                           @RequestParam(value = "facetFields",required = false) Set<String> facetFields,
                                                                                           @RequestParam(value = "userAttributeList",required = false) Set<String> userAttributeList) {
        FacetPageDTO<OpportunityApplicantDTO> result = facade.getApplicantsByFilterForChangeMaker(searchText,opportunityId,
                applicationStatus,stepCodeSet,stepStatusCodeSet,page,size,excludeApplicationStatus,sortFieldName,sortFieldOrder,
                currentCountry, currentState, currentCity,facetFields,rsvpStatusSet,userAttributeList);
        return ResponseEntity.status(result.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Get Applicant Ids by Filter", notes = "Returns the set of OpportunityApplicant IDs created with date filter",
            response = Set.class, tags = {"OpportunityApplicant"}
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Set.class) })
    @RequestMapping(
            value = "/api/v1/opportunity-applicants/filter/ids",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    public ResponseEntity<Set<Long>> getApplicantIdsByFilter( @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                                                                   @Valid @RequestParam(value = "startDateTime") Date startDateTime,
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                                                                   @Valid @RequestParam(value = "endDateTime") Date endDateTime
    ) {
        Set<Long> opportunityApplicantIds = facade.getApplicantIdsByFilter(startDateTime, endDateTime);
        return ResponseEntity.ok(opportunityApplicantIds);
    }

}