package com.platformcommons.platform.service.iam.application.utility;

import com.mindtree.bridge.platform.dto.GlobalRefDataDTO;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TLDUtil {

    @Autowired
    private TLDClient tldClient;

    public List<GlobalRefDataDTO> getGlobalRefDataByCriteria(String classCode, String languageCode) {
        StringBuilder criteria = new StringBuilder();
        if(!StringUtils.isEmpty(classCode)) {
            criteria.append("globalRefClass.classCode").append(" in ").append("('").append(classCode).append("')");
        }
        if(!StringUtils.isEmpty(languageCode)) {
            criteria.append(" and ").append("language.languageCode").append("='ENG'");
        }
        return tldClient.getGlobalRefData(criteria.toString(),PlatformSecurityUtil.getToken()).getBody();
    }
}
