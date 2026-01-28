package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.service.app.application.AppRbacMetaService;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.dto.AppRbacMetaDTO;
import com.platformcommons.platform.service.app.facade.AppRbacMetaFacade;
import com.platformcommons.platform.service.app.facade.assembler.AppMenuDTOAssembler;
import com.platformcommons.platform.service.app.facade.assembler.AppRbacMetaDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Transactional
public class AppRbacMetaFacadeImpl implements AppRbacMetaFacade {

    @Autowired
    private AppRbacMetaDTOAssembler assembler;

    @Autowired
    private AppRbacMetaService service;

    @Autowired
    private AppMenuDTOAssembler appMenuDTOAssembler;

    @Override
    public AppRbacMetaDTO getByAppCode(String appCode, String entityType, String roleType) {
        return assembler.toDTO(service.getByAppCode(appCode, entityType, roleType));
    }

    @Override
    public Long createAppRbacMeta(AppRbacMetaDTO appRbacMetaDTO) {
        PlatformUtil.validatePlatformAdmin();
        return service.createAppRbacMeta(assembler.fromDTO(appRbacMetaDTO)).getId();
    }

    @Override
    public AppRbacMetaDTO patchUpdate(AppRbacMetaDTO appRbacMetaDTO) {
        return assembler.toDTO(service.patchUpdate(assembler.fromDTO(appRbacMetaDTO)));
    }

    @Override
    public void addAppMenuToAppRbacMeta(Long appRbacMetaId, Set<AppMenuDTO> appMenuDTO) {
        service.addAppMenuToAppRbacMeta(appRbacMetaId, appMenuDTOAssembler.fromDTOs(appMenuDTO));
    }
}
