package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.changemaker.dto.TenantDataSyncDTO;
import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.dto.OpportunityFilterDTO;
import com.platformcommons.platform.service.search.dto.OpportunityStakeHolderCustomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.Set;

public interface OpportunityService {

    Opportunity getById(Long id);

    Opportunity save(Opportunity opportunity);

    void delete(Long id);

    void putUpdate(Opportunity opportunity);

    FacetPage<Opportunity> getOpportunitiesByFilterForAdmin(String searchTerm, OpportunityFilterDTO filterDTO, Pageable pageable);

    void addOpportunityStakeHolderToOpportunities(OpportunityStakeHolderCustomDTO stakeHolderCustomDTO);

    void deleteOpportunityStakeHolderFromOpportunity(OpportunityStakeHolderCustomDTO stakeHolderCustomDTO);

    Set<Long> getPromotedOpportunityIds(OpportunityFilterDTO filterDTO, Pageable pageable);

    Page<Opportunity> getOpportunitiesByPaginationForChangemaker(OpportunityFilterDTO filterDTO, Pageable pageable);

    FacetPage<Opportunity> getOpportunitiesForChangeMakerWithFilter(String searchTerm, OpportunityFilterDTO filterDTO, Pageable pageable);

    Page<Opportunity> getOpportunitiesCreatedByUserId(OpportunityFilterDTO filterDTO, Pageable pageable, Long userId);

    Long getCreatedByUserId(Long opportunityId);

    void updateOpportunityTenantDetails(TenantDataSyncDTO tenantDataSyncDTO);
}
