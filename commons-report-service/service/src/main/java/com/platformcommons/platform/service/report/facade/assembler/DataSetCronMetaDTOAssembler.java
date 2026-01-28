package com.platformcommons.platform.service.report.facade.assembler;

import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import com.platformcommons.platform.service.report.dto.DatasetCronMetaDTO;


public interface DataSetCronMetaDTOAssembler {

    DatasetCronMetaDTO toDTO(DatasetCronMeta cronMeta);
    DatasetCronMeta fromDTO(DatasetCronMetaDTO datasetCronMetaDTO);

}
