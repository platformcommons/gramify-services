package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.AppMenu;


import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.app.domain.repo.AppRbacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.app.application.AppMenuService;
import com.platformcommons.platform.service.app.domain.repo.AppMenuRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;



@Service
public class AppMenuServiceImpl implements AppMenuService {


    @Autowired
    private AppMenuRepository repository;

    @Autowired
    private AppRbacRepository appRbacRepository;


    @Override
    public Long save(AppMenu appMenu ){
        PlatformUtil.validateTenantAdmin();
        return repository.save(appMenu).getId();
    }


    @Override
    public AppMenu update(AppMenu appMenu,Boolean isPatch) {
       AppMenu fetchedAppMenu = this.getById(appMenu.getId());
       isPatch = isPatch!=null? isPatch:Boolean.TRUE;
       if(isPatch) {
           fetchedAppMenu.patch(appMenu);
       }
       else {
           fetchedAppMenu.update(appMenu);
       }
       return repository.save(fetchedAppMenu);
    }


    @Override
    public Page<AppMenu> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        AppMenu fetchedAppMenu = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request AppMenu with  %d  not found",id)));
        PlatformUtil.validateLoginTenant(fetchedAppMenu.getCreatedByTenant());
        fetchedAppMenu.deactivate(reason);
        repository.save(fetchedAppMenu);
    }


    @Override
    public AppMenu getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request AppMenu with  %d  not found",id)));
    }

    @Override
    public Long addSubMenuToAppMenu(Long appMenuId, AppMenu appSubMenu) {
        PlatformUtil.validateTenantAdmin();
        AppMenu fetchedAppMenu = this.getById(appMenuId);
        appSubMenu.setParentMenu(fetchedAppMenu);
        AppMenu savedAppSubMenu = repository.save(appSubMenu);
        return savedAppSubMenu.getId();
    }

    @Override
    public Page<AppMenu> getAppMenuByAppCodeAppRoleAndMenuCode(String appCode, Integer page, Integer size, String role, String menuCode) {
        return repository.findByAppCodeAppRoleAndMenuCode(appCode,role,menuCode,PageRequest.of(page,size));
    }

    @Override
    public Page<AppMenu> getAppMenuByIdOfAppRbac(Long rbacId, Integer page, Integer size) {
        return repository.findByIdOfAppRbac(rbacId,PageRequest.of(page,size));
    }

    @Override
    public AppMenu toggleAppMenu(String appCode, String role, String menuCode) {
        PlatformUtil.validateTenantAdmin();
        AppRbac appRbac = appRbacRepository.findByAppCodeAndRole(appCode, role,null)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Request AppRbac with AppCode %s and role %s not found", appCode, role)));
        AppMenu appMenu= repository.findByIdOfAppRbacAndMenuCode(appRbac.getId(),menuCode)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Request AppMenu with AppRbac Id %s and MenuCode %s not found", appRbac.getId(), menuCode)));
        if(appMenu.getIsEnabled().equals(Boolean.TRUE)){
            appMenu.setIsEnabled(Boolean.FALSE);
        }
        else{
            appMenu.setIsEnabled(Boolean.TRUE);
        }
        return repository.save(appMenu);

    }

    @Override
    public AppMenu toggleAppSubMenu(String appCode, String role, String menuCode, String subMenuCode) {
        PlatformUtil.validateTenantAdmin();
        AppMenu appMenu = repository.findByAppCodeAndRoleOfAppRbacAndMenuCode(appCode,role,menuCode)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Request AppMenu with AppCode %s and role %s of AppRbac and MenuCode %s not found", appCode, role,menuCode)));
        AppMenu appSubMenu=repository.findByIdAndMenuCode(appMenu.getId(),subMenuCode)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Request AppSubMenu with AppMenu Id %s and SubMenuCode %s not found", appMenu.getId(), subMenuCode)));
        if(appSubMenu.getIsEnabled().equals(Boolean.TRUE)){
            appSubMenu.setIsEnabled(Boolean.FALSE);
        }
        else{
            appSubMenu.setIsEnabled(Boolean.TRUE);
        }
        return repository.save(appSubMenu);
    }

    @Override
    public void toggleAppMenusInBulk(String appCode, String role, Set<AppMenu> appMenuSet) {
        PlatformUtil.validateIfTenantAdminOrPlatformAdmin();
        Set<String> topMostAppMenuCodes = appMenuSet.stream()
                .map(AppMenu::getMenuCode)
                .collect(Collectors.toSet());
        Map<String,AppMenu> appMenuMap = appMenuSet.stream()
                .collect(Collectors.toMap(AppMenu::getMenuCode, Function.identity()));

        Set<AppMenu> fetchedAppMenuSet = repository.findByAppCodeAndRoleAndTopMostMenuCodes(appCode,role,topMostAppMenuCodes);
        fetchedAppMenuSet.forEach(fetchedAppMenu -> {
            String appMenuCode = fetchedAppMenu.getMenuCode();
            AppMenu toBeUpdatedAppMenu = appMenuMap.getOrDefault(appMenuCode, null);
            if (toBeUpdatedAppMenu != null) {
                fetchedAppMenu.patchUpdateForToggle(toBeUpdatedAppMenu);
            }
        });
        repository.saveAll(fetchedAppMenuSet);
    }

    @Override
    public Page<AppMenu> getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu(String appCode, Integer page, Integer size, String role, String menuCode, String subMenuCode) {
        return repository.findByAppCodeAppRoleAndMenuCodeAndSubMenu(appCode,role,menuCode,subMenuCode,PageRequest.of(page,size));
    }


}
