package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonOrganization;
import com.platformcommons.platform.service.profile.dto.PersonOrganizationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonOrganizationFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonOrganizationService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonOrganizationDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonOrganizationFacadeImpl implements PersonOrganizationFacade {


    @Autowired
    private PersonOrganizationService service;

    private PersonOrganizationDTOAssembler assembler;


    @Override
    public Long save(PersonOrganizationDTO personOrganizationDTO ){
        return service.save(assembler.fromDTO(personOrganizationDTO));
    }


    @Override
    public PersonOrganizationDTO update(PersonOrganizationDTO personOrganizationDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personOrganizationDTO)));
    }

    @Override
    public PageDTO<PersonOrganizationDTO> getAllPage(Integer page, Integer size){
        Page<PersonOrganization> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonOrganizationDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
