package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.changemaker.dto.CommunityApplicantDTO;
import com.platformcommons.platform.service.changemaker.dto.CustomApplicantOnboardingDTO;
import com.platformcommons.platform.service.changemaker.dto.OpportunityApplicantMetaDTO;
import com.platformcommons.platform.service.changemaker.dto.RecruiterAndCommitteeAssignDTO;
import com.platformcommons.platform.service.dto.access.AuthResultDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.search.application.constant.PolicyEvaluatorEvaluateMethodConstant;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityApplicantService;
import com.platformcommons.platform.service.search.application.utility.ElasticSearchUtility;
import com.platformcommons.platform.service.search.domain.ApplicantOwnerResult;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.domain.OpportunityApplicantTask;
import com.platformcommons.platform.service.search.domain.RuleResult;
import com.platformcommons.platform.service.search.dto.*;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.facade.OpportunityFacade;
import com.platformcommons.platform.service.search.facade.UserFacade;
import com.platformcommons.platform.service.search.facade.assembler.OpportunityApplicantDTOAssembler;
import com.platformcommons.platform.service.search.facade.cache.validator.TenantMetaConfigValidator;
import com.platformcommons.platform.service.search.facade.client.CommonsReportClient;
import com.platformcommons.platform.service.search.facade.client.WorknodeClient;
import com.platformcommons.platform.service.search.messaging.mapper.OpportunityApplicantEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
@Slf4j
public class OpportunityApplicantFacadeImpl implements OpportunityApplicantFacade {

    @Autowired
    private OpportunityApplicantService service;

    @Autowired
    private OpportunityApplicantDTOAssembler assembler;

    @Autowired
    private TenantMetaConfigValidator tenantMetaConfigValidator;

    @Autowired
    private WorknodeClient worknodeClient;

    @Autowired
    private CommonsReportClient commonsReportClient;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private OpportunityFacade opportunityFacade;

    @Autowired
    private OpportunityApplicantEventMapper opportunityApplicantEventMapper;

    @Autowired
    private PolicyEvaluator policyEvaluator;

    public FacetPageDTO<OpportunityApplicantDTO> getApplicantsByFilter(String searchText, Long opportunityId, String applicationStatus,
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
                                                                       Set<String> areaOfResidenceSet, String ownerResponseStepCode,
                                                                       String ownerResponseResultCode, Double minimumTotalYearOfExperience,
                                                                       Double maximumTotalYearOfExperience, Set<String> educationQualificationCodes,
                                                                       Set<String> educationDegreeCodes, Set<String> currentCitySet,
                                                                       Set<String> relocationPreferenceSet, Set<String> selfDeclarationCodes,
                                                                       String applicantOnboardingStatus, Set<String> applicantSourceSet,
                                                                       Set<String> applicantMediumSet,Set<Long> includeApplicantIds,
                                                                       Set<Long> opportunityTaskIds, Set<String> opportunityApplicantTaskStatusSet,
                                                                       Set<Long> selectionStepIds, AssessmentFilterDTO assessmentFilterDTO) {

        policyEvaluator.evaluate(PolicyEvaluatorEvaluateMethodConstant.AUTH_METHOD_OPPORTUNITY_APPLICANT_LIST_GET);

        size = size > 500 ? 500 : size;
        OpportunityApplicantFilterDTO filterDTO = createApplicantFilterDTO(searchText, opportunityId, applicationStatus,
                stepCodeSet, stepStatusCodeSet, startDateTime, endDateTime,
                page, size, excludeApplicationStatus, supervisorId, attendanceStatusSet, sortFieldName, sortFieldOrder,
                candidateType, isVerified, currentOwnerEntityId, forRole, retentionStatus, assessmentSubStepId,
                currentCountry, currentState, currentCity,facetFields,rsvpStatusSet,userAttributeList,eligibilityCriteriaCode,
                isActive,startInterviewDateTime,endInterviewDateTime,pathwayCodes,roleCodes,cohorts,placementCities,
                applicantCampaignCodes, applicantReferrerIds, failedRuleCodes, areaOfResidenceSet, ownerResponseStepCode,
                ownerResponseResultCode, minimumTotalYearOfExperience,maximumTotalYearOfExperience, educationQualificationCodes,
                educationDegreeCodes, currentCitySet, relocationPreferenceSet, selfDeclarationCodes,applicantOnboardingStatus,
                applicantSourceSet,applicantMediumSet,includeApplicantIds, opportunityTaskIds, opportunityApplicantTaskStatusSet,
                selectionStepIds, assessmentFilterDTO);

        if(entityId != null) {
                filterDTO.setEntityIdSet(getAllLeafWorknodesIdByParentId(entityId));
        }

        addFiltersRelatedToLoggedInUserPermission(filterDTO,opportunityId);
        SearchHits<OpportunityApplicant> result = service.getApplicantsBySearch(filterDTO);
        LinkedHashSet<OpportunityApplicantDTO> opportunityApplicantSet = result.stream()
                .map(SearchHit::getContent)
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        appendUserDTOIntoOpportunityApplicantDTOs(opportunityApplicantSet,filterDTO);
        return new FacetPageDTO<>(opportunityApplicantSet,
                ElasticSearchUtility.computeHasMoreHits(result.getTotalHits(),result.getSearchHits().size(),filterDTO.getPage(),
                        filterDTO.getSize()),
                ElasticSearchUtility.createTermsFacetResult(result.getAggregations()),
                result.getTotalHits());
    }

