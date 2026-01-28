package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.dto.AppDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;

import java.util.List;
import java.util.Set;

public interface AppFacade {

    PageDTO<AppDTO> readAppByName(String name, Integer page, Integer size);

    FacetPageDTO<App> getResultWithFacet(Set<String> fields, Integer page, Integer size);

    FacetPageDTO<AppDTO> getResultWithFacetAndFilter(String searchTerm,Set<String> fields, List<String> domainCodes,
                                                  List<String> subDomainCodes, List<Long> useCseIds,
                                                  Integer page, Integer size,String sortBy,String direction);

    Set<AppDTO> getByIds(Set<Long> ids);

    AppDTO updateAppSlug(Long appId, String slug);
}
