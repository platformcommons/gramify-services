package com.platformcommons.platform.service.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSignUpRequestDTO {

    private String key;

    private String otpForEmail;

    private String otpForMobile;

    private String messageIdForEmail;

    private String messageIdForMobile;

}
