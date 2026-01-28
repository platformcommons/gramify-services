package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.AppMenuService;
import com.platformcommons.platform.service.app.application.AppOperationService;
import com.platformcommons.platform.service.app.application.AppRbacAsync;
import com.platformcommons.platform.service.app.application.AppRbacService;
import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.domain.AppOperation;
import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.app.domain.repo.AppRbacRepository;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
@Transactional
public class AppRbacAsyncImpl implements AppRbacAsync {

    @Autowired
    AppMenuService appMenuService;

    @Autowired
    AppOperationService appOperationService;

    @Autowired
    private AppRbacRepository appRbacRepository;

    @Autowired
    AppRbacService appRbacService;


    public void syncAppRbacOperations(Boolean syncHigherRbacOperation, Boolean syncLowerRbacOperation, AppOperationDTO savedAppOperationDTO,
                                      boolean isReadPermissionUpdated, boolean isCreatePermissionUpdated, boolean isUpdatePermissionUpdated,
                                      boolean isDeletePermissionUpdated, boolean isDownloadPermissionUpdated, PlatformToken platformToken) {
        if (isCreatePermissionUpdated || isReadPermissionUpdated || isUpdatePermissionUpdated || isDeletePermissionUpdated || isDownloadPermissionUpdated) {
            SecurityContextHolder.getContext().setAuthentication(platformToken);
            AppOperation appOperation = appOperationService.getById(savedAppOperationDTO.getId());
            if (appOperation.getAppRbac().getPriority() != null) {
                if (syncLowerRbacOperation != null && syncLowerRbacOperation) {
                    Set<AppOperation> lowerRbacAppOperations = appOperationService.getLowerRbacAppOperation(appOperation.getAppRbac().getAppCode(), appOperation.getEntity(), appOperation.getAppRbac().getPriority());
                    syncAndUpdateAppOperation(appOperation, isReadPermissionUpdated, isCreatePermissionUpdated,
                            isUpdatePermissionUpdated, isDeletePermissionUpdated, isDownloadPermissionUpdated, lowerRbacAppOperations, Boolean.FALSE);
                }
                if (syncHigherRbacOperation != null && syncHigherRbacOperation) {
                    Set<AppOperation> higherRbacAppOperations = appOperationService.getHigherRbacAppOperation(appOperation.getAppRbac().getAppCode(), appOperation.getEntity(), appOperation.getAppRbac().getPriority());
                    syncAndUpdateAppOperation(appOperation, isReadPermissionUpdated, isCreatePermissionUpdated,
                            isUpdatePermissionUpdated, isDeletePermissionUpdated, isDownloadPermissionUpdated, higherRbacAppOperations, Boolean.TRUE);
                }

            }
        }
    }


    private void updateAppRbacMenu(Set<AppRbac> appRbacSet, AppRbac savedAppRbac, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu) {
        for (AppRbac appRbac : appRbacSet) {
            Map<String, AppMenu> savedMenuMap = new HashMap<>();
            for (AppMenu appMenu : savedAppRbac.getAppMenuList()) {
                savedMenuMap.put(appMenu.getMenuCode(), appMenu);
            }

            for (AppMenu appMenu : appRbac.getAppMenuList()) {
                AppMenu savedAppMenu = savedMenuMap.get(appMenu.getMenuCode());
                if (savedAppMenu != null) {
                    if (syncLowerRbacMenu && savedAppMenu.isUpdated() && Boolean.FALSE.equals(savedAppMenu.getIsEnabled())) {
                        appMenu.setIsEnabled(false);
                    }
                    if (syncHigherRbacMenu && savedAppMenu.isUpdated() && Boolean.TRUE.equals(savedAppMenu.getIsEnabled())) {
                        appMenu.setIsEnabled(true);
                    }
                    updateChildSubMenu(appMenu, savedAppMenu, syncHigherRbacMenu, syncLowerRbacMenu);
                }
            }
        }
        appRbacRepository.saveAll(appRbacSet);
    }

