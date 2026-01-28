package com.platformcommons.platform.service.assessment.application.utility;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SecurityContextUtil {

    public static <T> CompletableFuture<T> runWithContext(Supplier<T> operation) {
        PlatformToken token = PlatformSecurityUtil.getContext();
        return CompletableFuture.supplyAsync(() -> {
            try {
                SecurityContextHolder.getContext().setAuthentication(token);
                return operation.get();
            } finally {
                SecurityContextHolder.clearContext();
            }
        });
    }

    public static void runWithContextVoid(Supplier<Void> operation) {
        PlatformToken token = PlatformSecurityUtil.getContext();
        CompletableFuture.supplyAsync(() -> {
            try {
                SecurityContextHolder.getContext().setAuthentication(token);
                return null;
            } finally {
                SecurityContextHolder.clearContext();
            }
        });
    }
}
