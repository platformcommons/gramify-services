package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import javax.validation.Valid;
import java.util.*;

public interface AppRbacFacade {

    Long save(AppRbacDTO appRbacDTO );

    PageDTO<AppRbacDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AppRbacDTO getById(Long id);

    AppRbacDTO getByAppCodeAndRole(String appCode,String role,String marketContext, Boolean includeDisabled);

    AppRbacDTO getOrCreatePrimaryRbacByAppCode(String appCode, String marketContext);

    AppRbacDTO getOrCreateAppRbacByAppCodeAndContext(String appCode, String context, String entityType,
                                                                      String marketContext);

    AppRbacDTO getAppRbacByAppCodeAndMarketAndPrimaryRbacCheck(String appCode, String marketContext);


    void syncDefaultRbacToAdminRbacs(String appCode, Set<String> adminRoleCodes);

    void syncAdminRbacToChildRoles(String appCode,Set<String> adminRoleCodes);

    Long copy(Long copyFromRbac, AppRbacDTO appRbacDTO);

    void addAppOperationToAppRbac(Long appRbacId, AppOperationDTO appOperationDTO);

    void addAppMenuToAppRbac(Long appRbacId, AppMenuDTO appMenuDTO);

    AppRbacDTO createRbacFromDefaultRbac(String appCode, String role, String marketContext);

    AppRbacDTO patchUpdate(AppRbacDTO body, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu);

    void patchAppRbacInBulk(@Valid Set<AppRbacDTO> appRbacDTOS);

    AppRbacDTO getAppRbacByAppcodeAndContextAndHierarchy(String appCode, String context, String entityType, String marketContext);

    void syncDefaultContextRbacToContextRbacs(String appCode, String context, String entityType);

    AppRbacDTO getAppRbacByAppCodeAndContext(String appCode, String context, String entityType, String marketContext);

    Long createAppRbacByAppCodeAndContext(AppRbacDTO body);

    Map<String,Boolean> checkIfRbacExistsForRoleCodes(String appCode, Set<String> roleCodes, String marketContext);

    PageDTO<AppRbacDTO> getAppRbacPageByAppCode(String appCode, Integer page, Integer size);
}
