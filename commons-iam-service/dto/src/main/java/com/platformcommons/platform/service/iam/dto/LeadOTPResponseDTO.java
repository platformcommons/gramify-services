package com.platformcommons.platform.service.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadOTPResponseDTO {

    private LeadDTO lead;

    private OTPResponse otpResponse;
}
