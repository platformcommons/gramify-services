package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.application.AppPlanService;
import com.platformcommons.platform.service.domain.domain.AppPlan;
import com.platformcommons.platform.service.domain.dto.AppPlanDTO;
import com.platformcommons.platform.service.domain.facade.AppPlanFacade;
import com.platformcommons.platform.service.domain.facade.assembler.AppPlanDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class AppPlanFacadeImpl implements AppPlanFacade {

    @Autowired
    private AppPlanService appPlanService;

    @Autowired
    private AppPlanDTOAssembler appPlanDTOAssembler;

    @Override
    public void createAppPlans(Long appId, List<AppPlanDTO> appPlanDTOS) {
        appPlanService.saveAppPlans(appId,appPlanDTOAssembler.fromDTOs(appPlanDTOS));
    }

    @Override
    public PageDTO<AppPlanDTO> fetchAppPlans(Long appId, Integer page, Integer size) {
        Page<AppPlan>  appPlans=appPlanService.fetchAppPlans(appId,page,size);
        return  new PageDTO<>(appPlans.stream().map(appPlanDTOAssembler::toDTO).
                collect(Collectors.toCollection(LinkedHashSet::new)),appPlans.hasNext(),appPlans.getTotalElements());
    }

    @Override
    public AppPlanDTO update(AppPlanDTO appPlanDTO) {
        AppPlan appPlan=appPlanService.update(appPlanDTOAssembler.fromDTO(appPlanDTO));
        return appPlanDTOAssembler.toDTO(appPlan);
    }
}
