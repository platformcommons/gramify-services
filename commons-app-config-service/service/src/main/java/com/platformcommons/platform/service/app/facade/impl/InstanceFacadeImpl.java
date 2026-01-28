package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.InstanceService;
import com.platformcommons.platform.service.app.domain.Instance;
import com.platformcommons.platform.service.app.dto.InstanceDTO;
import com.platformcommons.platform.service.app.facade.InstanceFacade;
import com.platformcommons.platform.service.app.facade.assembler.InstanceDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class InstanceFacadeImpl implements InstanceFacade {

    @Autowired
    private InstanceService service;

    @Autowired
    private InstanceDTOAssembler assembler;

    @Override
    public Long createInstance(InstanceDTO instanceDTO) {
        PlatformSecurityUtil.validatePlatformAdmin();
        return service.createInstance(assembler.fromDTO(instanceDTO));
    }

    @Override
    public PageDTO<InstanceDTO> getAllInstances(Integer page, Integer size) {
        PlatformSecurityUtil.validatePlatformAdmin();
        Page<Instance> result = service.getAllInstances(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                            .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public InstanceDTO getInstanceById(Long id) {
        PlatformSecurityUtil.validatePlatformAdmin();
        return assembler.toDTO(service.getInstanceById(id));
    }

    @Override
    public void deleteInstance(Long id) {
        PlatformSecurityUtil.validatePlatformAdmin();
        service.deleteInstance(id);
    }

    @Override
    public InstanceDTO updateInstance(InstanceDTO instanceDTO) {
        PlatformSecurityUtil.validatePlatformAdmin();
        return assembler.toDTO(service.update(assembler.fromDTO(instanceDTO))) ;
    }
}
