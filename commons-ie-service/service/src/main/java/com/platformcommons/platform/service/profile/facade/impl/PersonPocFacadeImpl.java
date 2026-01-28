package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonPoc;
import com.platformcommons.platform.service.profile.dto.PersonPocDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonPocFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonPocService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonPocDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonPocFacadeImpl implements PersonPocFacade {


    @Autowired
    private PersonPocService service;

    private PersonPocDTOAssembler assembler;


    @Override
    public Long save(PersonPocDTO personPocDTO ){
        return service.save(assembler.fromDTO(personPocDTO));
    }


    @Override
    public PersonPocDTO update(PersonPocDTO personPocDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personPocDTO)));
    }

    @Override
    public PageDTO<PersonPocDTO> getAllPage(Integer page, Integer size){
        Page<PersonPoc> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonPocDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