    public void addFiltersRelatedToLoggedInUserPermission(OpportunityApplicantFilterDTO filterDTO,Long opportunityId) {
        AuthResultDTO authResultDTO = policyEvaluator.evaluateAndReturnResult(
                PolicyEvaluatorEvaluateMethodConstant.AUTH_METHOD_OPPORTUNITY_APPLICANT_LIST_GET_ALL, null);

        Boolean isAuthorizedForViewAll = Boolean.TRUE;
        if(authResultDTO != null && authResultDTO.getIsValid() != null) {
            isAuthorizedForViewAll = authResultDTO.getIsValid();
        }

        Boolean isLoggedInUserOpportunityCreator = checkIfUserIsOpportunityCreator(PlatformSecurityUtil.getCurrentUserId(),opportunityId);
        Boolean workforceLinkedApplicantFilter = Boolean.FALSE;
        Boolean communityLinkedApplicantFilter = Boolean.FALSE;
        Boolean supervisorLinkedApplicantFilter = Boolean.FALSE;
        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigValidator.getByTenantLogin(PlatformSecurityUtil.getCurrentTenantLogin());

        if(tenantMetaConfigDTO != null) {
            String metaValue = tenantMetaConfigValidator.getMetaPropertyValueFromTenantMetaConfigDTO(
                    tenantMetaConfigDTO, TenantConfigConstant.CONFIG_KEY_WORKFORCE_LINKED_APPLICANTS_FILTER,null);
            if (Objects.equals(metaValue,"1")) {
                workforceLinkedApplicantFilter = Boolean.TRUE;
            }

            metaValue = tenantMetaConfigValidator.getMetaPropertyValueFromTenantMetaConfigDTO(
                    tenantMetaConfigDTO,TenantConfigConstant.CONFIG_KEY_COMMUNITY_LINKED_APPLICANT_FILTER,null);
            if (Objects.equals(metaValue,"1")) {
                communityLinkedApplicantFilter = Boolean.TRUE;
            }

            metaValue = tenantMetaConfigValidator.getMetaPropertyValueFromTenantMetaConfigDTO(
                    tenantMetaConfigDTO,TenantConfigConstant.CONFIG_KEY_SUPERVISOR_LINKED_APPLICANT_FILTER,null);
            if (Objects.equals(metaValue,"1")) {
                supervisorLinkedApplicantFilter = Boolean.TRUE;
            }

            Set<Long> worknodeIdsForLoggedInUserFilter = getLeafWorknodeForLoggedInUser(workforceLinkedApplicantFilter,
                    isLoggedInUserOpportunityCreator,isAuthorizedForViewAll);
            Set<Long> communityIdsForLoggedInUserFilter = getAllTaggedCommunityIdsForLoggedInUser(communityLinkedApplicantFilter,
                    isLoggedInUserOpportunityCreator,isAuthorizedForViewAll);
            Long supervisorIdForLoggedInUserFilter = getSupervisorIdForLoggedInUser(supervisorLinkedApplicantFilter,
                    isLoggedInUserOpportunityCreator,isAuthorizedForViewAll);

            filterDTO.setWorknodeIdsForLoggedInUserFilter(worknodeIdsForLoggedInUserFilter);
            filterDTO.setCommunityIdsForLoggedInUserFilter(communityIdsForLoggedInUserFilter);
            filterDTO.setSupervisorIdForLoggedInUserFilter(supervisorIdForLoggedInUserFilter);
        }
    }

