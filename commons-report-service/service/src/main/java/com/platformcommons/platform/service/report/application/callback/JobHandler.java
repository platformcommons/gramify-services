package com.platformcommons.platform.service.report.application.callback;

import com.platformcommons.platform.service.report.domain.DatasetCronMeta;

public interface JobHandler {

    Runnable buildJob(DatasetCronMeta cronDataId);

    void patchJobData(DatasetCronMeta dbData, DatasetCronMeta cronData);
}
