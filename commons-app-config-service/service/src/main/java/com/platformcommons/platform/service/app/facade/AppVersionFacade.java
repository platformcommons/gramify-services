package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.dto.AppVersionDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.Set;

public interface AppVersionFacade {

    Long save(AppVersionDTO AppVersionDTO);

    void addAppVersionToApp(Long appId, Set<AppVersionDTO> appVersions);

    Long copyAndCreateAppVersion(Long copyFromAppVersionId,AppVersionDTO appVersion);

    AppVersionDTO update(AppVersionDTO AppVersionDTO);

    PageDTO<AppVersionDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AppVersionDTO getById(Long id);

    PageDTO<AppVersionDTO> getAppVersionPageByAppId(Long appId, Integer page, Integer size);
}
