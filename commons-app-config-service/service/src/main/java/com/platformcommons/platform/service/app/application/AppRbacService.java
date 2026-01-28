package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.domain.AppOperation;
import com.platformcommons.platform.service.app.domain.AppRbac;

import org.springframework.data.domain.Page;

import java.util.*;

public interface AppRbacService {

    AppRbac save(AppRbac appRbac );

    Page<AppRbac> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    AppRbac getById(Long id);

    void syncDefaultRbacToAdminRbacs(String appCode, Set<String> adminRoleCodes);

    void syncAdminRbacToChildRoles(String appCode,Set<String> adminRoleCodes);

    AppRbac copy(Long copyFromRbac, AppRbac appRbac);

    AppRbac getByAppCodeAndRole(String appCode,String role,String marketContext);

    AppRbac getOrCreatePrimaryRbacByAppCode(String appCode, String marketContext);

    AppRbac getOrCreateAppRbacByAppCodeAndContext(String appCode, String context, String entityType, String marketContext);

    AppRbac getAppRbacByAppCodeAndMarketAndPrimaryRbacCheck(String appCode, String marketContext);

    void addAppOperationToAppRbac(Long appRbacId, AppOperation appOperation);

    void addAppMenuToAppRbac(Long appRbacId, AppMenu appMenu);

    AppRbac createRbacFromDefaultRbac(String appCode, String role, String marketContext);

    AppRbac patchUpdate(AppRbac appRbac, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu);

    AppRbac getAppRbacByAppcodeAndContextAndHierarchy(String appCode, String context, String entityType,String marketContext);

    void syncDefaultContextRbacToContextRbacs(String appCode, String context, String entityType);

    AppRbac getAppRbacByAppCodeAndContext(String appCode, String context, String entityType, String marketContext);

    AppRbac createAppRbacByAppCodeAndContext(AppRbac appRbac);

    void patchAppRbacInBulk(Set<AppRbac> appRbacs);

    Map<String, Boolean> checkIfRbacExistsForRoleCodes(String appCode, Set<String> roleCodes, String marketContext);

    void updateRbacPriorityByRoleAndAppCodes(Set<String> appCodes, String role, Integer priority);

    Page<AppRbac> getAppRbacPageByAppCode(String appCode, Integer page, Integer size);
}
