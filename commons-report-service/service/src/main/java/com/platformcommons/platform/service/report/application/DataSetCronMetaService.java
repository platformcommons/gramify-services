package com.platformcommons.platform.service.report.application;

import com.platformcommons.platform.service.report.domain.Dataset;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataSetCronMetaService {
    DatasetCronMeta createCronMetaForDataset(DatasetCronMeta datasetCronMeta, Long dataSetId);

    Page<DatasetCronMeta> getDatasetCronMetas(Long datasetId, Integer page, Integer size);

    void removeSchedulerForDataset(Dataset id);

    DatasetCronMeta getDatasetCronMetasById(Long datasetCronMetaId);

    List<DatasetCronMeta> getActiveCronMetaData();

    DatasetCronMeta patchCronMetaForDataset(DatasetCronMeta datasetCronMeta, Long dataSetId);

    void scheduleCronMeta(DatasetCronMeta cronMeta);
}
