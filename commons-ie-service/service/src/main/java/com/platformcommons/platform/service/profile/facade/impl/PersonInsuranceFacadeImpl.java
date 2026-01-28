package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonInsurance;
import com.platformcommons.platform.service.profile.dto.PersonInsuranceDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonInsuranceFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonInsuranceService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonInsuranceDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonInsuranceFacadeImpl implements PersonInsuranceFacade {


    @Autowired
    private PersonInsuranceService service;

    private PersonInsuranceDTOAssembler assembler;


    @Override
    public Long save(PersonInsuranceDTO personInsuranceDTO ){
        return service.save(assembler.fromDTO(personInsuranceDTO));
    }


    @Override
    public PersonInsuranceDTO update(PersonInsuranceDTO personInsuranceDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personInsuranceDTO)));
    }

    @Override
    public PageDTO<PersonInsuranceDTO> getAllPage(Integer page, Integer size){
        Page<PersonInsurance> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonInsuranceDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
