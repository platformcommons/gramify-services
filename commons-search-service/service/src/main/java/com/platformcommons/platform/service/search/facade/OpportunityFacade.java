package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.OpportunityDTO;

import java.util.Date;
import java.util.Set;

public interface OpportunityFacade {

    FacetPageDTO<OpportunityDTO> getOpportunitiesByFilterForAdmin(String searchTerm, String fetch, Set<String> causeCodes, Set<String> locationCodes,
                                                                  Set<String> locationTypes, Set<String> skillCodes, Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                                  String marketUUID, Set<String> jobTypes, Double minimumCompensation, Double maximumCompensation,
                                                                  Set<String> workPlaceTypes, Long minimumExperienceDays, Long maximumExperienceDays,
                                                                  String cityCode, String stateCode, String countryCode, Set<String> roleTypes,
                                                                  Set<String> pathways, Set<String> sectors, Set<String> cityCodes, Set<String> organisationCodes,
                                                                  String datePostedCode, String status, Integer page, Integer size,
                                                                  Set<String> facetFields, Set<String> organisationTypes,
                                                                  String marketPlaceVisibility, Boolean myOpportunities,
                                                                  Boolean fetchBasedOnRole, Boolean restrictApplication, String opportunityTimeline,
                                                                  Set<String> opportunityCategories, Set<String> opportunitySubCategories, Set<String> tags, Boolean isActive, Long worknodeId,
                                                                  String tenantLogin, Set<String> targetGroups, Boolean fetchPromotedOpportunities,
                                                                  Set<String> educationQualificationCodes, Set<String> educationDegreeCodes);



    Set<OpportunityDTO> getRecommendedOpportunities(Set<String> opportunityTypes, Set<String> opportunitySubTypes, String marketUUID,
                                                    Boolean fetchBasedOnRole, String opportunityTimeline,
                                                    Set<String> fetchSet, Set<String> opportunityTimelineBasedOnScheduledDate,
                                                    Set<String> opportunityTimelineBasedOnApplicationDate);

    Set<OpportunityDTO> getUserSuggestedCustomOpportunities(Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                            String marketUUID, Boolean fetchBasedOnRole,
                                                            String opportunityTimeline, Integer size,
                                                            Set<String> opportunityTimelineBasedOnApplicationDate,
                                                            Set<String> opportunityTimelineBasedOnScheduledDate);

    FacetPageDTO<OpportunityDTO> getOpportunitiesByFilterForChangeMaker(String searchTerm, String addressSearchTerm, String fetch, Set<String> causeCodes, Set<String> locationCodes,
                                                                        Set<String> locationTypes, Set<String> skillCodes, Set<String> opportunityTypes, Set<String> opportunitySubTypes,
                                                                        String marketUUID, Set<String> jobTypes, Double minimumCompensation,
                                                                        Double maximumCompensation, Set<String> workPlaceTypes,
                                                                        Long minimumExperienceDays, Long maximumExperienceDays, String cityCode,
                                                                        String stateCode, String countryCode, Set<String> roleTypes,
                                                                        Set<String> pathways, Set<String> sectors, Set<String> cityCodes,
                                                                        Set<String> organisationCodes, String datePostedCode, Integer page,
                                                                        Integer size, Set<String> organisationTypes,
                                                                        Boolean fetchBasedOnRole, Boolean restrictApplication, String opportunityTimeline,
                                                                        Set<String> opportunityCategories, Set<String> opportunitySubCategories, Set<String> tags, Set<String> facetFields,
                                                                        Set<String> userProgramCodes, String tenantLogin, Date scheduledStartDate,
                                                                        Date scheduledEndDate, Set<String> opportunityTimelineBasedOnScheduledDate,
                                                                        Set<String> opportunityTimelineBasedOnApplicationDate,
                                                                        Set<String> targetGroups, Boolean fetchPromotedOpportunities,
                                                                        Set<String> educationQualificationCodes, Set<String> educationDegreeCodes,
                                                                        Set<String> tenantLogins,Boolean loggedInUserOpportunity);

    PageDTO<OpportunityDTO> getOpportunitiesCreatedByUserId(Long userId, Set<String> opportunityTypes, Set<String> opportunitySubTypes,String fetch,
                                                            String marketUUID, Integer page, Integer size);

    Long getCreatedByUserId(Long opportunityId);
}
