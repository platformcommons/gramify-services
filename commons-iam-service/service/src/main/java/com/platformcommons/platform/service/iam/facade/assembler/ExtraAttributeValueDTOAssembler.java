package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.ExtraAttributeValue;
import com.platformcommons.platform.service.iam.dto.ExtraAttributeValueDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExtraAttributeValueDTOAssembler {

    ExtraAttributeValue fromDTO(ExtraAttributeValueDTO extraAttributeValueDTO);

    ExtraAttributeValueDTO toDTO(ExtraAttributeValue extraAttributeValue);
}
