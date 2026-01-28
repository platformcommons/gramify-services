package com.platformcommons.platform.service.report.application.callback;

import jdk.nashorn.internal.objects.annotations.Function;

@FunctionalInterface
public interface JobCallback {

    void execute(Long dataSetCronMeta);

}
