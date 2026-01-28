package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.AppPlan;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

public interface AppPlanService {
    void saveAppPlans(Long appId, List<AppPlan> appPlans);

    Page<AppPlan> fetchAppPlans(Long appId, Integer page, Integer size);

    AppPlan update(AppPlan fromDTO);
}
