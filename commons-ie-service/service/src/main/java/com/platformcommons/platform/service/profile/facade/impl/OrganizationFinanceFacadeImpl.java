package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.OrganizationFinance;
import com.platformcommons.platform.service.profile.dto.OrganizationFinanceDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.OrganizationFinanceFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.OrganizationFinanceService;
import com.platformcommons.platform.service.profile.facade.assembler.OrganizationFinanceDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class OrganizationFinanceFacadeImpl implements OrganizationFinanceFacade {


    @Autowired
    private OrganizationFinanceService service;

    private OrganizationFinanceDTOAssembler assembler;


    @Override
    public Long save(OrganizationFinanceDTO organizationFinanceDTO ){
        return service.save(assembler.fromDTO(organizationFinanceDTO));
    }


    @Override
    public OrganizationFinanceDTO update(OrganizationFinanceDTO organizationFinanceDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(organizationFinanceDTO)));
    }

    @Override
    public PageDTO<OrganizationFinanceDTO> getAllPage(Integer page, Integer size){
        Page<OrganizationFinance> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public OrganizationFinanceDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