    public Boolean checkIfUserIsOpportunityCreator(Long userId,Long opportunityId) {
        Long opportunityCreatorUserId = opportunityFacade.getCreatedByUserId(opportunityId);
        return Objects.equals(opportunityCreatorUserId,userId);
    }


    @Override
    public FacetPageDTO<OpportunityApplicantDTO> getApplicantsByFilterForChangeMaker(String searchText, Long opportunityId,
                                                                                     String applicationStatus, Set<String> stepCodeSet,
                                                                                     Set<String> stepStatusCodeSet, Integer page,
                                                                                     Integer size, String excludeApplicationStatus,
                                                                                     String sortFieldName, String sortFieldOrder,
                                                                                     String currentCountry, String currentState, String currentCity,
                                                                                     Set<String> facetFields, Set<String> rsvpStatusSet,
                                                                                     Set<String> userAttributeList) {
        size = size > 500 ? 500 : size;
        Boolean isActive = Boolean.TRUE;
        OpportunityApplicantFilterDTO filterDTO = createApplicantFilterDTO(searchText, opportunityId, applicationStatus,
                stepCodeSet, stepStatusCodeSet, null, null,
                page, size, excludeApplicationStatus, null, null, sortFieldName, sortFieldOrder,
                null, null, null, null, null, null,
                currentCountry, currentState, currentCity,facetFields,rsvpStatusSet,userAttributeList,null,
                isActive,null,null,null,null,null,
                null, null, null, null, null, null,
                null, null, null, null,
                null, null, null, null,null,
                null,null,null, null, null, 
                null, null);

        SearchHits<OpportunityApplicant> result = service.getApplicantsBySearch(filterDTO);
        LinkedHashSet<OpportunityApplicantDTO> opportunityApplicantSet = result.stream()
                .map(SearchHit::getContent)
                .map(assembler::toDTOForChangeMaker)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        appendUserDTOIntoOpportunityApplicantDTOs(opportunityApplicantSet,filterDTO);
        return new FacetPageDTO<>(opportunityApplicantSet,
                ElasticSearchUtility.computeHasMoreHits(result.getTotalHits(),result.getSearchHits().size(),filterDTO.getPage(),
                        filterDTO.getSize()),
                ElasticSearchUtility.createTermsFacetResult(result.getAggregations()),
                result.getTotalHits());
    }


    public void appendUserDTOIntoOpportunityApplicantDTOs(Set<OpportunityApplicantDTO> opportunityApplicantDTOS, OpportunityApplicantFilterDTO filterDTO) {
        Set<Long> userIds = opportunityApplicantDTOS.stream()
                .map(OpportunityApplicantDTO::getMarketUserId)
                .collect(Collectors.toSet());
        if(!userIds.isEmpty() && filterDTO.getUserAttributeList() != null && !filterDTO.getUserAttributeList().isEmpty()) {
            Set<String> fields = new HashSet<>(filterDTO.getUserAttributeList());
            Map<Long, UserDTO> userDTOMap = userFacade.getUserMapByUserIds(userIds,fields);
            for (OpportunityApplicantDTO opportunityApplicantDTO : opportunityApplicantDTOS) {
                opportunityApplicantDTO.setUserDTO(userDTOMap.getOrDefault(opportunityApplicantDTO.getMarketUserId(), null));
            }
        }
    }

