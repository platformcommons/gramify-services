package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class TenantPartnerVO {
    private Long id;
    private String login;
    private String name;
    private String domainURL;
    private String status;
    private Boolean isPrimary;
    private Long primaryTenantUserId;
    private String primaryTenantUserLogin;
    private String primaryTenantUserName;

    @Builder
    public TenantPartnerVO(Long id, String login, String name, String domainURL, String status,
                           Boolean isPrimary, Long primaryTenantUserId,
                           String primaryTenantUserLogin, String primaryTenantUserName) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.domainURL = domainURL;
        this.status = status;
        this.isPrimary = isPrimary;
        this.primaryTenantUserId = primaryTenantUserId;
        this.primaryTenantUserLogin = primaryTenantUserLogin;
        this.primaryTenantUserName = primaryTenantUserName;
    }
}
