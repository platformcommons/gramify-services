package com.platformcommons.platform.service.report.facade.assembler;

import com.platformcommons.platform.service.report.domain.Dataset;
import com.platformcommons.platform.service.report.dto.DatasetDTO;


import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring",uses = {

    })
public interface DatasetDTOAssembler {

    DatasetDTO toDTO(Dataset entity);

    Dataset fromDTO(DatasetDTO dto);

    Set<Dataset> fromDTOs(Set<DatasetDTO> datasetDTOS);

    Set<DatasetDTO> toDTOs(Set<Dataset> datasets);
}