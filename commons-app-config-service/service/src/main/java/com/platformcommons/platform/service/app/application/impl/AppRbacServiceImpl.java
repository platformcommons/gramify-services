package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.AppMenuService;
import com.platformcommons.platform.service.app.application.AppRbacAsync;
import com.platformcommons.platform.service.app.application.constant.ServiceConstant;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.domain.AppOperation;
import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.app.dto.RoleDTO;
import com.platformcommons.platform.service.app.facade.client.util.CommonsReportUtil;
import com.platformcommons.platform.service.utility.JPAUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.platformcommons.platform.service.app.application.AppRbacService;
import com.platformcommons.platform.service.app.domain.repo.AppRbacRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

@Service
@Slf4j
public class AppRbacServiceImpl implements AppRbacService {


    @Autowired
    private AppRbacRepository repository;

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    @Autowired
    private AppMenuService appMenuService;

    @Autowired
    private AppRbacAsync appRbacAsync;

    @Override
    public AppRbac save(AppRbac appRbac ){
        PlatformUtil.validateTenantAdmin();
        appRbac.init();
        return repository.save(appRbac);
    }

    @Override
    public AppRbac patchUpdate(AppRbac appRbac, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu) {
        AppRbac fetchedAppRbac = repository.findById(appRbac.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Request AppRbac with  %d  not found", appRbac.getId())));
        fetchedAppRbac.patch(appRbac);
        return repository.save(fetchedAppRbac);
    }


    private boolean markUpdatedMenus(AppMenu oldMenu, Map<Long, AppMenu> updatedMenuMap) {
        Long appMenuId = oldMenu.getId();
        boolean isAppMenuEnableStatusChanged = false;


        if (updatedMenuMap.containsKey(appMenuId)) {
            AppMenu updatedMenu = updatedMenuMap.get(appMenuId);

            // If isEnabled has changed, mark as updated
            if (updatedMenu.getIsEnabled() != null && !updatedMenu.getIsEnabled().equals(oldMenu.getIsEnabled())) {
                oldMenu.setUpdated(true);
                isAppMenuEnableStatusChanged = true;
            }

            // Recursively check all submenus
            if (oldMenu.getSubMenu() != null && updatedMenu.getSubMenu()!=null) {
                // Create a new map for the current submenu level
                Map<Long, AppMenu> updatedSubMenuMap = new HashMap<>();
                for (AppMenu updatedSubMenu : updatedMenu.getSubMenu()) {
                    updatedSubMenuMap.put(updatedSubMenu.getId(), updatedSubMenu);
                }

                for (AppMenu oldSubMenu : oldMenu.getSubMenu()) {
                    if (markUpdatedMenus(oldSubMenu, updatedSubMenuMap)) {
                        isAppMenuEnableStatusChanged = true;
                    }
                }
            }
        }
        return isAppMenuEnableStatusChanged;
    }

    @Override
    public AppRbac getAppRbacByAppcodeAndContextAndHierarchy(String appCode, String context, String entityType,String marketContext) {
        return repository.findByAppCodeAndContext(appCode, context,entityType,marketContext)
                .orElseGet(() -> getDefaultAppRbacWithAppCodeAndContext(appCode,context,entityType));
    }

    @Override
    public Page<AppRbac> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        AppRbac fetchedAppRbac = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request AppRbac with  %d  not found",id)));
        PlatformUtil.validateLoginTenant(fetchedAppRbac.getCreatedByTenant());
        fetchedAppRbac.deactivate(reason);
        repository.save(fetchedAppRbac);
    }


