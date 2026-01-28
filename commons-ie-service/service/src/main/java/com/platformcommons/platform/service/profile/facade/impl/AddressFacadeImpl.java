package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.Address;
import com.platformcommons.platform.service.profile.dto.AddressDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.AddressFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.AddressService;
import com.platformcommons.platform.service.profile.facade.assembler.AddressDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class AddressFacadeImpl implements AddressFacade {


    @Autowired
    private AddressService service;

    private AddressDTOAssembler assembler;


    @Override
    public Long save(AddressDTO addressDTO ){
        return service.save(assembler.fromDTO(addressDTO));
    }


    @Override
    public AddressDTO update(AddressDTO addressDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(addressDTO)));
    }

    @Override
    public PageDTO<AddressDTO> getAllPage(Integer page, Integer size){
        Page<Address> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public AddressDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
