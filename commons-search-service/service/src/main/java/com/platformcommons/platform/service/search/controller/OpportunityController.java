package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.OpportunityDTO;
import com.platformcommons.platform.service.search.facade.OpportunityFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Tag(name="Opportunity")
public class OpportunityController {

    @Autowired
    private OpportunityFacade facade;

    @ApiOperation(value = "Get Opportunities By Filter For Admin App",notes = "", response = OpportunityDTO.class, responseContainer = "Set", tags={ "Opportunity", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/opportunities/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<OpportunityDTO>> getOpportunitiesByFilterForAdmin(@RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                                  @ApiParam(allowableValues = "FETCH_TYPE.TOP,FETCH_TYPE.TRENDING,FETCH_TYPE.LATEST") @RequestParam(value = "fetch", required = false) String fetch,
                                                                                  @RequestParam(value = "causeCodes", required = false) Set<String> causeCodes,
                                                                                  @RequestParam(value = "locationCodes", required = false) Set<String> locationCodes,
                                                                                  @RequestParam(value = "locationTypes", required = false) Set<String> locationTypes,
                                                                                  @RequestParam(value = "skillCodes", required = false) Set<String> skillCodes,
                                                                                  @RequestParam(value = "opportunityTypes", required = false) Set<String> opportunityTypes,
                                                                                  @RequestParam(value = "opportunitySubTypes", required = false) Set<String> opportunitySubTypes,
                                                                                  @RequestParam(value = "marketUUID") String marketUUID,
                                                                                  @RequestParam(value = "jobTypes", required = false) Set<String> jobTypes,
                                                                                  @RequestParam(value = "minimumCompensation", required = false) Double minimumCompensation,
                                                                                  @RequestParam(value = "maximumCompensation", required = false) Double maximumCompensation,
                                                                                  @RequestParam(value = "workPlaceTypes", required = false) Set<String> workPlaceTypes,
                                                                                  @RequestParam(value = "minimumExperienceDays", required = false) Long minimumExperienceDays,
                                                                                  @RequestParam(value = "maximumExperienceDays", required = false) Long maximumExperienceDays,
                                                                                  @RequestParam(value = "cityCode", required = false) String cityCode,
                                                                                  @RequestParam(value = "stateCode", required = false) String stateCode,
                                                                                  @RequestParam(value = "countryCode", required = false) String countryCode,
                                                                                  @RequestParam(value = "roleTypes", required = false) Set<String> roleTypes,
                                                                                  @RequestParam(value = "pathways", required = false) Set<String> pathways,
                                                                                  @RequestParam(value = "sectors", required = false) Set<String> sectors,
                                                                                  @RequestParam(value = "cityCodes", required = false) Set<String> cityCodes,
                                                                                  @RequestParam(value = "organisationCodes", required = false) Set<String> organisationCodes,
                                                                                  @RequestParam(value = "organisationTypes", required = false) Set<String> organisationTypes,
                                                                                  @ApiParam(allowableValues = "DATE_POSTED.PAST_MONTH,DATE_POSTED.ANY_TIME,DATE_POSTED.PAST_WEEK,DATE_POSTED.PAST_24_HOURS")
                                                                                  @RequestParam(value = "datePostedCode", required = false) String datePostedCode,
                                                                                  @RequestParam(value = "status",required = false) String status,
                                                                                  @RequestParam(value = "facetFields",required = false) Set<String> facetFields,
                                                                                  @RequestParam(value = "marketPlaceVisibility",required = false) String marketPlaceVisibility,
                                                                                  @RequestParam(value = "myOpportunities",required = false) Boolean myOpportunities,
                                                                                  @RequestParam(value = "fetchBasedOnRole",required = false) Boolean fetchBasedOnRole,
                                                                                  @RequestParam(value = "restrictApplication",required = false) Boolean restrictApplication,
                                                                                  @RequestParam(value = "opportunityTimeline",required = false) String opportunityTimeline,
                                                                                  @RequestParam(value = "opportunityCategories",required = false) Set<String> opportunityCategories,
                                                                                  @RequestParam(value = "opportunitySubCategories",required = false) Set<String> opportunitySubCategories,
                                                                                  @RequestParam(value = "tags",required = false) Set<String> tags,
                                                                                  @RequestParam(value = "isActive",required = false) Boolean isActive,
                                                                                  @RequestParam(value = "worknodeId",required = false) Long worknodeId,
                                                                                  @RequestParam(value = "tenantLogin",required = false) String tenantLogin,
                                                                                  @RequestParam(value = "targetGroups",required = false) Set<String> targetGroups,
                                                                                  @RequestParam(value = "fetchPromotedOpportunities",required = false) Boolean fetchPromotedOpportunities,
                                                                                  @RequestParam(value = "educationQualificationCodes",required = false) Set<String> educationQualificationCodes,
                                                                                  @RequestParam(value = "educationDegreeCodes",required = false) Set<String> educationDegreeCodes,
                                                                                  @RequestParam Integer page,
                                                                                  @RequestParam Integer size){
        FacetPageDTO<OpportunityDTO> results = facade.getOpportunitiesByFilterForAdmin(searchTerm,fetch,causeCodes,locationCodes,locationTypes,skillCodes,
                opportunityTypes,opportunitySubTypes,marketUUID,jobTypes,minimumCompensation,maximumCompensation,workPlaceTypes,
                minimumExperienceDays,maximumExperienceDays,cityCode,stateCode,countryCode,roleTypes,pathways,sectors,cityCodes,
                organisationCodes,datePostedCode,status,page,size,facetFields,organisationTypes,marketPlaceVisibility,myOpportunities,
                fetchBasedOnRole,restrictApplication,opportunityTimeline,opportunityCategories,opportunitySubCategories,tags,isActive,worknodeId,
                tenantLogin,targetGroups,fetchPromotedOpportunities,educationQualificationCodes,educationDegreeCodes);
        return ResponseEntity.status(results.isHasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "Get Recommended Opportunities By Custom Logic",notes = "", response = OpportunityDTO.class, responseContainer = "Set", tags={ "Opportunity", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/opportunities/recommended",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<OpportunityDTO>> getRecommendedOpportunities(@RequestParam(value = "opportunityTypes", required = false) Set<String> opportunityTypes,
                                                                    @RequestParam(value = "opportunitySubTypes", required = false) Set<String> opportunitySubTypes,
                                                                    @RequestParam(value = "marketUUID") String marketUUID,
                                                                    @RequestParam(value = "fetchBasedOnRole",required = false) Boolean fetchBasedOnRole,
                                                                    @RequestParam(value = "opportunityTimeline",required = false) String opportunityTimeline,
                                                                    @Valid @RequestParam(name="fetchSet") Set<String> fetchSet,
                                                                    @ApiParam(allowableValues = "OPPORTUNITY_TIMELINE.ALL,OPPORTUNITY_TIMELINE.UPCOMING,OPPORTUNITY_TIMELINE.ONGOING,OPPORTUNITY_TIMELINE.PAST")
                                                                    @RequestParam(value = "opportunityTimelineBasedOnScheduledDate", required = false) Set<String> opportunityTimelineBasedOnScheduledDate,
                                                                    @ApiParam(allowableValues = "OPPORTUNITY_TIMELINE.ALL,OPPORTUNITY_TIMELINE.UPCOMING,OPPORTUNITY_TIMELINE.ONGOING,OPPORTUNITY_TIMELINE.PAST")
                                                                    @RequestParam(value = "opportunityTimelineBasedOnApplicationDate", required = false) Set<String> opportunityTimelineBasedOnApplicationDate){
        Set<OpportunityDTO> results = facade.getRecommendedOpportunities(opportunityTypes,opportunitySubTypes,marketUUID,
                fetchBasedOnRole,opportunityTimeline,fetchSet,opportunityTimelineBasedOnScheduledDate,opportunityTimelineBasedOnApplicationDate);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Get Opportunities Based On User Params Match", notes = "", response = OpportunityDTO.class, responseContainer = "Set", tags = {"Opportunity",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityDTO.class, responseContainer = "Set")})
    @RequestMapping(value = "/api/v1/opportunities/user-suggested",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Set<OpportunityDTO>> getUserSuggestedCustomOpportunities(@RequestParam(value = "opportunityTypes", required = false) Set<String> opportunityTypes,
                                                                            @RequestParam(value = "opportunitySubTypes", required = false) Set<String> opportunitySubTypes,
                                                                            @RequestParam(value = "marketUUID") String marketUUID,
                                                                            @RequestParam(value = "fetchBasedOnRole", required = false) Boolean fetchBasedOnRole,
                                                                            @RequestParam Integer size,
                                                                            @RequestParam(value = "opportunityTimeline", required = false) String opportunityTimeline,
                                                                            @ApiParam(allowableValues = "OPPORTUNITY_TIMELINE.ALL,OPPORTUNITY_TIMELINE.UPCOMING,OPPORTUNITY_TIMELINE.ONGOING,OPPORTUNITY_TIMELINE.PAST")
                                                                            @RequestParam(value = "opportunityTimelineBasedOnScheduledDate", required = false) Set<String> opportunityTimelineBasedOnScheduledDate,
                                                                            @ApiParam(allowableValues = "OPPORTUNITY_TIMELINE.ALL,OPPORTUNITY_TIMELINE.UPCOMING,OPPORTUNITY_TIMELINE.ONGOING,OPPORTUNITY_TIMELINE.PAST")
                                                                            @RequestParam(value = "opportunityTimelineBasedOnApplicationDate", required = false) Set<String> opportunityTimelineBasedOnApplicationDate) {
        Set<OpportunityDTO> results = facade.getUserSuggestedCustomOpportunities(opportunityTypes, opportunitySubTypes, marketUUID,
                fetchBasedOnRole, opportunityTimeline, size,opportunityTimelineBasedOnApplicationDate,opportunityTimelineBasedOnScheduledDate);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Get Opportunities By Filter For ChangeMaker",notes = "", response = OpportunityDTO.class, responseContainer = "Set", tags={ "Opportunity", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/opportunities/change-maker/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<OpportunityDTO>> getOpportunitiesByFilterForChangeMaker(@RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                                        @RequestParam(required = false) String addressSearchTerm,
                                                                                        @ApiParam(allowableValues = "FETCH_TYPE.TOP,FETCH_TYPE.TRENDING,FETCH_TYPE.LATEST") @RequestParam(value = "fetch", required = false) String fetch,
                                                                                        @RequestParam(value = "causeCodes", required = false) Set<String> causeCodes,
                                                                                        @RequestParam(value = "locationCodes", required = false) Set<String> locationCodes,
                                                                                        @RequestParam(value = "locationTypes", required = false) Set<String> locationTypes,
                                                                                        @RequestParam(value = "skillCodes", required = false) Set<String> skillCodes,
                                                                                        @RequestParam(value = "opportunityTypes", required = false) Set<String> opportunityTypes,
                                                                                        @RequestParam(value = "opportunitySubTypes", required = false) Set<String> opportunitySubTypes,
                                                                                        @RequestParam(value = "marketUUID") String marketUUID,
                                                                                        @RequestParam(value = "jobTypes", required = false) Set<String> jobTypes,
                                                                                        @RequestParam(value = "minimumCompensation", required = false) Double minimumCompensation,
                                                                                        @RequestParam(value = "maximumCompensation", required = false) Double maximumCompensation,
                                                                                        @RequestParam(value = "workPlaceTypes", required = false) Set<String> workPlaceTypes,
                                                                                        @RequestParam(value = "minimumExperienceDays", required = false) Long minimumExperienceDays,
                                                                                        @RequestParam(value = "maximumExperienceDays", required = false) Long maximumExperienceDays,
                                                                                        @RequestParam(value = "cityCode", required = false) String cityCode,
                                                                                        @RequestParam(value = "stateCode", required = false) String stateCode,
                                                                                        @RequestParam(value = "countryCode", required = false) String countryCode,
                                                                                        @RequestParam(value = "roleTypes", required = false) Set<String> roleTypes,
                                                                                        @RequestParam(value = "pathways", required = false) Set<String> pathways,
                                                                                        @RequestParam(value = "sectors", required = false) Set<String> sectors,
                                                                                        @RequestParam(value = "cityCodes", required = false) Set<String> cityCodes,
                                                                                        @RequestParam(value = "organisationCodes", required = false) Set<String> organisationCodes,
                                                                                        @RequestParam(value = "organisationTypes", required = false) Set<String> organisationTypes,
                                                                                        @ApiParam(allowableValues = "DATE_POSTED.PAST_MONTH,DATE_POSTED.ANY_TIME,DATE_POSTED.PAST_WEEK,DATE_POSTED.PAST_24_HOURS")
                                                                                        @RequestParam(value = "datePostedCode", required = false) String datePostedCode,
                                                                                        @RequestParam(value = "fetchBasedOnRole",required = false) Boolean fetchBasedOnRole,
                                                                                        @RequestParam(value = "restrictApplication",required = false) Boolean restrictApplication,
                                                                                        @ApiParam(allowableValues = "OPPORTUNITY_TIMELINE.ALL,OPPORTUNITY_TIMELINE.UPCOMING,OPPORTUNITY_TIMELINE.ONGOING,OPPORTUNITY_TIMELINE.PAST")
                                                                                        @RequestParam(value = "opportunityTimeline",required = false) String opportunityTimeline,
                                                                                        @ApiParam(allowableValues = "OPPORTUNITY_TIMELINE.ALL,OPPORTUNITY_TIMELINE.UPCOMING,OPPORTUNITY_TIMELINE.ONGOING,OPPORTUNITY_TIMELINE.PAST")
                                                                                        @RequestParam(value = "opportunityTimelineBasedOnScheduledDate",required = false) Set<String> opportunityTimelineBasedOnScheduledDate,
                                                                                        @ApiParam(allowableValues = "OPPORTUNITY_TIMELINE.ALL,OPPORTUNITY_TIMELINE.UPCOMING,OPPORTUNITY_TIMELINE.ONGOING,OPPORTUNITY_TIMELINE.PAST")
                                                                                        @RequestParam(value = "opportunityTimelineBasedOnApplicationDate",required = false) Set<String> opportunityTimelineBasedOnApplicationDate,
                                                                                        @RequestParam(value = "opportunityCategories",required = false) Set<String> opportunityCategories,
                                                                                        @RequestParam(value = "opportunitySubCategories",required = false) Set<String> opportunitySubCategories,
                                                                                        @RequestParam(value = "tags",required = false) Set<String> tags,
                                                                                        @RequestParam(value = "facetFields") Set<String> facetFields,
                                                                                        @RequestParam(value = "tenantLogin",required = false) String tenantLogin,
                                                                                        @RequestParam(value = "tenantLogins", required = false) Set<String> tenantLogins,
                                                                                        @RequestParam(value = "userProgramCodes",required = false) Set<String> userProgramCodes,
                                                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")   @ApiParam(value = "Date in the format yyyy-MM-dd'T'HH:mm:ss'Z'", example = "2024-11-05T14:30:00Z") Date scheduledStartDate,
                                                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")   @ApiParam(value = "Date in the format yyyy-MM-dd'T'HH:mm:ss'Z'", example = "2024-11-05T14:30:00Z") Date scheduledEndDate,
                                                                                        @RequestParam(value = "targetGroups",required = false) Set<String> targetGroups,
                                                                                        @RequestParam(value = "fetchPromotedOpportunities",required = false) Boolean fetchPromotedOpportunities,
                                                                                        @RequestParam(value = "educationQualificationCodes",required = false) Set<String> educationQualificationCodes,
                                                                                        @RequestParam(value = "educationDegreeCodes",required = false) Set<String> educationDegreeCodes,
                                                                                        @RequestParam(value = "loggedInUserOpportunity",required = false) Boolean loggedInUserOpportunity,
                                                                                        @RequestParam Integer page,
                                                                                        @RequestParam Integer size){
        FacetPageDTO<OpportunityDTO> results = facade.getOpportunitiesByFilterForChangeMaker(
                searchTerm,addressSearchTerm,fetch,causeCodes,locationCodes,locationTypes,skillCodes,
                opportunityTypes,opportunitySubTypes,marketUUID,jobTypes,minimumCompensation,maximumCompensation,workPlaceTypes,
                minimumExperienceDays,maximumExperienceDays,cityCode,stateCode,countryCode,roleTypes,pathways,sectors,cityCodes,
                organisationCodes,datePostedCode,page,size,organisationTypes,
                fetchBasedOnRole,restrictApplication,opportunityTimeline,opportunityCategories,opportunitySubCategories,tags,facetFields,
                userProgramCodes,tenantLogin,scheduledStartDate,scheduledEndDate,opportunityTimelineBasedOnScheduledDate,
                opportunityTimelineBasedOnApplicationDate,targetGroups,fetchPromotedOpportunities,
                educationQualificationCodes,educationDegreeCodes, tenantLogins,loggedInUserOpportunity);
        return ResponseEntity.status(results.isHasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Get Opportunities Created By UserId", notes = "", response = OpportunityDTO.class, responseContainer = "Set", tags = {"Opportunity",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OpportunityDTO.class, responseContainer = "Set")})
    @RequestMapping(value = "/api/v1/opportunities/user",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<OpportunityDTO>> getOpportunitiesCreatedByUserId(@RequestParam(value = "opportunityTypes", required = false) Set<String> opportunityTypes,
                                                                            @RequestParam(value = "opportunitySubTypes", required = false) Set<String> opportunitySubTypes,
                                                                            @ApiParam(allowableValues = "FETCH_TYPE.TOP,FETCH_TYPE.TRENDING,FETCH_TYPE.LATEST") @RequestParam(value = "fetch", required = false) String fetch,
                                                                            @RequestParam(value = "marketUUID") String marketUUID,
                                                                            @RequestParam Long userId,
                                                                            @RequestParam Integer page,
                                                                            @RequestParam Integer size) {
        PageDTO<OpportunityDTO> results = facade.getOpportunitiesCreatedByUserId(userId, opportunityTypes, opportunitySubTypes, fetch, marketUUID, page, size);
        return ResponseEntity.status(results.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(results);
    }

}
