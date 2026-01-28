package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.changemaker.dto.TenantDataSyncDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityService;
import com.platformcommons.platform.service.search.application.utility.*;
import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.domain.repo.OpportunityRepository;
import com.platformcommons.platform.service.search.dto.OpportunityFilterDTO;
import com.platformcommons.platform.service.search.dto.OpportunityStakeHolderCustomDTO;
import com.platformcommons.platform.service.search.facade.cache.validator.TenantMetaConfigValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.platformcommons.platform.service.search.application.constant.ServiceConstant.*;

@Service
@Transactional
@Slf4j
public class OpportunityServiceImpl implements OpportunityService {

    @Autowired
    private OpportunityRepository repository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TenantMetaConfigValidator tenantMetaConfigValidator;

    @Override
    public Opportunity getById(Long id){
        return repository.findById(id).orElseThrow(()->
                new NotFoundException(String.format("Opportunity with  %d  not found",id)));
    }

    @Override
    public Opportunity save(Opportunity opportunity) {
        opportunity.init();
        return repository.save(opportunity);
    }

    @Override
    public void delete(Long id) {
        Optional<Opportunity> opportunityOptional = repository.findById(id);
        if(opportunityOptional.isPresent()) {
            Opportunity opportunity = opportunityOptional.get();
            opportunity.setIsActive(Boolean.FALSE);
            repository.save(opportunity);
        }
    }

    @Override
    public void putUpdate(Opportunity opportunity) {
        repository.save(opportunity);
    }


    @Override
    public void addOpportunityStakeHolderToOpportunities(OpportunityStakeHolderCustomDTO stakeHolderCustomDTO) {
        stakeHolderCustomDTO.getOpportunityIds().forEach(opportunityId -> {
            Optional<Opportunity> optional = repository.findById(opportunityId);
            if (optional.isPresent()) {
                Opportunity opportunity = optional.get();
                if(opportunity.getStakeHolderUserIds()!= null) {
                    opportunity.getStakeHolderUserIds().add(stakeHolderCustomDTO.getUserId());
                } else {
                    Set<Long> stakeholderUserIDs = new HashSet<>();
                    stakeholderUserIDs.add(stakeHolderCustomDTO.getUserId());
                    opportunity.setStakeHolderUserIds(stakeholderUserIDs);
                }
                save(opportunity);
            }
        });
    }

    @Override
    public void updateOpportunityTenantDetails(TenantDataSyncDTO dto) {
        List<Opportunity> updatedOpportunities = repository.findByTenantAndTenantLogin(dto.getId(), dto.getTenantLogin())
                .stream()
                .peek(opportunity -> {
                    opportunity.setTenantName(dto.getTenantName());
                    opportunity.setTenantLink(dto.getWebsite());
                    opportunity.setTenantIconPic(dto.getIconpic());
                    opportunity.setTenantDescription(dto.getDescription());
                    opportunity.setTenantSlug(dto.getSlug());
                })
                .collect(Collectors.toList());

        repository.saveAll(updatedOpportunities);
    }


    @Override
    public void deleteOpportunityStakeHolderFromOpportunity(OpportunityStakeHolderCustomDTO stakeHolderCustomDTO) {
        stakeHolderCustomDTO.getOpportunityIds().forEach(opportunityId -> {
            Optional<Opportunity> optional = repository.findById(opportunityId);
            if (optional.isPresent()) {
                Opportunity opportunity = optional.get();
                if(opportunity.getStakeHolderUserIds()!= null) {
                    opportunity.getStakeHolderUserIds().remove(stakeHolderCustomDTO.getUserId());
                }
                save(opportunity);
            }
        });
    }

    @Override
    public Page<Opportunity> getOpportunitiesByPaginationForChangemaker(OpportunityFilterDTO filterDTO, Pageable pageable) {
        Query query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        query.setPageRequest(pageable);
        FilterQuery filterQuery = getFilterQueryBasedOnCommonOpportunityParameters(filterDTO,query);
        addCriteriaForChangeMakerPurpose(filterDTO, filterQuery, query);
        query.addFilterQuery(filterQuery);
        return solrTemplate.query(ServiceConstant.OPPORTUNITY_CORE, query, Opportunity.class);
    }

