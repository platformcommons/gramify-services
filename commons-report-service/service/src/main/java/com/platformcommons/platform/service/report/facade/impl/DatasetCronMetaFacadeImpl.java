package com.platformcommons.platform.service.report.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.application.DataSetCronMetaService;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import com.platformcommons.platform.service.report.dto.DatasetCronMetaDTO;
import com.platformcommons.platform.service.report.facade.DatasetCronMetaFacade;
import com.platformcommons.platform.service.report.facade.assembler.DataSetCronMetaDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DatasetCronMetaFacadeImpl implements DatasetCronMetaFacade {

    private final DataSetCronMetaDTOAssembler assembler;
    private final DataSetCronMetaService service;

    @Override
    public DatasetCronMetaDTO createCronMetaForDataset(DatasetCronMetaDTO cronMetaDTO, Long dataSetId) {
        return assembler.toDTO(service.createCronMetaForDataset(assembler.fromDTO(cronMetaDTO),dataSetId));
    }

    @Override
    public PageDTO<DatasetCronMetaDTO> getDatasetCronMetas(Long datasetId, Integer page, Integer size) {
        Page<DatasetCronMeta> datasetCronMetas = service.getDatasetCronMetas(datasetId, page, size);
        return new PageDTO<>(datasetCronMetas.map(assembler::toDTO).toSet(),datasetCronMetas.hasNext(),datasetCronMetas.getTotalElements());
    }

    @Override
    public DatasetCronMetaDTO patchDatasetCronMeta(DatasetCronMetaDTO cronMetaDTO, Long dataSetId) {
        return assembler.toDTO(service.patchCronMetaForDataset(assembler.fromDTO(cronMetaDTO),dataSetId));
    }


}