    @Override
    public FacetPageDTO<OpportunityApplicantDTO> getUserIdsOfApplicantsByFilter(Set<Long> opportunityIds, Set<String> stepCodeSet, Set<String> stepStatusCodeSet, Pageable pageable) {
        SearchHits<OpportunityApplicant> result = service.getUserIdsOfApplicantsByFilter(opportunityIds, stepCodeSet, stepStatusCodeSet, pageable);
        LinkedHashSet<OpportunityApplicantDTO> opportunityApplicantSet = result.stream()
                .map(SearchHit::getContent)
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new FacetPageDTO<>(
                opportunityApplicantSet,
                ElasticSearchUtility.computeHasMoreHits(result.getTotalHits(), result.getSearchHits().size(),
                        pageable.getPageNumber(), pageable.getPageSize()),
                ElasticSearchUtility.createTermsFacetResult(result.getAggregations()),
                result.getTotalHits()
        );
    }

    public Set<Long> getLeafWorknodeForLoggedInUser(Boolean workforceLinkedApplicantFilter, Boolean isLoggedInUserOpportunityCreator,
                                                    Boolean isAuthorizedForViewAll) {
        String leafWorkNodeType = ServiceConstant.LEAF_WORKNODE_TYPE_CENTER;
        Set<Long> worknodeIds = null;
        if(!isAuthorizedForViewAll && !isLoggedInUserOpportunityCreator && Objects.equals(workforceLinkedApplicantFilter,Boolean.TRUE)) {
            worknodeIds = Objects.requireNonNull(worknodeClient.getWorkNodeForWorkforce(PlatformSecurityUtil.getCurrentUserId(),
                    Boolean.TRUE, leafWorkNodeType,PlatformSecurityUtil.getToken()).getBody());
        }
        return  worknodeIds;
    }


    public Set<Long> getAllTaggedCommunityIdsForLoggedInUser(Boolean communityLinkedApplicantFilter,
                                                             Boolean isLoggedInUserOpportunityCreator,
                                                             Boolean isAuthorizedForViewAll) {
        Set<Long> communityIds = null;
        if(!isAuthorizedForViewAll && !isLoggedInUserOpportunityCreator && Objects.equals(communityLinkedApplicantFilter,Boolean.TRUE)) {
            List<CommunityApplicantDTO> communityApplicantDTOS = Objects.requireNonNull(commonsReportClient.getAllTaggedCommunitiesForLoggedInUser
                    (PlatformSecurityUtil.getToken()).getBody());
            communityIds = communityApplicantDTOS
                    .stream()
                    .map(CommunityApplicantDTO::getCommunityId)
                    .collect(Collectors.toSet());
        }
        return communityIds;
    }


    public Long getSupervisorIdForLoggedInUser(Boolean supervisorLinkedApplicantFilter, Boolean isLoggedInUserOpportunityCreator,
                                               Boolean isAuthorizedForViewAll) {
        Long finalSupersisorId = null;
        if(!isAuthorizedForViewAll && !isLoggedInUserOpportunityCreator && Objects.equals(supervisorLinkedApplicantFilter,Boolean.TRUE)) {
            finalSupersisorId = PlatformSecurityUtil.getCurrentUserId();
        }
        return finalSupersisorId;
    }

    public Set<Long> getAllLeafWorknodesIdByParentId(Long entityId) {
        Set<Long> worknodeIds = new HashSet<>();
        worknodeIds.add(entityId);
        worknodeIds.addAll(Objects.requireNonNull(worknodeClient.getLeafWorkNodeIdsForParentWorkNode(entityId,
                PlatformSecurityUtil.getToken()).getBody()));
        return worknodeIds;
    }

