package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.ProductSKUService;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.ProductSKU;
import com.platformcommons.platform.service.search.dto.AppDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.ProductSKUDTO;
import com.platformcommons.platform.service.search.facade.ProductSKUFacade;
import com.platformcommons.platform.service.search.facade.assembler.ProductSKUDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductSKUFacadeImpl implements ProductSKUFacade {

    @Autowired
    private ProductSKUService productSKUService;

    @Autowired
    private ProductSKUDTOAssembler productSKUDTOAssembler;

    @Override
    public ProductSKUDTO saveProductSKU(ProductSKUDTO productSKUDTO) {
        return productSKUDTOAssembler.toDTO(productSKUService.
                saveProductSKU(productSKUDTOAssembler.fromDTO(productSKUDTO)));
    }

    @Override
    public PageDTO<ProductSKUDTO> searchProductSKU(String keyword,Integer page, Integer size) {
        Page<ProductSKU> result= productSKUService.searchProductSKU(keyword,page,size);
        return new PageDTO<>(result.stream().map(productSKUDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public FacetPageDTO<ProductSKUDTO> getResultWithFacetAndFilter(String searchTerm, Set<String> fields, List<Long> tmaChannelProductIds, Integer page, Integer size, String sortBy, String direction) {
        FacetPage<ProductSKU> results= productSKUService.getProduckSKUWithFilter(searchTerm,fields,tmaChannelProductIds,page,size,sortBy,direction);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            results.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<>(results.stream().map(productSKUDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)), results.hasNext(), facetResult, results.getTotalElements());

    }
}
