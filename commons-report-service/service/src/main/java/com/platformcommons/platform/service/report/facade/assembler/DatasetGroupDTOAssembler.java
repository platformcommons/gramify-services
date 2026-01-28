package com.platformcommons.platform.service.report.facade.assembler;

import com.platformcommons.platform.service.report.domain.DatasetGroup;
import com.platformcommons.platform.service.report.dto.DatasetGroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
        DatasetDTOAssembler.class,
        TenantInfoDTOAssembler.class,
})
public interface DatasetGroupDTOAssembler {

    DatasetGroup fromDTO(DatasetGroupDTO datasetGroupDTO);

    DatasetGroupDTO toDTO(DatasetGroup datasetGroup);

}
