package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppMenu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface AppMenuService {

    Long save(AppMenu appMenu );

    AppMenu update(AppMenu appMenu ,Boolean isPatch);

    Page<AppMenu> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    AppMenu getById(Long id);

    Long addSubMenuToAppMenu(Long appMenuId, AppMenu appSubMenu);

    Page<AppMenu> getAppMenuByAppCodeAppRoleAndMenuCode(String appCode, Integer page, Integer size, String role, String menuCode);

    Page<AppMenu> getAppMenuByIdOfAppRbac(Long rbacId, Integer page, Integer size);

    AppMenu toggleAppMenu(String appCode, String role, String menuCode);

    AppMenu toggleAppSubMenu(String appCode, String role, String menuCode, String subMenuCode);

    void toggleAppMenusInBulk(String appCode, String role,Set<AppMenu> appMenuSet);

    Page<AppMenu> getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu(String appCode, Integer page, Integer size, String role, String menuCode, String subMenuCode);


}
