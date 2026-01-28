package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.domain.DomainHierarchy;
import com.platformcommons.platform.service.domain.dto.DomainHierarchyDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.DomainHierarchyFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.DomainHierarchyService;
import com.platformcommons.platform.service.domain.facade.assembler.DomainHierarchyDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class DomainHierarchyFacadeImpl implements DomainHierarchyFacade {


    @Autowired
    private DomainHierarchyService service;

    @Autowired
    private DomainHierarchyDTOAssembler assembler;


    @Override
    public Long save(DomainHierarchyDTO domainHierarchyDTO ){
        return service.save(assembler.fromDTO(domainHierarchyDTO));
    }


    @Override
    public DomainHierarchyDTO update(DomainHierarchyDTO domainHierarchyDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(domainHierarchyDTO)));
    }

    @Override
    public PageDTO<DomainHierarchyDTO> getAllPage(Integer page, Integer size){
        Page<DomainHierarchy> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public DomainHierarchyDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }


}
