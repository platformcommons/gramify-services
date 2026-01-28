package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.search.application.service.UserService;
import com.platformcommons.platform.service.search.application.utility.DateTimeUtil;
import com.platformcommons.platform.service.search.application.utility.PlatformUtil;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.User;
import com.platformcommons.platform.service.search.domain.repo.UserRepository;
import com.platformcommons.platform.service.search.dto.UserFilterDTO;
import com.platformcommons.platform.service.search.facade.cache.validator.TenantMetaConfigValidator;
import com.platformcommons.platform.service.search.facade.client.utility.CommonsReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.RequestMethod;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TenantMetaConfigValidator tenantMetaConfigValidator;

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User getByUserId(Long userId) {
        return repository.findByUserId(userId).orElse(null);
    }

    @Override
    public Boolean existsByUserId(Long userId) {
        return repository.existsById(userId);
    }

    @Override
    @Retryable(value = OptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public User putUpdate(User user) {
        User fetchedUser = getByUserId(user.getUserId());
        fetchedUser.putUpdate(user);
        return repository.save(fetchedUser);
    }

    @Override
    public void delete(Long id) {
        Optional<User> userOptional = repository.findByUserId(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(Boolean.FALSE);
            repository.save(user);
        }
    }

    @Override
    public FacetPage<User> getUsersWithFilter(String searchTerm, UserFilterDTO filterDTO, Pageable pageable) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryUserSearch(filterDTO.getSearchTerm())))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(filterDTO.getFacetFields())
                        .setFacetMinCount(1)
                        .setFacetLimit(-1)
                        .setFacetSort(FacetOptions.FacetSort.INDEX));
        FilterQuery filterQuery = new SimpleFilterQuery();
        addFilterCriterias(filterQuery, filterDTO);
        facetQuery.addFilterQuery(filterQuery);
        addFieldListInFacetQuery(facetQuery,filterDTO.getFieldList());

        if(pageable != null) {
            facetQuery.setPageRequest(pageable);
        }
        else {
            facetQuery.setRows(filterDTO.getSize());
        }

        return solrTemplate.queryForFacetPage(ServiceConstant.USER_CORE,facetQuery, User.class,RequestMethod.POST);
    }



    @Override
    public List<User> getUsersByFetchStrategy( UserFilterDTO filterDTO, Pageable pageable) {
        SimpleQuery query = new SimpleQuery(new SimpleStringCriteria(buildQueryUserSearch(filterDTO.getSearchTerm())));
        FilterQuery filterQuery = new SimpleFilterQuery();
        addFilterCriterias(filterQuery, filterDTO);
        query.addFilterQuery(filterQuery);
        query.setPageRequest(pageable);
        return solrTemplate.queryForPage(ServiceConstant.USER_CORE, query, User.class).getContent();
    }


    @Override
    public List<User> getAllUsersByFilter( UserFilterDTO filterDTO) {
        SimpleQuery query = new SimpleQuery(new SimpleStringCriteria("*:*"));
        FilterQuery filterQuery = new SimpleFilterQuery();
        addFilterCriterias(filterQuery, filterDTO);
        query.addFilterQuery(filterQuery);
        query.setPageRequest(PageRequest.of(0,Integer.MAX_VALUE));
        query.addProjectionOnField("userId");
        return solrTemplate.query(ServiceConstant.USER_CORE, query, User.class,RequestMethod.POST).getContent();
    }

    @Override
    public List<User> getUsersByUserIds(Set<Long> userIds, Set<String> fields) {
        List<User> userList = new ArrayList<>();
        if(userIds != null && !userIds.isEmpty()) {
            SimpleQuery query = new SimpleQuery(new SimpleStringCriteria("*:*"));
            Criteria criteria = Criteria.where("userId").in(userIds);
            query.addCriteria(criteria);
            addFieldListInFacetQuery(query,fields);
            query.setPageRequest(PageRequest.of(0,Integer.MAX_VALUE));
            userList =  solrTemplate.query(ServiceConstant.USER_CORE, query, User.class, RequestMethod.POST).getContent();
        }
        return userList;
    }

    private void addFilterCriterias(FilterQuery filterQuery, UserFilterDTO filterDTO) {
        addCriteriaBasedOnTenantConfigs(filterDTO.getTenantLogin(),filterQuery,filterDTO.getAppContext());
        filterDTO.setIsActive(filterDTO.getIsActive() == null ? Boolean.TRUE : filterDTO.getIsActive());
        Criteria isActiveCriteria = Criteria.where("isActive").is(filterDTO.getIsActive());
        filterQuery.addCriteria(isActiveCriteria);

        if(filterDTO.getTenantLogin() != null) {
            Criteria criteria = Criteria.where("tenantLogin").is(filterDTO.getTenantLogin());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getTenantId() != null) {
            Criteria criteria = Criteria.where("tenantId").is(filterDTO.getTenantId());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getGenderCodes() != null) {
            Criteria criteria = Criteria.where("genderCode").in(filterDTO.getGenderCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCurrentCityCodes()!=null) {
            Criteria criteria = Criteria.where("currentCityCode").in(filterDTO.getCurrentCityCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCurrentCityCode()!=null) {
            Criteria criteria = Criteria.where("currentCityCode").is(filterDTO.getCurrentCityCode());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCurrentStateCode()!=null) {
            Criteria criteria = Criteria.where("currentStateCode").is(filterDTO.getCurrentStateCode());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCurrentCountryCode()!=null) {
            Criteria criteria = Criteria.where("currentCountryCode").is(filterDTO.getCurrentCountryCode());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getEmploymentOrganisationCodes()!=null && !filterDTO.getEmploymentOrganisationCodes().isEmpty()) {
            Criteria criteria = Criteria.where("employmentOrganisationCodes").in(filterDTO.getEmploymentOrganisationCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCurrentEmploymentOrganisationCodes()!=null && !filterDTO.getCurrentEmploymentOrganisationCodes().isEmpty()) {
            Criteria criteria = Criteria.where("currentEmploymentOrganisationCodes").in(filterDTO.getCurrentEmploymentOrganisationCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getRoleCodes()!=null && !filterDTO.getRoleCodes().isEmpty()) {
            Criteria criteria = Criteria.where("roleCodes").in(filterDTO.getRoleCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getEducationInstituteCodes()!=null && !filterDTO.getEducationInstituteCodes().isEmpty()) {
            Criteria criteria = Criteria.where("educationInstituteCodes").in(filterDTO.getEducationInstituteCodes());
            filterQuery.addCriteria(criteria);
        }
        if (filterDTO.getEducationQualificationCodes() != null && !filterDTO.getEducationQualificationCodes().isEmpty()) {
            Criteria criteria = Criteria.where("educationQualificationCodes").in(filterDTO.getEducationQualificationCodes());
            filterQuery.addCriteria(criteria);
        }
        if (filterDTO.getEducationDegreeCodes() != null && !filterDTO.getEducationDegreeCodes().isEmpty()) {
            Criteria criteria = Criteria.where("educationDegreeCodes").in(filterDTO.getEducationDegreeCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getPathwayCodes()!=null && !filterDTO.getPathwayCodes().isEmpty()) {
            Criteria criteria = Criteria.where("pathwayCodes").in(filterDTO.getPathwayCodes());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCohorts()!=null && !filterDTO.getCohorts().isEmpty()) {
            Criteria criteria = Criteria.where("cohort").in(filterDTO.getCohorts());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getPlacementCities()!=null && !filterDTO.getPlacementCities().isEmpty()) {
            Criteria criteria = Criteria.where("placementCity").in(filterDTO.getPlacementCities());
            filterQuery.addCriteria(criteria);
        }
        if (filterDTO.getUserIds() != null){
            Criteria criteria = null;
            if (filterDTO.getUserIds().isEmpty()) {
                criteria = new SimpleStringCriteria("(userId:(\"\"))");

            } else {
                criteria = Criteria.where("userId").in(filterDTO.getUserIds());
            }
            filterQuery.addCriteria(criteria);
        }
        if (filterDTO.getExcludeUserIds() != null && !filterDTO.getExcludeUserIds().isEmpty()) {
            Criteria criteria = Criteria.where("userId").in(filterDTO.getExcludeUserIds()).not();
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getAgeBaseLimit() != null) {
            Date date  = DateTimeUtil.createDateFromAge(filterDTO.getAgeBaseLimit(),"UTC");
            Criteria criteria = Criteria.where("dateOfBirth").lessThanEqual(date);
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getAgeTopLimit() != null) {
            Date date  = DateTimeUtil.createDateFromAge(filterDTO.getAgeTopLimit(),"UTC");
            Criteria criteria = Criteria.where("dateOfBirth").greaterThanEqual(date);
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMemberSince() != null) {
            Criteria criteria = Criteria.where("memberSince").greaterThanEqual(filterDTO.getMemberSince());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMinLat()!=null){
            String query = String.format(" ( currentCityLatLong:[%s,%s TO %s,%s] ) ",
                    filterDTO.getMinLat(),filterDTO.getMinLong(),filterDTO.getMaxLat(),filterDTO.getMaxLong());
            Criteria locationCriteria = new Criteria().expression(query);
            filterQuery.addCriteria(locationCriteria);
        }
        if (filterDTO.getMinimumTotalYearOfExperience() != null && filterDTO.getMaximumTotalYearOfExperience() != null) {
            Criteria criteria = Criteria.where("totalYearsOfExperience")
                    .between(filterDTO.getMinimumTotalYearOfExperience(),
                            filterDTO.getMaximumTotalYearOfExperience());

            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getWorknodeIds() != null) {
            Criteria criteria = null;
            if (filterDTO.getWorknodeIds().isEmpty()) {
                criteria = new SimpleStringCriteria("(worknodeIds:(\"\"))");

            } else {
                criteria = Criteria.where("worknodeIds").in(filterDTO.getWorknodeIds());
            }
            filterQuery.addCriteria(criteria);
        }
    }


    public void addCriteriaBasedOnTenantConfigs(String tenantLogin, FilterQuery filterQuery, String appContext) {
        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigValidator.getByTenantLogin(tenantLogin);

        if (tenantMetaConfigDTO != null && PlatformUtil.isSession()) {
            addCriteriaForOpenToWorkByTenantConfig(filterQuery, tenantMetaConfigDTO,appContext);
            addCriteriaForProfileVisibilityByTenantConfig(filterQuery, tenantMetaConfigDTO,appContext);
        }
    }

    public void addCriteriaForProfileVisibilityByTenantConfig(FilterQuery filterQuery, TenantMetaConfigDTO tenantMetaConfigDTO,
                                                              String appContext) {
        Set<String> roleCodeSuffixSet = tenantMetaConfigValidator.getMetaValueForMultiValuedMetaByConfigDTO(tenantMetaConfigDTO,
                TenantConfigConstant.CONFIG_KEY_ORGANISATION_REPRESENTATIVE_ROLE_CODE_SUFFIX, appContext);

        if (roleCodeSuffixSet != null && !roleCodeSuffixSet.isEmpty()) {
            Boolean userContainsGivenRole = commonsReportUtil.checkIfUserHasGivenRoleSuffixGlobally(PlatformSecurityUtil.getCurrentUserId(),
                    roleCodeSuffixSet);

            Criteria criteria = null;
            if(userContainsGivenRole) {
                criteria = Criteria.where("profileVisibilityCodes").is(
                        ServiceConstant.PROFILE_VISIBILITY_VISIBLE_TO_ORGANISATION_REPRESENTATIVE);
            }
            else {
                criteria = Criteria.where("profileVisibilityCodes").is(
                        ServiceConstant.PROFILE_VISIBILITY_VISIBLE_TO_COMMUNITY_USERS);
            }
            filterQuery.addCriteria(criteria);
        }
    }

    public void addCriteriaForOpenToWorkByTenantConfig(FilterQuery filterQuery, TenantMetaConfigDTO tenantMetaConfigDTO,
                                                       String appContext) {
        Set<String> roleCodes = tenantMetaConfigValidator.getMetaValueForMultiValuedMetaByConfigDTO(tenantMetaConfigDTO,
                TenantConfigConstant.CONFIG_KEY_ROLE_CODES_TO_VIEW_OPEN_TO_WORK_USERS_ONLY, appContext);

        Set<String> currentUserRoles = PlatformSecurityUtil.getCurrentUserContext().getAuthorities();
        if (roleCodes != null && !roleCodes.isEmpty() && !currentUserRoles.isEmpty()
                && currentUserRoles.stream().anyMatch(roleCodes::contains)) {
            Criteria criteria = Criteria.where("currentProfessionalStatus").is(ServiceConstant.CURRENT_PROFESSIONAL_STATUS_OPEN_TO_WORK);
            filterQuery.addCriteria(criteria);
        }
    }


    @Override
    public Long getUsersCount(String tenantLogin) {
        Criteria criteria = null;
        if(tenantLogin != null) {
            criteria = new Criteria("tenantLogin").is(tenantLogin);
        }
        else {
            criteria = new SimpleStringCriteria("*:*");
        }
        Query query = new SimpleQuery(criteria);
        return solrTemplate.count(ServiceConstant.USER_CORE,query);
    }

    public String buildQueryUserSearch(String searchTerm) {
        String queryString = "*:*";
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            List<String> searchTermsArray = Arrays.asList(searchTerm.split("\\s+"));
            StringBuilder queryBuilder = new StringBuilder();

            for(String term : searchTermsArray) {
                if(queryBuilder.length()>0) {
                    queryBuilder.append(" AND ");
                }
                queryBuilder.append("( ")
                        .append("displayName:"+ term)
                        .append(" OR ")
                        .append("login:"+ term)
                        .append(" OR ")
                        .append("genderCode:"+ term)
                        .append(" OR ")
                        .append("primaryEmail:"+ term)
                        .append(" OR ")
                        .append("primaryContactNumber:"+ term)
                        .append(" OR ")
                        .append("employmentDesignations:"+ term)
                        .append(" OR ")
                        .append("employmentOrganisationNames:"+ term)
                        .append(" OR ")
                        .append("educationInstituteNames:"+ term)
                        .append(" OR ")
                        .append("currentAddressLabel:"+ term)
                        .append(" OR ")
                        .append("permanentAddressLabel:"+ term)
                        .append(" OR ")
                        .append("pathwayLabels:"+ term)
                        .append(" OR ")
                        .append("placementCity:"+ term)
                        .append(" )");
            }
            queryString = queryBuilder.toString();
        }
        return queryString;
    }

    public void addFieldListInFacetQuery(Query query, Set<String> fieldList) {
        if(fieldList != null && !fieldList.isEmpty()) {
            for(String field : fieldList) {
                query.addProjectionOnField(Field.of(field));
            }
        }
    }

}
