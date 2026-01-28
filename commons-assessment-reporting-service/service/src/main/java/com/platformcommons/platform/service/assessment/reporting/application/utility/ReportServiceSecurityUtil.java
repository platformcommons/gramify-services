package com.platformcommons.platform.service.assessment.reporting.application.utility;

import com.platformcommons.platform.exception.generic.DataAccessException;
import com.platformcommons.platform.security.token.PlatformAppToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ReportServiceSecurityUtil {


    public static String getAppToken(){
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof PlatformAppToken)){
                throw new DataAccessException("Authentication object is not of type PlatformAppToken") {
            };
        }
        PlatformAppToken token = (PlatformAppToken) SecurityContextHolder.getContext().getAuthentication();
        return String.format("Appkey %s", Base64.getEncoder().encodeToString(String.format("appKey:%s,appCode:%s",token.getAppKey(),token.getAppCode()).getBytes(StandardCharsets.UTF_8)));
    }

}
