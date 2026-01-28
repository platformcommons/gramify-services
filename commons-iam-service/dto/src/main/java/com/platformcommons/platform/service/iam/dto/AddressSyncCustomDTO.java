package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressSyncCustomDTO {

    private String addressLine1;

    private String addressLine2;

    private String countryCode;

    private String stateCode;

    private String cityCode;

    private String cityLabel;

    private String pinCode;

    private String addressNotes;

    private String addressTypeCode;
}
