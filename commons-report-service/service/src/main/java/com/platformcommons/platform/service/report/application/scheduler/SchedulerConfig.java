package com.platformcommons.platform.service.report.application.scheduler;

import com.platformcommons.platform.service.report.application.DataSetCronMetaService;
import com.platformcommons.platform.service.report.application.DatasetService;
import com.platformcommons.platform.service.report.application.JobService;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class SchedulerConfig {

    @Autowired
    private DataSetCronMetaService cronMetaService;

    @Autowired
    private JobService  service;
    @PostConstruct
    private void loadTasks(){
        cronMetaService.getActiveCronMetaData()
                .forEach(service::createJob);
    }

}