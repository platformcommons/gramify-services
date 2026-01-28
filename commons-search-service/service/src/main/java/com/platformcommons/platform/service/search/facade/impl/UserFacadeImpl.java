package com.platformcommons.platform.service.search.facade.impl;

import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.search.application.constant.Fetch;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.search.application.service.UserService;
import com.platformcommons.platform.service.search.application.utility.*;
import com.platformcommons.platform.service.search.domain.User;
import com.platformcommons.platform.service.search.dto.*;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.facade.UserFacade;
import com.platformcommons.platform.service.search.facade.assembler.UserDTOAssembler;
import com.platformcommons.platform.service.search.facade.cache.GlobalRefDataCacheManager;
import com.platformcommons.platform.service.search.facade.cache.validator.TenantMetaConfigValidator;
import com.platformcommons.platform.service.search.facade.client.CommonsConnectClient;
import com.platformcommons.platform.service.search.facade.client.CommonsIamClient;
import com.platformcommons.platform.service.search.facade.client.WorknodeClient;
import com.platformcommons.platform.service.search.facade.client.utility.CommonsReportUtil;
import com.platformcommons.platform.service.search.facade.client.utility.CommonsWorkNodeUtil;
import com.platformcommons.platform.service.worknode.dto.WorkforceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@Slf4j
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOAssembler assembler;

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    @Autowired
    private TenantMetaConfigValidator tenantMetaConfigValidator;

    @Autowired
    private CommonsConnectClient connectClient;

    @Autowired
    private OpportunityApplicantFacade opportunityApplicantFacade;

    @Autowired(required = false)
    private GlobalRefDataCacheManager globalRefDataCacheManager;

    @Autowired
    private CommonsWorkNodeUtil commonsWorkNodeUtil;

    @Autowired
    private WorknodeClient worknodeClient;

    @Autowired
    private CommonsIamClient commonsIamClient;

    @Value("${commons.platform.cache.appkey:Appkey YXBwS2V5OjdiMDNlMWJlLTkwMjctNDMwNC05YjRjLTlkMTdiZmI4NTM2NyxhcHBDb2RlOkNPTU1PTlNfQVBQLkNIQU5HRU1BS0VS}")
    private String appKey;

    @Override
    public void saveOrPutUpdate(UserDTO userDTO) {
        Boolean userExists = userService.existsByUserId(userDTO.getUserId());
        User incomingUser = null;
        User savedUser = null;
        if(userExists) {
            incomingUser = assembler.fromDTO(userDTO);
            savedUser = userService.putUpdate(incomingUser);
        }
        else {
            updateNonFlatFieldsInUserDTO(userDTO);
            incomingUser = assembler.fromDTO(userDTO);
            savedUser = userService.save(incomingUser);
        }

        UserDTO savedUserDTO = assembler.toDTO(savedUser);
        opportunityApplicantFacade.updateUserFieldsInApplicant(savedUserDTO);
    }

    public FacetPageDTO<UserDTO> getUsersByFilter(String searchTerm, Set<String> genderCodes, Boolean fetchBookmarkedUsers,
                                                  Set<String> currentCityCodes, Set<String> employmentOrganisationCodes, Set<String> facetFields,
                                                  String tenantLogin, Integer page, Integer size, Set<String> roleCodes,
                                                  Set<String> educationInstituteCodes, Set<String> pathwayCodes, Set<String> cohorts,
                                                  Set<String> placementCities, Set<String> currentProfessionalStatusList,
                                                  Set<String> currentEmploymentOrganisationCodes, String currentCountryCode,
                                                  String currentStateCode, String currentCityCode, Set<String> educationQualificationCodes,
                                                  Set<String> educationDegreeCodes,Double minimumTotalYearOfExperience,
                                                  Double maximumTotalYearOfExperience, String appContext) {
        UserFilterDTO userFilterDTO = createUserFilterDTO(searchTerm, genderCodes, fetchBookmarkedUsers, currentCityCodes,
                employmentOrganisationCodes, facetFields, tenantLogin, page, size, roleCodes, educationInstituteCodes, pathwayCodes,
                cohorts, placementCities, currentProfessionalStatusList, currentEmploymentOrganisationCodes, Boolean.TRUE, null,
                null, null, null, null, null, null, null,
                currentCountryCode, currentStateCode, currentCityCode,null,null,null, null, educationQualificationCodes,
                educationDegreeCodes, minimumTotalYearOfExperience,maximumTotalYearOfExperience,appContext);
        Pageable pageable = PageRequest.of(userFilterDTO.getPage(), userFilterDTO.getSize());
        List<Long> bookmarkedUserIds = null;
        if (Objects.equals(userFilterDTO.getFetchBookmarkedUsers(), Boolean.TRUE)) {
            bookmarkedUserIds = connectClient.getBookmarkedUserIdsForLoggedInUser(PlatformSecurityUtil.getToken()).getBody();
            userFilterDTO.setUserIds(new HashSet<>(bookmarkedUserIds));
        }
        FacetPage<User> facetPage = userService.getUsersWithFilter(userFilterDTO.getSearchTerm(), userFilterDTO, pageable);
        LinkedHashSet<UserDTO> userDTOSet = facetPage.stream()
                .map(assembler::toCustomDTOIgnoringSensitiveInfo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        updateUserDTOBasedOnEmployerRoleConfig(userDTOSet, userFilterDTO.getTenantLogin());
        return new FacetPageDTO<>(userDTOSet,
                facetPage.hasNext(),
                FacetUtil.createFacetResult(facetPage),
                facetPage.getTotalElements());
    }

    public Set<UserDTO> getUsersByFetchStrategy(Set<String> genderCodes, Set<String> currentCityCodes,
                                                Set<String> employmentOrganisationCodes, String tenantLogin,
                                                Integer size, Set<String> roleCodes, Set<String> educationInstituteCodes,
                                                Set<String> pathwayCodes, Set<String> cohorts, Set<String> placementCities,
                                                Set<String> currentProfessionalStatusList, String fetchType) {
        UserFilterDTO userFilterDTO = createUserFilterDTO(null, genderCodes, null, currentCityCodes,
                employmentOrganisationCodes, null, tenantLogin, null, size, roleCodes, educationInstituteCodes, pathwayCodes,
                cohorts, placementCities, currentProfessionalStatusList, null, Boolean.TRUE, fetchType,
                null, null, null, null, null, null, null,
                null, null, null,null,null,null, null,
                null, null, null,null,
                null);
        Pageable pageable = getPageableBasedOnFetchType(0, userFilterDTO.getSize(), userFilterDTO.getFetchType());
        List<User> latestUsers = userService.getUsersByFetchStrategy(userFilterDTO, pageable);
        LinkedHashSet<UserDTO> userDTOSet = latestUsers.stream()
                .map(assembler::toCustomDTOIgnoringSensitiveInfo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        updateUserDTOBasedOnEmployerRoleConfig(userDTOSet, userFilterDTO.getTenantLogin());
        return userDTOSet;
    }


    @Override
    public Set<Long> getAllUserIdsByFilter(UserFilterDTO userFilterDTO) {
        List<User> userList = userService.getAllUsersByFilter(userFilterDTO);
        return userList.stream()
                .map(User::getUserId)
                .collect(Collectors.toSet());
    }


    public void updateUserDTOBasedOnEmployerRoleConfig(LinkedHashSet<UserDTO> userDTOSet, String tenantLogin) {
        if (PlatformUtil.isSession()) {
            Set<String> currentUserRoles = PlatformSecurityUtil.getCurrentUserContext().getAuthorities();
            Set<String> roleCodes = tenantMetaConfigValidator.getMultiValueByMetaKeyAndTenantLogin(tenantLogin,
                    TenantConfigConstant.CONFIG_KEY_EMPLOYER_ROLE_CODES,null);

            boolean isCurrentUserEmployer = Boolean.FALSE;
            if (roleCodes != null && !roleCodes.isEmpty() && !currentUserRoles.isEmpty()
                    && currentUserRoles.stream().anyMatch(roleCodes::contains)) {
                isCurrentUserEmployer = Boolean.TRUE;
            }

            for (UserDTO userDTO : userDTOSet) {
                if (userDTO.getCurrentProfessionalStatus() != null) {
                    String currentProfessionalStatus = userDTO.getCurrentProfessionalStatus();
                    if (isCurrentUserEmployer && (Objects.equals(userDTO.getCurrentProfessionalStatusVisibility(), ServiceConstant.CURRENT_PROFESSIONAL_STATUS_VISIBILITY_ALL)
                            || Objects.equals(userDTO.getCurrentProfessionalStatusVisibility(), ServiceConstant.CURRENT_PROFESSIONAL_STATUS_VISIBILITY_EMPLOYER))) {
                        userDTO.setCurrentProfessionalStatus(currentProfessionalStatus);
                    } else if (!isCurrentUserEmployer
                            && Objects.equals(userDTO.getCurrentProfessionalStatusVisibility(), ServiceConstant.CURRENT_PROFESSIONAL_STATUS_VISIBILITY_ALL)) {
                        userDTO.setCurrentProfessionalStatus(currentProfessionalStatus);
                    } else {
                        userDTO.setCurrentProfessionalStatus(null);
                    }
                }
            }
        }
    }


    @Override
    public FacetPageDTO<UserDTO> getUsersWithFilterForAdminApp(String searchTerm, Set<String> genderCodes, String currentStateCode,
                                                               String currentCountryCode,
                                                               String currentCityCode, Set<String> facetFields, Set<String> roleCodes,
                                                               Set<Long> opportunityIds, Set<Long> excludeOpportunityIds,
                                                               String sortBy, String direction, Integer page, Integer size,
                                                               Integer ageBaseLimit, Integer ageTopLimit, Date memberSince,
                                                               Boolean isActive, Long worknodeId, Set<String> fieldList, Set<Long> userIdList,
                                                               Set<String> stepCodeSet, Set<String> stepStatusCodeSet, Boolean isOpportunityFilterApplied) {
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        String tenantLogin = PlatformSecurityUtil.getCurrentTenantLogin();

        UserFilterDTO userFilterDTO = createUserFilterDTO(searchTerm, genderCodes, null, null, null,
                facetFields, tenantLogin, page, size, roleCodes, null, null,
                null, null, null, null,isActive, null,
                opportunityIds, excludeOpportunityIds, sortBy, direction, ageBaseLimit, ageTopLimit, memberSince,
                currentCountryCode, currentStateCode, currentCityCode,worknodeId,fieldList,tenantId,userIdList,
                null, null, null,null,null);
        Pageable pageable = SearchHelper.getPageable(userFilterDTO.getSearchTerm(),userFilterDTO.getSortBy(),
                userFilterDTO.getDirection(), userFilterDTO.getPage(),userFilterDTO.getSize());
        FacetPageDTO<OpportunityApplicantDTO> elasticResult = null;
        if (userFilterDTO.getOpportunityIds() != null && !userFilterDTO.getOpportunityIds().isEmpty()) {
            elasticResult = opportunityApplicantFacade.getUserIdsOfApplicantsByFilter(userFilterDTO.getOpportunityIds(),
                    stepCodeSet, stepStatusCodeSet, pageable);

            Set<Long> userIds = elasticResult.getElements().stream()
                    .map(OpportunityApplicantDTO::getMarketUserId)
                    .collect(Collectors.toSet());

            if(userFilterDTO.getUserIds() == null) {
                userFilterDTO.setUserIds(new HashSet<>());
            }
            userFilterDTO.getUserIds().addAll(userIds);
        }
        if (userFilterDTO.getExcludeOpportunityIds() != null && !userFilterDTO.getExcludeOpportunityIds().isEmpty()) {
            FacetPageDTO<OpportunityApplicantDTO> excludedResult = opportunityApplicantFacade.getUserIdsOfApplicantsByFilter(userFilterDTO.getExcludeOpportunityIds(),
                    null, null, pageable);
            Set<Long> userIds = excludedResult.getElements().stream()
                    .map(OpportunityApplicantDTO::getMarketUserId)
                    .collect(Collectors.toSet());

            userFilterDTO.setExcludeUserIds(userIds);
        }

        if(userFilterDTO.getWorknodeId() != null) {
            Set<Long> worknodeIds = commonsWorkNodeUtil.getAllLeafWorkNodesIdByParentId(userFilterDTO.getWorknodeId());
            userFilterDTO.setWorknodeIds(worknodeIds);
        }
        Integer resolvedPage = Boolean.TRUE.equals(isOpportunityFilterApplied) ? 0 : userFilterDTO.getPage();
        Pageable pageableForSolr = SearchHelper.getPageable(userFilterDTO.getSearchTerm(),userFilterDTO.getSortBy(),
                userFilterDTO.getDirection(), resolvedPage, userFilterDTO.getSize());
        FacetPage<User> facetPage = userService.getUsersWithFilter(userFilterDTO.getSearchTerm(), userFilterDTO, pageableForSolr);
        LinkedHashSet<UserDTO> userDTOSet = facetPage.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        boolean hasNext;
        long totalElements;
        Map<String, Map<String, Long>> facetResult;

        if (Boolean.TRUE.equals(isOpportunityFilterApplied) && elasticResult != null) {
            totalElements = elasticResult.getTotalElements();
            hasNext = ElasticSearchUtility.computeHasMoreHits(totalElements, elasticResult.getElements().size(),
                    pageable.getPageNumber(), pageable.getPageSize());
            facetResult = elasticResult.getFacetResult();
        } else {
            totalElements = facetPage.getTotalElements();
            hasNext = facetPage.hasNext();
            facetResult = FacetUtil.createFacetResult(facetPage);
        }

        return new FacetPageDTO<>(userDTOSet, hasNext,
                facetResult, totalElements);
    }


    @Override
    public Map<Long, UserDTO> getUserMapByUserIds(Set<Long> userIds, Set<String> fields) {
        List<User> userList = userService.getUsersByUserIds(userIds, fields);
        return userList.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toMap(UserDTO::getUserId, Function.identity(), (a, b) -> a));
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return assembler.toDTO(userService.getByUserId(userId));
    }

    @Override
    @Retryable(value = OptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void assignRoleToUser(UserRoleMapDTO userRoleMapDTO) {
        Long userId = Long.valueOf(userRoleMapDTO.getUser().getId());
        String roleCode = userRoleMapDTO.getRole().getCode();
        User user = userService.getByUserId(userId);
        if (user != null) {
            Set<String> roleCodes = user.getRoleCodes() == null ? new HashSet<>() : user.getRoleCodes();
            roleCodes.add(roleCode);
            user.setRoleCodes(roleCodes);
            user = userService.save(user);
            UserDTO userDTO = assembler.toDTO(user);
            opportunityApplicantFacade.updateUserFieldsInApplicant(userDTO);
        }
    }

    @Override
    @Retryable(value = OptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void deleteRoleFromUser(UserRoleMapDTO userRoleMapDTO) {
        Long userId = Long.valueOf(userRoleMapDTO.getUser().getId());
        String roleCode = userRoleMapDTO.getRole().getCode();
        User user = userService.getByUserId(userId);
        if (user != null) {
            Set<String> roleCodes = user.getRoleCodes() == null ? new HashSet<>() : user.getRoleCodes();
            roleCodes.remove(roleCode);
            user.setRoleCodes(roleCodes);
            user = userService.save(user);
            UserDTO userDTO = assembler.toDTO(user);
            opportunityApplicantFacade.updateUserFieldsInApplicant(userDTO);
        }
    }

    @Override
    @Retryable(value = OptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void updateWorkForceToAnUser(WorkforceDTO workforceDTO) {
        User user = userService.getByUserId(workforceDTO.getUserId());
        if (user != null) {
            String syncCohortAndPlacementCity = tenantMetaConfigValidator.getMetaPropertyValueByMetaKeyAndTenantLogin(
                    TenantConfigConstant.CONFIG_KEY_FETCH_COHORT_AND_PLACEMENT_CITY, user.getTenantLogin(),null);
            if (Objects.equals(syncCohortAndPlacementCity, "1")) {
                user.setCohort(workforceDTO.getCohort());
                user.setPlacementCity(workforceDTO.getWorknodeDTO().getName());
            }
            Set<Long> worknodeIds = user.getWorknodeIds() == null ? new HashSet<>() : user.getWorknodeIds();
            worknodeIds.add(workforceDTO.getWorknodeDTO().getId());
            user.setWorknodeIds(worknodeIds);
            user = userService.save(user);
            UserDTO userDTO = assembler.toDTO(user);
            opportunityApplicantFacade.updateUserFieldsInApplicant(userDTO);
        }
    }


    @Override
    public Long getUsersCount(String tenantLogin) {
        return userService.getUsersCount(tenantLogin);
    }

    @Override
    public Set<MapLocationCoordinateDTO> getUsersByFilterForLocationDistribution(String tenantLogin, Set<String> roleCodes,
                                                                                 Double minLat, Double minLong, Double maxLat,
                                                                                 Double maxLong) {
        String facetField = "currentCityCode";
        UserFilterDTO filterDTO = UserFilterDTO.builder()
                .tenantLogin(tenantLogin)
                .roleCodes(roleCodes)
                .facetFields(Collections.singleton(facetField))
                .minLat(minLat)
                .minLong(minLong)
                .maxLat(maxLat)
                .maxLong(maxLong)
                .size(0)
                .isActive(Boolean.TRUE)
                .build();

        FacetPage<User> facetPage = userService.getUsersWithFilter(filterDTO.getSearchTerm(), filterDTO, null);
        Map<String, Long> facetResult = FacetUtil.getFacetFieldResultByFieldName(facetPage,facetField);
        Set<String> cityCodes = facetResult.keySet();
        List<GlobalRefDataDTO> globalRefDataDTOList = null;
        if(globalRefDataCacheManager != null) {
            globalRefDataDTOList = globalRefDataCacheManager.getValuesInBulk(cityCodes);
        }
        else {
            globalRefDataDTOList = commonsReportUtil.getGlobalRefDataFromCodeInBulk(cityCodes);
        }
        return globalRefDataDTOList.stream()
                        .map(globalRefDataDTO -> MapLocationCoordinateDTO.builder()
                                 .code(globalRefDataDTO.getDataCode())
                                 .label(globalRefDataDTO.getLabel())
                                 .count(facetResult.getOrDefault(globalRefDataDTO.getDataCode(),0L))
                                 .latitude(globalRefDataDTO.getNotes() != null ? Double.parseDouble(globalRefDataDTO.getNotes()) : null)
                                 .longitude(globalRefDataDTO.getAliasId() != null ? Double.parseDouble(globalRefDataDTO.getAliasId()) : null)
                                 .build())
                .collect(Collectors.toSet());
    }

    public Pageable getPageableBasedOnFetchType(Integer page, Integer size, String fetch) {
        String sortBy;
        String direction;
        if (fetch == null) {
            sortBy = "displayName";
            direction = "ASC";
        } else {
            switch (fetch) {
                case Fetch.LATEST:
                    sortBy = "memberSince";
                    direction = "DESC";
                    break;
                default:
                    throw new InvalidInputException("Invalid fetch type");
            }
        }
        return PageRequest.of(page, size, JPAUtility.convertToSort(sortBy, direction));
    }

    public UserFilterDTO createUserFilterDTO(String searchTerm, Set<String> genderCodes, Boolean fetchBookmarkedUsers,
                                             Set<String> currentCityCodes, Set<String> employmentOrganisationCodes,
                                             Set<String> facetFields, String tenantLogin, Integer page, Integer size, Set<String> roleCodes,
                                             Set<String> educationInstituteCodes, Set<String> pathwayCodes, Set<String> cohorts,
                                             Set<String> placementCities, Set<String> currentProfessionalStatusList,
                                             Set<String> currentEmploymentOrganisationCodes, Boolean isActive, String fetchType,
                                             Set<Long> opportunityIds, Set<Long> excludeOpportunityIds, String sortBy,
                                             String direction, Integer ageBaseLimit, Integer ageTopLimit, Date memberSince,
                                             String currentCountryCode, String currentStateCode, String currentCityCode,
                                             Long worknodeId, Set<String> fieldList, Long tenantId, Set<Long> userIdList, Set<String> educationQualificationCodes,
                                             Set<String> educationDegreeCodes, Double minimumTotalYearOfExperience,
                                             Double maximumTotalYearOfExperience, String appContext) {
        return UserFilterDTO.builder()
                .searchTerm(searchTerm)
                .tenantLogin(tenantLogin)
                .genderCodes(genderCodes)
                .currentCountryCode(currentCountryCode)
                .currentStateCode(currentStateCode)
                .currentCityCode(currentCityCode)
                .fetchBookmarkedUsers(fetchBookmarkedUsers)
                .currentCityCodes(currentCityCodes)
                .employmentOrganisationCodes(employmentOrganisationCodes)
                .currentEmploymentOrganisationCodes(currentEmploymentOrganisationCodes)
                .educationInstituteCodes(educationInstituteCodes)
                .pathwayCodes(pathwayCodes)
                .facetFields(facetFields)
                .roleCodes(roleCodes)
                .cohorts(cohorts)
                .placementCities(placementCities)
                .currentProfessionalStatusList(currentProfessionalStatusList)
                .fetchType(fetchType)
                .opportunityIds(opportunityIds)
                .excludeOpportunityIds(excludeOpportunityIds)
                .sortBy(sortBy)
                .direction(direction)
                .ageBaseLimit(ageBaseLimit)
                .ageTopLimit(ageTopLimit)
                .memberSince(memberSince)
                .educationQualificationCodes(educationQualificationCodes)
                .educationDegreeCodes(educationDegreeCodes)
                .minimumTotalYearOfExperience(minimumTotalYearOfExperience)
                .maximumTotalYearOfExperience(maximumTotalYearOfExperience)
                .isActive(isActive == null ? Boolean.TRUE : isActive)
                .worknodeId(worknodeId)
                .fieldList(fieldList)
                .tenantId(tenantId)
                .userIds(userIdList)
                .appContext(appContext)
                .page(page)
                .size(size)
                .build();

    }

    public void updateNonFlatFieldsInUserDTO(UserDTO userDTO) {
        Long userId = userDTO.getUserId();

        Set<Long> worknodeIds  = getWorkNodeIdsAssignedToUser(userId);
        userDTO.setWorknodeIds(worknodeIds);

        Set<String> roleCodes = getRoleCodesForUser(userId, userDTO.getTenantId());
        userDTO.setRoleCodes(roleCodes);

        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigValidator.getByTenantLogin(userDTO.getTenantLogin());
        String syncCohortAndPlacementCity = "0";
        if (tenantMetaConfigDTO != null) {
            syncCohortAndPlacementCity = tenantMetaConfigValidator.getMetaPropertyValueFromTenantMetaConfigDTO(
                    tenantMetaConfigDTO, TenantConfigConstant.CONFIG_KEY_FETCH_COHORT_AND_PLACEMENT_CITY,null);
        }

        if (Objects.equals(syncCohortAndPlacementCity, "1")) {
            appendWorkforceDetails(userDTO, userId);
        }
    }

    @Override
    public void sync(UserDTO userDTO) {
        updateNonFlatFieldsInUserDTO(userDTO);

        User user = assembler.fromDTO(userDTO);
        userDTO = assembler.toDTO(userService.save(user));

        opportunityApplicantFacade.updateUserFieldsInApplicant(userDTO);
    }

    public Set<Long> getWorkNodeIdsAssignedToUser(Long userId) {
        Set<Long> worknodeIds = new HashSet<>();
        try {
            worknodeIds.addAll(Objects.requireNonNull(worknodeClient.getWorkNodeIdsAssignedToUser(userId,appKey).getBody()));
        }
        catch (Exception exception) {
            log.info(exception.getMessage());
        }
        return worknodeIds;
    }

    public void appendWorkforceDetails(UserDTO userDTO, Long userId) {
        Map<String, String> workforceDetailsMap = commonsReportUtil.getWorkforceDetailsForUser(userId);
        if (workforceDetailsMap != null) {
            userDTO.setCohort(workforceDetailsMap.getOrDefault("cohort", null));
            userDTO.setPlacementCity(workforceDetailsMap.getOrDefault("placementCity", null));
        }
    }

    public Set<String> getRoleCodesForUser(Long userId, Long tenantId) {
        return commonsReportUtil.getRoleCodesByUserId(userId, tenantId);
    }


}
