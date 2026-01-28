package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.GenericProductDTO;

import java.util.List;
import java.util.Set;

public interface GenericProductFacade {

    List<GenericProductDTO> findAllGenericProducts();

    GenericProductDTO save(GenericProductDTO dto);

    FacetPageDTO<GenericProductDTO> readGenericProducts(String marketCode, String searchTerm, Set<String> categoryCodes, Set<String> subCategoryCodes,
                                                        Set<String> fields, Integer page, Integer size, String sortBy, String direction);

    void reindex();
}
