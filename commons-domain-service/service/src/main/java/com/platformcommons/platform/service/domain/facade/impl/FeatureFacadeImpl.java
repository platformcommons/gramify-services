package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.domain.Feature;
import com.platformcommons.platform.service.domain.dto.FeatureDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.FeatureFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.FeatureService;
import com.platformcommons.platform.service.domain.facade.assembler.FeatureDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class FeatureFacadeImpl implements FeatureFacade {


    @Autowired
    private FeatureService service;

    @Autowired
    private FeatureDTOAssembler assembler;


    @Override
    public Long save(FeatureDTO featureDTO ){
        return service.save(assembler.fromDTO(featureDTO));
    }


    @Override
    public FeatureDTO update(FeatureDTO featureDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(featureDTO)));
    }

    @Override
    public PageDTO<FeatureDTO> getAllPage(Integer page, Integer size){
        Page<Feature> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public FeatureDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }


}
