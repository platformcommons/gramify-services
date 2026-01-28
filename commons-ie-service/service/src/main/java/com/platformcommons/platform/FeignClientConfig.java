package com.platformcommons.platform;


import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.profile.application.utility.PlatformUtil;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    private static final Logger log = LoggerFactory.getLogger(FeignClientConfig.class);

    public FeignClientConfig() {
    }

    @Bean
    public RequestInterceptor authenticationInterceptor() {
        return (requestTemplate) -> {
            if(requestTemplate.headers().containsKey(PlatformSecurityConstant.SESSIONID)){
                log.info("Token : {}", requestTemplate.headers().get(PlatformSecurityConstant.SESSIONID).stream().findFirst().orElse(null));
                return;
            }
            String token = PlatformSecurityUtil.getToken();
            log.info("Token : {}", token);
            if (null != token && PlatformUtil.isSession() && !requestTemplate.headers().containsKey(PlatformSecurityConstant.SESSIONID)) {
                requestTemplate.header(PlatformSecurityConstant.SESSIONID, token);
            }

        };
    }
}
