package com.platformcommons.platform.service.report.facade.assembler.impl.DataSetCronMetaDTOAssessmbler;

import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import com.platformcommons.platform.service.report.dto.DatasetCronMetaDTO;
import com.platformcommons.platform.service.report.facade.assembler.DataSetCronMetaDTOAssembler;
import com.platformcommons.platform.service.report.facade.assembler.DatasetDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class DataSetCronMetaDTOAssemblerImpl implements DataSetCronMetaDTOAssembler {
    private final DatasetDTOAssembler assembler;
    @Override
    public DatasetCronMetaDTO toDTO(DatasetCronMeta cronMeta) {
        return DatasetCronMetaDTO.builder()
                .id(cronMeta.getId())
                .cronExpression(cronMeta.getCronExpression())
                .name(cronMeta.getName())
                .dataSetCronData(cronMeta.getDataSetCronData())
                .startTime(cronMeta.getStartTime())
                .endTime(cronMeta.getEndTime())
                .timeZone(cronMeta.getTimeZone().toString())
                .dataset(assembler.toDTO(cronMeta.getDataset()))
                .method(cronMeta.getMethod())
                .build();
    }

    @Override
    public DatasetCronMeta fromDTO(DatasetCronMetaDTO cronMeta) {
        return DatasetCronMeta.builder()
                .id(cronMeta.getId())
                .cronExpression(cronMeta.getCronExpression())
                .name(cronMeta.getName())
                .dataSetCronData(cronMeta.getDataSetCronData())
                .startTime(cronMeta.getStartTime())
                .endTime(cronMeta.getEndTime())
                .timeZone(TimeZone.getTimeZone(cronMeta.getTimeZone()))
                .dataset(assembler.fromDTO(cronMeta.getDataset()))
                .method(cronMeta.getMethod())
                .build();
    }
}
