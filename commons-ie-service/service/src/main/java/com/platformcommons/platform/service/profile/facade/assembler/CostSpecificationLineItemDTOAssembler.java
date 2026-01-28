package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.CostSpecification;
import com.platformcommons.platform.service.profile.domain.CostSpecificationLineItem;
import com.platformcommons.platform.service.profile.dto.CostSpecificationDTO;
import com.platformcommons.platform.service.profile.dto.CostSpecificationLineItemDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring",uses = {
})
public interface CostSpecificationLineItemDTOAssembler {
    CostSpecificationLineItemDTO toDTO(CostSpecificationLineItem entity);

    CostSpecificationLineItem fromDTO(CostSpecificationLineItemDTO dto);

    Set<CostSpecificationLineItemDTO> toDTOs(Set<CostSpecificationLineItem> entitySet);

    Set<CostSpecificationLineItem> fromDTOs(Set<CostSpecificationLineItemDTO> dtoSet);

}