    @Override
    @Async
    public void validateAndUpdateAppOperation(AppOperationDTO sourceDTO, AppOperationDTO targetDTO, Boolean syncHigherRbacOperation, Boolean syncLowerRbacOperation, PlatformToken platformToken) {
        if (syncHigherRbacOperation != null || syncLowerRbacOperation != null) {
            boolean isReadPermissionUpdated = false;
            boolean isCreatePermissionUpdated = false;
            boolean isUpdatePermissionUpdated = false;
            boolean isDeletePermissionUpdated = false;
            boolean isDownloadPermissionUpdated = false;

            isReadPermissionUpdated = getPermissionChangeStatus(targetDTO.getRead(), sourceDTO.getRead());
            isCreatePermissionUpdated = getPermissionChangeStatus(targetDTO.getCreate(), sourceDTO.getCreate());
            isUpdatePermissionUpdated = getPermissionChangeStatus(targetDTO.getUpdate(), sourceDTO.getUpdate());
            isDeletePermissionUpdated = getPermissionChangeStatus(targetDTO.getDelete(), sourceDTO.getDelete());
            isDownloadPermissionUpdated = getPermissionChangeStatus(targetDTO.getDownload(), sourceDTO.getDownload());

            syncAppRbacOperations(syncHigherRbacOperation, syncLowerRbacOperation, targetDTO, isReadPermissionUpdated, isCreatePermissionUpdated,
                    isUpdatePermissionUpdated, isDeletePermissionUpdated, isDownloadPermissionUpdated, platformToken);
        }
    }

    @Override
    @Async
    public void validateAndUpdateAppMenu(AppRbacDTO oldAppRbac, PlatformToken platformToken, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu) {
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        AppRbac appRbac = appRbacService.getById(oldAppRbac.getId());
        if (getAppRbacWithChangeStatus(oldAppRbac, appRbac)) {

            if (syncLowerRbacMenu != null && syncLowerRbacMenu) {
                Set<AppRbac> appRbacOfLowerPriority = appRbacRepository.getAppRbacOfLowerPriority(
                        appRbac.getAppCode(), appRbac.getPriority(), PlatformSecurityUtil.getCurrentTenantId()
                );
                if (!appRbacOfLowerPriority.isEmpty()) {
                    updateAppRbacMenu(appRbacOfLowerPriority, appRbac, false, syncLowerRbacMenu);
                }
            }

            if (syncHigherRbacMenu != null && syncHigherRbacMenu) {
                Set<AppRbac> appRbacOfHigherPriority = appRbacRepository.getAppRbacOfHigherPriority(
                        appRbac.getAppCode(), appRbac.getPriority(), PlatformSecurityUtil.getCurrentTenantId()
                );
                if (!appRbacOfHigherPriority.isEmpty()) {
                    updateAppRbacMenu(appRbacOfHigherPriority, appRbac, syncHigherRbacMenu, false);
                }
            }
        }


    }


    private boolean getAppRbacWithChangeStatus(AppRbacDTO oldAppRbacDTO, AppRbac currentAppRbac) {
        Map<Long, AppMenuDTO> oldAppRbacMap = new HashMap<>();
        AtomicBoolean isMenuChanged = new AtomicBoolean(false);
        oldAppRbacDTO.getAppMenuList().forEach(appMenu -> oldAppRbacMap.put(appMenu.getId(), appMenu));

        for (AppMenu currentMenu : currentAppRbac.getAppMenuList()) {
            if (markUpdatedMenus(currentMenu, oldAppRbacMap)) {
                isMenuChanged.set(true);
            }
        }
        return isMenuChanged.get();
    }

