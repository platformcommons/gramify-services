package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.ExtraAttribute;
import com.platformcommons.platform.service.iam.dto.ExtraAttributesDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {AttributeMetaDTOAssembler.class,ExtraAttributeValueDTOAssembler.class})
public interface ExtraAttributeDTOAssembler {

    ExtraAttributesDTO toDTO(ExtraAttribute extraAttribute);

    ExtraAttribute fromDTO(ExtraAttributesDTO extraAttributeDTO);

    Set<ExtraAttribute> fromDTOs(Set<ExtraAttributesDTO> extraAttributes);

    Set<ExtraAttributesDTO> toDTOs(Set<ExtraAttribute> extraAttributes);
}
