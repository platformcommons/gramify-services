package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.dto.AppPlanDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.List;

public interface AppPlanFacade {
    void createAppPlans(Long appId, List<AppPlanDTO> appPlanDTOS);

    PageDTO<AppPlanDTO> fetchAppPlans(Long appId, Integer page, Integer size);

    AppPlanDTO update(AppPlanDTO appPlanDTO);
}