    public OpportunityApplicantFilterDTO createApplicantFilterDTO(String searchText, Long opportunityId, String applicationStatus,
                                                                  Set<String> stepCodeSet, Set<String> stepStatusCodeSet,
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
                                                                  Set<String> areaOfResidenceSet, String ownerResponseStepCode,
                                                                  String ownerResponseResultCode, Double minimumTotalYearOfExperience,
                                                                  Double maximumTotalYearOfExperience, Set<String> educationQualificationCodes,
                                                                  Set<String> educationDegreeCodes, Set<String> currentCitySet,
                                                                  Set<String> relocationPreferenceSet, Set<String> selfDeclarationCodes,
                                                                  String applicantOnboardingStatus,Set<String> applicantSourceSet,
                                                                  Set<String> applicantMediumSet,Set<Long> includeApplicantIds,
                                                                  Set<Long> opportunityTaskIds, Set<String> opportunityApplicantTaskStatusSet,
                                                                  Set<Long> selectionStepIds, AssessmentFilterDTO assessmentFilter) {
        return  OpportunityApplicantFilterDTO.builder()
                .searchTerm(searchText)
                .isActive(isActive == null ? Boolean.TRUE : isActive)
                .opportunityId(opportunityId)
                .eligibilityCriteriaCode(eligibilityCriteriaCode)
                .applicationStatus(applicationStatus)
                .stepCodeSet(stepCodeSet)
                .stepStatusCodeSet(stepStatusCodeSet)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .startInterviewDateTime(startInterviewDateTime)
                .endInterviewDateTime(endInterviewDateTime)
                .page(page)
                .size(size)
                .excludeApplicationStatus(excludeApplicationStatus)
                .supervisorId(supervisorId)
                .attendanceStatusSet(attendanceStatusSet)
                .sortFieldName(sortFieldName)
                .sortFieldOrder(sortFieldOrder)
                .candidateType(candidateType)
                .isVerified(isVerified)
                .currentOwnerEntityId(currentOwnerEntityId)
                .forRole(forRole)
                .retentionStatus(retentionStatus)
                .assessmentSubStepId(assessmentSubStepId)
                .currentCountry(currentCountry)
                .currentState(currentState)
                .currentCity(currentCity)
                .facetFields(facetFields)
                .rsvpStatusSet(rsvpStatusSet)
                .userAttributeList(userAttributeList)
                .pathwayCodes(pathwayCodes)
                .roleCodes(roleCodes)
                .cohorts(cohorts)
                .areaOfResidenceSet(areaOfResidenceSet)
                .placementCities(placementCities)
                .applicantCampaignCodes(applicantCampaignCodes)
                .applicantReferrerIds(applicantReferrerIds)
                .failedRuleCodes(failedRuleCodes)
                .ownerResponseStepCode(ownerResponseStepCode)
                .ownerResponseResultCode(ownerResponseResultCode)
                .minimumTotalYearOfExperience(minimumTotalYearOfExperience)
                .maximumTotalYearOfExperience(maximumTotalYearOfExperience)
                .educationQualificationCodes(educationQualificationCodes)
                .educationDegreeCodes(educationDegreeCodes)
                .currentCitySet(currentCitySet)
                .relocationPreferenceSet(relocationPreferenceSet)
                .selfDeclarationCodes(selfDeclarationCodes)
                .applicantOnboardingStatus(applicantOnboardingStatus)
                .applicantSourceSet(applicantSourceSet)
                .applicantMediumSet(applicantMediumSet)
                .includeApplicantIds(includeApplicantIds)
                .opportunityTaskIds(opportunityTaskIds)
                .opportunityApplicantTaskStatusSet(opportunityApplicantTaskStatusSet)
                .selectionStepIds(selectionStepIds)
                .assessmentFilter(assessmentFilter)
                .build();
    }

    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void createOrUpdateOpportunityApplicant(OpportunityApplicant opportunityApplicant) {
        Long id = opportunityApplicant.getId();
        Long userId = opportunityApplicant.getMarketUserId();
        Optional<OpportunityApplicant> optionalOpportunityApplicant = service.getOptionalById(id);

        if (optionalOpportunityApplicant.isPresent()) {
            OpportunityApplicant fetchedOpportunityApplicant = optionalOpportunityApplicant.get();
            if(fetchedOpportunityApplicant.getApplicantVersion() == null
                    || opportunityApplicant.getApplicantVersion() == null
                    || (fetchedOpportunityApplicant.getApplicantVersion() < opportunityApplicant.getApplicantVersion())) {
                fetchedOpportunityApplicant.putUpdate(opportunityApplicant);
                service.save(fetchedOpportunityApplicant);
            }
            else {
                log.error("Opportunity Applicant with id {} could not be update due to older version",id);
            }
        } else {
            UserDTO userDTO = userFacade.getUserById(userId);
            syncUserDetailsInApplicant(opportunityApplicant, userDTO);
            service.save(opportunityApplicant);
        }
    }


