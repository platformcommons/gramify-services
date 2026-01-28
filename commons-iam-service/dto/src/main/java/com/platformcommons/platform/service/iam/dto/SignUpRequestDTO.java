package com.platformcommons.platform.service.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpRequestDTO {

    private String key;

    private String email;

    private String mobile;

    private String otp;

    private String otpForEmail;

    private String otpForMobile;

    private String messageId;

    private String messageIdForEmail;

    private String messageIdForMobile;

    private String preferredDomainName;

    private String domainURL;

    private String defaultMarketUUID;

    private Boolean useMobileAsUserLogin;

    private boolean addInLinkedSystem;

    private String referralCode;

}
