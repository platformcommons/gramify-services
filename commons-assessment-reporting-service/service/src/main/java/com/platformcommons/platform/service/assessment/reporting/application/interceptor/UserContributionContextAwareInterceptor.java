

package com.platformcommons.platform.service.assessment.reporting.application.interceptor;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.UserContributionDimRepository;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserContributionContextAwareInterceptor implements MethodInterceptor {
    private static final Logger log = LoggerFactory.getLogger(UserContributionContextAwareInterceptor.class);
    private final EntityManager entityManager;

    public UserContributionContextAwareInterceptor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        log.trace("Method declaring class : {}", method.getDeclaringClass());
        List<Class<?>> list = new ArrayList<>();
        list.add(method.getDeclaringClass());
        list.addAll(Arrays.stream(method.getDeclaringClass().getInterfaces()).collect(Collectors.toList()));
        if (list.contains(UserContributionDimRepository.class)) {
            if(method.getName().startsWith("enableContextFilter")){
                this.enableContextFilter();
                Object result = methodInvocation.proceed();
                this.disableContextFilter();
                return result;
            }
        }

        return methodInvocation.proceed();
    }

    private void enableContextFilter() {
        try (Session session = this.entityManager.unwrap(Session.class)){
            session.enableFilter("contextFilter")
                   .setParameter("tenantId", this.getCurrentTenantId())
                   .setParameter("userId", PlatformSecurityUtil.getCurrentUserId());
        } catch (Exception var2) {
            log.error("Error enabling tenantFilter : Reason - " + var2.getMessage());
        }
    }

    private void disableContextFilter() {
        try (Session session = this.entityManager.unwrap(Session.class)) {
            if (null != session)  session.disableFilter("contextFilter");
        }
    }

    private Long getCurrentTenantId() {
        return PlatformSecurityUtil.getCurrentTenantId();
    }
}
