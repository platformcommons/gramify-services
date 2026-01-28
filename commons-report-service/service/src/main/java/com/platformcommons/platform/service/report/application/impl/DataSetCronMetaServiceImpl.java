package com.platformcommons.platform.service.report.application.impl;

import com.netflix.discovery.converters.Auto;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.report.application.DataSetCronMetaService;
import com.platformcommons.platform.service.report.application.DatasetService;
import com.platformcommons.platform.service.report.application.JobService;
import com.platformcommons.platform.service.report.domain.Dataset;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import com.platformcommons.platform.service.report.domain.repo.DatasetCronMetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DataSetCronMetaServiceImpl implements DataSetCronMetaService {

    @Autowired
    private  DatasetCronMetaRepository datasetCronMetaRepository;
    private JobService jobService;
    private DatasetService datasetService;

    @Override
    public DatasetCronMeta createCronMetaForDataset(DatasetCronMeta datasetCronMeta, Long dataSetId) {
        datasetCronMeta.init();
        Dataset dataset = datasetService.getById(dataSetId);
        datasetCronMeta.setDataset(dataset);
        if(!dataset.getIsCronEnabled()){
            throw new IllegalStateException("Dataset is cron enabled");
        }
        DatasetCronMeta cronMeta = datasetCronMetaRepository.save(datasetCronMeta);
        jobService.createJob(cronMeta);
        datasetCronMetaRepository.save(cronMeta);
        return cronMeta;
    }


    @Override
    public Page<DatasetCronMeta> getDatasetCronMetas(Long datasetId, Integer page, Integer size) {
        return datasetCronMetaRepository.findByDataset(datasetId, PageRequest.of(page,size));
    }

    @Override
    public void removeSchedulerForDataset(Dataset dataset) {
        dataset.getDatasetCronMetas().forEach(jobService::removeJob);
    }

    @Override
    public DatasetCronMeta getDatasetCronMetasById(Long datasetCronMetaId) {
        return datasetCronMetaRepository.findById(datasetCronMetaId)
                .orElseThrow(() -> new NotFoundException("DatasetCronMeta not found"));
    }

    @Override
    public List<DatasetCronMeta> getActiveCronMetaData() {
        return datasetCronMetaRepository.getActiveCronMetaData(true);
    }

    @Override
    public DatasetCronMeta patchCronMetaForDataset(DatasetCronMeta datasetCronMeta, Long dataSetId) {
        DatasetCronMeta cronMeta = getDatasetCronMetasById(datasetCronMeta.getId());
        Dataset dataset = dataSetId==null?null:datasetService.getById(dataSetId);
        jobService.patchJobData(cronMeta,datasetCronMeta);
        cronMeta.patch(datasetCronMeta,dataset);
        DatasetCronMeta updatedCronMeta = datasetCronMetaRepository.save(cronMeta);
        jobService.removeJob(cronMeta);
        if(updatedCronMeta.getDataset().getIsCronEnabled()) jobService.createJob(cronMeta);
        return cronMeta;
    }

    @Override
    public void scheduleCronMeta(DatasetCronMeta cronMeta) {
        jobService.createJob(cronMeta);
    }


    @Autowired
    public void setDatasetService(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }
}
