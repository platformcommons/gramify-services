package com.platformcommons.platform.service.report.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.dto.DatasetCronMetaDTO;

public interface DatasetCronMetaFacade {
    DatasetCronMetaDTO createCronMetaForDataset(DatasetCronMetaDTO cronMetaDTO, Long dataSetId);

    PageDTO<DatasetCronMetaDTO> getDatasetCronMetas(Long datasetId, Integer page, Integer size);

    DatasetCronMetaDTO patchDatasetCronMeta(DatasetCronMetaDTO cronMetaDTO, Long dataSetId);
}
