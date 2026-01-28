package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CrossLoginRequestDTO {

    @NotNull
    private String otp;
    @NotNull
    private String tenantLogin;
    @NotNull
    private String userLogin;

    private String crossTenantLogin;

    private String modKey;


}
