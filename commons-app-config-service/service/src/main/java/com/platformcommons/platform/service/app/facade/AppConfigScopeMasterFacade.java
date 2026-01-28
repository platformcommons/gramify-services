package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.dto.AppConfigScopeMasterDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

public interface AppConfigScopeMasterFacade {

    Long save(AppConfigScopeMasterDTO AppConfigScopeMasterDTO);

    AppConfigScopeMasterDTO update(AppConfigScopeMasterDTO AppConfigScopeMasterDTO);

    PageDTO<AppConfigScopeMasterDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AppConfigScopeMasterDTO getById(Long id);
}
