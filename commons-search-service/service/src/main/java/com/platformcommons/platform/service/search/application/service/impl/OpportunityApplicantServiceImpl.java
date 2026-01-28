package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityApplicantService;
import com.platformcommons.platform.service.search.application.service.OpportunityService;
import com.platformcommons.platform.service.search.application.utility.DateTimeUtil;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.PlatformUtil;
import com.platformcommons.platform.service.search.application.utility.SearchHelper;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.domain.repo.OpportunityApplicantElasticRepository;
import com.platformcommons.platform.service.search.dto.AssessmentFilterDTO;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantFilterDTO;
import com.platformcommons.platform.service.search.dto.QuestionFilterDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.composite.CompositeAggregationBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class OpportunityApplicantServiceImpl implements OpportunityApplicantService {

    @Autowired
    private OpportunityApplicantElasticRepository elasticRepository;

    @Autowired(required = false)
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private OpportunityService opportunityService;

    @Override
    public Optional<OpportunityApplicant> getOptionalById(Long id) {
        return elasticRepository.findById(id);
    }

    @Override
    public void save(OpportunityApplicant opportunityApplicant) {
        elasticRepository.save(opportunityApplicant);
    }

    @Override
    public void saveAll(Set<OpportunityApplicant> opportunityApplicantSet) {
        elasticRepository.saveAll(opportunityApplicantSet);
    }

    @Override
    public void delete(Long id) {
        Optional<OpportunityApplicant> optionalOpportunityApplicant = elasticRepository.findById(id);
        if(optionalOpportunityApplicant.isPresent()) {
            OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
            opportunityApplicant.setIsActive(Boolean.FALSE);
            elasticRepository.save(opportunityApplicant);
        }
    }

    @Override
    public Set<OpportunityApplicant> getAllByMarketUserId(Long marketUserId) {
        return elasticRepository.findByMarketUserId(marketUserId);
    }


    @Override
    public SearchHits<OpportunityApplicant> getApplicantsBySearch(OpportunityApplicantFilterDTO filterDTO) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"opportunityId",filterDTO.getOpportunityId());
        createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"isActive",filterDTO.getIsActive());

        createQueryForApplicationStartAndEndDate(filterDTO.getApplicationStatus(), filterDTO.getExcludeApplicationStatus(),
                filterDTO.getStartDateTime(), filterDTO.getEndDateTime(), boolQueryBuilder);
        createQueryForInterviewDateTime(filterDTO.getStartInterviewDateTime(),filterDTO.getEndInterviewDateTime(), boolQueryBuilder);
        createQueryRelatedToLoggedInUserPermission(boolQueryBuilder,filterDTO);
        createQueryForAttendanceStatus(boolQueryBuilder,filterDTO.getAttendanceStatusSet());

        if(filterDTO.getIncludeApplicantIds() != null && !filterDTO.getIncludeApplicantIds().isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"id",filterDTO.getIncludeApplicantIds());
        }
        if(filterDTO.getSearchTerm() != null) {
            createQueryForSearchTextMatch(boolQueryBuilder,filterDTO.getSearchTerm());
        }
        if(filterDTO.getApplicationStatus() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"applicationStatus",filterDTO.getApplicationStatus());
        }
        if(filterDTO.getStepCodeSet() != null && !filterDTO.getStepCodeSet().isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentStepCode",filterDTO.getStepCodeSet());
        }
        if(filterDTO.getStepStatusCodeSet() != null && !filterDTO.getStepStatusCodeSet().isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentStepStatus",filterDTO.getStepStatusCodeSet());
        }
        if(filterDTO.getInterviewDateTime() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"interviewDateTime",filterDTO.getInterviewDateTime());
        }
        if(filterDTO.getExcludeApplicationStatus() != null) {
            createTermQueryAndMustNotInBoolQuery(boolQueryBuilder,"applicationStatus",filterDTO.getExcludeApplicationStatus());
        }
        if(filterDTO.getCandidateType() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"candidateType",filterDTO.getCandidateType());
        }
        if(filterDTO.getIsVerified() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"isVerified",filterDTO.getIsVerified());
        }
        if(filterDTO.getForRole() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"forRole",filterDTO.getForRole());
        }
        if (filterDTO.getCohorts() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"cohort",filterDTO.getCohorts());
        }
        if (filterDTO.getPlacementCities() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"placementCity",filterDTO.getPlacementCities());
        }
        if (filterDTO.getRoleCodes() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"roleCodes",filterDTO.getRoleCodes());
        }
        if (filterDTO.getPathwayCodes() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"pathwayCodes",filterDTO.getPathwayCodes());
        }
        if (filterDTO.getAreaOfResidenceSet() != null && !filterDTO.getAreaOfResidenceSet().isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"areaOfResidence",filterDTO.getAreaOfResidenceSet());
        }
        if (filterDTO.getApplicantCampaignCodes() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"applicantCampaign",filterDTO.getApplicantCampaignCodes());
        }
        if (filterDTO.getApplicantReferrerIds() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"applicantReferrer",filterDTO.getApplicantReferrerIds());
        }
        if (filterDTO.getApplicantSourceSet() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"applicantSource",filterDTO.getApplicantSourceSet());
        }
        if (filterDTO.getApplicantMediumSet() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"applicantMedium",filterDTO.getApplicantMediumSet());
        }
        if(filterDTO.getRetentionStatus() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"retentionStatus",filterDTO.getRetentionStatus());
        }
        if(filterDTO.getAssessmentSubStepId() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"assessmentSubStepId",filterDTO.getAssessmentSubStepId());
        }
        if(filterDTO.getRsvpStatusSet() != null && !filterDTO.getRsvpStatusSet().isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"rsvpStatus",filterDTO.getRsvpStatusSet());
        }
        if(filterDTO.getCurrentCountry() != null ) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentCountryCode",filterDTO.getCurrentCountry());
        }
        if(filterDTO.getCurrentState() != null ) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentStateCode",filterDTO.getCurrentState());
        }
        if(filterDTO.getCurrentCity() != null ) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentCityCode",filterDTO.getCurrentCity());
        }
        if (filterDTO.getCurrentCitySet() != null && !filterDTO.getCurrentCitySet().isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentCityCode",filterDTO.getCurrentCitySet());
        }
        if (filterDTO.getMinimumTotalYearOfExperience() != null  && filterDTO.getMaximumTotalYearOfExperience() != null) {
            createQueryForTotalWorkExperience(boolQueryBuilder,filterDTO.getMinimumTotalYearOfExperience(),
                    filterDTO.getMaximumTotalYearOfExperience());
        }
        if (filterDTO.getEducationQualificationCodes() != null ) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"educationQualificationCodes",filterDTO.getEducationQualificationCodes());
        }
        if (filterDTO.getEducationDegreeCodes() != null ) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"educationDegreeCodes",filterDTO.getEducationDegreeCodes());
        }
        if (filterDTO.getRelocationPreferenceSet() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"relocationPreferenceSet",filterDTO.getRelocationPreferenceSet());
        }
        if (filterDTO.getSelfDeclarationCodes() != null) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"selfDeclarationCodes",filterDTO.getSelfDeclarationCodes());
        }
        if (filterDTO.getFailedRuleCodes() != null) {
            createNestedQueryForFailedRuleCodes(boolQueryBuilder, filterDTO.getFailedRuleCodes());
        }
        if (filterDTO.getOwnerResponseResultCode() != null ) {
            createNestedQueryForOwnerResponse(boolQueryBuilder, filterDTO.getOwnerResponseStepCode(),
                    filterDTO.getOwnerResponseResultCode(), filterDTO.getOpportunityId());
        }
        if(filterDTO.getUserIds() != null) {
            if(filterDTO.getUserIds().isEmpty()) {
                boolQueryBuilder.mustNot(QueryBuilders.matchAllQuery());
            }
            else {
                createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"marketUserId",filterDTO.getUserIds());
            }
        }
        if(filterDTO.getCurrentOwnerEntityId() != null ) {
            if(filterDTO.getCurrentOwnerEntityId().equals(-1L)) {
                QueryBuilder queryBuilder = QueryBuilders.existsQuery("currentOwnerEntityId");
                boolQueryBuilder.mustNot(queryBuilder);
            }
            else {
                createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentOwnerEntityId",filterDTO.getCurrentOwnerEntityId());
            }
        }
        if (filterDTO.getApplicantOnboardingStatus() != null) {
            if (filterDTO.getApplicantOnboardingStatus().equals("-1")) {
                QueryBuilder queryBuilder = QueryBuilders.existsQuery("applicantOnboardingStatus");
                boolQueryBuilder.mustNot(queryBuilder);
            }
            else {
                createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"applicantOnboardingStatus",filterDTO.getApplicantOnboardingStatus());
            }
        }
        if(filterDTO.getSupervisorId() != null ) {
            if(filterDTO.getSupervisorId().equals(-1L)) {
                QueryBuilder queryBuilder = QueryBuilders.existsQuery("currentSupervisorLinkedUserId");
                boolQueryBuilder.mustNot(queryBuilder);
            }
            else {
                createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"currentSupervisorLinkedUserId",filterDTO.getSupervisorId());
            }
        }
        if(filterDTO.getEntityIdSet() != null ) {
            if(filterDTO.getEntityIdSet().equals(-1L)) {
                QueryBuilder queryBuilder = QueryBuilders.existsQuery("forEntityId");
                boolQueryBuilder.mustNot(queryBuilder);
            }
            else {
                createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"forEntityId",filterDTO.getEntityIdSet());
            }
        }

        createQueryForOpportunityApplicantTasks(boolQueryBuilder, filterDTO.getOpportunityTaskIds(), filterDTO.getOpportunityApplicantTaskStatusSet(), filterDTO.getSelectionStepIds());
        if (filterDTO.getAssessmentFilter() != null) {
            applyAssessmentFilter(boolQueryBuilder, filterDTO.getAssessmentFilter());
        }


        Pageable pageable = SearchHelper.getPageable(filterDTO.getSearchTerm(),filterDTO.getSortFieldName(),filterDTO.getSortFieldOrder(),
                filterDTO.getPage(),filterDTO.getSize());

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .withTrackTotalHits(true)
                .build();
        addAggregationOnFacetFields(nativeSearchQuery,filterDTO.getFacetFields());
        return elasticsearchRestTemplate.search(nativeSearchQuery, OpportunityApplicant.class, IndexCoordinates.of("opportunity_applicant"));
    }


    @Override
    public SearchHits<OpportunityApplicant> getUserIdsOfApplicantsByFilter(Set<Long> opportunityIds, Set<String> stepCodeSet,
                                                                           Set<String> stepStatusCodeSet, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"isActive",Boolean.TRUE);
        createTermQueryAndFilterInBoolQuery(boolQueryBuilder,"opportunityId",opportunityIds);
        if (stepCodeSet != null && !stepCodeSet.isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder, "currentStepCode", stepCodeSet);
        }
        if (stepStatusCodeSet != null && !stepStatusCodeSet.isEmpty()) {
            createTermQueryAndFilterInBoolQuery(boolQueryBuilder, "currentStepStatus", stepStatusCodeSet);
        }
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .withFields("marketUserId")
                .build();
        return elasticsearchRestTemplate.search(nativeSearchQuery, OpportunityApplicant.class, IndexCoordinates.of("opportunity_applicant"));
    }


    public void addAggregationOnFacetFields(NativeSearchQuery nativeSearchQuery, Set<String> facetFields) {
        if(facetFields != null && !facetFields.isEmpty()) {
            for(String facetField :  facetFields) {
                nativeSearchQuery
                        .addAggregation(AggregationBuilders.terms(facetField)
                                .field(facetField)
                                .size(Integer.MAX_VALUE)
                                .minDocCount(1)
                                .missing("N/A")
                                .order(BucketOrder.key(true)));
            }
        }
    }


    public void createQueryRelatedToLoggedInUserPermission(BoolQueryBuilder parentBoolQueryBuilder,OpportunityApplicantFilterDTO filterDTO) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(filterDTO.getWorknodeIdsForLoggedInUserFilter() != null) {
            QueryBuilder queryBuilder = QueryBuilders.termsQuery("forEntityId",filterDTO.getWorknodeIdsForLoggedInUserFilter());
            boolQueryBuilder.should(queryBuilder);
        }
        if(filterDTO.getCommunityIdsForLoggedInUserFilter() != null) {
            QueryBuilder queryBuilder = QueryBuilders.termsQuery("currentOwnerEntityId", filterDTO.getCommunityIdsForLoggedInUserFilter());
            boolQueryBuilder.should(queryBuilder);
        }
        if(filterDTO.getSupervisorIdForLoggedInUserFilter() != null) {
            QueryBuilder queryBuilder = QueryBuilders.termQuery("currentSupervisorLinkedUserId",filterDTO.getSupervisorIdForLoggedInUserFilter());
            boolQueryBuilder.should(queryBuilder);
        }

        if(boolQueryBuilder.should() != null && !boolQueryBuilder.should().isEmpty()) {
            parentBoolQueryBuilder.filter(boolQueryBuilder);
        }
    }

    public void createQueryForSearchTextMatch(BoolQueryBuilder parentBoolQueryBuilder,String searchText) {
        String searchTerm = SearchTermParser.parseSearchTerm(searchText);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(searchTerm)
                .field("name", 1.5f)
                .field("applicantEmail", 2.0f)
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .operator(Operator.AND)
                .fuzziness("AUTO")
                .tieBreaker(0.5f);
        boolQueryBuilder.should(multiMatchQuery);

        WildcardQueryBuilder emailWildcard = QueryBuilders.wildcardQuery("applicantEmail.keyword", "*" + searchTerm + "*");
        boolQueryBuilder.should(emailWildcard);

        WildcardQueryBuilder nameWildcard = QueryBuilders.wildcardQuery("name.keyword", "*" + searchTerm + "*");
        boolQueryBuilder.should(nameWildcard);

        createTermQueryAndShouldInBoolQuery(boolQueryBuilder,"contactNumber",searchTerm);
        boolQueryBuilder.minimumShouldMatch(1);

        parentBoolQueryBuilder.must(boolQueryBuilder);
    }

    public void createQueryForAttendanceStatus(BoolQueryBuilder parentBoolQueryBuilder, Set<String> attendanceStatusSet) {
        if(attendanceStatusSet != null && !attendanceStatusSet.isEmpty()) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if(attendanceStatusSet.contains(ServiceConstant.ATTENDANCE_STATUS_PRESENT)) {
                QueryBuilder queryBuilder = QueryBuilders.termQuery("attendanceStatus",ServiceConstant.ATTENDANCE_STATUS_PRESENT);
                boolQueryBuilder.should(queryBuilder);
            }
            if(attendanceStatusSet.contains(ServiceConstant.ATTENDANCE_STATUS_ABSENT)) {
                QueryBuilder queryBuilder = QueryBuilders.termQuery("attendanceStatus",ServiceConstant.ATTENDANCE_STATUS_ABSENT);
                boolQueryBuilder.should(queryBuilder);
            }
            if(attendanceStatusSet.contains(ServiceConstant.ATTENDANCE_STATUS_NOT_MARKED)) {
                QueryBuilder queryBuilder = QueryBuilders.existsQuery("attendanceStatus");
                BoolQueryBuilder boolQueryBuilderForNotMarked = QueryBuilders.boolQuery()
                        .mustNot(queryBuilder);
                boolQueryBuilder.should(boolQueryBuilderForNotMarked);
            }

            if(boolQueryBuilder.should() != null && !boolQueryBuilder.should().isEmpty()) {
                parentBoolQueryBuilder.filter(boolQueryBuilder);
            }
        }
    }

    public void createQueryForTotalWorkExperience(BoolQueryBuilder parentBoolQuery, Double minimumTotalWorkExperience,
                                                  Double maximumTotalWorkExperience) {
        QueryBuilder queryBuilder = QueryBuilders.rangeQuery("totalYearOfExperience")
                .gte(minimumTotalWorkExperience)
                .lte(maximumTotalWorkExperience);

        parentBoolQuery.filter(queryBuilder);
    }

    public void createQueryForApplicationStartAndEndDate(String applicationStatus, String excludeApplicationStatus,
                                                      Date startDateTime,Date endDateTime,
                                                      BoolQueryBuilder boolQueryBuilder) {
        if(startDateTime != null && endDateTime != null) {
            String startDate = DateTimeUtil.convertDateToStringInGivenZoneAndPattern(startDateTime,"yyyy-MM-dd'T'HH:mm:ss'Z'","UTC");
            String endDate = DateTimeUtil.convertDateToStringInGivenZoneAndPattern(endDateTime,"yyyy-MM-dd'T'HH:mm:ss'Z'","UTC");
            String fieldName = null;
            if(Objects.equals(applicationStatus, ServiceConstant.APPLICATION_STATUS_DRAFT)) {
                fieldName = "applicationDateTime";
            }
            else if(Objects.equals(excludeApplicationStatus,ServiceConstant.APPLICATION_STATUS_DRAFT)) {
                fieldName = "applicationSubmitDateTime";
            }
            else {
                fieldName = "applicationDateTime";
            }
            QueryBuilder queryBuilder = QueryBuilders.rangeQuery(fieldName)
                    .gte(startDate)
                    .lte(endDate)
                    .format("yyyy-MM-dd'T'HH:mm:ss'Z'");
            boolQueryBuilder.filter(queryBuilder);
        }
    }

    public void createQueryForInterviewDateTime(Date startInterviewDateTime, Date endInterviewDateTime, BoolQueryBuilder boolQueryBuilder) {
        if(startInterviewDateTime != null && endInterviewDateTime != null) {
            String startDate = DateTimeUtil.convertDateToStringInGivenZoneAndPattern(startInterviewDateTime,"yyyy-MM-dd'T'HH:mm:ss'Z'","UTC");
            String endDate = DateTimeUtil.convertDateToStringInGivenZoneAndPattern(endInterviewDateTime,"yyyy-MM-dd'T'HH:mm:ss'Z'","UTC");

            QueryBuilder queryBuilder = QueryBuilders.rangeQuery("interviewDate")
                    .gte(startDate)
                    .lte(endDate)
                    .format("yyyy-MM-dd'T'HH:mm:ss'Z'");
            boolQueryBuilder.filter(queryBuilder);
        }
    }

    public void createTermQueryAndFilterInBoolQuery(BoolQueryBuilder boolQueryBuilder,String fieldName,Object fieldValue) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery(fieldName,fieldValue);
        boolQueryBuilder.filter(queryBuilder);
    }

    public void createTermQueryAndFilterInBoolQuery(BoolQueryBuilder boolQueryBuilder,String fieldName,Collection<?> fieldValues) {
        QueryBuilder queryBuilder = QueryBuilders.termsQuery(fieldName,fieldValues);
        boolQueryBuilder.filter(queryBuilder);
    }

    public void createTermQueryAndShouldInBoolQuery(BoolQueryBuilder boolQueryBuilder,String fieldName,Object fieldValue) {
        QueryBuilder queryBuilder = QueryBuilders.termsQuery(fieldName,fieldValue);
        boolQueryBuilder.should(queryBuilder);
    }

    public void createTermQueryAndMustNotInBoolQuery(BoolQueryBuilder boolQueryBuilder,String fieldName,Object fieldValue) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery(fieldName,fieldValue);
        boolQueryBuilder.mustNot(queryBuilder);
    }

    public void createNestedQueryForFailedRuleCodes(BoolQueryBuilder parentBoolQueryBuilder, Collection<String> failedRuleCodes) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termsQuery("ruleResults.ruleCode", failedRuleCodes))
                .filter(QueryBuilders.termQuery("ruleResults.ruleOutcome", false));

        parentBoolQueryBuilder.filter(QueryBuilders.nestedQuery("ruleResults", boolQueryBuilder, ScoreMode.None));
    }


    public void createQueryForOpportunityApplicantTasks(BoolQueryBuilder parentBool, Set<Long> opportunityTaskIds,
                                                        Set<String> opportunityApplicantTaskStatusSet, Set<Long> selectionStepIds) {

        boolean isLookingForNotAssigned = opportunityApplicantTaskStatusSet != null && opportunityApplicantTaskStatusSet.contains("NOT_ASSIGNED");

        if (opportunityTaskIds != null && !opportunityTaskIds.isEmpty() && !isLookingForNotAssigned) {
            if (opportunityTaskIds.contains(-1L)) {
                if (selectionStepIds != null && !selectionStepIds.isEmpty()) {
                    BoolQueryBuilder nestedHasTaskInStep = QueryBuilders.boolQuery()
                            .filter(QueryBuilders.termsQuery("opportunityApplicantTasks.selectionStepId", selectionStepIds))
                            .filter(QueryBuilders.existsQuery("opportunityApplicantTasks.opportunityTaskId"));

                    parentBool.mustNot(QueryBuilders.nestedQuery(
                            "opportunityApplicantTasks",
                            nestedHasTaskInStep,
                            ScoreMode.None
                    ));
                } else {
                    parentBool.mustNot(QueryBuilders.nestedQuery(
                            "opportunityApplicantTasks",
                            QueryBuilders.existsQuery("opportunityApplicantTasks.opportunityTaskId"),
                            ScoreMode.None
                    ));
                }
                return;
            }
            BoolQueryBuilder nestedTaskQuery = QueryBuilders.boolQuery()
                    .must(QueryBuilders.termsQuery("opportunityApplicantTasks.opportunityTaskId", opportunityTaskIds));

            if (selectionStepIds != null && !selectionStepIds.isEmpty()) {
                nestedTaskQuery.filter(QueryBuilders.termsQuery("opportunityApplicantTasks.selectionStepId", selectionStepIds));
            }
            if (opportunityApplicantTaskStatusSet != null && !opportunityApplicantTaskStatusSet.isEmpty()) {
                nestedTaskQuery.must(QueryBuilders.termsQuery("opportunityApplicantTasks.opportunityApplicantTaskStatus", opportunityApplicantTaskStatusSet));
            }

            parentBool.filter(QueryBuilders.nestedQuery(
                    "opportunityApplicantTasks",
                    nestedTaskQuery,
                    ScoreMode.None
            ));
        }

        if (opportunityApplicantTaskStatusSet != null && !opportunityApplicantTaskStatusSet.isEmpty()) {
            if (opportunityApplicantTaskStatusSet.contains("NOT_ASSIGNED")) {
                if (opportunityTaskIds != null && !opportunityTaskIds.isEmpty() && !opportunityTaskIds.contains(-1L)) {
                    BoolQueryBuilder nestedTaskQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.termsQuery("opportunityApplicantTasks.opportunityTaskId", opportunityTaskIds));

                    if (selectionStepIds != null && !selectionStepIds.isEmpty()) {
                        nestedTaskQuery.filter(QueryBuilders.termsQuery("opportunityApplicantTasks.selectionStepId", selectionStepIds));
                    }
                    parentBool.mustNot(QueryBuilders.nestedQuery(
                            "opportunityApplicantTasks",
                            nestedTaskQuery,
                            ScoreMode.None
                    ));
                } else {
                    parentBool.mustNot(QueryBuilders.nestedQuery(
                            "opportunityApplicantTasks",
                            QueryBuilders.existsQuery("opportunityApplicantTasks.opportunityTaskId"),
                            ScoreMode.None
                    ));
                }
            } else {
                if (opportunityTaskIds == null || opportunityTaskIds.isEmpty()) {
                    BoolQueryBuilder nestedStatusQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.termsQuery("opportunityApplicantTasks.opportunityApplicantTaskStatus", opportunityApplicantTaskStatusSet));

                    if (selectionStepIds != null && !selectionStepIds.isEmpty()) {
                        nestedStatusQuery.filter(QueryBuilders.termsQuery("opportunityApplicantTasks.selectionStepId", selectionStepIds));
                    }

                    parentBool.filter(QueryBuilders.nestedQuery(
                            "opportunityApplicantTasks",
                            nestedStatusQuery,
                            ScoreMode.None
                    ));
                }
            }
        }
    }


    public void createNestedQueryForOwnerResponse(BoolQueryBuilder parentQuery, String ownerResponseStepCode,
                                                  String ownerResponseResultCode, Long opportunityId) {

        if (PlatformUtil.isSession()) {
            Long currentUserId = PlatformSecurityUtil.getCurrentUserId();
            boolean isLoggedInUserOpportunityCreator = Objects.equals(currentUserId, opportunityService.getCreatedByUserId(opportunityId));
            boolean isAdmin = PlatformSecurityUtil.isTenantAdmin() ||
                    PlatformSecurityUtil.hasRole(ServiceConstant.PROLE_OPPORTUNITY_APPLICANT_ADMIN) ||
                    isLoggedInUserOpportunityCreator;

            if ("STEP_STATUS.PENDING".equals(ownerResponseResultCode)) {
                // .PENDING is for fetching applicant for whom committee member has not responded yet. For Admin it would
                // show applicants for whom the response result array is empty
                if(ownerResponseStepCode == null && isAdmin) {
                    // Admin + No stepCode: Get applicants where applicantOwnerResults is empty
                    BoolQueryBuilder emptyOwnerResultQuery = QueryBuilders.boolQuery()
                            .mustNot(QueryBuilders.nestedQuery(
                                    "applicantOwnerResults",
                                    QueryBuilders.matchAllQuery(),
                                    ScoreMode.None
                            ));

                    parentQuery.filter(emptyOwnerResultQuery);
                }
                else {
                    // Build base filter: stepCode (if any) + ownerUserId (for non-admins)
                    BoolQueryBuilder baseFilterQuery = QueryBuilders.boolQuery();
                    if (ownerResponseStepCode != null) {
                        baseFilterQuery.filter(QueryBuilders.termQuery("applicantOwnerResults.stepCode", ownerResponseStepCode));
                    }
                    if (!isAdmin) {
                        baseFilterQuery.filter(QueryBuilders.termQuery("applicantOwnerResults.ownerUserId", currentUserId));
                    }

                    // Match applicants where no matching entry exists in applicantOwnerResults
                    BoolQueryBuilder noMatchingResultQuery = QueryBuilders.boolQuery()
                            .mustNot(QueryBuilders.nestedQuery(
                                    "applicantOwnerResults",
                                    baseFilterQuery,
                                    ScoreMode.None
                            ));

                    parentQuery.filter(noMatchingResultQuery);
                }
            } else {
                // This is for member response filter
                BoolQueryBuilder nestedQuery = QueryBuilders.boolQuery()
                        .filter(QueryBuilders.termsQuery("applicantOwnerResults.stepResultCode", ownerResponseResultCode));

                if (ownerResponseStepCode != null) {
                    nestedQuery.filter(QueryBuilders.termQuery("applicantOwnerResults.stepCode", ownerResponseStepCode));
                }
                if (!isAdmin) {
                    nestedQuery.filter(QueryBuilders.termQuery("applicantOwnerResults.ownerUserId", currentUserId));
                }

                parentQuery.filter(QueryBuilders.nestedQuery("applicantOwnerResults", nestedQuery, ScoreMode.None));
            }
        }
    }

    @Override
    public Set<Long> getApplicantIdsByFilter(Date startDateTime, Date endDateTime) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        createQueryForApplicationStartAndEndDate(null, null, startDateTime, endDateTime, boolQueryBuilder);

        String[] includes = {"id"};
        SourceFilter sourceFilter = new FetchSourceFilter(includes,null);

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withSourceFilter(sourceFilter)
                .build();

        SearchHits<OpportunityApplicant> hits = elasticsearchRestTemplate.search(nativeSearchQuery, OpportunityApplicant.class,
                IndexCoordinates.of("opportunity_applicant"));

        return hits.stream()
                .map(hit -> hit.getContent().getId())
                .collect(Collectors.toSet());

    }

    private void applyAssessmentFilter (BoolQueryBuilder parentBoolQueryBuilder ,AssessmentFilterDTO assessmentFilter) {
        if (assessmentFilter.getAssessmentId() == null) return;

        BoolQueryBuilder mainNestedBool = QueryBuilders.boolQuery();
        mainNestedBool.filter(QueryBuilders.termQuery("applicantAssessmentResponses.assessmentId", assessmentFilter.getAssessmentId()));

        if (assessmentFilter.getQuestionFilter() != null){
            for (QuestionFilterDTO questionFilter : assessmentFilter.getQuestionFilter()) {
                if (questionFilter.getQuestionId() == null || questionFilter.getResponseIds() == null
                        || questionFilter.getResponseIds().isEmpty()){
                    continue;
                }

                BoolQueryBuilder questionBool = QueryBuilders.boolQuery();

                questionBool.filter(QueryBuilders.termQuery("applicantAssessmentResponses.questionResponses.questionId",
                        questionFilter.getQuestionId()));

                Boolean isAnd = questionFilter.getIsAnd() != null ? questionFilter.getIsAnd() : Boolean.FALSE;
                if (!isAnd) {
                    questionBool.filter(QueryBuilders.termsQuery("applicantAssessmentResponses.questionResponses.responseIds",
                            questionFilter.getResponseIds()));
                } else {
                    for (Long responseId : questionFilter.getResponseIds()) {
                        questionBool.filter(QueryBuilders.termQuery("applicantAssessmentResponses.questionResponses.responseIds",
                                responseId));
                    }
                }
                NestedQueryBuilder innerNested = QueryBuilders.nestedQuery("applicantAssessmentResponses.questionResponses",
                        questionBool, ScoreMode.None);

                mainNestedBool.filter(innerNested);
            }
        }
        NestedQueryBuilder wrapper = QueryBuilders.nestedQuery("applicantAssessmentResponses", mainNestedBool,
                ScoreMode.None);

        parentBoolQueryBuilder.filter(wrapper);
    }


}