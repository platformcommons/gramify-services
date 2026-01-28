package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.changemaker.dto.RuleResultCustomDTO;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantFilterDTO;
import com.platformcommons.platform.service.search.dto.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OpportunityApplicantService {

    Optional<OpportunityApplicant> getOptionalById(Long id);

    void save(OpportunityApplicant opportunityApplicant);

    void saveAll(Set<OpportunityApplicant> opportunityApplicantSet);

    void delete(Long id);

    Set<OpportunityApplicant> getAllByMarketUserId(Long marketUserId);

    SearchHits<OpportunityApplicant> getApplicantsBySearch(OpportunityApplicantFilterDTO filterDTO);

    SearchHits<OpportunityApplicant> getUserIdsOfApplicantsByFilter(Set<Long> opportunityIds, Set<String> stepCodeSet,
                                                                    Set<String> stepStatusCodeSet, Pageable pageable);

    Set<Long> getApplicantIdsByFilter(Date startDateTime, Date endDateTime);

}
