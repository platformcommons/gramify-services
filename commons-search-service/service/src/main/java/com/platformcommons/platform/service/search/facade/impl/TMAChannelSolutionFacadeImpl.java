package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.TMAChannelSolutionService;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import com.platformcommons.platform.service.search.dto.TMAChannelSolutionDTO;
import com.platformcommons.platform.service.search.facade.TMAChannelSolutionFacade;
import com.platformcommons.platform.service.search.facade.assembler.TMAChannelSolutionDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class TMAChannelSolutionFacadeImpl implements TMAChannelSolutionFacade {

    @Autowired
    private TMAChannelSolutionService tmaChannelSolutionService;

    @Autowired
    private TMAChannelSolutionDTOAssembler tmaChannelSolutionDTOAssembler;

    @Override
    public PageDTO<TMAChannelSolutionDTO> readTMAChannelSolutionByTitles(String marketCode, String channelCode, String searchTerm,Long baseSolutionId, Set<String> categoryCodes,
                                                                         Set<String> subCategoryCodes, Long traderId, String solutionType, String solutionClass, String tagType, Set<String> tagCodes,
                                                                         Integer page, Integer size, String sortBy, String direction) {
        Page<TMAChannelSolution> tmaChannelSolutions= tmaChannelSolutionService.readTMAChannelSolutionByTitles(marketCode,channelCode,searchTerm,baseSolutionId,categoryCodes,subCategoryCodes,traderId,solutionType,solutionClass,tagType,tagCodes,page,size,sortBy,direction);
        return new PageDTO<>(tmaChannelSolutions.stream().map(tmaChannelSolutionDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),tmaChannelSolutions.hasNext(),tmaChannelSolutions.getTotalElements());

    }

    @Override
    public PageDTO<TMAChannelSolutionDTO> getTMAChannelSolutions(String marketCode, String channelCode, Long traderId, String solutionType, String solutionClass, Integer page, Integer size, String sortBy, String direction) {
        Page<TMAChannelSolution> tmaChannelSolutions= tmaChannelSolutionService.getTMAChannelSolutions(marketCode,channelCode,traderId,solutionType,solutionClass,page,size,sortBy,direction);
        return new PageDTO<>(tmaChannelSolutions.stream().map(tmaChannelSolutionDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),tmaChannelSolutions.hasNext(),tmaChannelSolutions.getTotalElements());

    }

    @Override
    public void updateWithTrader(Long id, String traderDisplayName, String iconPic) {
        tmaChannelSolutionService.updateTraderDetails(id,traderDisplayName,iconPic);
    }

    @Override
    public TMAChannelSolutionDTO save(TMAChannelSolutionDTO tmaChannelSolutionDTO){
        return tmaChannelSolutionDTOAssembler.toDTO(tmaChannelSolutionService.save(tmaChannelSolutionDTOAssembler.fromDTO(tmaChannelSolutionDTO)));
    }

    @Override
    public void reindex() {
        tmaChannelSolutionService.reindex();
    }
}
