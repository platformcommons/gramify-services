package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.service.app.application.AppVersionService;
import com.platformcommons.platform.service.app.domain.AppVersion;
import com.platformcommons.platform.service.app.dto.AppConfigDTO;
import com.platformcommons.platform.service.app.dto.AppVersionDTO;
import com.platformcommons.platform.service.app.facade.AppVersionFacade;
import com.platformcommons.platform.service.app.facade.assembler.AppConfigDTOAssembler;
import com.platformcommons.platform.service.app.facade.assembler.AppVersionDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class AppVersionFacadeImpl implements AppVersionFacade {

    @Autowired
    private AppVersionService service;

    @Autowired
    private AppVersionDTOAssembler assembler;

    @Autowired
    private AppConfigDTOAssembler appConfigDTOAssembler;

    @Override
    public Long save(AppVersionDTO appVersionDTO) {
        return service.save(assembler.fromDTO(appVersionDTO));
    }

    @Override
    public void addAppVersionToApp(Long appId, Set<AppVersionDTO> appVersions) {
        service.addAppVersionToApp(appId,appVersions.stream().map(it->assembler.fromDTO(it)).collect(Collectors.toSet()));
    }

    @Override
    public Long copyAndCreateAppVersion(Long copyFromAppVersionId, AppVersionDTO appVersion) {
        Set<AppConfigDTO> appConfigDTOS= this.getById(copyFromAppVersionId).getAppConfigList();
        appConfigDTOS.forEach(it->it.setUuid(null));
        return service.copyAndCreateAppVersion(copyFromAppVersionId,assembler.fromDTO(appVersion),
                appConfigDTOS.stream().map(appConfigDTOAssembler::fromDTO).collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    @Override
    public AppVersionDTO update(AppVersionDTO AppVersionDTO) {
        return assembler.toDTO(service.update(assembler.fromDTO(AppVersionDTO)));
    }

    @Override
    public PageDTO<AppVersionDTO> getAllPage(Integer page, Integer size) {
        Page<AppVersion> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id, String reason) {
        service.deleteById(id,reason);
    }

    @Override
    public AppVersionDTO getById(Long id) {
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public PageDTO<AppVersionDTO> getAppVersionPageByAppId(Long appId, Integer page, Integer size) {
        Page<AppVersion> result = service.getAppVersionPageByAppId(appId,page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
        .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }
}
