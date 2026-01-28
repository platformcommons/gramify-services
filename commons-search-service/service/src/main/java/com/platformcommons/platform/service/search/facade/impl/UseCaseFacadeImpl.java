package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.search.application.service.UseCaseService;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.UseCase;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.facade.UseCaseFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class UseCaseFacadeImpl implements UseCaseFacade {

    @Autowired
    UseCaseService useCaseService;

    @Override
    public FacetPageDTO<UseCase> getResultWithFacet(Set<String> fields, Integer page, Integer size) {
        FacetPage<UseCase> results= useCaseService.getUseCase(fields,page,size);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            results.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<UseCase>(results.get().collect(Collectors.toCollection(LinkedHashSet::new)), results.hasNext(),facetResult);
    }

    @Override
    public FacetPageDTO<UseCase> getResultWithFacetAndFilter(Set<String> fields, List<String> domainCodes,
                                                             List<String> subDomainCodes, List<Long> useCaseIds,
                                                             Integer page, Integer size,String sortBy,String direction){
        FacetPage<UseCase> results= useCaseService.getUseCaseWithFilter(fields,page,size,domainCodes,subDomainCodes,useCaseIds,sortBy,direction);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            results.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<UseCase>(results.get().collect(Collectors.toCollection(LinkedHashSet::new)), results.hasNext(),facetResult, results.getTotalElements());

    }
}
