package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.market.dto.UpdateTmaChannelProductGpGpvRequestDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.List;
import java.util.Set;

public interface TMAChannelProductSKUService {

    TMAChannelProductSKU save(TMAChannelProductSKU tmaChannelProductSKU);

    Set<TMAChannelProductSKU> saveAll(Set<TMAChannelProductSKU> tmaChannelProductSKUSet);

    FacetPage<TMAChannelProductSKU> getTMAChannelProductSKUWithFilter(String searchTerm, String groupByField,String pivotFieldKey,  String pivotFieldValue,
                                                            List<String> projectionFields, Integer page, Integer size, String sortBy, String direction);

    void updateTraderDetails(Long id, String traderDisplayName, String iconPic);

    void updateWithProduct(ProductDTO payload);

    Set<TMAChannelProductSKU> getAllTMAChannelProductSKUByProductIds(Long productId,Long marketId, Long channelId);

    FacetPage<TMAChannelProductSKU> getPublishedSKUs(String channelCode, String marketCode, String searchTerm,
                                                Set<String> categoryCodes, Set<String> subCategoryCodes, Set<String> fields,
                                                Long genericProductId, Long genericProductVarietyId, Long traderId, Integer page, Integer size, String sortBy,
                                                     String direction, Long solutionId, String productSubType, Set<String> skuFactorCodes,
                                                     Set<String> servingAreaCodes, Boolean isTypeAsOffering, String tagType,Set<String> tagCodes);

    void deleteTmaChannelProductSKU(Long productSkuId, Long productId, Long traderId, Long channelId, Long marketId);

    void updateByTmaChannelProductId(TMAChannelProductSKU tmaChannelProductSKU);

    void updateGpAndGpv(UpdateTmaChannelProductGpGpvRequestDTO updateTmaChannelProductGpGpvRequestDTO);

    Set<TMAChannelProductSKU> getTMAChannelProductSkusById( Set<Long> tmaChannelProductSkuIds);

    void updateGenericProductDetails(GenericProduct newGenericProduct);

    void updateGenericProductVarietyDetails(GenericProductVariety newGenericProductVariety );

    void reindex();
    List<TMAChannelProductSKU> fetchBySkuIdsAndChannelMarket(Set<String> ids, Long marketId, Long channelId);

    void saveAllByBeans(List<TMAChannelProductSKU> toUpdate);

    Set<TMAChannelProductSKU> getTMAChannelProductSkusByProductSkuIds(Set<Long> productSKUIds, Long marketId, Long channelId);
}
