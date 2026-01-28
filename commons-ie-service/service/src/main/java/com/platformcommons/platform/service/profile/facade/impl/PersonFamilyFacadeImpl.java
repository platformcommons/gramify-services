package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonFamily;
import com.platformcommons.platform.service.profile.dto.PersonFamilyDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonFamilyFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonFamilyService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonFamilyDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonFamilyFacadeImpl implements PersonFamilyFacade {


    @Autowired
    private PersonFamilyService service;

    private PersonFamilyDTOAssembler assembler;


    @Override
    public Long save(PersonFamilyDTO personFamilyDTO ){
        return service.save(assembler.fromDTO(personFamilyDTO));
    }


    @Override
    public PersonFamilyDTO update(PersonFamilyDTO personFamilyDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personFamilyDTO)));
    }

    @Override
    public PageDTO<PersonFamilyDTO> getAllPage(Integer page, Integer size){
        Page<PersonFamily> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonFamilyDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
