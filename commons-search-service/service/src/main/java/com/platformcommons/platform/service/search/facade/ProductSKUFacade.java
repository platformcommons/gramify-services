package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.ProductSKUDTO;

import java.util.List;
import java.util.Set;

public interface ProductSKUFacade {
    ProductSKUDTO saveProductSKU(ProductSKUDTO productSKUDTO);

    PageDTO<ProductSKUDTO> searchProductSKU(String keyword,Integer page,Integer size);

    FacetPageDTO<ProductSKUDTO> getResultWithFacetAndFilter(String searchTerm, Set<String> fields, List<Long> tmaChannelProductIds, Integer page, Integer size, String sortBy, String direction);
}
