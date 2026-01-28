package com.platformcommons.platform.service.assessment.application.utility;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.facade.client.UserVerificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentSecurityUtil {

    private static UserVerificationClient client;

    public AssessmentSecurityUtil(UserVerificationClient client){
        AssessmentSecurityUtil.client = client;
    }
    public static Boolean isVerified(){
        if(PlatformSecurityUtil.isPlatformAppToken()){
            throw new UnAuthorizedAccessException("Not authorized to execute!");
        }
        return client.isVerified(PlatformSecurityUtil.getToken()).getBody();
    }

}
