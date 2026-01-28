package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.AppService;
import com.platformcommons.platform.service.app.domain.App;
import com.platformcommons.platform.service.app.dto.AppDTO;
import com.platformcommons.platform.service.app.dto.InstanceDTO;
import com.platformcommons.platform.service.app.facade.AppFacade;
import com.platformcommons.platform.service.app.facade.assembler.AppDTOAssembler;
import com.platformcommons.platform.service.app.facade.assembler.InstanceDTOAssembler;
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
public class AppFacadeImpl  implements AppFacade {

    @Autowired
    private AppService service;

    @Autowired
    private AppDTOAssembler assembler;

    @Autowired
    private InstanceDTOAssembler instanceDTOAssembler;

    @Override
    public Long save(AppDTO appDTO) {
        return service.save(assembler.fromDTO(appDTO));
    }

    @Override
    public AppDTO update(AppDTO appDTO) {
        return assembler.toDTO(service.update(assembler.fromDTO(appDTO)));
    }

    @Override
    public PageDTO<AppDTO> getAllPage(Integer page, Integer size) {
        Page<App> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id, String reason) {
        service.deleteById(id,reason);
    }

    @Override
    public AppDTO getById(Long id) {
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public void addInstanceForApp(Long appId, Long instanceId) {
        PlatformSecurityUtil.validatePlatformAdmin();
        service.addInstanceForApp(appId,instanceId);
    }

    @Override
    public Set<InstanceDTO> getAvailableInstancesForApp(String appCode) {
        return service.getAvailableInstancesForApp(appCode).stream()
                .map(instance -> instanceDTOAssembler.toDTO(instance))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
