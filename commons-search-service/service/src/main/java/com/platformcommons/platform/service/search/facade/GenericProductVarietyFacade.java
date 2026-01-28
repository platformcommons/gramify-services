package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.GenericProductSearchDTO;
import com.platformcommons.platform.service.search.dto.GenericProductVarietyFilterDTO;
import com.platformcommons.platform.service.search.dto.GenericProductVarietySearchDTO;

import javax.validation.Valid;
import java.util.Set;

public interface GenericProductVarietyFacade {

    GenericProductVarietySearchDTO save(GenericProductVarietySearchDTO genericProductVarietySearchDTO);

    GenericProductVarietySearchDTO getById(Long id);



    FacetPageDTO<GenericProductVarietySearchDTO> filterSearch(String searchTerm, Set<String> facetFields,
                                                              Integer page, Integer size, String sortBy, String direction);

    FacetPageDTO<GenericProductVarietySearchDTO> getFacetWithFilters(GenericProductVarietyFilterDTO genericProductVarietyFilterDTO);

    FacetPageDTO<GenericProductVarietySearchDTO> getFacetWithFilters(String channelCode, String marketCode, String searchTerm, Set<String> categoryCodes, Set<String> subCategoryCodes, Set<String> fields,
                                                                     Long maxSellerCount, Double priceStartRange, Double priceEndRange, Double quantityStartRange, Double quantityEndRange,Set<Long> genericProductVarietyId,
                                                                     Set<Long> genericProductId, Long excludeGenericProductVarietyId, Integer page, Integer size, String sortBy, String direction);

    GenericProductVarietySearchDTO getByGenericProductVarietyId(Long genericProductVarietyId, String channelCode, String marketCode);


    Set<GenericProductSearchDTO> getGenericProducts(String marketCode,String channelCode,String categoryCode,String subCategoryCode,String searchTerm);

    FacetPageDTO<GenericProductVarietySearchDTO> getFacetWithFiltersV2(String channelCode, String marketCode, String searchTerm,
                                                                       Set<String> categoryCodes, Set<String> subCategoryCodes,
                                                                       Set<String> fields, Long maxSellerCount, Double priceStartRange,
                                                                       Double priceEndRange, Double quantityStartRange, Double quantityEndRange,
                                                                       Set<Long> genericProductVarietyId, Set<Long> genericProductId, Long excludeGenericProductVarietyId,
                                                                       Set<String> excludeCategoryCodes,
                                                                       Integer page, Integer size, String sortBy, String direction, String parentClassificationCode,
                                                                       String sourceTraderType, String transactionType, Long solutionId );

    Set<GenericProductSearchDTO> getGenericProductsV2(String marketCode,String channelCode,String categoryCode,String subCategoryCode,
                                                      String searchTermString,String parentClassificationCode, String sourceTraderType, String transactionType);

    Set<GenericProductVarietySearchDTO> getGenericProductVarieties(String marketCode, String channelCode, Set<String> genericProductVarietyCodes);

    void reindex();

    void refreshGenericProductVarieties(Long marketId, Long channelId, Set<Long> genericProductVarietyIds);
}
