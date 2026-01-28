package com.platformcommons.platform.service.search.facade.assembler;


import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.dto.TMAChannelProductDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TMAChannelProductDTOAssembler {

    TMAChannelProduct fromDTO(TMAChannelProductDTO tmaChannelProductDTO);

    TMAChannelProductDTO toDTO(TMAChannelProduct tmaChannelProduct);

    Set<TMAChannelProductDTO> toDTOs(Set<TMAChannelProduct> tmaChannelProducts);

    Set<TMAChannelProduct> fromDTOs(Set<TMAChannelProductDTO> tmaChannelProductDTOS);
}
