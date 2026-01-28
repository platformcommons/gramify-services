package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.opportunity.dto.MarketConfigDTO;
import com.platformcommons.platform.service.search.application.constant.Fetch;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityService;
import com.platformcommons.platform.service.search.application.utility.*;
import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.dto.*;
import com.platformcommons.platform.service.search.facade.OpportunityFacade;
import com.platformcommons.platform.service.search.facade.assembler.OpportunityDTOAssembler;
import com.platformcommons.platform.service.search.facade.client.CommonsOpportunityClient;
import com.platformcommons.platform.service.search.facade.client.WorknodeClient;
import com.platformcommons.platform.service.search.facade.client.utility.CommonsReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class OpportunityFacadeImpl implements OpportunityFacade {

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private OpportunityDTOAssembler assembler;

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    @Autowired
    private WorknodeClient worknodeClient;

    @Autowired
    private CommonsOpportunityClient commonsOpportunityClient;

    @Override
    public FacetPageDTO<OpportunityDTO> getOpportunitiesByFilterForAdmin(String searchTerm, String fetch, Set<String> causeCodes, Set<String> locationCodes,
                                                                         Set<String> locationTypes, Set<String> skillCodes, Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                                         String marketUUID, Set<String> jobTypes, Double minimumCompensation,
                                                                         Double maximumCompensation, Set<String> workPlaceTypes, Long minimumExperienceDays,
                                                                         Long maximumExperienceDays, String cityCode, String stateCode, String countryCode,
                                                                         Set<String> roleTypes, Set<String> pathways, Set<String> sectors, Set<String> cityCodes,
                                                                         Set<String> organisationCodes, String datePostedCode, String status, Integer page, Integer size,
                                                                         Set<String> facetFields, Set<String> organisationTypes, String marketPlaceVisibility,
                                                                         Boolean myOpportunities, Boolean fetchBasedOnRole, Boolean restrictApplication,
                                                                         String opportunityTimeline, Set<String> opportunityCategories, Set<String> opportunitySubCategories, Set<String> tags,
                                                                         Boolean isActive, Long worknodeId, String tenantLogin,
                                                                         Set<String> targetGroups, Boolean fetchPromotedOpportunities,
                                                                         Set<String> educationQualificationCodes, Set<String> educationDegreeCodes) {

        OpportunityFilterDTO opportunityFilterDTO = makeOpportunityFilterDTO(searchTerm,null, fetch, causeCodes, locationCodes,locationTypes, skillCodes,
                opportunityTypes, opportunitySubTypes, marketUUID, jobTypes, minimumCompensation, maximumCompensation, workPlaceTypes,
                minimumExperienceDays, maximumExperienceDays, cityCode, stateCode, countryCode, roleTypes, pathways, sectors, cityCodes,
                organisationCodes, datePostedCode, status, page, size, facetFields, organisationTypes, marketPlaceVisibility,
                fetchBasedOnRole, restrictApplication, opportunityTimeline, opportunityCategories, tags,isActive,
                null, null,tenantLogin,null,null,
                targetGroups,fetchPromotedOpportunities,educationQualificationCodes,educationDegreeCodes, opportunitySubCategories, null);
        appendMarketAndPartnerAccessInFilterDTO(opportunityFilterDTO);
        appendCreatedByUserInFilterDTO(opportunityFilterDTO,worknodeId,myOpportunities,null,null);
        Pageable pageable = getPageableBasedOnFetchType(opportunityFilterDTO.getSearchTerm(),opportunityFilterDTO.getPage(),
                opportunityFilterDTO.getSize(),opportunityFilterDTO.getFetch());
        FacetPage<Opportunity> facetPage = opportunityService.getOpportunitiesByFilterForAdmin(opportunityFilterDTO.getSearchTerm(),
                opportunityFilterDTO,pageable);
        return new FacetPageDTO<>(facetPage.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),
                facetPage.hasNext(), FacetUtil.createFacetResult(facetPage), facetPage.getTotalElements());    }

    public void appendCreatedByUserInFilterDTO(OpportunityFilterDTO opportunityFilterDTO,Long worknodeId,Boolean myOpportunities,
                                               Set<String> userProgramCodes,Boolean loggedInUserOpportunity) {
        Set<Long> createdByUserIds = null;
        if(worknodeId != null || Boolean.TRUE.equals(myOpportunities) || (userProgramCodes != null && !userProgramCodes.isEmpty())
                || Objects.equals(loggedInUserOpportunity,Boolean.TRUE) ){
            createdByUserIds = new HashSet<>();
            if(worknodeId != null) {
                Set<Long> userIds = worknodeClient.getUserIdsFromWorkForceChildWorkNodeIdsAndParentWorkNode(worknodeId,
                        PlatformSecurityUtil.getToken()).getBody();
                assert userIds != null;
                createdByUserIds.addAll(userIds);
            }

            if(Boolean.TRUE.equals(myOpportunities)) {
                createdByUserIds.add(PlatformSecurityUtil.getCurrentUserId());
            }

            if(userProgramCodes != null && ! userProgramCodes.isEmpty()) {
                Set<Long> userIds = commonsReportUtil.getUserIdsByProgramCodes(userProgramCodes);
                createdByUserIds.addAll(userIds);
            }
            if(PlatformUtil.isSession() && Objects.equals(loggedInUserOpportunity,Boolean.TRUE)) {
                createdByUserIds.add(PlatformSecurityUtil.getCurrentUserId());
            }
        }
        opportunityFilterDTO.setCreatedByUserIds(createdByUserIds);
    }

    public void appendMarketAndPartnerAccessInFilterDTO(OpportunityFilterDTO opportunityFilterDTO) {
        MarketConfigDTO marketConfigDTO = commonsOpportunityClient.getByMarketUUID(opportunityFilterDTO.getMarketUUID(),null).getBody();
        if(marketConfigDTO == null) {
            throw new NotFoundException(String.format("Market Config with uuid %s not found",opportunityFilterDTO.getMarketUUID()));
        }
        String tenantLogin = null;
        if(!marketConfigDTO.getGovernorTenant().equals(PlatformSecurityUtil.getCurrentTenantId())) {
            tenantLogin = PlatformSecurityUtil.getCurrentTenantLogin();
        }
        opportunityFilterDTO.setTenantLogin(tenantLogin);
    }

    @Override
    public Set<OpportunityDTO> getRecommendedOpportunities(Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                           String marketUUID, Boolean fetchBasedOnRole,
                                                           String opportunityTimeline, Set<String> fetchSet,
                                                           Set<String> opportunityTimelineBasedOnScheduledDate,
                                                           Set<String> opportunityTimelineBasedOnApplicationDate) {
        String marketPlaceVisibility = ServiceConstant.MARKET_PLACE_VISIBILITY_VISIBLE;
        String status = ServiceConstant.CHANGEMAKER_OPPORTUNITY_STATUS_PUBLISHED;
        OpportunityFilterDTO filterDTO = buildCommonsOpportunityFilterDTO(opportunityTypes, opportunitySubTypes, marketUUID,
                fetchBasedOnRole, Boolean.TRUE, status, marketPlaceVisibility,  opportunityTimeline,
                opportunityTimelineBasedOnScheduledDate,opportunityTimelineBasedOnApplicationDate,Boolean.TRUE);

        Set<Long> promotedOpportunityIds = opportunityService.getPromotedOpportunityIds(filterDTO,PageRequest.of(0,10000));
        filterDTO.setExcludeOpportunityIds(promotedOpportunityIds);
        Integer page = 0;
        Integer size = fetchSet != null ? fetchSet.size()*2 : 6 ;
        Set<OpportunityDTO> resultSet = new LinkedHashSet<>();
        Set<Long> resultOpportunityIds = new HashSet<>();
        Pageable pageable;
        Page<Opportunity> opportunityPage;
        if (fetchSet != null) {
            for(String fetchType : fetchSet) {
                pageable = getPageableBasedOnFetchType(null,page,size,fetchType);
                opportunityPage = opportunityService.getOpportunitiesByPaginationForChangemaker(filterDTO,pageable);
                opportunityPage.stream()
                        .filter(opportunity -> !resultOpportunityIds.contains(opportunity.getId()))
                        .findFirst()
                        .ifPresent(opportunity -> {
                            OpportunityDTO opportunityDTO = assembler.toDTO(opportunity);
                            opportunityDTO.setFetchType(fetchType);
                            resultSet.add(opportunityDTO);
                            resultOpportunityIds.add(opportunity.getId());
                        });
            }
        }
        return resultSet;
    }

    @Override
    public Set<OpportunityDTO> getUserSuggestedCustomOpportunities(Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                                   String marketUUID, Boolean fetchBasedOnRole,
                                                                   String opportunityTimeline, Integer size,
                                                                   Set<String> opportunityTimelineBasedOnApplicationDate,
                                                                   Set<String> opportunityTimelineBasedOnScheduledDate) {
        String status = ServiceConstant.CHANGEMAKER_OPPORTUNITY_STATUS_PUBLISHED;
        String marketPlaceVisibility = ServiceConstant.MARKET_PLACE_VISIBILITY_VISIBLE;
        Set<OpportunityDTO> suggestedOpportunities = new LinkedHashSet<>();
        Map<String, String> userDetailsMap = commonsReportUtil.getUserDetailsForOpportunityRecommendation(
                PlatformSecurityUtil.getCurrentUserId(), opportunityTypes);
        Set<String> pathwayCodeSet = null;
        Set<String> skillSet = null;
        Set<Long> opportunitiesToExcludeSet = null;
        String city = null;

        if (userDetailsMap != null && !userDetailsMap.isEmpty()) {
            pathwayCodeSet = getSetOfStringFromConcatenatedString(userDetailsMap.get("pathwayCodes"),",");
            skillSet = getSetOfStringFromConcatenatedString(userDetailsMap.get("skillCodes"),",");
            opportunitiesToExcludeSet = getSetOfStringFromConcatenatedString(userDetailsMap.get("opportunityIds"),",")
                    .stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
            city = userDetailsMap.containsKey("city") ? userDetailsMap.get("city").trim() : null;
        }

        OpportunityFilterDTO filterDTO = buildCommonsOpportunityFilterDTO(opportunityTypes, opportunitySubTypes, marketUUID,
                fetchBasedOnRole, Boolean.TRUE,status, marketPlaceVisibility, opportunityTimeline,opportunityTimelineBasedOnScheduledDate,
                opportunityTimelineBasedOnApplicationDate,Boolean.TRUE);
        filterDTO.setExcludeOpportunityIds(opportunitiesToExcludeSet);
        Pageable pageable = PageRequest.of(0, size);

        if(pathwayCodeSet != null && !pathwayCodeSet.isEmpty()) {
            filterDTO.setPathways(pathwayCodeSet);
            Page<Opportunity> pathwayOpportunitiesPage = opportunityService.getOpportunitiesByPaginationForChangemaker(filterDTO, pageable);
            opportunitiesToExcludeSet.addAll(pathwayOpportunitiesPage.stream()
                    .peek(opportunity -> {
                        OpportunityDTO opportunityDTO = assembler.toDTO(opportunity);
                        opportunityDTO.setMatchType("MATCH_TYPE.PATHWAY");
                        suggestedOpportunities.add(opportunityDTO);
                    })
                    .map(Opportunity::getId)
                    .collect(Collectors.toSet()));

            filterDTO.setExcludeOpportunityIds(opportunitiesToExcludeSet);
            filterDTO.setPathways(null);
        }

        if (suggestedOpportunities.size() < size && skillSet != null && !skillSet.isEmpty()) {
            Set<String> opportunityMappedUserSkills = SkillMapper.mapUserSkillsToOpportunitySkills(skillSet);
            filterDTO.setSkillCodes(opportunityMappedUserSkills);
            pageable = PageRequest.of(0, size - suggestedOpportunities.size());
            Page<Opportunity> skillOpportunitiesPage = opportunityService.getOpportunitiesByPaginationForChangemaker(filterDTO, pageable);
            opportunitiesToExcludeSet.addAll(skillOpportunitiesPage.stream()
                    .peek(opportunity -> {
                        OpportunityDTO opportunityDTO = assembler.toDTO(opportunity);
                        opportunityDTO.setMatchType("MATCH_TYPE.SKILL");
                        suggestedOpportunities.add(opportunityDTO);
                    })
                    .map(Opportunity::getId)
                    .collect(Collectors.toSet()));

            filterDTO.setSkillCodes(null);
            filterDTO.setExcludeOpportunityIds(opportunitiesToExcludeSet);
        }

        if (suggestedOpportunities.size() < size && city != null) {
            pageable = PageRequest.of(0, size - suggestedOpportunities.size());
            filterDTO.setCityCode(city);
            Page<Opportunity> cityOpportunitiesPage = opportunityService.getOpportunitiesByPaginationForChangemaker(filterDTO, pageable);
            opportunitiesToExcludeSet.addAll(cityOpportunitiesPage.stream()
                    .peek(opportunity -> {
                        OpportunityDTO opportunityDTO = assembler.toDTO(opportunity);
                        opportunityDTO.setMatchType("MATCH_TYPE.CITY");
                        suggestedOpportunities.add(opportunityDTO);
                    })
                    .map(Opportunity::getId)
                    .collect(Collectors.toSet()));

            filterDTO.setCityCode(null);
            filterDTO.setExcludeOpportunityIds(opportunitiesToExcludeSet);
        }

        return suggestedOpportunities;
    }

    public static Set<String> getSetOfStringFromConcatenatedString(String value,String separator) {
        return Optional.ofNullable(value)
                .map(String::trim)
                .filter(it -> !it.isEmpty())
                .map(it -> Arrays.stream(it.split(separator))
                        .map(String::trim)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }


    @Override
    public FacetPageDTO<OpportunityDTO> getOpportunitiesByFilterForChangeMaker(String searchTerm, String addressSearchTerm, String fetch, Set<String> causeCodes,
                                                                               Set<String> locationCodes, Set<String> locationTypes, Set<String> skillCodes,
                                                                               Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                                               String marketUUID, Set<String> jobTypes, Double minimumCompensation,
                                                                               Double maximumCompensation, Set<String> workPlaceTypes,
                                                                               Long minimumExperienceDays, Long maximumExperienceDays, String cityCode,
                                                                               String stateCode, String countryCode, Set<String> roleTypes,
                                                                               Set<String> pathways, Set<String> sectors, Set<String> cityCodes,
                                                                               Set<String> organisationCodes, String datePostedCode, Integer page,
                                                                               Integer size, Set<String> organisationTypes,
                                                                               Boolean fetchBasedOnRole, Boolean restrictApplication,
                                                                               String opportunityTimeline, Set<String> opportunityCategories, Set<String> opportunitySubCategories,
                                                                               Set<String> tags, Set<String> facetFields, Set<String> userProgramCodes,
                                                                               String tenantLogin, Date scheduledStartDate, Date scheduledEndDate,
                                                                               Set<String> opportunityTimelineBasedOnScheduledDate,
                                                                               Set<String> opportunityTimelineBasedOnApplicationDate,
                                                                               Set<String> targetGroups, Boolean fetchPromotedOpportunities,
                                                                               Set<String> educationQualificationCodes, Set<String> educationDegreeCodes,
                                                                               Set<String> tenantLogins,Boolean loggedInUserOpportunity) {

        String marketPlaceVisibility = ServiceConstant.MARKET_PLACE_VISIBILITY_VISIBLE;
        String status = ServiceConstant.CHANGEMAKER_OPPORTUNITY_STATUS_PUBLISHED;
        OpportunityFilterDTO filterDTO = makeOpportunityFilterDTO(searchTerm, addressSearchTerm,fetch, causeCodes, locationCodes, locationTypes,
                skillCodes, opportunityTypes, opportunitySubTypes, marketUUID, jobTypes, minimumCompensation, maximumCompensation,
                workPlaceTypes, minimumExperienceDays, maximumExperienceDays, cityCode, stateCode, countryCode, roleTypes, pathways,
                sectors, cityCodes, organisationCodes, datePostedCode, status, page, size, facetFields, organisationTypes,
                marketPlaceVisibility, fetchBasedOnRole, restrictApplication, opportunityTimeline,
                opportunityCategories, tags,Boolean.TRUE,scheduledStartDate,scheduledEndDate,
                tenantLogin,opportunityTimelineBasedOnScheduledDate,opportunityTimelineBasedOnApplicationDate,targetGroups,
                fetchPromotedOpportunities,educationQualificationCodes,educationDegreeCodes, opportunitySubCategories, tenantLogins);

        appendCreatedByUserInFilterDTO(filterDTO,null,null,userProgramCodes,loggedInUserOpportunity);
        Pageable pageable = getPageableBasedOnFetchType(filterDTO.getSearchTerm(),filterDTO.getPage(),filterDTO.getSize(),
                filterDTO.getFetch());
        FacetPage<Opportunity> facetPage = opportunityService.getOpportunitiesForChangeMakerWithFilter(filterDTO.getSearchTerm(),filterDTO,pageable);
        return new FacetPageDTO<>(facetPage.stream()
                .map(assembler::toDTO)
                .map(dto -> {
                    dto.setOpportunityTimeline(getTimeLineValueFromApplicationEndDate(dto));
                    return dto;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new)),
                facetPage.hasNext(), FacetUtil.createFacetResult(facetPage), facetPage.getTotalElements());
    }

    private String getTimeLineValueFromApplicationEndDate(OpportunityDTO opportunityDTO) {
        Date applicationStartDate = opportunityDTO.getApplicationStartDate();
        Date applicationEndDate = opportunityDTO.getApplicationEndDate();
        Date currentDate = new Date();
        if (applicationEndDate !=null && applicationEndDate.before(currentDate)) {
            return ServiceConstant.OPPORTUNITY_TIMELINE_PAST;
        } else if (applicationStartDate !=null && applicationStartDate.after(currentDate)) {
            return ServiceConstant.OPPORTUNITY_TIMELINE_UPCOMING;
        } else if (applicationEndDate !=null && applicationStartDate !=null && applicationStartDate.before(currentDate) && applicationEndDate.after(currentDate)) {
            return ServiceConstant.OPPORTUNITY_TIMELINE_ONGOING;
        } else {
            return ServiceConstant.OPPORTUNITY_TIMELINE_UNKNOWN;
        }
    }

    public Pageable getPageableBasedOnFetchType(String searchTerm,Integer page,Integer size,String fetch) {
        String sortBy;
        String direction;
        if(fetch == null) {
            sortBy = "title";
            direction = "ASC";
        }
        else {
            switch (fetch) {
                case Fetch.TOP:
                    sortBy = "sequence";
                    direction = "DESC";
                    break;
                case Fetch.LATEST:
                    sortBy = "createdDateTime";
                    direction = "DESC";
                    break;
                case Fetch.TRENDING:
                    sortBy = "opportunityApplicantCount";
                    direction = "DESC";
                    break;
                case Fetch.CLOSES_SOON:
                    sortBy = "applicationEndDate";
                    direction = "DESC";
                    break;
                default:
                    throw new InvalidInputException("Invalid fetch type");
            }
        }
        return SearchHelper.getPageable(searchTerm,sortBy,direction,page,size);
    }

    @Override
    public PageDTO<OpportunityDTO> getOpportunitiesCreatedByUserId(Long userId, Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                                   String fetch, String marketUUID, Integer page, Integer size) {
        String marketPlaceVisibility = null;
        String status = null;
        if (!PlatformUtil.isSession() || !PlatformSecurityUtil.getCurrentUserId().equals(userId)) {
            marketPlaceVisibility = ServiceConstant.MARKET_PLACE_VISIBILITY_VISIBLE;
            status = ServiceConstant.CHANGEMAKER_OPPORTUNITY_STATUS_PUBLISHED;
        }
        OpportunityFilterDTO filterDTO = buildCommonsOpportunityFilterDTO(opportunityTypes, opportunitySubTypes, marketUUID,
                null, Boolean.TRUE, status,marketPlaceVisibility,  null,null,null,Boolean.TRUE);

        Pageable pageable = getPageableBasedOnFetchType(null,page, size, fetch);
        Page<Opportunity> opportunityPage = opportunityService.getOpportunitiesCreatedByUserId(filterDTO, pageable, userId);

        return new PageDTO<>(
                opportunityPage.stream()
                        .map(assembler::toDTO)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                opportunityPage.hasNext(),
                opportunityPage.getTotalElements()
        );
    }

    public OpportunityFilterDTO makeOpportunityFilterDTO(String searchTerm, String addressSearchTerm, String fetch, Set<String> causeCodes, Set<String> locationCodes,
                                                         Set<String> locationTypes, Set<String> skillCodes, Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                         String marketUUID, Set<String> jobTypes, Double minimumCompensation,
                                                         Double maximumCompensation, Set<String> workPlaceTypes, Long minimumExperienceDays,
                                                         Long maximumExperienceDays, String cityCode, String stateCode, String countryCode,
                                                         Set<String> roleTypes, Set<String> pathways, Set<String> sectors, Set<String> cityCodes,
                                                         Set<String> organisationCodes, String datePostedCode, String status, Integer page, Integer size,
                                                         Set<String> facetFields, Set<String> organisationTypes, String marketPlaceVisibility,
                                                          Boolean fetchBasedOnRole, Boolean restrictApplication,
                                                         String opportunityTimeline, Set<String> opportunityCategories, Set<String> tags,
                                                         Boolean isActive,  Date scheduledStartDate, Date scheduledEndDate,
                                                         String tenantLogin,Set<String> opportunityTimelineBasedOnScheduledDate,
                                                         Set<String> opportunityTimelineBasedOnApplicationDate,
                                                         Set<String> targetGroups,Boolean fetchPromotedOpportunities,
                                                         Set<String> educationQualificationCodes, Set<String> educationDegreeCodes,
                                                         Set<String> opportunitySubCategories, Set<String> tenantLogins) {
        return OpportunityFilterDTO.builder()
                .searchTerm(searchTerm)
                .tenantLogin(tenantLogin)
                .addressSearchTerm(addressSearchTerm)
                .fetch(fetch)
                .causeCodes(causeCodes)
                .locationCodes(locationCodes)
                .locationTypes(locationTypes)
                .skillCodes(skillCodes)
                .opportunityTypes(opportunityTypes)
                .opportunitySubTypes(opportunitySubTypes)
                .marketUUID(marketUUID)
                .jobTypes(jobTypes)
                .minimumCompensation(minimumCompensation)
                .maximumCompensation(maximumCompensation)
                .workPlaceTypes(workPlaceTypes)
                .minimumExperienceDays(minimumExperienceDays)
                .maximumExperienceDays(maximumExperienceDays)
                .cityCode(cityCode)
                .stateCode(stateCode)
                .countryCode(countryCode)
                .roleTypes(roleTypes)
                .pathways(pathways)
                .sectors(sectors)
                .cityCodes(cityCodes)
                .organisationCodes(organisationCodes)
                .organisationTypes(organisationTypes)
                .datePostedCode(datePostedCode)
                .status(status)
                .page(page)
                .size(size)
                .tenantLogins(tenantLogins)
                .facetFields(facetFields)
                .marketPlaceVisibility(marketPlaceVisibility)
                .fetchBasedOnRole(fetchBasedOnRole)
                .restrictApplication(restrictApplication)
                .opportunityTimeline(opportunityTimeline)
                .opportunityCategories(opportunityCategories)
                .tags(tags)
                .isActive(isActive)
                .scheduledStartDate(scheduledStartDate)
                .scheduledEndDate(scheduledEndDate)
                .opportunityTimelineBasedOnScheduledDate(opportunityTimelineBasedOnScheduledDate)
                .opportunityTimelineBasedOnApplicationDate(opportunityTimelineBasedOnApplicationDate)
                .targetGroups(targetGroups)
                .opportunitySubCategories(opportunitySubCategories)
                .fetchPromotedOpportunities(fetchPromotedOpportunities)
                .educationQualificationCodes(educationQualificationCodes)
                .educationDegreeCodes((educationDegreeCodes))
                .build();
    }

    private OpportunityFilterDTO buildCommonsOpportunityFilterDTO(Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                                  String marketUUID, Boolean fetchBasedOnRole, Boolean isRestrict,
                                                                  String status, String visibility, String opportunityTimeline,
                                                                  Set<String> opportunityTimelineBasedOnScheduledDate,
                                                                  Set<String> opportunityTimelineBasedOnApplicationDate,
                                                                  Boolean isActive){
        return OpportunityFilterDTO.builder()
                .opportunityTypes(opportunityTypes)
                .opportunitySubTypes(opportunitySubTypes)
                .marketUUID(marketUUID)
                .fetchBasedOnRole(fetchBasedOnRole)
                .opportunityTimeline(opportunityTimeline)
                .restrictApplication(isRestrict)
                .status(status)
                .marketPlaceVisibility(visibility)
                .opportunityTimelineBasedOnScheduledDate(opportunityTimelineBasedOnScheduledDate)
                .opportunityTimelineBasedOnApplicationDate(opportunityTimelineBasedOnApplicationDate)
                .isActive(isActive)
                .build();
    }

    @Override
    public Long getCreatedByUserId(Long opportunityId) {
        return opportunityService.getCreatedByUserId(opportunityId);
    }


}
