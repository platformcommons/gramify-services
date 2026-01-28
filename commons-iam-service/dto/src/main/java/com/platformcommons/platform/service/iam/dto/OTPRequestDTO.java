package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class OTPRequestDTO {

    @NotNull
    private String userLogin;

    @NotNull
    private String tenantLogin;

    private String password;

    @NotNull
    private String otp;

    @NotNull
    private String otpKey;

    @Builder
    public OTPRequestDTO(@NotNull String userLogin, @NotNull String tenantLogin, @NotNull String password, @NotNull String otp, @NotNull String otpKey) {
        this.userLogin = userLogin;
        this.tenantLogin = tenantLogin;
        this.password = password;
        this.otp = otp;
        this.otpKey = otpKey;
    }
}
