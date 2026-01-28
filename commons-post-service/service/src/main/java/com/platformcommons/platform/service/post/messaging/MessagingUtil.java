package com.platformcommons.platform.service.post.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformcommons.platform.context.ActorContext;
import com.platformcommons.platform.context.AppContext;
import com.platformcommons.platform.context.TenantContext;
import com.platformcommons.platform.context.UserContext;
import com.platformcommons.platform.security.filter.session.TLDTenantContext;
import com.platformcommons.platform.security.filter.session.TLDUserContext;
import com.platformcommons.platform.security.token.PlatformToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MessagingUtil {


    public void  setContext(String userContext, String tenantContext) throws JsonProcessingException {
        TLDUserContext tldUserContext = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(userContext,TLDUserContext.class);
        TLDTenantContext tldTenantContext = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(tenantContext,TLDTenantContext.class);

        Set<GrantedAuthority> authorities = tldUserContext.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        PlatformToken platformToken=new PlatformToken(authorities, UserContext.builder()
                .userId((long)tldUserContext.getUserId()).username(tldUserContext.getUserLoginName())
                .name(tldUserContext.getFirstName()).authorities(authorities.stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())).build(), TenantContext.builder().tenantId((long)tldTenantContext.getTenantId())
                .tenantLogin(tldTenantContext.getTenantLoginName()).tenantName(tldTenantContext.getName()).build(), (AppContext)null,
                "sessionId", ActorContext.builder().actorContext("ACTOR_TYPE.BRIDGE_USER").build());
        SecurityContextHolder.getContext().setAuthentication(platformToken);
    }

}
