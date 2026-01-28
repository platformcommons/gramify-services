package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.service.app.application.InstanceService;
import com.platformcommons.platform.service.app.domain.App;
import com.platformcommons.platform.service.app.domain.Instance;
import org.springframework.data.domain.Page;
import com.platformcommons.platform.service.app.application.AppService;
import com.platformcommons.platform.service.app.domain.repo.AppRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    public AppRepository appRepository;

    @Autowired
    private InstanceService instanceService;

    @Override
    public Long save(App app) {
        return appRepository.save(app).getId();
    }

    @Override
    public App update(App app) {
        App fetchedApp = appRepository.findById(app.getId())
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
        return appRepository.save(app);
    }

    @Override
    public void deleteById(Long id, String reason) {
        App fetchedApp = appRepository.findById(id)
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
        // fetchedApp.deactivate(reason);
        appRepository.save(fetchedApp);
    }

    @Override
    public App getById(Long id) {
        return appRepository.findById(id)
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
    }

    @Override
    public Page<App> getByPage(Integer page, Integer size) {
        return appRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void addInstanceForApp(Long appId, Long instanceId) {
        App fetchedApp = this.getById(appId);
        Instance fetchedInstance = instanceService.getInstanceById(instanceId);
        fetchedApp.getInstances().add(fetchedInstance);
    }

    @Override
    public Set<Instance> getAvailableInstancesForApp(String appCode) {
        App fetchedApp = appRepository.findByAppCode(appCode);
        return fetchedApp.getInstances();
    }
}