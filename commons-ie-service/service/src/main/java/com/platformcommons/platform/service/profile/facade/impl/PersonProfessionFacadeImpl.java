package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonProfession;
import com.platformcommons.platform.service.profile.dto.PersonProfessionDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonProfessionFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonProfessionService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonProfessionDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonProfessionFacadeImpl implements PersonProfessionFacade {


    @Autowired
    private PersonProfessionService service;

    private PersonProfessionDTOAssembler assembler;


    @Override
    public Long save(PersonProfessionDTO personProfessionDTO ){
        return service.save(assembler.fromDTO(personProfessionDTO));
    }


    @Override
    public PersonProfessionDTO update(PersonProfessionDTO personProfessionDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personProfessionDTO)));
    }

    @Override
    public PageDTO<PersonProfessionDTO> getAllPage(Integer page, Integer size){
        Page<PersonProfession> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonProfessionDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
