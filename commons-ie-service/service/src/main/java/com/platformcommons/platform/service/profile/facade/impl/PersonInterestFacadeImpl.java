package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonInterest;
import com.platformcommons.platform.service.profile.dto.PersonInterestDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonInterestFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonInterestService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonInterestDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonInterestFacadeImpl implements PersonInterestFacade {


    @Autowired
    private PersonInterestService service;

    private PersonInterestDTOAssembler assembler;


    @Override
    public Long save(PersonInterestDTO personInterestDTO ){
        return service.save(assembler.fromDTO(personInterestDTO));
    }


    @Override
    public PersonInterestDTO update(PersonInterestDTO personInterestDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personInterestDTO)));
    }

    @Override
    public PageDTO<PersonInterestDTO> getAllPage(Integer page, Integer size){
        Page<PersonInterest> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonInterestDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
