package com.platformcommons.platform.service.report.application.utility;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IGXSecurityUtility {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String GET_CURRENT_TENANT_ID = "SELECT id from authdb.tenant where login = :loginName";
    private final String GET_CURRENT_USER_ID = "SELECT id from authdb.user where login = :userLogin and tenant_id = (SELECT id from authdb.tenant where login = :tenantLogin)";
    public Long getCurrentTenantId() {
        String loginName = PlatformSecurityUtil.getCurrentTenantLogin();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginName", loginName);
        return ((Number)namedParameterJdbcTemplate.queryForMap(GET_CURRENT_TENANT_ID, paramMap)
                                                  .values()
                                                  .stream()
                                                  .findFirst()
                                                  .orElseThrow(()->new NotFoundException("IGX_CURREN_TENANT_ID Not Found"))).longValue();
    }

    public Long getCurrentUserId() {
        String tenantLogin = PlatformSecurityUtil.getCurrentTenantLogin();
        String userLogin = PlatformSecurityUtil.getCurrentUserLogin();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tenantLogin", tenantLogin);
        paramMap.put("userLogin", userLogin);
        return ((Number)namedParameterJdbcTemplate.queryForMap(GET_CURRENT_USER_ID, paramMap)
                                                  .values()
                                                  .stream()
                                                  .findFirst()
                                                  .orElseThrow(()->new NotFoundException("IGX_CURRENT_USER_ID Not Found"))).longValue();


    }

}
