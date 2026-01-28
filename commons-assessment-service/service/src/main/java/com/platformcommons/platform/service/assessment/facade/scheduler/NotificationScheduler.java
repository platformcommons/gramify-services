package com.platformcommons.platform.service.assessment.facade.scheduler;

import com.platformcommons.platform.context.TenantContext;
import com.platformcommons.platform.context.UserContext;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.assessment.facade.AssessmentFacade;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final AssessmentFacade facade;

    // Runs every 24 hours
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    @SchedulerLock(name = "scheduleReviewAssessmentReminder", lockAtLeastFor = "30s", lockAtMostFor = "1m")
    public void scheduleReviewAssessmentReminder() {
        mimicTenant(1L,1L);
        facade.triggerReviewAssessmentReminder();
    }

    public  void mimicTenant(Long tenantId, Long userId) {
        TenantContext tenantContext = TenantContext.builder().tenantId(tenantId).build();
        UserContext userContext = UserContext.builder().userId(userId).authorities(Collections.EMPTY_SET).build();
        PlatformToken platformToken = PlatformToken.builder().tenantContext(tenantContext).userContext(userContext).build();
        setAuthenticationInSecurityContext(platformToken);
    }

    public  void setAuthenticationInSecurityContext(Authentication authentication) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

}

