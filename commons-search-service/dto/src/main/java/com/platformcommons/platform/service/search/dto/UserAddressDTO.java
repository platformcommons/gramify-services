package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class UserAddressDTO {

    private Long userId;

    private String currentCountryCode;

    private String currentStateCode;

    private String currentCityCode;

    private String permanentCountryCode;

    private String permanentStateCode;

    private String permanentCityCode;
}
