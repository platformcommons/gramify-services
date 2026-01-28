package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.UseCase;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.List;
import java.util.Set;

public interface UseCaseService {

    UseCase createUseCase(UseCase useCase);

    FacetPage<UseCase> getUseCase(Set<String> fields, Integer page, Integer size);

    UseCase updateUseCase(UseCase useCase);

    FacetPage<UseCase> getUseCaseWithFilter(Set<String> fields, Integer page, Integer size, List<String> domainCodes,
                                        List<String> subDomainCodes, List<Long> userCaseIds,
                                        String sortBy,String direction);
}
