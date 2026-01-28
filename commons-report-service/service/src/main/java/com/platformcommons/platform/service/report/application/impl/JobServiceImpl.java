package com.platformcommons.platform.service.report.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.report.application.JobService;
import com.platformcommons.platform.service.report.application.callback.JobHandler;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class JobServiceImpl implements JobService{


    private final Map<String, JobHandler> jobHandlerMap;
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final TaskScheduler scheduler;

    public JobServiceImpl(List<JobHandler> callbacks, TaskScheduler scheduler) {
        this.jobHandlerMap = new ConcurrentHashMap<>();
        callbacks.forEach(callback -> jobHandlerMap.put(callback.getClass().getSimpleName(), callback));
        this.scheduler = scheduler;
    }

    @Override
    public void createJob(DatasetCronMeta cronMeta) {
        JobHandler handler = getJobHandler(cronMeta.getMethod());
        Runnable job = handler.buildJob(cronMeta);
        ScheduledFuture<?> task = scheduler.schedule(job, new CronTrigger(cronMeta.getCronExpression()));
        scheduledTasks.put(cronMeta.getId(),task);
    }

    @Override
    public void removeJob(DatasetCronMeta cronMeta) {
        Long id = cronMeta.getId();
        if(scheduledTasks.containsKey(id)){
            scheduledTasks.get(id).cancel(false);
            scheduledTasks.remove(id);
        }
    }
    @Override
    public void patchJobData(DatasetCronMeta dbData, DatasetCronMeta cronData) {
        JobHandler handler = getJobHandler(dbData.getMethod());
        handler.patchJobData(dbData,cronData);
    }

    private JobHandler getJobHandler(String method){
        JobHandler handler = jobHandlerMap.get(method);
        if(handler == null)  throw new NotFoundException("No handler found for method: "+method);
        return handler;
    }

}
