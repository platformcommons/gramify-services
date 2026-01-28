package com.platformcommons.platform.service.report.application.callback.impl;

import com.platformcommons.platform.service.report.application.scheduler.DatasetEmailJobExecutor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class EmailJob implements Runnable {

    private final Long dataSetCronMeta;

    private final DatasetEmailJobExecutor jobExecutor;

    @Override
    public void run() {
        jobExecutor.executeCronJob(dataSetCronMeta);
    }

}
