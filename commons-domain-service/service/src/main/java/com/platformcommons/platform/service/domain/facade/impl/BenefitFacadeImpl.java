package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.domain.Benefit;
import com.platformcommons.platform.service.domain.dto.BenefitDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.BenefitFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.BenefitService;
import com.platformcommons.platform.service.domain.facade.assembler.BenefitDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class BenefitFacadeImpl implements BenefitFacade {


    @Autowired
    private BenefitService service;

    @Autowired
    private BenefitDTOAssembler assembler;


    @Override
    public Long save(BenefitDTO benefitDTO ){
        return service.save(assembler.fromDTO(benefitDTO));
    }


    @Override
    public BenefitDTO update(BenefitDTO benefitDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(benefitDTO)));
    }

    @Override
    public PageDTO<BenefitDTO> getAllPage(Integer page, Integer size){
        Page<Benefit> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public BenefitDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }


}
