package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.UseCase;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

public interface UseCaseFacade {
    FacetPageDTO<UseCase> getResultWithFacet(Set<String> fields, Integer page, Integer size) ;

    FacetPageDTO<UseCase> getResultWithFacetAndFilter(Set<String> fields, List<String> domainCodes,
                                                      List<String> subDomainCodes, List<Long> useCaseIds,
                                                      Integer page, Integer size,String sortBy,String direction);
}
