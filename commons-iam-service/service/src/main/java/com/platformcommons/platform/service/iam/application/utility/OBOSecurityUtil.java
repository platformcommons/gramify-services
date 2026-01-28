package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.filter.session.TLDPlatformTokenProvider;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OBOSecurityUtil {

    @Value("${commons.config.tenant.registration.user.sender:admin@@platform}")
    private String privilegeUser;

    @Value("${commons.config.tenant.registration.pass:password1}")
    private String privilegePass;

    private final TLDClient tldClient;
    private final TLDPlatformTokenProvider tldPlatformTokenProvider;
    private static final String SEPARATOR = "@@";

    public PlatformToken initPrivilegeUser() {
        String sessionId = Objects.requireNonNull(tldClient.login(privilegeUser, privilegePass
                , null, null,
                null, null).getHeaders().get(PlatformSecurityConstant.SESSIONID)).get(0);
        PlatformToken platformToken = tldPlatformTokenProvider.getPlatformTokenFromSessionId(sessionId);
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        return platformToken;
    }

    public String getPrivilegeUserSession() {
       return Objects.requireNonNull(tldClient.login(privilegeUser, privilegePass
                , null, null,
                null, null).getHeaders().get(PlatformSecurityConstant.SESSIONID)).get(0);
    }

    public String linkedSystemToken(String username,String password) {
        String sessionId = Objects.requireNonNull(tldClient.login(username, password
                , null, null,
                null, null).getHeaders().get(PlatformSecurityConstant.SESSIONID)).get(0);
        PlatformToken platformToken = tldPlatformTokenProvider.getPlatformTokenFromSessionId(sessionId);
        return platformToken.getToken();
    }

    public PlatformToken initUserAuth(String user, String pass) {
        String sessionId = Objects.requireNonNull(tldClient.login(user, pass
                , null, null,
                null, null).getHeaders().get(PlatformSecurityConstant.SESSIONID)).get(0);
        PlatformToken platformToken = tldPlatformTokenProvider.getPlatformTokenFromSessionId(sessionId);
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        return platformToken;
    }

    public void initUserAndSetSecurityContext(String userLogin,String password,String tenantLogin, String appId, String appVersion,
                                              String appKey, String deviceId) {
        clearContext();
        String sessionId = getSessionIdFromLoginDetails(userLogin,password,tenantLogin,null,null,null,null);
        PlatformToken platformToken = tldPlatformTokenProvider.getPlatformTokenFromSessionId(sessionId);
        SecurityContextHolder.getContext().setAuthentication(platformToken);
    }

    public String getSessionIdFromLoginDetails(String userLogin,String password,String tenantLogin, String appId,
                                               String appVersion, String appKey, String deviceId) {
        String userName = userLogin + SEPARATOR + tenantLogin;
        return Objects.requireNonNull(tldClient.login(userName, password, appId, appKey,
                appVersion, deviceId).getHeaders().get(PlatformSecurityConstant.SESSIONID)).get(0);
    }

    public void fetchTokenAndSetSecurityContext(String sessionId) {
        clearContext();
        PlatformToken platformToken = tldPlatformTokenProvider.getPlatformTokenFromSessionId(sessionId);
        SecurityContextHolder.getContext().setAuthentication(platformToken);
    }

    public void clearContext() {
        SecurityContextHolder.clearContext();
    }

    public String fetchUserSessionViaTenantOpsSupport(String userLogin,String tenantLogin,TenantMetaConfigFacade tenantMetaConfigFacade) {
        String userName = userLogin + SEPARATOR + tenantLogin;
        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigFacade.getByTenantLoginFromCacheOrDB(tenantLogin);
        String opsSupportSession = getSessionIdFromLoginDetails(tenantMetaConfigDTO.getUserLogin(),tenantMetaConfigDTO.getPassword(),
                tenantMetaConfigDTO.getTenantLogin(),null,null,null,null);
        return tldClient.getUserSession(opsSupportSession,userName).getHeaders().getFirst(PlatformSecurityConstant.SESSIONID);
    }

    public void initTenantSupportUserByTenantMetaConfig(String tenantLogin,TenantMetaConfigFacade tenantMetaConfigFacade) {
        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigFacade.getByTenantLoginFromCacheOrDB(tenantLogin);
        initUserAndSetSecurityContext(tenantMetaConfigDTO.getUserLogin(),tenantMetaConfigDTO.getPassword(),
                tenantMetaConfigDTO.getTenantLogin(),null,null,null,null);
    }

    public String getOpsSupportTenantUserSession(String tenantLogin,TenantMetaConfigFacade tenantMetaConfigFacade) {
        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigFacade.getByTenantLoginFromCacheOrDB(tenantLogin);
        return getSessionIdFromLoginDetails(tenantMetaConfigDTO.getUserLogin(),tenantMetaConfigDTO.getPassword(),
                tenantMetaConfigDTO.getTenantLogin(),null,null,null,null);
    }
}
