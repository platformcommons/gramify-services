package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonProfile;
import com.platformcommons.platform.service.profile.dto.PersonProfileDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonProfileFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonProfileService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonProfileDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonProfileFacadeImpl implements PersonProfileFacade {


    @Autowired
    private PersonProfileService service;

    private PersonProfileDTOAssembler assembler;


    @Override
    public Long save(PersonProfileDTO personProfileDTO ){
        return service.save(assembler.fromDTO(personProfileDTO));
    }


    @Override
    public PersonProfileDTO update(PersonProfileDTO personProfileDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personProfileDTO)));
    }

    @Override
    public PageDTO<PersonProfileDTO> getAllPage(Integer page, Integer size){
        Page<PersonProfile> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonProfileDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
