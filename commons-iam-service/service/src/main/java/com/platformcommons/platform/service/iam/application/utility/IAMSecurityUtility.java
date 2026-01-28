package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.cache.manager.AuthCacheManager;
import com.platformcommons.platform.context.ActorContext;
import com.platformcommons.platform.context.TenantContext;
import com.platformcommons.platform.context.UserContext;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.domain.*;
import com.platformcommons.platform.service.iam.dto.brbase.*;
import com.platformcommons.platform.service.sdk.person.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IAMSecurityUtility {

    @Autowired(required = false)
    private AuthCacheManager<PlatformToken> authCacheManager;

    @Value("${tenant.admin.prefix:_prefix}")
    private String adminPrefix;
    @Value("${tenant.admin.suffix:_suffix}")
    private String adminSuffix;
    @Value("${tenant.ops.prefix:_prefix}")
    private String opsPrefix;
    @Value("${tenant.ops.suffix:_suffix}")
    private String opsSuffix;
    @Value("${commons.app.context:platform}")
    private String appContext;


    private static void addAuthorities(Role role, HashSet<SimpleGrantedAuthority> authorities) {
        role.getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getCode())));
        authorities.add(new SimpleGrantedAuthority(role.getCode()));
    }

    public static void setTokenToContextHolder(PlatformToken token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public String buildAdminPassword(String tenantUUID) {
        return adminPrefix + tenantUUID.substring(0, 2) + tenantUUID.substring(tenantUUID.length() - 2) + adminSuffix;
    }

    public String buildOpsPassword(String tenantUUID) {
        return opsPrefix + tenantUUID.substring(0, 2) + tenantUUID.substring(tenantUUID.length() - 2) + opsSuffix;
    }

    public PlatformToken generatePlatformToken(User user, PlatformToken platformTokenActual) {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getAuthorities(user);
        String token = UUID.randomUUID().toString();
        return PlatformToken.builder().userContext(UserContext.builder()
                        .userId(user.getId()).name(user.getFirstName())
                        .userUUID(user.getUuid()).username(user.getUserLogin())
                        .authorities(simpleGrantedAuthorities.stream().map(SimpleGrantedAuthority::getAuthority)
                                .collect(Collectors.toSet())).build()).authorities(simpleGrantedAuthorities)
                .actorContext(ActorContext.builder().actorContext(IAMConstant.IAM_ACTOR).build()).appContext(platformTokenActual!=null? platformTokenActual.getAppContext():null)
                .tenantContext(TenantContext.builder().tenantId(user.getTenant().getId())
                        .tenantLogin(user.getTenant().getTenantLogin())
                        .tenantName(user.getTenant().getTenantName())
                        .tenantUUID(user.getTenant().getTenantUUID())
                        .build()).token(token).build();
    }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        HashSet<SimpleGrantedAuthority> authorities = new LinkedHashSet<>();
        user.getUserRoleMaps().forEach(userRoleMap -> {
            Role role = userRoleMap.getRole();
            addAuthorities(role, authorities);
            role.getInheritedRolePermissions().forEach(inheritedRole -> addAuthorities(inheritedRole, authorities));
        });
        return authorities;
    }

    public PlatformToken generatePlatformTokenAndSetToContext(User user, PlatformToken platformTokenActual) {
        PlatformToken platformToken = generatePlatformToken(user,platformTokenActual);
        setTokenToContextHolder(platformToken);
        authCacheManager.getSessionCache().put(platformToken.getToken(), platformToken);
        return platformToken;
    }

    public void invalidateSession(String sessionId) {
        authCacheManager.getSessionCache().remove(sessionId);
    }

    public UserSecurity buildUserSecurityDomain(User user, String password) {
        return UserSecurity.builder().user(user).tenantId(user.getTenantId()).password(password).build();
    }

    public UserSession buildUserSessionDomain(User user, HttpServletRequest request, String appKey, String appId, String token, String appVersion, String loginType) {
        return UserSession.builder().userId(user).ipAddress(request.getRemoteAddr()).userAgent(request.getHeader(IAMConstant.USER_AGENT)).appKey(appKey).appId(appId).lastLoginDateTime(new Date()).session(token).status(true).appVersion(appVersion).loginType(loginType).deviceType(request.getHeader(IAMConstant.DEVICE_TYPE)).build();
    }

    public User buildUserDomain(Tenant tenant, UserSelfRegistrationDTO userSelfRegistrationDTO) {
        User user = User.builder().id(userSelfRegistrationDTO.getId() == null ? null : userSelfRegistrationDTO.getId().longValue()).userLogin(userSelfRegistrationDTO.getLogin()).firstName(userSelfRegistrationDTO.getFirstName()).lastName(userSelfRegistrationDTO.getLastName()).tenant(tenant).tenantId(tenant.getId()).appCreatedTimestamp(userSelfRegistrationDTO.getCreatedDateTime() == null ? new Date().getTime() : userSelfRegistrationDTO.getCreatedDateTime().getTime()).build();
        user.deactivate("Self Registered User need to be activated separately");
        userSelfRegistrationDTO.getUserContacts().forEach(userContactDTO -> user.addUserContact(UserContact.builder().contact(Contact.builder().contactType(userContactDTO.getContact().getContactType().getDataCode()).contactValue(userContactDTO.getContact().getContactValue()).isVerified(false).build()).user(user).primaryContact(userContactDTO.getPrimaryContact()).build()));
        return user;
    }

    public UserSecurity createSelfRegisteredUserSecurity(User createdUser, Long id, String password) {

        return UserSecurity.builder().user(createdUser).accountLockReason("Self Registered User").isAccountLocked(true).tenantId(id).password(password).build();
    }

    public String getAppContext(String appContext, String appVersion, String appKey) {
        return appContext == null ? this.appContext : appContext;
    }

    public UserSelfRegistrationDTO getUserSelfRegistrationDTO(User user, String appContext, UserSecurity security) {
        UserSelfRegistrationDTO registrationDTO = new UserSelfRegistrationDTO();
        UserSelfRegistrationResponseDTO responseDTO = new UserSelfRegistrationResponseDTO();

        registrationDTO.setId(user.getId().intValue());
        registrationDTO.setFirstName(user.getFirstName());
        registrationDTO.setLastName(user.getLastName());
        registrationDTO.setLogin(user.getUserLogin());
        registrationDTO.setAppContext(appContext);
        registrationDTO.setResponseDTO(responseDTO);

        responseDTO.setOtpSentMasg("OTP has been sent to the registered contact number");
        responseDTO.setModKey(security.getOtpKey());
        responseDTO.setResponseMsg(security.getOtpFor());

        registrationDTO.setUserContacts(new ArrayList<>());
        if (user.getUserContacts() != null) user.getUserContacts().forEach(userContact -> {
            UserContactDTO userContactDTO = new UserContactDTO();
            ContactDTO contactDTO = new ContactDTO();
            userContactDTO.setId(userContact.getId());
            userContactDTO.setPrimaryContact(userContact.getPrimaryContact());
            userContactDTO.setContact(contactDTO);

            contactDTO.setId(userContact.getContact().getId().intValue());
            contactDTO.setContactType(new GlobalRefDataDTO(userContact.getContact().getContactType()));
            contactDTO.setContactValue(userContact.getContact().getContactValue());
            contactDTO.setVerified(userContact.getContact().getIsVerified());

            registrationDTO.getUserContacts().add(userContactDTO);
        });
        return registrationDTO;
    }


}
