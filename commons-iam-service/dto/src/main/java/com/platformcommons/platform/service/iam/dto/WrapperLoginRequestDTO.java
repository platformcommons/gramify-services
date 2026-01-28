package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WrapperLoginRequestDTO {
    private String userLogin;
    private String tenantLogin;

    @Builder
    public WrapperLoginRequestDTO(String userLogin, String tenantLogin) {
        this.userLogin = userLogin;
        this.tenantLogin = tenantLogin;
    }
}
