package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class LoginWithContactRequestDTO {
    @NotNull
    private String contact;
    @NotNull
    private String password;
    @NotNull
    private String tenantLogin;

    @Override
    public String toString() {
        return "LoginWithContactRequestDTO{" +
                "contact='" + contact + '\'' +
                ", password-length='" + password.length() + '\'' +
                ", tenantLogin='" + tenantLogin + '\'' +
                '}';
    }
}