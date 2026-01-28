package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Feature;
import com.platformcommons.platform.service.domain.dto.FeatureDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface FeatureDTOAssembler {

    FeatureDTO toDTO(Feature entity);

    Feature fromDTO(FeatureDTO dto);
}