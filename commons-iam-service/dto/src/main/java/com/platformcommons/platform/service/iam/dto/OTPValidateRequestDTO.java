package com.platformcommons.platform.service.iam.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
public class OTPValidateRequestDTO {

    @NotNull(message = "Key must be provided")
    private String key;

    @NotNull(message = "OTP must be provided")
    private String otp;

    @NotNull(message = "MessageId must be provided")
    private String messageId;

    @NotNull(message = "EmailId must be provided")
    private String email;


    @Builder
    public OTPValidateRequestDTO(String key, String otp, String messageId, String email) {
        this.key = key;
        this.otp = otp;
        this.messageId = messageId;
        this.email = email;
    }
}