    public void syncUserDetailsInApplicant(OpportunityApplicant opportunityApplicant, UserDTO userDTO) {
        if(userDTO != null) {
            opportunityApplicant.setName(userDTO.getDisplayName());
            opportunityApplicant.setApplicantEmail(userDTO.getPrimaryEmail());
            opportunityApplicant.setContactNumber(userDTO.getPrimaryContactNumber());
            opportunityApplicant.setCurrentCountryCode(userDTO.getCurrentCountryCode());
            opportunityApplicant.setCurrentStateCode(userDTO.getCurrentStateCode());
            opportunityApplicant.setCurrentCityCode(userDTO.getCurrentCityCode());
            opportunityApplicant.setCohort(userDTO.getCohort());
            opportunityApplicant.setPlacementCity(userDTO.getPlacementCity());
            opportunityApplicant.setPathwayCodes(userDTO.getPathwayCodes());
            opportunityApplicant.setRoleCodes(userDTO.getRoleCodes());
            opportunityApplicant.setTotalYearOfExperience(userDTO.getTotalYearsOfExperience());
            opportunityApplicant.setEducationQualificationCodes(userDTO.getEducationQualificationCodes());
            opportunityApplicant.setEducationDegreeCodes(userDTO.getEducationDegreeCodes());
            opportunityApplicant.setRelocationPreferenceSet(userDTO.getRelocationPreferenceSet());
            opportunityApplicant.setSelfDeclarationCodes(userDTO.getSelfDeclarationCodes());
        }
    }

    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void updateUserFieldsInApplicant(UserDTO userDTO) {
        Long userId = userDTO.getUserId();
        Set<OpportunityApplicant> opportunityApplicantSet = service.getAllByMarketUserId(userId);
        for (OpportunityApplicant opportunityApplicant : opportunityApplicantSet) {
            syncUserDetailsInApplicant(opportunityApplicant,userDTO);
        }
        service.saveAll(opportunityApplicantSet);
    }

