package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.app.facade.AppMenuFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.app.application.AppMenuService;
import com.platformcommons.platform.service.app.facade.assembler.AppMenuDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class AppMenuFacadeImpl implements AppMenuFacade {


    @Autowired
    private AppMenuService service;

    @Autowired
    private AppMenuDTOAssembler assembler;


    @Override
    public Long save(AppMenuDTO appMenuDTO ){
        return service.save(assembler.fromDTO(appMenuDTO));
    }


    @Override
    public AppMenuDTO update(AppMenuDTO appMenuDTO,Boolean isPatch ){
        return assembler.toDTO(service.update(assembler.fromDTO(appMenuDTO),isPatch));
    }

    @Override
    public PageDTO<AppMenuDTO> getAllPage(Integer page, Integer size){
        Page<AppMenu> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public AppMenuDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public Long addSubMenuToAppMenu(Long appMenuId, AppMenuDTO appSubMenuDTO) {
        return service.addSubMenuToAppMenu(appMenuId, assembler.fromDTO(appSubMenuDTO));
    }

    @Override
    public PageDTO<AppMenuDTO> getAppMenuByAppCodeAppRoleAndMenuCode(String appCode, Integer page, Integer size, String role, String menuCode) {
        Page<AppMenu> result= service.getAppMenuByAppCodeAppRoleAndMenuCode(appCode, page, size,role,menuCode);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public PageDTO<AppMenuDTO> getAppMenuByIdOfAppRbac(Long rbacId, Integer page, Integer size) {
        Page<AppMenu> result= service.getAppMenuByIdOfAppRbac(rbacId, page, size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public AppMenuDTO toggleAppMenu(String appCode, String role, String menuCode) {
        return assembler.toDTO(service.toggleAppMenu(appCode,role,menuCode));
    }

    @Override
    public void toggleAppMenusInBulk(String appCode, String role, Set<AppMenuDTO> appMenuSet) {
        service.toggleAppMenusInBulk(appCode,role,assembler.fromDTOs(appMenuSet));
    }

    @Override
    public AppMenuDTO toggleAppSubMenu(String appCode, String role, String menuCode, String subMenuCode) {
        return assembler.toDTO(service.toggleAppSubMenu(appCode,role,menuCode,subMenuCode));
    }

    @Override
    public PageDTO<AppMenuDTO> getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu(String appCode, Integer page, Integer size, String role, String menuCode, String subMenuCode) {
        Page<AppMenu> result= service.getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu(appCode, page, size,role,menuCode,subMenuCode);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

}
