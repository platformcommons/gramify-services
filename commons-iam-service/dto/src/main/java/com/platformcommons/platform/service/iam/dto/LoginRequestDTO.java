package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class LoginRequestDTO {
    @NotNull
    private String userLogin;
    @NotNull
    private String password;
    @NotNull
    private String tenantLogin;

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "username='" + userLogin + '\'' +
                ", password-length='" + password.length() + '\'' +
                ", tenantLogin='" + tenantLogin + '\'' +
                '}';
    }
}
