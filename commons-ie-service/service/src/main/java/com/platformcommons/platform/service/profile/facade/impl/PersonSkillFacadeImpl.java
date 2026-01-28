package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonSkill;
import com.platformcommons.platform.service.profile.dto.PersonSkillDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonSkillFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonSkillService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonSkillDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonSkillFacadeImpl implements PersonSkillFacade {


    @Autowired
    private PersonSkillService service;

    private PersonSkillDTOAssembler assembler;


    @Override
    public Long save(PersonSkillDTO personSkillDTO ){
        return service.save(assembler.fromDTO(personSkillDTO));
    }


    @Override
    public PersonSkillDTO update(PersonSkillDTO personSkillDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personSkillDTO)));
    }

    @Override
    public PageDTO<PersonSkillDTO> getAllPage(Integer page, Integer size){
        Page<PersonSkill> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonSkillDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
