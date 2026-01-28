package com.platformcommons.platform.service.iam.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTPActivationResponseDTO {

    private Boolean otpForEmailValidated;

    private Boolean otpForMobileValidated;

}
