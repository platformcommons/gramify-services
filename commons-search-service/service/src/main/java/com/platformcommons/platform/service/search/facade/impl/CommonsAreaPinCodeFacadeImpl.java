package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.CommonsAreaPinCodeService;
import com.platformcommons.platform.service.search.dto.CommonsAreaPinCodeDTO;
import com.platformcommons.platform.service.search.facade.CommonsAreaPinCodeFacade;
import com.platformcommons.platform.service.search.facade.assembler.CommonsAreaPinCodeAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommonsAreaPinCodeFacadeImpl implements CommonsAreaPinCodeFacade {

    @Autowired
    private CommonsAreaPinCodeService service;

    @Autowired
    private CommonsAreaPinCodeAssembler assembler;


    @Override
    public CommonsAreaPinCodeDTO update(CommonsAreaPinCodeDTO body) {
        return assembler.toDTO(service.update(assembler.fromDTO(body)));
    }

    @Override
    public CommonsAreaPinCodeDTO save(CommonsAreaPinCodeDTO body) {
        return assembler.toDTO(service.save(assembler.fromDTO(body)));
    }

    @Override
    public PageDTO<CommonsAreaPinCodeDTO> readPincode(String keyword) {
        Set<CommonsAreaPinCodeDTO> result = service.readPincodeByAreaCode(keyword).
                stream().map(ob->assembler.toDTO(ob)).collect(Collectors.toSet());
        return new PageDTO<>(result,true,result.size());
    }
}