    @Override
    public Set<Long> getPromotedOpportunityIds(OpportunityFilterDTO filterDTO, Pageable pageable) {
        Query query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        query.addProjectionOnField(Field.of("id"));
        query.setPageRequest(pageable);
        updateQueryForGettingPromotedOpportunities(query,filterDTO);
        SolrResultPage<Opportunity> opportunityPage = solrTemplate.query(ServiceConstant.OPPORTUNITY_CORE, query, Opportunity.class);
        return opportunityPage.stream()
                .map(Opportunity::getId)
                .collect(Collectors.toSet());
    }

    @Deprecated
    public void updateQueryForGettingPromotedOpportunities(Query query,OpportunityFilterDTO filterDTO) {
        FilterQuery filterQuery = getFilterQueryBasedOnCommonOpportunityParameters(filterDTO,query);
        Criteria recommendedCriteria = Criteria.where("isRecommended").is(Boolean.TRUE);
        filterQuery.addCriteria(recommendedCriteria);
        addCriteriaForChangeMakerPurpose(filterDTO, filterQuery, query);
        query.addFilterQuery(filterQuery);

    }

    @Override
    public FacetPage<Opportunity> getOpportunitiesForChangeMakerWithFilter(String searchTerm, OpportunityFilterDTO filterDTO, Pageable pageable) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForOpportunitySearch(filterDTO.getSearchTerm(),filterDTO.getAddressSearchTerm())))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(filterDTO.getFacetFields())
                        .setFacetMinCount(1)
                        .setFacetLimit(-1)
                        .setFacetSort(FacetOptions.FacetSort.INDEX));

        facetQuery.setPageRequest(pageable);
        FilterQuery filterQuery = getFilterQueryBasedOnCommonOpportunityParameters(filterDTO,facetQuery);
        addCriteriaForChangeMakerPurpose(filterDTO, filterQuery, facetQuery);
        facetQuery.addFilterQuery(filterQuery);
        return solrTemplate.queryForFacetPage(ServiceConstant.OPPORTUNITY_CORE,facetQuery,Opportunity.class);
    }

    public void addCriteriaForChangeMakerPurpose(OpportunityFilterDTO filterDTO, FilterQuery filterQuery, Query query) {

//        Criteria visibilityCriteria = new Criteria().expression(" ( visibilityDateTime:[* TO NOW] OR (-visibilityDateTime:[* TO *] )) ");
//        query.addCriteria(visibilityCriteria);

        if(filterDTO.getRestrictApplication() != null ) {
            Criteria criteria = Criteria.where("restrictApplication").is(filterDTO.getRestrictApplication()).not();
            filterQuery.addCriteria(criteria);
        }
        if(Objects.equals(filterDTO.getFetchBasedOnRole(),Boolean.TRUE)) {
            Criteria visibleToRolesCriteria = getCriteriaForVisibleToRolesFilter();
            if(visibleToRolesCriteria != null) {
                query.addCriteria(visibleToRolesCriteria);
            }
        }
    }


    @Override
    public FacetPage<Opportunity> getOpportunitiesByFilterForAdmin(String searchTerm, OpportunityFilterDTO filterDTO, Pageable pageable) {

        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForOpportunitySearch(filterDTO.getSearchTerm(), null)))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(filterDTO.getFacetFields())
                        .setFacetMinCount(1)
                        .setFacetLimit(-1)
                        .setFacetSort(FacetOptions.FacetSort.INDEX));
        facetQuery.setPageRequest(pageable);

        FilterQuery filterQuery = getFilterQueryBasedOnCommonOpportunityParameters(filterDTO,facetQuery);
        facetQuery.addFilterQuery(filterQuery);

        return solrTemplate.queryForFacetPage(ServiceConstant.OPPORTUNITY_CORE,facetQuery,Opportunity.class);
    }

    public FilterQuery getFilterQueryBasedOnCommonOpportunityParameters(OpportunityFilterDTO filterDTO, Query query) {
        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria marketUUIDCriteria = Criteria.where("marketUuid").is(filterDTO.getMarketUUID());
        filterQuery.addCriteria(marketUUIDCriteria);

        Boolean isActive = filterDTO.getIsActive() != null ? filterDTO.getIsActive() : Boolean.TRUE;
        Criteria isActiveCriteria = Criteria.where("isActive").is(isActive);
        filterQuery.addCriteria(isActiveCriteria);

        if(filterDTO.getStatus() != null) {
            Criteria statusCriteria = Criteria.where("status").is(filterDTO.getStatus());
            filterQuery.addCriteria(statusCriteria);
        }
        if(filterDTO.getTenantLogin() != null) {
            Criteria tenantLoginCriteria = Criteria.where("tenantLogin").is(filterDTO.getTenantLogin());
            filterQuery.addCriteria(tenantLoginCriteria);
        }
        if(filterDTO.getMarketPlaceVisibility() != null) {
            Criteria criteria = Criteria.where("marketPlaceVisibility").is(filterDTO.getMarketPlaceVisibility());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCauseCodes()!=null && !filterDTO.getCauseCodes().isEmpty()) {
            Criteria criteria = Criteria.where("opportunityCauseList").in(filterDTO.getCauseCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getSkillCodes()!=null && !filterDTO.getSkillCodes().isEmpty()) {
            Criteria criteria = Criteria.where("opportunitySkillList").in(filterDTO.getSkillCodes());
            filterQuery.addCriteria(criteria);
        }
        if (filterDTO.getTenantLogins() != null && !filterDTO.getTenantLogins().isEmpty()) {
            Criteria criteria = Criteria.where("tenantLogin").in(filterDTO.getTenantLogins());
            filterQuery.addCriteria(criteria);
        }
        if (filterDTO.getLocationTypes() != null && !filterDTO.getLocationTypes().isEmpty()) {
            Criteria criteria = Criteria.where("locationType").in(filterDTO.getLocationTypes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getOpportunityTypes()!=null && !filterDTO.getOpportunityTypes().isEmpty()) {
            Criteria criteria = Criteria.where("type").in(filterDTO.getOpportunityTypes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getOpportunitySubTypes()!=null && !filterDTO.getOpportunitySubTypes().isEmpty()) {
            Criteria criteria = Criteria.where("subType").in(filterDTO.getOpportunitySubTypes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getOpportunityCategories()!=null && !filterDTO.getOpportunityCategories().isEmpty()) {
            Criteria criteria = Criteria.where("opportunityCategoryCodes").in(filterDTO.getOpportunityCategories());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getOpportunitySubCategories()!=null && !filterDTO.getOpportunitySubCategories().isEmpty()) {
            Criteria criteria = Criteria.where("opportunitySubCategoryCodes").in(filterDTO.getOpportunitySubCategories());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getJobTypes()!=null && !filterDTO.getJobTypes().isEmpty()) {
            Criteria criteria = Criteria.where("jobType").in(filterDTO.getJobTypes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMinimumCompensation()!=null ) {
            Criteria criteria = Criteria.where("minimumCompensation").greaterThanEqual(filterDTO.getMinimumCompensation());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMaximumCompensation()!=null ) {
            Criteria criteria = Criteria.where("maximumCompensation").lessThanEqual(filterDTO.getMaximumCompensation());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getWorkPlaceTypes()!=null && !filterDTO.getWorkPlaceTypes().isEmpty()) {
            Criteria criteria = Criteria.where("workPlaceType").in(filterDTO.getWorkPlaceTypes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMinimumExperienceDays()!=null ) {
            Criteria criteria = Criteria.where("minimumExperienceDays").greaterThanEqual(filterDTO.getMinimumExperienceDays());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMaximumExperienceDays()!=null ) {
            Criteria criteria = Criteria.where("maximumExperienceDays").lessThanEqual(filterDTO.getMaximumExperienceDays());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCityCode()!=null ) {
            Criteria criteria = Criteria.where("cityCodes").is(filterDTO.getCityCode());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getStateCode()!=null ) {
            Criteria criteria = Criteria.where("stateCodes").is(filterDTO.getStateCode());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCountryCode()!=null ) {
            Criteria criteria = Criteria.where("countryCodes").is(filterDTO.getCountryCode());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getRoleTypes()!=null && !filterDTO.getRoleTypes().isEmpty()) {
            Criteria criteria = Criteria.where("opportunityRoleTypeCodes").in(filterDTO.getRoleTypes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getPathways()!=null && !filterDTO.getPathways().isEmpty()) {
            Criteria criteria = Criteria.where("opportunityPathwayCodes").in(filterDTO.getPathways());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getSectors()!=null && !filterDTO.getSectors().isEmpty()) {
            Criteria criteria = Criteria.where("opportunitySectorCodes").in(filterDTO.getSectors());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCityCodes()!=null && !filterDTO.getCityCodes().isEmpty()) {
            Criteria criteria = Criteria.where("cityCodes").in(filterDTO.getCityCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getOrganisationCodes()!=null && !filterDTO.getOrganisationCodes().isEmpty()) {
            Criteria criteria = Criteria.where("organisationCode").in(filterDTO.getOrganisationCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getOrganisationTypes()!=null && !filterDTO.getOrganisationTypes().isEmpty()) {
            Criteria criteria = Criteria.where("organisationType").in(filterDTO.getOrganisationTypes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getDatePostedCode() != null) {
            setCriteriaForDatePostedCode(filterDTO.getDatePostedCode(),filterQuery);
        }
        if(filterDTO.getExcludeOpportunityIds() != null && !filterDTO.getExcludeOpportunityIds().isEmpty()) {
            Criteria criteria = Criteria.where("id").in(filterDTO.getExcludeOpportunityIds()).not();
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getTags()!=null && !filterDTO.getTags().isEmpty()) {
            Criteria criteria = Criteria.where("tags").in(filterDTO.getTags());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCreatedByUserIds() != null ){
            Criteria criteria = null;
            if (filterDTO.getCreatedByUserIds().isEmpty()) {
                criteria = new SimpleStringCriteria(null);
            } else {
                criteria = Criteria.where("createdByUser").in(filterDTO.getCreatedByUserIds())
                        .or("stakeHolderUserIds").in(filterDTO.getCreatedByUserIds());
            }
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getScheduledStartDate() != null && filterDTO.getScheduledEndDate() != null ) {
            Criteria criteria = getCriteriaForScheduledDate(filterDTO.getScheduledStartDate(),filterDTO.getScheduledEndDate());
            query.addCriteria(criteria);
        }
        if(filterDTO.getOpportunityTimelineBasedOnScheduledDate() != null && !filterDTO.getOpportunityTimelineBasedOnScheduledDate().isEmpty()) {
            Criteria criteria = getCriteriaForOpportunityTimeLineWithGivenDateField(filterDTO.getOpportunityTimelineBasedOnScheduledDate(),
                    "scheduledStartDate","scheduledEndDate");
            query.addCriteria(criteria);
        }
        if(filterDTO.getOpportunityTimelineBasedOnApplicationDate() != null && !filterDTO.getOpportunityTimelineBasedOnApplicationDate().isEmpty()) {
            Criteria criteria = getCriteriaForOpportunityTimeLineWithGivenDateField(filterDTO.getOpportunityTimelineBasedOnApplicationDate(),
                    "applicationStartDate","applicationEndDate");
            query.addCriteria(criteria);
        }
        if(filterDTO.getOpportunityTimeline() != null) {
            Criteria criteria = getCriteriaForOpportunityTimeLine(filterDTO.getOpportunityTimeline());
            query.addCriteria(criteria);
        }
        if(filterDTO.getTargetGroups()!=null && !filterDTO.getTargetGroups().isEmpty()) {
            Criteria criteria = Criteria.where("targetGroups").in(filterDTO.getTargetGroups());
            filterQuery.addCriteria(criteria);
        }
        if(Objects.equals(filterDTO.getFetchPromotedOpportunities(),Boolean.TRUE)) {
            Criteria criteria = Criteria.where("isRecommended").is(Boolean.TRUE);
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getEducationQualificationCodes()!=null && !filterDTO.getEducationQualificationCodes().isEmpty()) {
            Criteria criteria = Criteria.where("educationQualificationCodes").in(filterDTO.getEducationQualificationCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getEducationDegreeCodes()!=null && !filterDTO.getEducationDegreeCodes().isEmpty()) {
            Criteria criteria = Criteria.where("educationDegreeCodes").in(filterDTO.getEducationDegreeCodes());
            filterQuery.addCriteria(criteria);
        }
        return filterQuery;
    }

    public Criteria getCriteriaForOpportunityTimeLineWithGivenDateField(Set<String> timeLineSet,String startDateField, String endDateField) {
        String timeLineQuery;
        List<String> timeLineQueryArray = new ArrayList<>();
        for(String timeLine : timeLineSet) {
            switch (timeLine) {
                case OPPORTUNITY_TIMELINE_UPCOMING:
                    timeLineQueryArray.add(String.format(" ( %s:[NOW TO *] ) ",startDateField));
                    break;
                case OPPORTUNITY_TIMELINE_ONGOING:
                    // Both Not Null and in range
                    String case1 = String.format(" ( %s:[* TO NOW] AND %s:[NOW TO *] ) ",startDateField,endDateField);
                    // Both null
                    String case2 = String.format(" ( -%s:[* TO *] AND -%s:[* TO *] ) ",startDateField,endDateField);
                    // end null in range
                    String case3 = String.format(" ( %s:[* TO NOW] AND -%s:[* TO *] )",startDateField,endDateField);
                    // start null and end in range
                    String case4 = String.format(" ( -%s:[* TO *] AND %s:[NOW TO *] )",startDateField,endDateField);

                    timeLineQueryArray.add(String.format(" ( %s OR %s OR %s OR %s) ", case1, case2, case3, case4));
                    break;
                case OPPORTUNITY_TIMELINE_PAST:
                    timeLineQueryArray.add(String.format(" ( %s:[* TO NOW] ) ",endDateField));
                    break;
                default:
                    throw new InvalidInputException("OpportunityTimeLine code did not match with standard values");
            }
        }
        timeLineQuery = StringUtils.join(timeLineQueryArray," OR ");
        timeLineQuery = String.format("( %s )",timeLineQuery);
        return new Criteria().expression(timeLineQuery);
    }

    public Criteria getCriteriaForOpportunityTimeLine(String opportunityTimeline) {
        Criteria criteria = null;
        if (opportunityTimeline.equals(OPPORTUNITY_TIMELINE_UPCOMING) || opportunityTimeline.equals(OPPORTUNITY_TIMELINE_ONGOING)) {
            String case1 = String.format(" ( %s:[* TO NOW] AND %s:[NOW TO *] ) ","applicationStartDate","applicationEndDate");
            String case2 = String.format(" ( -%s:[* TO *] AND -%s:[* TO *] ) ","applicationStartDate","applicationEndDate");
            String case3 = String.format(" ( %s:[* TO NOW] AND -%s:[* TO *] )","applicationStartDate","applicationEndDate");
            String case4 = String.format(" ( -%s:[* TO *] AND %s:[NOW TO *] )","applicationStartDate","applicationEndDate");
            String case5 = String.format(" ( %s:[NOW TO *] ) ","applicationStartDate");
            String query = String.format(" ( %s OR %s OR %s OR %s OR %s ) ", case1, case2, case3,case4,case5);
            criteria = new Criteria().expression(query);
        }
        else if (opportunityTimeline.equals(OPPORTUNITY_TIMELINE_PAST)) {
            criteria = Criteria.where("applicationEndDate").lessThanEqual(new Date());
        }
        else {
            throw new InvalidInputException("OpportunityTimeLine code did not match with standard values");
        }
        return criteria;
    }


    public Criteria getCriteriaForScheduledDate(Date scheduledStartDate, Date scheduledEndDate) {
        String startDate = DateTimeUtil.convertDateToStringInGivenZoneAndPattern(scheduledStartDate,
                "yyyy-MM-dd'T'HH:mm:ss'Z'", "UTC");
        String endDate = DateTimeUtil.convertDateToStringInGivenZoneAndPattern(scheduledEndDate,
                "yyyy-MM-dd'T'HH:mm:ss'Z'", "UTC");
        // Both Not Null and in range
        String case1 = String.format(" ( scheduledStartDate:[%s TO *] AND scheduledEndDate:[* TO %s] )  ",
                startDate, endDate);
        // Both null
        String case2 = " ( (-scheduledStartDate:[* TO *]) AND (-scheduledEndDate:[* TO *]) ) ";
        // end null in range
        String case3 = String.format(" ( scheduledStartDate:[* TO *] AND (-scheduledEndDate:[* TO *]) AND scheduledStartDate:[%s TO *] )", startDate);

        String rangeQuery = String.format(" ( %s OR %s OR %s ) ", case1, case2, case3);
        return new Criteria().expression(rangeQuery);
    }

    public Criteria getCriteriaForVisibleToRolesFilter() {
        Criteria criteria = null;
        if (!PlatformSecurityUtil.isPlatformAppToken()) {
            String tenantLogin = PlatformSecurityUtil.getCurrentTenantLogin();
            Long currentUserId = PlatformSecurityUtil.getCurrentUserId();
            Set<String> currentUserRoles = PlatformSecurityUtil.getCurrentUserContext().getAuthorities();
            TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigValidator.getByTenantLogin(tenantLogin);
            if (tenantMetaConfigDTO != null) {
                String visibleToRolesFilterEnabled = tenantMetaConfigValidator.getMetaPropertyValueFromTenantMetaConfigDTO(
                        tenantMetaConfigDTO, TenantConfigConstant.CONFIG_KEY_VISIBLE_TO_ROLE_FILTER_FOR_OPPORTUNITY_FETCH,null);
                if(Objects.equals(visibleToRolesFilterEnabled,"1")) {
                    String showAllOpportunitiesForVisibleToRolesNull = tenantMetaConfigValidator.getMetaPropertyValueFromTenantMetaConfigDTO(
                            tenantMetaConfigDTO, TenantConfigConstant.CONFIG_KEY_SHOW_ALL_OPPORTUNITIES_TO_USERS_FOR_VISIBLE_TO_ROLE_IS_NULL,null);
                    Criteria visibleToRoleCriteria = null;
                    if (Objects.equals(showAllOpportunitiesForVisibleToRolesNull,"1")) {
                        String currentRolesCriteriaString = currentUserRoles.stream()
                                .map(role -> "visibleToRoles:" + role)
                                .collect(Collectors.joining(" OR "));
                        String nullFieldCriteriaString = "-visibleToRoles:[* TO *]";
                        visibleToRoleCriteria = new SimpleStringCriteria("( " + currentRolesCriteriaString + " OR ( " + nullFieldCriteriaString + " ) )");
                    }
                    else {
                        visibleToRoleCriteria = Criteria.where("visibleToRoles").in(currentUserRoles);
                    }

                    Criteria currentLoggedInUserCriteria = Criteria.where("createdByUser").is(currentUserId);
                    criteria = visibleToRoleCriteria.or(currentLoggedInUserCriteria);
                }
            }
        }
        return criteria;
    }

    public void setCriteriaForDatePostedCode(String datePostedCode, FilterQuery filterQuery) {
        LocalDateTime localDateTime = null;
        switch(datePostedCode) {
            case ServiceConstant.DATE_POSTED_PAST_MONTH :
                localDateTime = LocalDateTime.now().minusMonths(1);
                break;
            case ServiceConstant.DATE_POSTED_PAST_WEEK:
                localDateTime = LocalDateTime.now().minusWeeks(1);
                break;
            case ServiceConstant.DATE_POSTED_PAST_24_HOURS:
                localDateTime = LocalDateTime.now().minusHours(24);
                break;
            case ServiceConstant.DATE_POSTED_ANY_TIME:
                break;
            default:
                throw new InvalidInputException("Invalid Date Posted Code");
        }

        if(localDateTime != null) {
            LocalDateTime utcLocalDateTime = convertLocalTimeToUTCLocalDateTime(localDateTime);
            Date utcDate = convertLocalDateTimeToDate(utcLocalDateTime);
            Criteria criteria = Criteria.where("createdDateTime").greaterThanEqual(utcDate);
            filterQuery.addCriteria(criteria);
        }
    }

    public LocalDateTime convertLocalTimeToUTCLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return java.util.Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
    }


    private String buildQueryForOpportunitySearch(String searchTerm, String addressSearchTerm){
        String queryString = "*:*";
        StringBuilder queryBuilder = new StringBuilder();
        if(searchTerm != null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            List<String> searchTermsArray = Arrays.asList(searchTerm.split("\\s+"));
            queryBuilder.append("( ")
                    .append(SearchHelper.buildQueryForIndividualField("title", searchTermsArray))
                    .append(" OR ")
                    .append(SearchHelper.buildQueryForIndividualField("opportunitySectorNames", searchTermsArray))
                    .append(" OR ")
                    .append(SearchHelper.buildQueryForIndividualField("opportunityPathwayNames", searchTermsArray))
                    .append(" OR ")
                    .append(SearchHelper.buildQueryForIndividualField("opportunityRoleTypeNames", searchTermsArray))
                    .append(" OR ")
                    .append(SearchHelper.buildQueryForIndividualField("workPlaceName", searchTermsArray))
                    .append(" OR ")
                    .append(SearchHelper.buildQueryForIndividualField("jobTypeName", searchTermsArray))
                    .append(" OR ")
                    .append(SearchHelper.buildQueryForIndividualField("organisationName", searchTermsArray))
                    .append(")");

        }
        if(addressSearchTerm != null){
            addressSearchTerm = SearchTermParser.parseSearchTerm(addressSearchTerm);
            List<String> searchTermsArray = Arrays.asList(addressSearchTerm.split("\\s+"));
            if(queryBuilder.length() != 0) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append("( ")
                    .append(SearchHelper.buildQueryForIndividualField("addressesText",searchTermsArray))
                    .append(" )");
        }
        if(queryBuilder.length() != 0){
            queryString = queryBuilder.toString();
        }
        return queryString;
    }

    @Override
    public Page<Opportunity> getOpportunitiesCreatedByUserId(OpportunityFilterDTO filterDTO, Pageable pageable, Long userId) {
        Query query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        query.setPageRequest(pageable);

        FilterQuery filterQuery = getFilterQueryBasedOnCommonOpportunityParameters(filterDTO,query);
        Criteria createdByCriteria = Criteria.where("createdByUser").is(userId);
        filterQuery.addCriteria(createdByCriteria);
        addCriteriaForChangeMakerPurpose(filterDTO, filterQuery, query);
        query.addFilterQuery(filterQuery);
        return solrTemplate.query(ServiceConstant.OPPORTUNITY_CORE, query, Opportunity.class);
    }

    @Override
    public Long getCreatedByUserId(Long opportunityId) {
        Query query = new SimpleQuery(Criteria.where("id").is(opportunityId));
        query.addProjectionOnField(Field.of("createdByUser"));
        SolrResultPage<Opportunity> opportunityPage = solrTemplate.query(ServiceConstant.OPPORTUNITY_CORE, query, Opportunity.class);
        return opportunityPage.stream()
                .findFirst()
                .map(Opportunity::getCreatedByUser)
                .orElse(null);
    }



}