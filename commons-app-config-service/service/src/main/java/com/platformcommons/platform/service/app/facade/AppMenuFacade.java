package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface AppMenuFacade {

    Long save(AppMenuDTO appMenuDTO );

    AppMenuDTO update(AppMenuDTO appMenuDTO,Boolean isPatch );

    PageDTO<AppMenuDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AppMenuDTO getById(Long id);

    Long addSubMenuToAppMenu(Long appMenuId, AppMenuDTO appSubMenuDTO);

    PageDTO<AppMenuDTO> getAppMenuByAppCodeAppRoleAndMenuCode(String appCode, Integer page, Integer size, String role, String menuCode);

    PageDTO<AppMenuDTO> getAppMenuByIdOfAppRbac(Long rbacId, Integer page, Integer size);

    AppMenuDTO toggleAppMenu(String appCode, String role, String menuCode);

    void toggleAppMenusInBulk(String appCode, String role, Set<AppMenuDTO> appMenuSet);

    AppMenuDTO toggleAppSubMenu(String appCode, String role, String menuCode, String subMenuCode);

    PageDTO<AppMenuDTO> getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu(String appCode, Integer page, Integer size, String role, String menuCode, String subMenuCode);

}