    @Override
    public void syncOpportunityApplicant(OpportunityApplicant opportunityApplicant) {
        Long userId = opportunityApplicant.getMarketUserId();
        UserDTO userDTO = userFacade.getUserById(userId);
        syncUserDetailsInApplicant(opportunityApplicant,userDTO);
        service.save(opportunityApplicant);
    }

    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void addRuleResultInApplicant(RuleResult ruleResult) {
        if(ruleResult != null && ruleResult.getOpportunityApplicantId() != null) {
            Long opportunityApplicantId = ruleResult.getOpportunityApplicantId();
            Optional<OpportunityApplicant> optionalOpportunityApplicant = service.getOptionalById(opportunityApplicantId);

            if (optionalOpportunityApplicant.isPresent()) {
                OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
                Set<RuleResult> ruleResults = opportunityApplicant.getRuleResults();

                if (ruleResults == null) {
                    ruleResults = new HashSet<>();
                }

                ruleResults.add(ruleResult);
                opportunityApplicant.setRuleResults(ruleResults);
                service.save(opportunityApplicant);
            }
        }
    }


    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void addOrUpdateApplicantOwnerResultInApplicant(ApplicantOwnerResult applicantOwnerResult) {
        if(applicantOwnerResult != null && applicantOwnerResult.getOpportunityApplicantId() != null
                && applicantOwnerResult.getId() != null) {
            Long id = applicantOwnerResult.getId();
            Long opportunityApplicantId = applicantOwnerResult.getOpportunityApplicantId();
            Optional<OpportunityApplicant> optionalOpportunityApplicant = service.getOptionalById(opportunityApplicantId);

            if (optionalOpportunityApplicant.isPresent()) {
                OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
                Set<ApplicantOwnerResult> applicantOwnerResultSet = opportunityApplicant.getApplicantOwnerResults();

                if (applicantOwnerResultSet == null) {
                    applicantOwnerResultSet = new HashSet<>();
                }

                Optional<ApplicantOwnerResult> optionalApplicantOwnerResult = applicantOwnerResultSet.stream()
                        .filter(it-> Objects.equals(id,it.getId()))
                        .findFirst();

                if(optionalApplicantOwnerResult.isPresent()) {
                    ApplicantOwnerResult fetchedApplicantOwnerResult = optionalApplicantOwnerResult.get();
                    fetchedApplicantOwnerResult.patchUpdate(applicantOwnerResult);
                }
                else {
                    applicantOwnerResultSet.add(applicantOwnerResult);
                }

                opportunityApplicant.setApplicantOwnerResults(applicantOwnerResultSet);
                service.save(opportunityApplicant);
            }
        }
    }


    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void addApplicantOnboardingDetailsInApplicant(CustomApplicantOnboardingDTO customApplicantOnboardingDTO) {
        if (customApplicantOnboardingDTO != null && customApplicantOnboardingDTO.getOpportunityApplicantId() != null){
            Long opportunityApplicantId = customApplicantOnboardingDTO.getOpportunityApplicantId();
            Optional<OpportunityApplicant> optionalOpportunityApplicant = service.getOptionalById(opportunityApplicantId);
            if (optionalOpportunityApplicant.isPresent()) {
                OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
                opportunityApplicant.setApplicantOnboardingStatus(customApplicantOnboardingDTO.getOnboardingStatus());
                service.save(opportunityApplicant);
            }
        }
    }

    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void updateAttendanceStatus(Long opportunityApplicantId, String attendanceStatus) {
        if(opportunityApplicantId != null && attendanceStatus != null) {
            Optional<OpportunityApplicant> optionalOpportunityApplicant = service.getOptionalById(opportunityApplicantId);

            if (optionalOpportunityApplicant.isPresent()) {
                OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
                opportunityApplicant.setAttendanceStatus(attendanceStatus);
                service.save(opportunityApplicant);
            }
        }
    }

    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void updateRecruiterOrCommitteeAssignedInBulk(RecruiterAndCommitteeAssignDTO recruiterAndCommitteeAssignDTO) {
        if (recruiterAndCommitteeAssignDTO.getOpportunityApplicantId() != null) {
            Optional<OpportunityApplicant> optionalOpportunityApplicant = service.getOptionalById(recruiterAndCommitteeAssignDTO.getOpportunityApplicantId());
            if (optionalOpportunityApplicant.isPresent()) {
                OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
                opportunityApplicant.setCurrentSupervisorLinkedUserId(recruiterAndCommitteeAssignDTO.getCurrentSupervisorLinkedUserId());
                opportunityApplicant.setCurrentOwnerEntityId(recruiterAndCommitteeAssignDTO.getCurrentOwnerEntityId());
                opportunityApplicant.setCurrentOwnerEntityType(recruiterAndCommitteeAssignDTO.getCurrentOwnerEntityType());
                opportunityApplicant.setApplicantVersion(recruiterAndCommitteeAssignDTO.getVersion());
                service.save(opportunityApplicant);
            }
        }
    }

    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void appendOpportunityApplicantMetaDetails(OpportunityApplicantMetaDTO opportunityApplicantMetaDTO) {
        if(opportunityApplicantMetaDTO != null && opportunityApplicantMetaDTO.getOpportunityApplicantId() != null) {
            Long opportunityApplicantId = opportunityApplicantMetaDTO.getOpportunityApplicantId();
            Optional<OpportunityApplicant> optionalOpportunityApplicant = service.getOptionalById(opportunityApplicantId);

            if (optionalOpportunityApplicant.isPresent()) {
                OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
                opportunityApplicantEventMapper.appendOpportunityApplicantMeta(opportunityApplicant,opportunityApplicantMetaDTO);
                service.save(opportunityApplicant);
            }
        }
    }

    @Override
    public Set<Long> getApplicantIdsByFilter(Date startDateTime, Date endDateTime) {
        return service.getApplicantIdsByFilter(startDateTime, endDateTime);
    }

}