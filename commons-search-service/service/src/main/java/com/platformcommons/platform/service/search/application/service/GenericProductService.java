package com.platformcommons.platform.service.search.application.service;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.List;
import java.util.Set;

public interface GenericProductService {
    
    List<GenericProduct> findAllGenericProducts();

    GenericProduct save(GenericProduct body);

    Set<GenericProduct> getByMarketIdAndGenericProductId(Long marketId, Long id);

    void saveAll(Set<GenericProduct> fetchedGenericProducts);

    FacetPage<GenericProduct> getFacetWithFilters(String marketId, String searchTerm, Set<String> categoryCodes, Set<String> subCategoryCodes,
                                                  Set<String> fields, Integer page, Integer size, String sortBy, String direction);

    void reindex();
}
