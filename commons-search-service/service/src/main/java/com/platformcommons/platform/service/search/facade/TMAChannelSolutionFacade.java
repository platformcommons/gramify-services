package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.GenericSolutionDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelSolutionDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;


public interface TMAChannelSolutionFacade {

    PageDTO<TMAChannelSolutionDTO> readTMAChannelSolutionByTitles(String marketCode, String channelCode, String searchTerm,Long baseSolutionId, Set<String> categoryCodes,
                                                                  Set<String> subCategoryCodes, Long traderId, String solutionType, String solutionClass, String tagType, Set<String> tagCodes,
                                                                  Integer page, Integer size, String sortBy, String direction);

    PageDTO<TMAChannelSolutionDTO> getTMAChannelSolutions(String marketCode, String channelCode, Long traderId, String solutionType, String solutionClass, Integer page, Integer size, String sortBy, String direction);

    void updateWithTrader(Long id, String traderDisplayName, String iconPic);

    TMAChannelSolutionDTO save(TMAChannelSolutionDTO tmaChannelSolutionDTO);

    void reindex();
}
