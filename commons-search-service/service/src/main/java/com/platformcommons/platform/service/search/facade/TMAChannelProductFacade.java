package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductDTO;

import java.util.Set;

public interface TMAChannelProductFacade {

    TMAChannelProductDTO save(TMAChannelProductDTO tmaChannelProductDTO);

    void saveAll(Set<TMAChannelProductDTO> tmaChannelProductDTOS);

    PageDTO<TMAChannelProductDTO> getByGenericProductVarietyId(String marketCode, String channelCode, Long varietyId,Set<Long> excludeVarietyIds,
                                                               Long traderId, Integer page, Integer size, String sortBy, String direction);

    void updateWithTrader(Long id, String traderDisplayName, String iconPic);

    void updateWithProduct(ProductDTO payload);

    Set<TMAChannelProductDTO> getByVarietyIdWithOutPage(Long marketId, Long channelId, Set<Long> genericProductVarietyIds);

    PageDTO<TMAChannelProductDTO> getByGenericProductVarietyIdV2(String marketCode, String channelCode, Long varietyId,Set<Long> excludeVarietyIds,
                                                               Long traderId, Integer page, Integer size, String sortBy, String direction,
                                                                 String parentClassificationCode, String sourceTraderType, String transactionType,
                                                                 Long solutionId, Set<String> categoryCodes, Set<String> subCategoryCodes,String searchTerm,
                                                                 Set<Long> linkedSolutionResourceIds);

    void reindex();
}
