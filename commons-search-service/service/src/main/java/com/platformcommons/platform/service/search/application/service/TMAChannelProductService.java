package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface TMAChannelProductService {

    TMAChannelProduct save(TMAChannelProduct TMAChannelProduct);

    TMAChannelProduct update(TMAChannelProduct TMAChannelProduct);

    TMAChannelProduct getById(String id);

    Optional<TMAChannelProduct> getByOptional(String id);

    void saveAll(Set<TMAChannelProduct> tmaChannelProductSet);

    Page<TMAChannelProduct> getByGenericProductVarietyId(String marketCode, String channelCode, Long varietyId,Set<Long> excludeVarietyIds,
                                                         Long traderId, Integer page, Integer size, String sortBy, String direction);

    Set<TMAChannelProduct> getByGenericProductVarietyId(Long varietyId, Long marketId, Long channelId);

    void updateTraderDetails(Long id, String traderDisplayName, String iconPic);

    void updateWithProduct(ProductDTO payload);

    Set<TMAChannelProduct> getByVarietyIdWithOutPage(Long marketId, Long channelId, Set<Long> genericProductVarietyIds);

    Page<TMAChannelProduct> getByGenericProductVarietyIdV2(String marketCode, String channelCode, Long varietyId,Set<Long> excludeVarietyIds,
                                                         Long traderId, Integer page, Integer size, String sortBy, String direction,
                                                           Set<String> classificationCodes,String sourceTraderType,Long solutionId,
                                                           Set<String> categoryCodes, Set<String> subCategoryCodes, String searchTerm,
                                                           Set<Long> linkedSolutionResourceIds);

    Set<TMAChannelProduct> getByDefaultSkuId(Long productSKUId);

    void updateGpAndGpv(GenericProductVarietyEventDTO genericProductVarietyEventDTO, ProductDTO productDTO, Long channelId, Long marketId);

    void updateGenericProductDetails(GenericProduct newGenericProduct);

    void updateGenericProductVarietyDetails(GenericProductVariety newGenericProductVariety);

    void reindex();
}
