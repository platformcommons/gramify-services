package com.platformcommons.platform.service.report.facade.assembler;

import com.platformcommons.platform.service.report.domain.DataSource;
import com.platformcommons.platform.service.report.dto.DataSourceDTO;


import org.mapstruct.Mapper;

import javax.xml.crypto.Data;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {
          DatasetDTOAssembler.class,
    })
public interface DataSourceDTOAssembler {

    DataSourceDTO toDTO(DataSource entity);

    DataSource fromDTO(DataSourceDTO dto);

    Set<DataSource> fromDTOs(Set<DataSourceDTO> dataSourceDTOS);

    Set<DataSourceDTO> toDTOs(Set<DataSource> dataSources);
}