package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.dto.AppRbacMetaDTO;

import javax.validation.Valid;
import java.util.Set;

public interface AppRbacMetaFacade {

    AppRbacMetaDTO getByAppCode(String appCode, String entityType, String roleType);

    Long createAppRbacMeta(AppRbacMetaDTO appRbacMetaDTO);

    AppRbacMetaDTO patchUpdate(@Valid AppRbacMetaDTO appRbacMetaDTO);

    void addAppMenuToAppRbacMeta(Long appRbacMetaId, @Valid Set<AppMenuDTO> appMenuDTO);
}
