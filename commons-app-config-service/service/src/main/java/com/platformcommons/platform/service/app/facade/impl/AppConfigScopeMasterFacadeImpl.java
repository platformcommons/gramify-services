package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.service.app.application.AppConfigScopeMasterService;
import com.platformcommons.platform.service.app.domain.AppConfigScopeMaster;
import com.platformcommons.platform.service.app.dto.AppConfigScopeMasterDTO;
import com.platformcommons.platform.service.app.facade.AppConfigScopeMasterFacade;
import com.platformcommons.platform.service.app.facade.assembler.AppConfigScopeMasterDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class AppConfigScopeMasterFacadeImpl implements AppConfigScopeMasterFacade {

    @Autowired
    private AppConfigScopeMasterService service;

    @Autowired
    private AppConfigScopeMasterDTOAssembler assembler;

    @Override
    public Long save(AppConfigScopeMasterDTO appConfigScopeMasterDTO) {
        return service.save(assembler.fromDTO(appConfigScopeMasterDTO));
    }

    @Override
    public AppConfigScopeMasterDTO update(AppConfigScopeMasterDTO appConfigScopeMasterDTO) {
        return assembler.toDTO(service.update(assembler.fromDTO(appConfigScopeMasterDTO)));
    }

    @Override
    public PageDTO<AppConfigScopeMasterDTO> getAllPage(Integer page, Integer size) {
        Page<AppConfigScopeMaster> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id, String reason) {
        service.deleteById(id,reason);
    }

    @Override
    public AppConfigScopeMasterDTO getById(Long id) {
        return assembler.toDTO(service.getById(id));
    }
}
