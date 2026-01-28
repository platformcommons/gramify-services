package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.GenericSolution;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface GenericSolutionService {

    GenericSolution createGenericSolution(GenericSolution genericSolution);

    GenericSolution updateGenericSolution(GenericSolution genericSolution);

    Page<GenericSolution> readGenericSolutionByTitles(String marketId, String searchTerm, Set<String> categoryCodes,
                                                      Set<String> subCategoryCodes, String solutionType, Integer page, Integer size, String sortBy, String direction);

    void syncGenericSolutionVariantCount(Long marketId, Long channelId, Long baseSolutionId);
}
