package com.platformcommons.platform.service.report.application;

import com.platformcommons.platform.service.report.domain.DatasetCronMeta;

public interface JobService {
    void createJob(DatasetCronMeta cronMeta);

    void removeJob(DatasetCronMeta cronMeta);

    void patchJobData(DatasetCronMeta dbData, DatasetCronMeta cronData);
}
