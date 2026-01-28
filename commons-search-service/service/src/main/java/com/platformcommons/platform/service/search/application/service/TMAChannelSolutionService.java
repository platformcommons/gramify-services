package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface TMAChannelSolutionService {
    TMAChannelSolution addOrUpdate(TMAChannelSolution tmaChannelSolution);

    Page<TMAChannelSolution> readTMAChannelSolutionByTitles(String marketCode, String channelCode, String searchTerm, Long baseSolutionId, Set<String> categoryCodes,
                                                      Set<String> subCategoryCodes, Long traderId, String solutionType, String solutionClass, String tagType, Set<String> tagCodes,
                                                            Integer page, Integer size, String sortBy, String direction);

    Page<TMAChannelSolution> getTMAChannelSolutions(String marketCode, String channelCode, Long traderId, String solutionType, String solutionClass, Integer page, Integer size, String sortBy, String direction);

    void updateTraderDetails(Long id, String traderDisplayName, String iconPic);

    void deleteTmaChannelSolution(Long solutionId, Long traderId, Long channelId, Long marketId);

    TMAChannelSolution save(TMAChannelSolution tmaChannelSolution);

    void updateGenericProductDetails(GenericProduct newGenericProduct);

    void updateGenericProductVarietyDetails(GenericProductVariety newGenericProductVariety);

    void reindex();
}
