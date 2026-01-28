package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.search.application.service.GenericProductService;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.GenericProductDTO;
import com.platformcommons.platform.service.search.facade.GenericProductFacade;
import com.platformcommons.platform.service.search.facade.assembler.GenericProductAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Component
public class GenericProductFacadeImpl implements GenericProductFacade {

    @Autowired
    GenericProductService service;

    @Autowired
    GenericProductAssembler assembler;

    @Override
    public List<GenericProductDTO> findAllGenericProducts() {
        List<GenericProduct> result = service.findAllGenericProducts();
        if (result.isEmpty()){
           log.info("No products found");
        }
        return result.stream()
                     .map(assembler::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GenericProductDTO save(GenericProductDTO dto) {
        GenericProductDTO result = assembler.toDTO(service.
                save(assembler.fromDTO(dto)));
        return result;
    }

    @Override
    public FacetPageDTO<GenericProductDTO> readGenericProducts(String marketCode, String searchTerm, Set<String> categoryCodes, Set<String> subCategoryCodes, Set<String> fields, Integer page, Integer size, String sortBy, String direction) {
        FacetPage<GenericProduct> facetPage = service.getFacetWithFilters(marketCode,searchTerm,categoryCodes,subCategoryCodes,fields,page,size,sortBy,direction);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            facetPage.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<>(facetPage.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),
                facetPage.hasNext(), facetResult, facetPage.getTotalElements());
    }

    @Override
    public void reindex() {
        service.reindex();
    }
}
