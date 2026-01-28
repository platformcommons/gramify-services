package com.platformcommons.platform.service.domain.application.config;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.token.PlatformAppToken;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Base64;

@Configuration
public class FeignClientConfig {
    private static final Logger log = LoggerFactory.getLogger(FeignClientConfig.class);

    public static  final String[] OPEN_END_POINTS = new String[]{"/ctld/api/session/v1"};

    public FeignClientConfig() {
    }

    @Bean
    public RequestInterceptor authenticationInterceptor() {
        return (requestTemplate) -> {
            if(!Arrays.asList(OPEN_END_POINTS).contains(requestTemplate.url())) {

                if(!requestTemplate.headers().containsKey(PlatformSecurityConstant.SESSIONID) && !requestTemplate.headers().containsKey(PlatformSecurityConstant.AUTHORIZATION)) {
                    if(PlatformSecurityUtil.isPlatformAppToken()){
                        PlatformAppToken platformAppToken =  (PlatformAppToken) SecurityContextHolder.getContext().getAuthentication();
                        requestTemplate.header(PlatformSecurityConstant.AUTHORIZATION,
                               PlatformSecurityConstant.TOKEN_TYPE_APP_KEY+" "+encodeToBase64(platformAppToken.getAppKey(),platformAppToken.getAppCode()));
                    } else if (isPlatformToken()) {
                        String token = PlatformSecurityUtil.getToken();
                        requestTemplate.header(PlatformSecurityConstant.SESSIONID,token);
                    }
                }
            }
        };
    }

    public static boolean isPlatformToken() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof PlatformToken;
    }

    public static String encodeToBase64(String appKey, String appCode) {
        if (appKey == null || appCode == null) {
            throw new IllegalArgumentException("Input strings must not be null");
        }
        String formattedString = String.format("appKey:%s,appCode:%s", appKey, appCode);
        return Base64.getEncoder().encodeToString(formattedString.getBytes());
    }

}
