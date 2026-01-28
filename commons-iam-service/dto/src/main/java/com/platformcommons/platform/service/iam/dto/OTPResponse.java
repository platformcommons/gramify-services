package com.platformcommons.platform.service.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTPResponse {

    private String key;

    private String messageId;

    private String messageIdForEmail;

    private String messageIdForMobile;

    public OTPResponse(String key,String messageId) {
        this.key = key;
        this.messageId = messageId;
    }

}
