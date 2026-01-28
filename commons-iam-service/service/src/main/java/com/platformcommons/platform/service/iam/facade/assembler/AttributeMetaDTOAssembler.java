package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.AttributeMeta;
import com.platformcommons.platform.service.iam.dto.AttributeMetaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeMetaDTOAssembler {

    AttributeMetaDTO toDTO(AttributeMeta attributeMeta);

    AttributeMeta fromDTO(AttributeMetaDTO attributeMetaDTO);
}
