package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.GenericSolutionService;
import com.platformcommons.platform.service.search.domain.GenericSolution;
import com.platformcommons.platform.service.search.dto.GenericSolutionDTO;
import com.platformcommons.platform.service.search.facade.GenericSolutionFacade;
import com.platformcommons.platform.service.search.facade.assembler.GenericSolutionDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class GenericSolutionFacadeImpl implements GenericSolutionFacade {

    @Autowired
    private GenericSolutionService genericSolutionService;

    @Autowired
    private GenericSolutionDTOAssembler genericSolutionDTOAssembler;

    @Override
    public PageDTO<GenericSolutionDTO> readGenericSolutionByTitles(String marketId, String searchTerm, Set<String> categoryCodes,
                                                                   Set<String> subCategoryCodes, String solutionType, Integer page, Integer size, String sortBy, String direction) {
        Page<GenericSolution> genericSolutions= genericSolutionService.readGenericSolutionByTitles(marketId,searchTerm,categoryCodes,subCategoryCodes,solutionType,page,size,sortBy,direction);
        return new PageDTO<>(genericSolutions.stream().map(genericSolutionDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),genericSolutions.hasNext(),genericSolutions.getTotalElements());

    }

    @Override
    public void syncGenericSolutionVariantCount(Long marketId, Long channelId, Long baseSolutionId) {
        genericSolutionService.syncGenericSolutionVariantCount(marketId,channelId,baseSolutionId);
    }
}
