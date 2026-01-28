package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.domain.application.AppPlanService;
import com.platformcommons.platform.service.domain.application.AppService;
import com.platformcommons.platform.service.domain.domain.App;
import com.platformcommons.platform.service.domain.domain.AppPlan;
import com.platformcommons.platform.service.domain.domain.repo.AppPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppPlanServiceImpl implements AppPlanService {

    @Autowired
    private AppService appService;

    @Autowired
    private AppPlanRepository repository;

    @Override
    public void saveAppPlans(Long appId, List<AppPlan> appPlans) {
        App app=appService.getById(appId);
        appPlans.forEach(appPlan -> {
            appPlan.setApp(app);});
        repository.saveAll(appPlans);
    }

    @Override
    public Page<AppPlan> fetchAppPlans(Long appId, Integer page, Integer size) {
        return repository.getAppPlansByAppId(appId, PageRequest.of(page,size));
    }

    @Override
    public AppPlan update(AppPlan appPlan) {
        AppPlan fetchedApp = repository.findById(appPlan.getId())
                .orElseThrow(()-> new NotFoundException(String.format("Request AppPlan with  %d  not found",appPlan.getId())));

        fetchedApp.update(appPlan);
        return repository.save(fetchedApp);
    }
}
