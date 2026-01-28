package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.App;
import com.platformcommons.platform.service.app.domain.Instance;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface AppService {

    Long save(App app);

    App update(App app);

    void deleteById(Long id, String reason);

    App getById(Long id);

    Page<App> getByPage(Integer page, Integer size);

    void addInstanceForApp(Long appId, Long instanceId);

    Set<Instance> getAvailableInstancesForApp(String appCode);
}