package com.platformcommons.platform.service.search.facade.assembler;


import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.dto.GenericProductVarietySearchDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO;
import org.mapstruct.Mapper;

import java.util.Set;

//@Mapper(componentModel = "spring")
public interface TMAChannelProductSKUDTOAssembler {

    TMAChannelProductSKU fromDTO(TMAChannelProductSKUDTO tmaChannelProductSKUDTO);

    TMAChannelProductSKUDTO toDTO(TMAChannelProductSKU tmaChannelProductSKU);

    Set<TMAChannelProductSKUDTO> toDTOs(Set<TMAChannelProductSKU> tmaChannelProductSKUS);
}
