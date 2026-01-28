package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;


public interface AppRbacAsync {

    void validateAndUpdateAppOperation(AppOperationDTO sourceDTO, AppOperationDTO targetDTO, Boolean syncHigherRbacOperation, Boolean syncLowerRbacOperation, PlatformToken platformToken);

    void validateAndUpdateAppMenu( AppRbacDTO oldAppRbac, PlatformToken platformToken, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu);
}