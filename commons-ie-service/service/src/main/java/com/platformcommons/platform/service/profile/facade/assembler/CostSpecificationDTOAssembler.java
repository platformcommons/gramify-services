package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.CostSpecification;
import com.platformcommons.platform.service.profile.dto.CostSpecificationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {CostSpecificationLineItemDTOAssembler.class,
                                          SpecificationDTOAssembler.class
})
public interface CostSpecificationDTOAssembler {
    CostSpecificationDTO toDTO(CostSpecification entity);

    CostSpecification fromDTO(CostSpecificationDTO dto);
}
