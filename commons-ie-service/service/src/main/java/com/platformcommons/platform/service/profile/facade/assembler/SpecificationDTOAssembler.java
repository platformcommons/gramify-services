package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.CostSpecificationLineItem;
import com.platformcommons.platform.service.profile.domain.Specification;
import com.platformcommons.platform.service.profile.dto.CostSpecificationLineItemDTO;
import com.platformcommons.platform.service.profile.dto.SpecificationDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring",uses = {
})
public interface SpecificationDTOAssembler {

    SpecificationDTO toDTO(Specification entity);

    Specification fromDTO(SpecificationDTO dto);

    Set<SpecificationDTO> toDTOs(Set<Specification> entitySet);

    Set<Specification> fromDTOs(Set<SpecificationDTO> dtoSet);
}
