package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.dto.GenericProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenericProductAssembler {

    GenericProductDTO toDTO(GenericProduct genericProduct);

    GenericProduct fromDTO(GenericProductDTO dto);
}