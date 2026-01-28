package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonIdentifier;
import com.platformcommons.platform.service.profile.dto.PersonIdentifierDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonIdentifierFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonIdentifierService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonIdentifierDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonIdentifierFacadeImpl implements PersonIdentifierFacade {


    @Autowired
    private PersonIdentifierService service;

    private PersonIdentifierDTOAssembler assembler;


    @Override
    public Long save(PersonIdentifierDTO personIdentifierDTO ){
        return service.save(assembler.fromDTO(personIdentifierDTO));
    }


    @Override
    public PersonIdentifierDTO update(PersonIdentifierDTO personIdentifierDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personIdentifierDTO)));
    }

    @Override
    public PageDTO<PersonIdentifierDTO> getAllPage(Integer page, Integer size){
        Page<PersonIdentifier> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonIdentifierDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
