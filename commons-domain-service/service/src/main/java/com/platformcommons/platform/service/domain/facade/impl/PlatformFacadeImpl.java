package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.domain.Platform;
import com.platformcommons.platform.service.domain.dto.PlatformDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.PlatformFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.PlatformService;
import com.platformcommons.platform.service.domain.facade.assembler.PlatformDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PlatformFacadeImpl implements PlatformFacade {


    @Autowired
    private PlatformService service;

    @Autowired
    private PlatformDTOAssembler assembler;


    @Override
    public Long save(PlatformDTO platformDTO ){
        return service.save(assembler.fromDTO(platformDTO));
    }


    @Override
    public PlatformDTO update(PlatformDTO platformDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(platformDTO)));
    }

    @Override
    public PageDTO<PlatformDTO> getAllPage(Integer page, Integer size){
        Page<Platform> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PlatformDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }


}
