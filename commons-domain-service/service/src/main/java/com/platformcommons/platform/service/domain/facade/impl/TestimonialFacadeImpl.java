package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.domain.Testimonial;
import com.platformcommons.platform.service.domain.dto.TestimonialDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.TestimonialFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.TestimonialService;
import com.platformcommons.platform.service.domain.facade.assembler.TestimonialDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class TestimonialFacadeImpl implements TestimonialFacade {


    @Autowired
    private TestimonialService service;

    @Autowired
    private TestimonialDTOAssembler assembler;


    @Override
    public Long save(TestimonialDTO testimonialDTO ){
        return service.save(assembler.fromDTO(testimonialDTO));
    }


    @Override
    public TestimonialDTO update(TestimonialDTO testimonialDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(testimonialDTO)));
    }

    @Override
    public PageDTO<TestimonialDTO> getAllPage(Integer page, Integer size){
        Page<Testimonial> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public TestimonialDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }


}