    @Override
    public AppRbac getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request AppRbac with  %d  not found",id)));
    }

    @Override
    public AppRbac getByAppCodeAndRole(String appCode,String role, String marketContext) {
        AppRbac appRbac;
        if(PlatformSecurityUtil.isTenantAdmin()){
            if(role !=null){
                appRbac = repository.findByAppCodeAndRole(appCode, role,marketContext)
                        .orElseThrow(()-> new NotFoundException(
                                String.format("Request AppRbac with AppCode %s and role %s not found", appCode, role)));
            }else{
                Optional<AppRbac> appRbacForAdmin = repository.findByAppCodeAndRole(appCode,
                        String.format("role.%s.admin",PlatformSecurityUtil.getCurrentTenantLogin()),marketContext);
                appRbac = appRbacForAdmin.orElseGet(() -> getDefaultAppRbac(appCode,marketContext));
            }

        }else{
            appRbac = getRBACViaCurrentLoggedInUser(appCode, marketContext);
        }
        return appRbac;
    }

    @Override
    public AppRbac getOrCreatePrimaryRbacByAppCode(String appCode, String marketContext) {
        Optional<AppRbac> optionalAppRbac;
        String tenantLogin = PlatformSecurityUtil.getCurrentTenantLogin();
        String adminRole = String.format("role.%s.admin",tenantLogin);
        if (marketContext != null){
            optionalAppRbac = repository.findDefaultByAppCodeAndMarketContext(appCode,marketContext);
        } else {
            optionalAppRbac = repository.findByAppCodeAndRole(appCode,adminRole,null);
        }
        return optionalAppRbac.orElseGet(() -> createPrimaryRbac(appCode,marketContext,adminRole));
    }

    public AppRbac createPrimaryRbac(String appCode,String marketContext,String role) {
        AppRbac appRbac = null;
        AppRbac defaultRbac = getDefaultAppRbac(appCode,marketContext);
        if(appCode != null) {
            appRbac = AppRbac.builder()
                    .id(0L)
                    .appCode(appCode)
                    .role(role)
                    .priority(1)
                    .isDefault(Boolean.FALSE)
                    .marketContext(marketContext)
                    .appMenuList(deLinkAppMenuSetFromDataBase(defaultRbac.getAppMenuList()))
                    .appOperationList(deLinkAppOperationSetFromDataBase(defaultRbac.getAppOperationList()))
                    .build();
            if(marketContext != null) {
                appRbac.setIsMarketDefault(Boolean.TRUE);
            }
            appRbac = repository.save(appRbac);
        }
        return appRbac;
    }

    @Override
    public AppRbac getOrCreateAppRbacByAppCodeAndContext(String appCode, String context, String entityType, String marketContext) {
        AppRbac appRbac;
        PlatformUtil.validateTenantAdmin();
        Optional<AppRbac> optionalAppRbacForTenant = repository.findByAppCodeAndContext(appCode,context,entityType,marketContext);
        appRbac = optionalAppRbacForTenant.orElseGet(()->createRbacWithContextFromDefaultRbac(appCode,context,entityType,marketContext));
        return appRbac;
    }


    @Override
    public AppRbac getAppRbacByAppCodeAndContext(String appCode, String context, String entityType, String marketContext) {
        Optional<AppRbac> optionalAppRbacForTenant = repository.findByAppCodeAndContext(appCode,context,entityType,marketContext);
        return optionalAppRbacForTenant.orElse(null);
    }

    @Override
    public AppRbac createAppRbacByAppCodeAndContext(AppRbac appRbac) {
        if(StringUtils.isBlank(appRbac.getContext()) || StringUtils.isBlank(appRbac.getAppCode())) {
            throw new IllegalArgumentException("Context, isDefault, or app code must not be null or empty.");
        }

        if (Objects.equals(appRbac.getIsDefault(),Boolean.TRUE)) {
            PlatformUtil.validatePlatformAdmin();
        }
        else {
            PlatformUtil.validateTenantAdmin();
        }

        appRbac.init();
        return repository.save(appRbac);
    }

    @Override
    public void patchAppRbacInBulk(Set<AppRbac> appRbacs) {
        Set<Long> rbacIds = appRbacs.stream()
                .map(AppRbac::getId)
                .collect(Collectors.toSet());

        List<AppRbac> fetchedAppRbacs = repository.findAllById(rbacIds);
        Map<Long, AppRbac> fetchedAppRbacMap = fetchedAppRbacs.stream()
                .collect(Collectors.toMap(AppRbac::getId, rbac -> rbac));

        appRbacs.forEach(appRbac -> {
            AppRbac fetchedAppRbac = fetchedAppRbacMap.get(appRbac.getId());
            if (fetchedAppRbac != null) {
                fetchedAppRbac.patch(appRbac);
            }
        });
        repository.saveAll(fetchedAppRbacs);
    }

    @Override
    public void addAppOperationToAppRbac(Long appRbacId, AppOperation appOperation) {
        PlatformUtil.validateTenantAdmin();
        AppRbac fetchedAppRbac = repository.findById(appRbacId)
                .orElseThrow(()-> new NotFoundException(String.format("Request AppRbac with  %d  not found",appRbacId)));
        fetchedAppRbac.getAppOperationList().add(appOperation);
        appOperation.setAppRbac(fetchedAppRbac);
        repository.save(fetchedAppRbac);
    }

    @Override
    public void addAppMenuToAppRbac(Long appRbacId, AppMenu appMenu) {
        PlatformUtil.validateTenantAdmin();
        AppRbac fetchedAppRbac = repository.findById(appRbacId)
                .orElseThrow(()-> new NotFoundException(String.format("Request AppRbac with  %d  not found",appRbacId)));
        fetchedAppRbac.getAppMenuList().add(appMenu);
        appMenu.setAppRbac(fetchedAppRbac);
        appMenu.setIsEnabled(Boolean.TRUE);
        repository.save(fetchedAppRbac);
    }

    @Override
    public void syncDefaultRbacToAdminRbacs(String appCode, Set<String> adminRoleCodes) {
        PlatformUtil.validatePlatformAdmin();
        AppRbac defaultAppRbac = getDefaultAppRbac(appCode,null);
        adminRoleCodes = adminRoleCodes.stream()
                .filter(it->it.contains(".admin"))
                .collect(Collectors.toSet());
        Set<AppRbac> adminAppRbacs = repository.findByAppCodeAndRoleCodesAndNonDefault(appCode,adminRoleCodes);

        adminAppRbacs.forEach(adminAppRbac -> {
            Boolean replicateOperationAccess = Boolean.TRUE;
            Boolean replicateAppMenuSetting = Boolean.TRUE;
            PlatformSecurityUtil.mimicTenantWithToken(adminAppRbac.getTenantId(),1L,PlatformSecurityUtil.getToken());
            syncAppMenuSetFromSourceAppMenuSet(null,adminAppRbac.getAppMenuList(),defaultAppRbac.getAppMenuList(),replicateAppMenuSetting);
            adminAppRbac.getAppMenuList().forEach(it->it.setAppRbac(adminAppRbac));
            syncAppOperationListFromSourceAppOperationList(adminAppRbac.getAppOperationList(),defaultAppRbac.getAppOperationList(),
                    replicateOperationAccess);
            adminAppRbac.getAppOperationList().forEach(it->it.setAppRbac(adminAppRbac));
            repository.save(adminAppRbac);
        });
    }

    @Override
    public void syncDefaultContextRbacToContextRbacs(String appCode, String context, String entityType) {
        PlatformUtil.validatePlatformAdmin();
        AppRbac defaultAppRbac = getDefaultAppRbacWithAppCodeAndContext(appCode, context,entityType);
        Set<AppRbac> contextAppRbacSet = repository.findByAppCodeAndContextAndNonDefault(appCode,context,entityType);
        contextAppRbacSet.forEach(contextAppRbac -> {
            Boolean replicateOperationAccess = Boolean.TRUE;
            Boolean replicateAppMenuSetting = Boolean.TRUE;
            PlatformSecurityUtil.mimicTenantWithToken(contextAppRbac.getTenantId(), 1L, PlatformSecurityUtil.getToken());

            syncAppMenuSetFromSourceAppMenuSet(null, contextAppRbac.getAppMenuList(), defaultAppRbac.getAppMenuList(),replicateAppMenuSetting);
            contextAppRbac.getAppMenuList().forEach(it -> it.setAppRbac(contextAppRbac));

            syncAppOperationListFromSourceAppOperationList(contextAppRbac.getAppOperationList(), defaultAppRbac.getAppOperationList(), replicateOperationAccess);
            contextAppRbac.getAppOperationList().forEach(it -> it.setAppRbac(contextAppRbac));
            repository.save(contextAppRbac);
        });
    }

    @Override
    public void syncAdminRbacToChildRoles(String appCode,Set<String> adminRoleCodes) {
        PlatformUtil.validatePlatformAdmin();
        adminRoleCodes = adminRoleCodes.stream()
                .filter(it->it.contains(".admin"))
                .collect(Collectors.toSet());
        Set<AppRbac> adminAppRbacSet = repository.findByAppCodeAndRoleCodesAndNonDefault(appCode,adminRoleCodes);
        adminAppRbacSet.forEach(adminAppRbac -> {
            PlatformSecurityUtil.mimicTenantWithToken(adminAppRbac.getTenantId(),1L,PlatformSecurityUtil.getToken());
            Set<RoleDTO> tenantRoles = commonsReportUtil.getTenantRolesByTenantId(adminAppRbac.getTenantId());
            Set<String> tenantRoleCodes = tenantRoles.stream()
                    .map(RoleDTO::getCode)
                    .filter(code -> !code.equals(adminAppRbac.getRole()))
                    .collect(Collectors.toSet());

            List<AppRbac> childRolesAppRbacList = repository.findByAppCodeAndRoleCodeAndNonDefault(appCode,tenantRoleCodes);
            childRolesAppRbacList.forEach(childAppRbac -> {
                Boolean replicateOperationAccess = Boolean.FALSE;
                Boolean replicateAppMenuSetting = Boolean.FALSE;
                syncAppMenuSetFromSourceAppMenuSet(null,childAppRbac.getAppMenuList(),adminAppRbac.getAppMenuList(),replicateAppMenuSetting);
                childAppRbac.getAppMenuList().forEach(it->it.setAppRbac(childAppRbac));
                syncAppOperationListFromSourceAppOperationList(childAppRbac.getAppOperationList(),adminAppRbac.getAppOperationList(),
                        replicateOperationAccess);
                childAppRbac.getAppOperationList().forEach(it->it.setAppRbac(childAppRbac));
            });
            repository.saveAll(childRolesAppRbacList);
        });
    }

    public void syncAppOperationListFromSourceAppOperationList(Set<AppOperation> appOperationSet, Set<AppOperation> sourceAppOperationSet,
                                                               Boolean replicateOperationAccess) {
        if(sourceAppOperationSet == null || sourceAppOperationSet.isEmpty()) {
            return;
        }
        Map<String,AppOperation> appOperationMap = appOperationSet.stream()
                .collect(Collectors.toMap(AppOperation::getEntity, Function.identity(),(a,b)->a));
        sourceAppOperationSet.forEach(sourceAppOperation -> {
            String entity = sourceAppOperation.getEntity();
            if(!appOperationMap.containsKey(entity)) {
                AppOperation appOperation = buildAppOperation(sourceAppOperation,replicateOperationAccess);
                appOperation.init(null,null);
                appOperationSet.add(appOperation);
            }
        });
    }


    public void syncAppMenuSetFromSourceAppMenuSet(AppMenu parentAppMenu,Set<AppMenu> appMenuSet, Set<AppMenu> sourceAppMenuSet,
                                                   Boolean replicateAppMenuSetting) {
        if(sourceAppMenuSet == null || sourceAppMenuSet.isEmpty()) {
            return;
        }
        Map<String,AppMenu> appMenuMap = appMenuSet.stream()
                .collect(Collectors.toMap(AppMenu::getMenuCode, Function.identity(),(a,b)->a));
        sourceAppMenuSet.forEach(sourceAppMenu -> {
            String menuCode = sourceAppMenu.getMenuCode();
            AppMenu appMenu = appMenuMap.getOrDefault(menuCode,null);
            if(appMenu != null) {
                syncAppMenuSetFromSourceAppMenuSet(appMenu,appMenu.getSubMenu(),sourceAppMenu.getSubMenu(),replicateAppMenuSetting);
            }
            else {
                appMenu = buildAppMenu(sourceAppMenu,replicateAppMenuSetting);
                appMenu.init(null,null,parentAppMenu);
                appMenuSet.add(appMenu);
            }
        });
    }

    @Override
    public AppRbac getAppRbacByAppCodeAndMarketAndPrimaryRbacCheck(String appCode, String marketContext) {
        AppRbac appRbac;
        if (marketContext != null) {
            appRbac = getAppRbacWithMarketDefaultCheck(appCode, marketContext);
        } else {
            appRbac = getAppRbacWithAdminRbacCheck(appCode);
        }
        return appRbac;
    }


    public AppRbac getAppRbacWithAdminRbacCheck(String appCode) {
        AppRbac appRbac;
        if(PlatformSecurityUtil.isTenantAdmin()){
            Optional<AppRbac> appRbacForAdmin = repository.findByAppCodeAndRole(appCode,
                    String.format("role.%s.admin",PlatformSecurityUtil.getCurrentTenantLogin()),null);
            appRbac = appRbacForAdmin.orElseGet(() -> getDefaultAppRbac(appCode,null));
        }else{
            appRbac = getRbacForLoggedInUserWithAdminRbacCheck(appCode);
        }
        return appRbac;
    }

    public AppRbac getAppRbacWithMarketDefaultCheck(String appCode, String marketContext) {
        AppRbac appRbac;
        if(PlatformSecurityUtil.isTenantAdmin()){
            Optional<AppRbac> appRbacForAdmin = repository.findByAppCodeAndRole(appCode,
                    String.format("role.%s.admin",PlatformSecurityUtil.getCurrentTenantLogin()), marketContext);
            if (!appRbacForAdmin.isPresent()){
                appRbac = getDefaultAppRbac(appCode, marketContext);
            }else{
                appRbac = appRbacForAdmin.get();
                if (!Objects.equals(appRbac.getIsMarketDefault(),Boolean.TRUE)){
                    compareAppMenusFromMarketDefaultAppRbac(appRbac,appCode,marketContext);
                }
            }
        }else{
            appRbac = getRbacForLoggedInUserWithMarketDefaultCheck(appCode, marketContext);
        }
        return appRbac;
    }

    public void compareAppMenusFromMarketDefaultAppRbac(AppRbac appRbac,String appCode,String marketContext) {
        Optional<AppRbac> optionalMarketDefaultAppRbac =
                repository.findDefaultByAppCodeAndMarketContext(appCode, marketContext);
        if(optionalMarketDefaultAppRbac.isPresent()) {
            AppRbac marketDefaultAppRbac = optionalMarketDefaultAppRbac.get();
            updateAppMenuSetFromSourceAppMenuForIsEnabled(appRbac.getAppMenuList(), marketDefaultAppRbac.getAppMenuList());
        }
    }

    public AppRbac getDefaultAppRbac(String appCode,String marketContext) {
        Optional<AppRbac> appRbacOptional = Optional.empty();
        if(appCode != null) {
            if(marketContext != null) {
                appRbacOptional  = repository.findDefaultByAppCodeAndMarketContext(appCode,marketContext);
            }
            if(!appRbacOptional.isPresent()) {
                appRbacOptional = repository.findDefaultByAppCode(appCode);
            }
        }
        return appRbacOptional.orElseThrow(
                ()-> new NotFoundException(String.format("Default AppRbac with AppCode %s not found", appCode)));
    }

    public AppRbac getDefaultAppRbacWithAppCodeAndContext(String appCode,String context,String entityType) {
        return repository.findDefaultByAppCodeAndContext(appCode,context,entityType)
                .orElseThrow(()-> new NotFoundException(String.format("Default AppRbac with AppCode %s And Context %s not found", appCode,context)));
    }

    public AppRbac getRBACViaCurrentLoggedInUser(String appCode, String marketContext) {
        Set<String> roles = PlatformSecurityUtil.getAuthorities();
        return repository.findByAppCodeAndMarketContext(appCode,roles,marketContext,PageRequest.of(0,1,
                        JPAUtility.convertToSort(ServiceConstant.PRIORITY,ServiceConstant.ORDER_BY_ASC))).stream().findFirst()
                        .orElseThrow(()-> new NotFoundException(String.format("Request AppRbac with AppCode %s not found", appCode)));
    }


    public AppRbac getRbacForLoggedInUserWithMarketDefaultCheck(String appCode, String marketContext) {
        Set<String> roles = PlatformSecurityUtil.getAuthorities();
        AppRbac appRbac = repository.findByAppCodeAndMarketContext(appCode,roles,marketContext,PageRequest.of(0,1,
                        JPAUtility.convertToSort(ServiceConstant.PRIORITY,ServiceConstant.ORDER_BY_ASC)))
                .stream()
                .findFirst()
                .orElseThrow(()-> new NotFoundException(String.format("Request AppRbac with AppCode %s not found", appCode)));

        compareAppMenusFromMarketDefaultAppRbac(appRbac,appCode,marketContext);
        return appRbac;
    }

    public AppRbac getRbacForLoggedInUserWithAdminRbacCheck(String appCode) {
        Set<String> roles = PlatformSecurityUtil.getAuthorities();
        AppRbac appRbac = repository.findByAppCodeAndMarketContext(appCode,roles,null,PageRequest.of(0,1,
                        JPAUtility.convertToSort(ServiceConstant.PRIORITY,ServiceConstant.ORDER_BY_ASC)))
                .stream()
                .findFirst()
                .orElseThrow(()-> new NotFoundException(String.format("Request AppRbac with AppCode %s not found", appCode)));

        Optional<AppRbac> adminAppRbacOptional = repository.findByAppCodeAndRole(appCode, String.format("role.%s.admin",
                PlatformSecurityUtil.getCurrentTenantLogin()),null);
        if(adminAppRbacOptional.isPresent()) {
            AppRbac adminAppRbac = adminAppRbacOptional.get();
            updateAppMenuSetFromSourceAppMenuForIsEnabled(appRbac.getAppMenuList(),adminAppRbac.getAppMenuList());
        }
        return appRbac;
    }

    public void updateAppMenuSetFromSourceAppMenuForIsEnabled(Set<AppMenu> appMenuSet,Set<AppMenu> sourceAppMenuSet) {
        if(sourceAppMenuSet != null && appMenuSet != null) {
            Map<String,AppMenu> sourceAppMenuMap = sourceAppMenuSet.stream()
                    .collect(Collectors.toMap(AppMenu::getMenuCode, Function.identity(),(a,b)->a));
            appMenuSet.forEach(appMenu -> {
                String menuCode = appMenu.getMenuCode();
                if(sourceAppMenuMap.containsKey(menuCode)) {
                    updateAppMenuFromSourceAppMenuForIsEnabled(appMenu,sourceAppMenuMap.get(menuCode));
                }
            });
        }
    }

    public void updateAppMenuFromSourceAppMenuForIsEnabled(AppMenu appMenu, AppMenu sourceAppMenu){
        if(sourceAppMenu != null && appMenu != null) {
            appMenu.setIsEnabled(Objects.equals(sourceAppMenu.getIsEnabled(),Boolean.FALSE) ? Boolean.FALSE : appMenu.getIsEnabled());
            updateAppMenuSetFromSourceAppMenuForIsEnabled(appMenu.getSubMenu(),sourceAppMenu.getSubMenu());
        }
    }

    @Override
    public AppRbac copy(Long copyFromRbac, AppRbac newAppRbac) {
        PlatformUtil.validateTenantAdmin();
        AppRbac appRbac = this.getById(copyFromRbac);
        if(!Objects.equals(appRbac.getAppCode(), newAppRbac.getAppCode())){
            throw new InvalidInputException("AppCodes not matched");
        }

        Boolean exists = repository.existsByAppCodeAndRole(newAppRbac.getAppCode(), newAppRbac.getRole(), newAppRbac.getMarketContext());
        if (exists) {
            throw new DuplicateResourceException(String.format("App Rbac for the role - %s already exists In App with AppCode - %s", newAppRbac.getRole(), newAppRbac.getAppCode()));
        }

        newAppRbac.setAppMenuList(deLinkAppMenuSetFromDataBase(appRbac.getAppMenuList()));
        newAppRbac.setAppOperationList(deLinkAppOperationSetFromDataBase(appRbac.getAppOperationList()));
        newAppRbac.setIsDefault(Boolean.FALSE);
        newAppRbac.setIsMarketDefault(Boolean.FALSE);
        newAppRbac.init();
        return this.save(newAppRbac);
    }

    @Override
    public AppRbac createRbacFromDefaultRbac(String appCode, String role, String marketContext) {
        Boolean exists = repository.existsByAppCodeAndRole(appCode,role,marketContext);
        if(exists) {
            throw new DuplicateResourceException(String.format("App Rbac for the role - %s already exists In App with AppCode - %s",role,appCode));
        }
        AppRbac defaultRbac = getDefaultAppRbac(appCode,marketContext);
        AppRbac appRbac = AppRbac.builder()
                .id(0L)
                .appCode(appCode)
                .isDefault(Boolean.FALSE)
                .role(role)
                .priority(1)
                .marketContext(marketContext)
                .isMarketDefault(Boolean.FALSE)
                .appMenuList(deLinkAppMenuSetFromDataBase(defaultRbac.getAppMenuList()))
                .appOperationList(deLinkAppOperationSetFromDataBase(defaultRbac.getAppOperationList()))
                .build();
        appRbac.init();
        return repository.save(appRbac);
    }

    public AppRbac createRbacWithContextFromDefaultRbac(String appCode, String context,String entityType,String marketContext) {
        AppRbac defaultRbac = getDefaultAppRbacWithAppCodeAndContext(appCode,context,entityType);
        AppRbac appRbac = AppRbac.builder()
                .id(0L)
                .appCode(appCode)
                .isDefault(Boolean.FALSE)
                .priority(1)
                .context(context)
                .entityType(entityType)
                .marketContext(marketContext)
                .appMenuList(deLinkAppMenuSetFromDataBase(defaultRbac.getAppMenuList()))
                .appOperationList(deLinkAppOperationSetFromDataBase(defaultRbac.getAppOperationList()))
                .build();
        appRbac.init();
        return repository.save(appRbac);
    }


    public Set<AppMenu> deLinkAppMenuSetFromDataBase(Set<AppMenu> appMenuSet) {
        if(appMenuSet == null) {
            return null;
        }
        return appMenuSet.stream()
                .map(it->buildAppMenu(it,Boolean.TRUE))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<AppOperation> deLinkAppOperationSetFromDataBase(Set<AppOperation> appOperationSet) {
        if(appOperationSet == null) {
            return null;
        }
        return appOperationSet.stream()
                .map(it->buildAppOperation(it,Boolean.TRUE))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    private AppMenu buildAppMenu(AppMenu oldAppMenu, Boolean replicateIsEnabled){
        AppMenu appMenu= AppMenu.builder()
                .id(0L)
                .isEnabled(replicateIsEnabled !=null && replicateIsEnabled.equals(Boolean.TRUE) ? oldAppMenu.getIsEnabled() :Boolean.FALSE)
                .isRoot(oldAppMenu.getIsRoot())
                .label(oldAppMenu.getLabel())
                .menuCode(oldAppMenu.getMenuCode())
                .sequence(oldAppMenu.getSequence())
                .route(oldAppMenu.getRoute())
                .menuType(oldAppMenu.getMenuType())
                .icon(oldAppMenu.getIcon())
                .canEdit(oldAppMenu.getCanEdit())
                .displayAppMenu(oldAppMenu.getDisplayAppMenu())
                .displaySubMenus(oldAppMenu.getDisplaySubMenus())
                .activeIcon(oldAppMenu.getActiveIcon())
                .description(oldAppMenu.getDescription())
                .build();

        if(oldAppMenu.getSubMenu()!=null){
           Set<AppMenu> subMenus= oldAppMenu.getSubMenu().stream().map(it->buildAppMenu(it,replicateIsEnabled)).collect(Collectors.toCollection(LinkedHashSet::new));
           subMenus.forEach(it->it.setParentMenu(appMenu));
           appMenu.setSubMenu(subMenus);
        }
        return  appMenu;
    }

    public AppOperation buildAppOperation(AppOperation appOperation,Boolean replicateOperationAccess){
        return  AppOperation.builder()
                .id(0L)
                .entity(appOperation.getEntity())
                .create(replicateOperationAccess !=null && replicateOperationAccess.equals(Boolean.TRUE) ? appOperation.getCreate() :Boolean.FALSE)
                .update(replicateOperationAccess !=null && replicateOperationAccess.equals(Boolean.TRUE) ? appOperation.getUpdate() :Boolean.FALSE)
                .read(replicateOperationAccess !=null && replicateOperationAccess.equals(Boolean.TRUE) ? appOperation.getRead() :Boolean.FALSE)
                .delete(replicateOperationAccess !=null && replicateOperationAccess.equals(Boolean.TRUE) ? appOperation.getDelete() :Boolean.FALSE)
                .group(appOperation.getGroup())
                .isDirect(appOperation.getIsDirect())
                .download(replicateOperationAccess !=null && replicateOperationAccess.equals(Boolean.TRUE) ? appOperation.getDownload() :Boolean.FALSE)
                .linkedMenuCode(appOperation.getLinkedMenuCode())
                .build();
    }

    @Override
    public Map<String, Boolean> checkIfRbacExistsForRoleCodes(String appCode, Set<String> roleCodes, String marketContext) {
        Set<String> rbacWithRoleCodesExist = repository.findRoleCodesForRbacExists(appCode,roleCodes,marketContext);
        Map<String,Boolean> result = new HashMap<>();
        for(String roleCode : roleCodes) {
            if(rbacWithRoleCodesExist.contains(roleCode)) {
                result.put(roleCode,Boolean.TRUE);
            }
            else {
                result.put(roleCode,Boolean.FALSE);
            }
        }
        return result;
    }


    @Override
    public void updateRbacPriorityByRoleAndAppCodes(Set<String> appCodes, String role, Integer priority) {
        Set<AppRbac> appRbacSet = repository.findByAppCodesAndRoleCodesAndNonDefault(appCodes, role);
        appRbacSet.forEach(appRbac -> {
            appRbac.setPriority(priority);
        });
        repository.saveAll(appRbacSet);
    }

    @Override
    public Page<AppRbac> getAppRbacPageByAppCode(String appCode, Integer page, Integer size) {
        return repository.findByAppCode(appCode,PageRequest.of(page,size));
    }
}
