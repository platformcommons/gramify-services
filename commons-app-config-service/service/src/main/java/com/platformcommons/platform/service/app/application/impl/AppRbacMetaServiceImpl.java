package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.app.application.AppRbacMetaService;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.domain.AppRbacMeta;
import com.platformcommons.platform.service.app.domain.repo.AppMenuRepository;
import com.platformcommons.platform.service.app.domain.repo.AppRbacMetaNonMTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class AppRbacMetaServiceImpl implements AppRbacMetaService {

    @Autowired
    private AppRbacMetaNonMTRepository appRbacMetaNonMTRepository;

    @Autowired
    private AppMenuRepository appMenuRepository;

    public AppRbacMeta getById(Long id) {
        return appRbacMetaNonMTRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Request AppRbacMeta with  %d  not found",id)));
    }

    @Override
    public AppRbacMeta getByAppCode(String appCode, String entityType, String roleType) {
        return appRbacMetaNonMTRepository.getByAppCode(appCode,entityType,roleType)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Request AppRbacMeta with AppCode %s not found", appCode)));
    }

    @Override
    public AppRbacMeta createAppRbacMeta(AppRbacMeta appRbacMeta) {
        Optional<AppRbacMeta> optionalAppRbacMeta = appRbacMetaNonMTRepository.getByAppCodeAndEntityTypeAndExactMatch(appRbacMeta.getAppCode(),
                appRbacMeta.getEntityType(), appRbacMeta.getRoleType());
        if (optionalAppRbacMeta.isPresent()){
            throw new DuplicateResourceException(String.format("App Rbac Meta already exists In App with AppCode - %s",
                    appRbacMeta.getAppCode()));
        }
        appRbacMeta.init();
        return appRbacMetaNonMTRepository.save(appRbacMeta);
    }

    @Override
    public AppRbacMeta patchUpdate(AppRbacMeta appRbacMeta) {
        AppRbacMeta fetchedAppRbacMeta = this.getById(appRbacMeta.getId());
        fetchedAppRbacMeta.patch(appRbacMeta);
        return appRbacMetaNonMTRepository.save(fetchedAppRbacMeta);
    }

    @Override
    public void addAppMenuToAppRbacMeta(Long appRbacMetaId, Set<AppMenu> appMenus) {
        AppRbacMeta fetchedAppRbacMeta = this.getById(appRbacMetaId);
        PlatformUtil.validateLoginTenantAndAdmin(fetchedAppRbacMeta.getTenantId());
        appMenus.forEach(appMenu -> {
            appMenu.init(null,fetchedAppRbacMeta, null);
        });
        appMenuRepository.saveAll(appMenus);
    }

}
