package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonEducation;
import com.platformcommons.platform.service.profile.dto.PersonEducationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonEducationFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonEducationService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonEducationDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonEducationFacadeImpl implements PersonEducationFacade {


    @Autowired
    private PersonEducationService service;

    private PersonEducationDTOAssembler assembler;


    @Override
    public Long save(PersonEducationDTO personEducationDTO ){
        return service.save(assembler.fromDTO(personEducationDTO));
    }


    @Override
    public PersonEducationDTO update(PersonEducationDTO personEducationDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personEducationDTO)));
    }

    @Override
    public PageDTO<PersonEducationDTO> getAllPage(Integer page, Integer size){
        Page<PersonEducation> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonEducationDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
