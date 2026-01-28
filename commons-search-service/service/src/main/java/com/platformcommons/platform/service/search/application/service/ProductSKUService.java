package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.ProductSKU;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.List;
import java.util.Set;

public interface ProductSKUService {
    ProductSKU saveProductSKU(ProductSKU productSKU);

    Page<ProductSKU> searchProductSKU(String keyword, Integer page, Integer size);

    FacetPage<ProductSKU> getProduckSKUWithFilter(String searchTerm, Set<String> fields, List<Long> tmaChannelProductIds, Integer page, Integer size, String sortBy, String direction);
}
