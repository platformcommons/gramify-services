package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.GenericSolutionDTO;

import java.util.Set;


public interface GenericSolutionFacade {

    PageDTO<GenericSolutionDTO> readGenericSolutionByTitles(String marketId, String searchTerm, Set<String> categoryCodes,
                                                            Set<String> subCategoryCodes, String solutionType, Integer page, Integer size, String sortBy, String direction);

    void syncGenericSolutionVariantCount(Long marketId, Long channelId, Long baseSolutionId);
}
