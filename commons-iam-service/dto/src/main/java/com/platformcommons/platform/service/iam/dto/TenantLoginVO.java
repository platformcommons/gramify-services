package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TenantLoginVO {

    private String tenantLogin;

    @Builder
    public TenantLoginVO(String tenantLogin) {
        this.tenantLogin = tenantLogin;
    }
}
