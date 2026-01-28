package com.platformcommons.platform.service.app.facade.client.util;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.dto.RoleDTO;
import com.platformcommons.platform.service.app.facade.client.CommonsReportClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommonsReportUtil {

    @Autowired
    private CommonsReportClient commonsReportClient;

    public Set<RoleDTO> getTenantRolesByTenantId(Long tenantId) {
        String params = "TENANT_ID="+tenantId;
        return new HashSet<>(commonsReportClient.getTenantRolesByTenantId(params,PlatformSecurityUtil.getToken()).getBody());
    }
}
