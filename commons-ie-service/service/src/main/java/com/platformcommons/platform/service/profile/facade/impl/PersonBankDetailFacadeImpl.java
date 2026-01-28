package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonBankDetail;
import com.platformcommons.platform.service.profile.dto.PersonBankDetailDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonBankDetailFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonBankDetailService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonBankDetailDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonBankDetailFacadeImpl implements PersonBankDetailFacade {


    @Autowired
    private PersonBankDetailService service;

    private PersonBankDetailDTOAssembler assembler;


    @Override
    public Long save(PersonBankDetailDTO personBankDetailDTO ){
        return service.save(assembler.fromDTO(personBankDetailDTO));
    }


    @Override
    public PersonBankDetailDTO update(PersonBankDetailDTO personBankDetailDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personBankDetailDTO)));
    }

    @Override
    public PageDTO<PersonBankDetailDTO> getAllPage(Integer page, Integer size){
        Page<PersonBankDetail> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonBankDetailDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
