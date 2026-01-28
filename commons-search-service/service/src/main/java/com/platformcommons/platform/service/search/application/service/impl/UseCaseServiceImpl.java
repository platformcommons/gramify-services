package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.service.UseCaseService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.UseCase;
import com.platformcommons.platform.service.search.domain.repo.UseCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UseCaseServiceImpl implements UseCaseService {

    @Autowired
    private UseCaseRepository useCaseRepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public UseCase createUseCase(UseCase useCase) {
        return useCaseRepository.save(useCase);
    }

    @Override
    public FacetPage<UseCase> getUseCase(Set<String> fields, Integer page, Integer size) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria("*:*"))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(fields)
                        .setFacetMinCount(1));
        facetQuery.setPageRequest(PageRequest.of(page,size));
        FacetPage<UseCase> results =solrTemplate.queryForFacetPage("use_case",facetQuery, UseCase.class);

        return results;
    }

    @Override
    public UseCase updateUseCase(UseCase useCase) {
        useCaseRepository.findById(useCase.getId()).orElseThrow(()-> new NotFoundException(String.format("UseCase with  %d  not found",useCase.getId())));
        return useCaseRepository.save(useCase);
    }

    @Override
    public FacetPage<UseCase> getUseCaseWithFilter(Set<String> fields, Integer page, Integer size,
                                               List<String> domainCodes, List<String> subDomainCodes,
                                               List<Long> userCaseIds,String sortBy,String direction){
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria("*:*"))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(fields)
                        .setFacetMinCount(1));
        facetQuery.setPageRequest(PageRequest.of(page,size, JPAUtility.convertToSort(sortBy!=null?sortBy:"sequence",direction)));
        FilterQuery filterQuery = new SimpleFilterQuery();
        if(domainCodes!=null && !domainCodes.isEmpty()) {
            Criteria criteria = new Criteria("domainCodes").in(domainCodes);
            filterQuery.addCriteria(criteria);
        }
        if(subDomainCodes!=null && !subDomainCodes.isEmpty()){
            Criteria criteria = new Criteria("subDomainCodes").in(subDomainCodes);
            filterQuery.addCriteria(criteria);
        }
        if(userCaseIds!=null && !userCaseIds.isEmpty()){
            Criteria criteria = new Criteria("useCaseIds").in(userCaseIds);
            filterQuery.addCriteria(criteria);
        }
        facetQuery.addFilterQuery(filterQuery);
        FacetPage<UseCase> results =solrTemplate.queryForFacetPage("use_case",facetQuery, UseCase.class);
        return results;
    }
}
