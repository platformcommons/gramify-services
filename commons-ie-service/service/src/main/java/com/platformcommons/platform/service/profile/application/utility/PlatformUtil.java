package com.platformcommons.platform.service.profile.application.utility;

import com.platformcommons.platform.exception.generic.PlatformSecurityException;
import com.platformcommons.platform.security.token.PlatformToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class PlatformUtil {

    private static PlatformToken getContext() {
        try {
            return (PlatformToken) SecurityContextHolder.getContext().getAuthentication();
        } catch (ClassCastException var1) {
            throw new PlatformSecurityException("Unable to cast to UserPrincipal");
        } catch (Exception var2) {
            throw new PlatformSecurityException("Security context is not processable", var2);
        }
    }

    public static boolean isSession(){
        return !(getContext().getToken().contains(" "));
    }
}
