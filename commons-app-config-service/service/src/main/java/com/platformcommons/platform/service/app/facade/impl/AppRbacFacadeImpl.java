package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.app.application.AppRbacAsync;
import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;
import com.platformcommons.platform.service.app.facade.assembler.AppMenuDTOAssembler;
import com.platformcommons.platform.service.app.facade.assembler.AppOperationDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.app.facade.AppRbacFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.app.application.AppRbacService;
import com.platformcommons.platform.service.app.facade.assembler.AppRbacDTOAssembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Collectors;


import java.util.*;

@Component
@Transactional
public class AppRbacFacadeImpl implements AppRbacFacade {


    @Autowired
    private AppRbacService service;

    @Autowired
    private AppRbacDTOAssembler assembler;

    @Autowired
    private AppOperationDTOAssembler appOperationDTOAssembler;

    @Autowired
    private AppMenuDTOAssembler appMenuDTOAssembler;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    AppRbacAsync appRbacAsync;


    @Override
    public Long save(AppRbacDTO appRbacDTO ){
        return service.save(assembler.fromDTO(appRbacDTO)).getId();
    }

    @Override
    public PageDTO<AppRbacDTO> getAllPage(Integer page, Integer size){
        Page<AppRbac> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public AppRbacDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public AppRbacDTO getByAppCodeAndRole(String appCode,String role,String marketContext, Boolean includeDisabled) {
        AppRbacDTO appRbacDTO = assembler.toDTO(service.getByAppCodeAndRole(appCode,role, marketContext));
        includeDisabled = includeDisabled == null ? Boolean.FALSE : includeDisabled;
        if(!includeDisabled) {
            removeDisabledAppMenusFromRbac(appRbacDTO);
        }
        return appRbacDTO;
    }

    @Override
    public AppRbacDTO getOrCreatePrimaryRbacByAppCode(String appCode, String marketContext) {
        return assembler.toDTO(service.getOrCreatePrimaryRbacByAppCode(appCode, marketContext));
    }

    @Override
    public AppRbacDTO getOrCreateAppRbacByAppCodeAndContext(String appCode, String context, String entityType,
                                                                             String marketContext) {
        return assembler.toDTO(service.getOrCreateAppRbacByAppCodeAndContext(appCode,context,entityType,
                marketContext));
    }

    @Override
    public AppRbacDTO getAppRbacByAppCodeAndMarketAndPrimaryRbacCheck(String appCode, String marketContext) {
        AppRbac appRbac = service.getAppRbacByAppCodeAndMarketAndPrimaryRbacCheck(appCode, marketContext);
        AppRbacDTO appRbacDTO = assembler.toDTO(appRbac);
        removeDisabledAppMenusFromRbac(appRbacDTO);
        entityManager.detach(appRbac);
        entityManager.clear();
        return appRbacDTO;
    }

    public void removeDisabledAppMenusFromRbac(AppRbacDTO appRbacDTO) {
        if (appRbacDTO != null && appRbacDTO.getAppMenuList() != null && !appRbacDTO.getAppMenuList().isEmpty()) {
            removeDisabledAppMenus(appRbacDTO.getAppMenuList());
        }
    }

    public void removeDisabledAppMenus(Set<AppMenuDTO> appMenuDTOSet) {
        if (appMenuDTOSet != null && !appMenuDTOSet.isEmpty()) {
            Iterator<AppMenuDTO> iterator = appMenuDTOSet.iterator();
            while (iterator.hasNext()) {
                AppMenuDTO appMenu = iterator.next();
                if (Objects.equals(appMenu.getIsEnabled(), Boolean.FALSE)) {
                    iterator.remove();
                } else {
                    removeDisabledAppMenus(appMenu.getSubMenu());
                }
            }
        }
    }

    @Override
    public AppRbacDTO getAppRbacByAppCodeAndContext(String appCode, String context, String entityType, String marketContext) {
        return assembler.toDTO(service.getAppRbacByAppCodeAndContext(appCode,context,entityType,marketContext));
    }

    @Override
    public Long createAppRbacByAppCodeAndContext(AppRbacDTO appRbacDTO) {
        return assembler.toDTO(service.createAppRbacByAppCodeAndContext(assembler.fromDTO(appRbacDTO))).getId();
    }

    @Override
    public void syncDefaultRbacToAdminRbacs(String appCode, Set<String> adminRoleCodes) {
        service.syncDefaultRbacToAdminRbacs(appCode,adminRoleCodes);
    }

    @Override
    public void syncAdminRbacToChildRoles(String appCode,Set<String> adminRoleCodes) {
        service.syncAdminRbacToChildRoles(appCode,adminRoleCodes);
    }

    @Override
    public Long copy(Long copyFromRbac, AppRbacDTO appRbacDTO) {
        return service.copy(copyFromRbac,assembler.fromDTO(appRbacDTO)).getId();
    }

    @Override
    public void addAppOperationToAppRbac(Long appRbacId, AppOperationDTO appOperationDTO) {
        service.addAppOperationToAppRbac(appRbacId, appOperationDTOAssembler.fromDTO(appOperationDTO));
    }

    @Override
    public void addAppMenuToAppRbac(Long appRbacId, AppMenuDTO appMenuDTO) {
        service.addAppMenuToAppRbac(appRbacId, appMenuDTOAssembler.fromDTO(appMenuDTO));
    }

    @Override
    public AppRbacDTO createRbacFromDefaultRbac(String appCode, String role, String marketContext) {
        return assembler.toDTO(service.createRbacFromDefaultRbac(appCode,role,marketContext));
    }

    @Override
    public AppRbacDTO patchUpdate(AppRbacDTO body, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu) {
        AppRbacDTO oldAppRbac = getById(body.getId());
        AppRbacDTO currentAppRbac = assembler.toDTO(service.patchUpdate(assembler.fromDTO(body), syncHigherRbacMenu, syncLowerRbacMenu));
        if (currentAppRbac.getPriority() != null && (syncHigherRbacMenu != null || syncLowerRbacMenu != null)) {
            appRbacAsync.validateAndUpdateAppMenu(oldAppRbac, (PlatformToken) SecurityContextHolder.getContext().getAuthentication(), syncHigherRbacMenu, syncLowerRbacMenu);
        }
        return currentAppRbac;
    }

    @Override
    public void patchAppRbacInBulk(Set<AppRbacDTO> appRbacDTOS) {
        service.patchAppRbacInBulk(assembler.fromDTOs(appRbacDTOS));
    }

    @Override
    public AppRbacDTO getAppRbacByAppcodeAndContextAndHierarchy(String appCode, String context, String entityType,
                                                                String marketContext) {
        return assembler.toDTO(service.getAppRbacByAppcodeAndContextAndHierarchy(appCode, context,entityType,marketContext));
    }

    @Override
    public void syncDefaultContextRbacToContextRbacs(String appCode, String context, String entityType) {
        service.syncDefaultContextRbacToContextRbacs(appCode,context,entityType);
    }

    @Override
    public Map<String,Boolean> checkIfRbacExistsForRoleCodes(String appCode, Set<String> roleCodes, String marketContext) {
        return service.checkIfRbacExistsForRoleCodes(appCode,roleCodes,marketContext);
    }

    @Override
    public PageDTO<AppRbacDTO> getAppRbacPageByAppCode(String appCode, Integer page, Integer size) {
        Page<AppRbac> result= service.getAppRbacPageByAppCode(appCode,page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }
}
