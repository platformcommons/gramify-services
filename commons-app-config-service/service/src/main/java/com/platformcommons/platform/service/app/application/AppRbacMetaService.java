package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.domain.AppRbacMeta;

import java.util.Set;

public interface AppRbacMetaService {


    AppRbacMeta getByAppCode(String appCode, String entityType, String roleType);

    AppRbacMeta createAppRbacMeta(AppRbacMeta appRbacMeta);

    AppRbacMeta patchUpdate(AppRbacMeta appRbacMeta);

    void addAppMenuToAppRbacMeta(Long appRbacMetaId, Set<AppMenu> appMenus);
}
