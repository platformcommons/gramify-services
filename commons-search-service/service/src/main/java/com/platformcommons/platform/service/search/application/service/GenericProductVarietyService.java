package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.dto.GPVSummaryDTO;
import com.platformcommons.platform.service.search.dto.GenericProductSearchDTO;
import com.platformcommons.platform.service.search.dto.GenericProductVarietyFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.Optional;
import java.util.Set;

public interface GenericProductVarietyService {

    GenericProductVariety save(GenericProductVariety genericProductVariety);

    GenericProductVariety update(GenericProductVariety genericProductVariety);

    GenericProductVariety getById(Long id);

    Optional<GenericProductVariety> getByIdOptional(String id);

    FacetPage<GenericProductVariety> filterSearch(String searchTerm, Set<String> facetField,Integer page, Integer size, String sortBy, String direction);

    FacetPage<GenericProductVariety> getFacetWithFilters(GenericProductVarietyFilterDTO genericProductVarietyFilterDTO);

    GenericProductVariety getByGenericProductVarietyId(Long genericProductVarietyId, String channelCode, String marketCode);

    FacetPage<GenericProductVariety> getGenericProducts(String marketCode,String channelCode,String categoryCode,String subCategoryCode,String searchTerm);

    FacetPage<GenericProductVariety> getFacetWithFiltersV2(GenericProductVarietyFilterDTO genericProductVarietyFilterDTO,Set<String> classificationCodes, String sourceTraderType);

    FacetPage<GenericProductVariety> getGenericProductsV2(String marketCode,String channelCode,String categoryCode,String subCategoryCode,String searchTerm,Set<String> classificationCodes, String sourceTraderType);

    Set<GenericProductVariety> getByMarketIdAndGenericProductVarietyId(Long marketId,Long id);

    Set<GenericProductVariety> getGenericProductVarieties(String marketCode, String channelCode, Set<String> genericProductVarietyCodes);

    void saveAll(Set<GenericProductVariety> genericProductVarietySet);

    void createOrUpdate(GenericProductVarietyEventDTO genericProductVarietyEventDTO, Long channelId, String channelCode, String channelName, Long marketId, String marketCode, String marketName);

    void updateGenericProductDetails(GenericProduct newGenericProduct);

    void reindex();

    void updateSolrWithSummary(Long marketId, Long channelId, Long gpvId, GPVSummaryDTO summary);
}
