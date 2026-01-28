package com.platformcommons.platform.service.search.facade;

import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.service.iam.dto.UserConsentDTO;
import com.platformcommons.platform.service.search.domain.User;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.MapLocationCoordinateDTO;
import com.platformcommons.platform.service.search.dto.UserDTO;
import com.platformcommons.platform.service.search.dto.UserFilterDTO;
import com.platformcommons.platform.service.worknode.dto.WorkforceDTO;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface UserFacade {

    FacetPageDTO<UserDTO> getUsersByFilter(String searchTerm, Set<String> genderCodes, Boolean fetchBookmarkedUsers, Set<String> currentCityCodes,
                                           Set<String> employmentOrganisationCodes, Set<String> facetFields,
                                           String tenantLogin, Integer page, Integer size, Set<String> roleCodes,
                                           Set<String> educationInstituteCodes, Set<String> pathwayCodes, Set<String> cohorts,
                                           Set<String> placementCities, Set<String> currentProfessionalStatusList,
                                           Set<String> currentEmploymentOrganisationCodes, String currentCountryCode,
                                           String currentStateCode, String currentCityCode, Set<String> educationQualificationCodes,
                                           Set<String> educationDegreeCodes, Double minimumTotalYearOfExperience,
                                           Double maximumTotalYearOfExperience,String appContext);

    void assignRoleToUser(UserRoleMapDTO userRoleMapDTO);

    void deleteRoleFromUser(UserRoleMapDTO userRoleMapDTO);

    void updateWorkForceToAnUser(WorkforceDTO workforceDTO);

    Long getUsersCount(String tenantLogin);

    Set<MapLocationCoordinateDTO> getUsersByFilterForLocationDistribution(String tenantLogin, Set<String> roleCodes, Double minLat, Double minLong, Double maxLat, Double maxLong);

    Set<UserDTO> getUsersByFetchStrategy(Set<String> genderCodes, Set<String> currentCityCodes, Set<String> employmentOrganisationCodes,
                                         String tenantLogin, Integer size, Set<String> roleCodes, Set<String> educationInstituteCodes,
                                         Set<String> pathwayCodes, Set<String> cohorts, Set<String> placementCities,
                                         Set<String> currentProfessionalStatusList, String fetchType);

    FacetPageDTO<UserDTO> getUsersWithFilterForAdminApp(String searchTerm, Set<String> genderCodes,
                                                        String currentStateCode, String currentCountryCode, String currentCityCode,
                                                        Set<String> facetFields, Set<String> roleCodes, Set<Long> opportunityIds,
                                                        Set<Long> excludeOpportunityIds, String sortBy, String direction,
                                                        Integer page, Integer size, Integer ageBaseLimit, Integer ageTopLimit,
                                                        Date memberSince, Boolean isActive, Long worknodeId, Set<String> fieldList, Set<Long> userIdList,
                                                        Set<String> stepCodeSet, Set<String> stepStatusCodeSet, Boolean isOpportunityFilterApplied);

    Map<Long, UserDTO> getUserMapByUserIds(Set<Long> userIds, Set<String> fields);

    UserDTO getUserById(Long userId);

    Set<Long> getAllUserIdsByFilter(UserFilterDTO userFilterDTO);

    void saveOrPutUpdate(UserDTO userDTO);

    void sync(UserDTO userDTO);
}
