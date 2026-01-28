package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.app.application.AppRbacAsync;
import com.platformcommons.platform.service.app.domain.AppOperation;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.app.facade.AppOperationFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.app.application.AppOperationService;
import com.platformcommons.platform.service.app.facade.assembler.AppOperationDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;

@Component
@Transactional
public class AppOperationFacadeImpl implements AppOperationFacade {


    @Autowired
    private AppOperationService service;

    @Autowired
    private AppOperationDTOAssembler assembler;

    @Autowired
    AppRbacAsync appRbacAsync;


    @Override
    public Long save(AppOperationDTO appOperationDTO ){
        return service.save(assembler.fromDTO(appOperationDTO));
    }


    @Override
    public AppOperationDTO update(AppOperationDTO appOperationDTO , Boolean isPatch){
        return assembler.toDTO(service.update(assembler.fromDTO(appOperationDTO),isPatch));
    }

    @Override
    public AppOperationDTO patch(AppOperationDTO appOperationDTO, Boolean syncHigherRbacOperation, Boolean syncLowerRbacOperation) {
        AppOperationDTO existingAppOperationDTO = assembler.toDTO(service.getById(appOperationDTO.getId()));
        AppOperationDTO savedAppOperationDTO = assembler.toDTO(service.patch(assembler.fromDTO(appOperationDTO)));
        appRbacAsync.validateAndUpdateAppOperation(existingAppOperationDTO, savedAppOperationDTO, syncHigherRbacOperation,
                syncLowerRbacOperation, (PlatformToken) SecurityContextHolder.getContext().getAuthentication());
        return savedAppOperationDTO;
    }

    @Override
    public PageDTO<AppOperationDTO> getAllPage(Integer page, Integer size){
        Page<AppOperation> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public AppOperationDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public PageDTO<AppOperationDTO> getAppOperationByAppCodeAppRoleAndEntity(String appCode, Integer page, Integer size, String role, String entity) {
        Page<AppOperation> result= service.getAppOperationByAppCodeAppRoleAndEntity(appCode, page, size,role,entity);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public void updateAppOperationInBulk(Set<AppOperationDTO> appOperationDTOS) {
        service.updateAppOperationInBulk(assembler.fromDTOs(appOperationDTOS));
    }


}
