package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.Instance;
import org.springframework.data.domain.Page;

public interface InstanceService {

    Long createInstance(Instance instance);

    Page<Instance> getAllInstances(Integer page, Integer size);

    Instance getInstanceById(Long id);

    void deleteInstance(Long id);

    Instance update(Instance instance);

}
