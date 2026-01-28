package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.service.app.application.PlatformAppVersionService;
import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.domain.PlatformAppVersion;
import com.platformcommons.platform.service.app.dto.PlatformAppVersionDTO;
import com.platformcommons.platform.service.app.facade.PlatformAppVersionFacade;
import com.platformcommons.platform.service.app.facade.assembler.PlatformAppVersionDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class PlatformAppVersionFacadeImpl implements PlatformAppVersionFacade {

    @Autowired
    private PlatformAppVersionService service;

    @Autowired
    private PlatformAppVersionDTOAssembler assembler;

    @Override
    public Long save(PlatformAppVersionDTO platformAppVersionDTO) {
        return service.save(assembler.fromDTO(platformAppVersionDTO));
    }

    @Override
    public PlatformAppVersionDTO update(PlatformAppVersionDTO platformAppVersionDTO) {
        return assembler.toDTO(service.update(assembler.fromDTO(platformAppVersionDTO)));
    }

    @Override
    public PageDTO<PlatformAppVersionDTO> getAllPage(Integer page, Integer size) {
        Page<PlatformAppVersion> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id, String reason) {
        service.deleteById(id,reason);
    }

    @Override
    public PlatformAppVersionDTO getById(Long id) {
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public PlatformAppVersionDTO getPlatformAppVersionLatest(Platform platform, String identifier) {
        return assembler.toDTO(service.getPlatformAppVersionLatest(platform,identifier));
    }

}