    private boolean markUpdatedMenus(AppMenu currentMenu, Map<Long, AppMenuDTO> oldAppRbacMap) {
        Long appMenuId = currentMenu.getId();
        boolean isAppMenuEnableStatusChanged = false;

        if (oldAppRbacMap.containsKey(appMenuId)) {
            AppMenuDTO oldMenuDTO = oldAppRbacMap.get(appMenuId);

            // If isEnabled has changed, mark as updated
            if (oldMenuDTO.getIsEnabled() != null && !oldMenuDTO.getIsEnabled().equals(currentMenu.getIsEnabled())) {
                currentMenu.setUpdated(true);
                isAppMenuEnableStatusChanged = true;
            }

            // Recursively check all submenus
            if (currentMenu.getSubMenu() != null && oldMenuDTO.getSubMenu() != null) {
                // Create a new map for the current submenu level
                Map<Long, AppMenuDTO> oldSubMenuMap = new HashMap<>();
                for (AppMenuDTO oldSubMenu : oldMenuDTO.getSubMenu()) {
                    oldSubMenuMap.put(oldSubMenu.getId(), oldSubMenu);
                }

                for (AppMenu currentSubMenu : currentMenu.getSubMenu()) {
                    if (markUpdatedMenus(currentSubMenu, oldSubMenuMap)) {
                        isAppMenuEnableStatusChanged = true;
                    }
                }
            }
        }
        return isAppMenuEnableStatusChanged;
    }

    private boolean getPermissionChangeStatus(Boolean newPermission, Boolean existingPermission) {
        if (newPermission == null && existingPermission == null) {
            return false;
        }
        if (newPermission == null || existingPermission == null) {
            return true;
        }
        return !newPermission.equals(existingPermission);
    }


    private void updateChildSubMenu(AppMenu appMenu, AppMenu savedAppMenu, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu) {
        if (savedAppMenu.getSubMenu() == null || appMenu.getSubMenu() == null) {
            return;
        }

        Map<String, AppMenu> savedSubMenuMap = new HashMap<>();
        for (AppMenu subMenu : savedAppMenu.getSubMenu()) {
            savedSubMenuMap.put(subMenu.getMenuCode(), subMenu);
        }

        for (AppMenu childMenu : appMenu.getSubMenu()) {
            AppMenu savedChildMenu = savedSubMenuMap.get(childMenu.getMenuCode());
            if (savedChildMenu != null) {
                if (savedChildMenu.isUpdated() && syncLowerRbacMenu && Boolean.FALSE.equals(savedChildMenu.getIsEnabled())) {
                    childMenu.setIsEnabled(false);
                }
                if (savedChildMenu.isUpdated() && syncHigherRbacMenu && Boolean.TRUE.equals(savedChildMenu.getIsEnabled())) {
                    childMenu.setIsEnabled(true);
                }
                updateChildSubMenu(childMenu, savedChildMenu, syncHigherRbacMenu, syncLowerRbacMenu);
            }
        }
    }

    private void syncAndUpdateAppOperation(AppOperation appOperation, boolean isReadPermissionUpdated, boolean isCreatePermissionUpdated, boolean isUpdatePermissionUpdated,
                                           boolean isDeletePermissionUpdated, boolean isDownloadPermissionUpdated, Set<AppOperation> appOperations, Boolean enabledStatus) {

        appOperations.forEach(rbacAppOperation -> {
            if (isCreatePermissionUpdated && appOperation.getCreate() != null && appOperation.getCreate().equals(enabledStatus)) {
                rbacAppOperation.setCreate(enabledStatus);
            }
            if (isReadPermissionUpdated && appOperation.getRead() != null && appOperation.getRead().equals(enabledStatus)) {
                rbacAppOperation.setRead(enabledStatus);
            }
            if (isUpdatePermissionUpdated && appOperation.getUpdate() != null && appOperation.getUpdate().equals(enabledStatus)) {
                rbacAppOperation.setUpdate(enabledStatus);
            }
            if (isDeletePermissionUpdated && appOperation.getDelete() != null && appOperation.getDelete().equals(enabledStatus)) {
                rbacAppOperation.setDelete(enabledStatus);
            }
            if (isDownloadPermissionUpdated && appOperation.getDownload() != null && appOperation.getDownload().equals(enabledStatus)) {
                rbacAppOperation.setDownload(enabledStatus);
            }
        });
        appOperationService.updateRbacOperation(appOperations);
        log.debug("--> syncAndUpdateAppOperation, Successfully updated AppOperation");
    }
}