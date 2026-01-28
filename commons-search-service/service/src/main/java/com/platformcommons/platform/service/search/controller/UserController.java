package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.MapLocationCoordinateDTO;
import com.platformcommons.platform.service.search.dto.UserDTO;
import com.platformcommons.platform.service.search.facade.UserFacade;
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

import java.util.Date;
import java.util.Set;

@RestController
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserFacade facade;

    @ApiOperation(value = "Get Users By Filter", notes = "", response = UserDTO.class, responseContainer = "Set", tags = {"User",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class, responseContainer = "Set")})
    @RequestMapping(value = "/api/v1/user/filter",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<UserDTO>> getUsersByFilter(@RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                           @RequestParam(value = "genderCodes", required = false) Set<String> genderCodes,
                                                           @RequestParam(value = "tenantLogin") String tenantLogin,
                                                           @RequestParam(value = "fetchBookmarkedUsers", required = false) Boolean fetchBookmarkedUsers,
                                                           @RequestParam(value = "currentCityCodes", required = false) Set<String> currentCityCodes,
                                                           @RequestParam(value = "currentStateCode", required = false) String currentStateCode,
                                                           @RequestParam(value = "currentCountryCode", required = false) String currentCountryCode,
                                                           @RequestParam(value = "currentCityCode", required = false) String currentCityCode,
                                                           @RequestParam(value = "employmentOrganisationCodes", required = false) Set<String> employmentOrganisationCodes,
                                                           @RequestParam(value = "currentEmploymentOrganisationCodes", required = false) Set<String> currentEmploymentOrganisationCodes,
                                                           @RequestParam(value = "educationInstituteCodes", required = false) Set<String> educationInstituteCodes,
                                                           @RequestParam(value = "educationQualificationCodes", required = false) Set<String> educationQualificationCodes,
                                                           @RequestParam(value = "educationDegreeCodes", required = false) Set<String> educationDegreeCodes,
                                                           @RequestParam(value = "minimumTotalYearOfExperience", required = false) Double minimumTotalYearOfExperience,
                                                           @RequestParam(value = "maximumTotalYearOfExperience", required = false) Double maximumTotalYearOfExperience,
                                                           @RequestParam(value = "pathwayCodes", required = false) Set<String> pathwayCodes,
                                                           @RequestParam(value = "facetFields") Set<String> facetFields,
                                                           @RequestParam(value = "roleCodes", required = false) Set<String> roleCodes,
                                                           @RequestParam(value = "cohorts", required = false) Set<String> cohorts,
                                                           @RequestParam(value = "placementCities", required = false) Set<String> placementCities,
                                                           @RequestParam(value = "currentProfessionalStatusList", required = false) Set<String> currentProfessionalStatusList,
                                                           @RequestParam(value = "appContext", required = false) String appContext,
                                                           @RequestParam Integer page,
                                                           @RequestParam Integer size) {
        FacetPageDTO<UserDTO> results = facade.getUsersByFilter(searchTerm, genderCodes, fetchBookmarkedUsers, currentCityCodes,
                employmentOrganisationCodes, facetFields, tenantLogin, page, size, roleCodes, educationInstituteCodes, pathwayCodes,
                cohorts, placementCities, currentProfessionalStatusList, currentEmploymentOrganisationCodes, currentCountryCode,
                currentStateCode, currentCityCode, educationQualificationCodes, educationDegreeCodes, minimumTotalYearOfExperience,
                maximumTotalYearOfExperience,appContext);
        return ResponseEntity.status(results.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "Get Latest Sign Up Users", notes = "", response = UserDTO.class, responseContainer = "Set", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class, responseContainer = "Set")
    })
    @RequestMapping(value = "/api/v1/users/fetch",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Set<UserDTO>> getUsersByFetchStrategy(@RequestParam(value = "fetchType") String fetchType,
                                                         @RequestParam(value = "genderCodes", required = false) Set<String> genderCodes,
                                                         @RequestParam(value = "tenantLogin") String tenantLogin,
                                                         @RequestParam(value = "currentCityCodes", required = false) Set<String> currentCityCodes,
                                                         @RequestParam(value = "employmentOrganisationCodes", required = false) Set<String> employmentOrganisationCodes,
                                                         @RequestParam(value = "educationInstituteCodes", required = false) Set<String> educationInstituteCodes,
                                                         @RequestParam(value = "pathwayCodes", required = false) Set<String> pathwayCodes,
                                                         @RequestParam(value = "roleCodes", required = false) Set<String> roleCodes,
                                                         @RequestParam(value = "cohorts", required = false) Set<String> cohorts,
                                                         @RequestParam(value = "placementCities", required = false) Set<String> placementCities,
                                                         @RequestParam(value = "currentProfessionalStatusList", required = false) Set<String> currentProfessionalStatusList,
                                                         @RequestParam Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getUsersByFetchStrategy(genderCodes, currentCityCodes,
                employmentOrganisationCodes, tenantLogin, size, roleCodes, educationInstituteCodes,
                pathwayCodes, cohorts, placementCities, currentProfessionalStatusList, fetchType));
    }


    @ApiOperation(value = "Get Users Count", notes = "", response = Long.class, responseContainer = "Set", tags = {"User",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class, responseContainer = "Set")})
    @RequestMapping(value = "/api/v1/user/count",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Long> getUsersCount(@RequestParam(value = "tenantLogin", required = false) String tenantLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getUsersCount(tenantLogin));
    }


    @ApiOperation(value = "Get Users By Filter For Location Distribution", tags = "User")
    @GetMapping(value = "/api/v1/user/change-maker/location/bounding-box")
    ResponseEntity<Set<MapLocationCoordinateDTO>> getUsersByFilterForLocationDistribution(@RequestParam(value = "tenantLogin") String tenantLogin,
                                                                                          @RequestParam(value = "roleCodes", required = false) Set<String> roleCodes,
                                                                                          @RequestParam(value = "minLat") Double minLat,
                                                                                          @RequestParam(value = "minLong") Double minLong,
                                                                                          @RequestParam(value = "maxLat") Double maxLat,
                                                                                          @RequestParam(value = "maxLong") Double maxLong) {
        Set<MapLocationCoordinateDTO> results = facade.getUsersByFilterForLocationDistribution(tenantLogin, roleCodes, minLat, minLong, maxLat, maxLong);
        return ResponseEntity.status(HttpStatus.OK).body(results );
    }

    @ApiOperation(value = "Get Users By Filter", notes = "", response = UserDTO.class, responseContainer = "Set", tags = {"User",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class, responseContainer = "Set")})
    @RequestMapping(value = "/api/v1/admin-app/user/filter",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<FacetPageDTO<UserDTO>> getUsersWithFilterForAdminApp(@RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                        @RequestParam(value = "genderCodes", required = false) Set<String> genderCodes,
                                                                        @RequestParam(value = "currentStateCode", required = false) String currentStateCode,
                                                                        @RequestParam(value = "currentCountryCode", required = false) String currentCountryCode,
                                                                        @RequestParam(value = "currentCityCode", required = false) String currentCityCode,
                                                                        @RequestParam(value = "facetFields") Set<String> facetFields,
                                                                        @RequestParam(value = "roleCodes", required = false) Set<String> roleCodes,
                                                                        @RequestParam(value = "opportunityIds", required = false) Set<Long> opportunityIds,
                                                                        @RequestParam(value = "excludeOpportunityIds", required = false) Set<Long> excludeOpportunityIds,
                                                                        @RequestParam(value = "ageBaseLimit", required = false) Integer ageBaseLimit,
                                                                        @RequestParam(value = "ageTopLimit", required = false) Integer ageTopLimit,
                                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
                                                                        @ApiParam(value = "Date in the format yyyy-MM-dd'T'HH:mm:ss'Z'", example = "2024-11-05T14:30:00Z")
                                                                        Date memberSince,
                                                                        @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                        @RequestParam(value = "direction", required = false) String direction,
                                                                        @RequestParam(value = "isActive",required = false) Boolean isActive,
                                                                        @RequestParam(value = "worknodeId",required = false) Long worknodeId,
                                                                        @RequestParam(value = "fieldList",required = false) Set<String> fieldList,
                                                                        @RequestParam(value = "userIdList",required = false) Set<Long> userIdList,
                                                                        @RequestParam(value = "stepCodeSet", required = false) Set<String> stepCodeSet,
                                                                        @RequestParam(value = "stepStatusCodeSet", required = false) Set<String> stepStatusCodeSet,
                                                                        @RequestParam(value = "isOpportunityFilterApplied", required = false) Boolean isOpportunityFilterApplied,
                                                                        @RequestParam Integer page,
                                                                        @RequestParam Integer size) {
        FacetPageDTO<UserDTO> results = facade.getUsersWithFilterForAdminApp(searchTerm, genderCodes,
                currentStateCode, currentCountryCode, currentCityCode, facetFields, roleCodes, opportunityIds, excludeOpportunityIds,
                sortBy, direction, page, size, ageBaseLimit, ageTopLimit, memberSince,isActive,worknodeId,fieldList,userIdList,
                stepCodeSet, stepStatusCodeSet, isOpportunityFilterApplied);
        return ResponseEntity.status(results.isHasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(results);
    }


}
