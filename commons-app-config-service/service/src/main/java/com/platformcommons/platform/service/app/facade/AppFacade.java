package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.domain.Instance;
import com.platformcommons.platform.service.app.dto.AppDTO;
import com.platformcommons.platform.service.app.dto.InstanceDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import liquibase.pro.packaged.L;

import java.util.Set;

public interface AppFacade {

    Long save(AppDTO appDTO);

    AppDTO update(AppDTO appDTO);

    PageDTO<AppDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AppDTO getById(Long id);

    void addInstanceForApp(Long appId, Long instanceId);

    Set<InstanceDTO> getAvailableInstancesForApp(String appCode);
}
