package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO;
import org.springframework.data.solr.core.query.result.FacetPage;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TMAChannelProductSKUFacade {

    TMAChannelProductSKUDTO save(TMAChannelProductSKUDTO tmaChannelProductSKUDTO);


    void updateWithTrader(Long id, String traderDisplayName, String iconPic);

    void updateWithProduct(ProductDTO payload);


    FacetPageDTO<TMAChannelProductSKUDTO> getPublishedSKUs(String channelCode, String marketCode,
                                                      String searchTerm, Set<String> categoryCodes,
                                                      Set<String> subCategoryCodes, Set<String> fields,
                                                      Long genericProductId, Long genericProductVarietyId,  Long traderId,Integer page, Integer size,
                                                      String sortBy, String direction, Long solutionId, String productSubType,
                                                           Set<String> skuFactorCodes, String locationCode, Boolean isTypeAsOffering,
                                                           String tagType,Set<String> tagCodes);

    Map<Long,TMAChannelProductSKUDTO> getTMAChannelProductSkusById(Set<Long> tmaChannelProductSkuIds);

    void reindex();

    void refreshTmaChannelProductSKUS(Long marketId, Long channelId, Set<Long> productSkuIds);

    Map<Long, TMAChannelProductSKUDTO> getTMAChannelProductSkusByProductSkuIds(Set<Long> productSKUIds, Long marketId, Long channelId);
}
