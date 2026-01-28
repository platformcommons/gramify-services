package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserConsentDTO {

    private Long id;

    private Long userId;

    @NotNull(message = "Consent Type must not be null")
    private String consentType;

    private String consentDoc;

    private String consentVersion;

    private String consentStatus;

    private Date consentGivenOn;

    private String consentContext;

    public UserConsentDTO(Long id, Long userId, String consentType, String consentDoc,
                       String consentVersion, String consentStatus, Date consentGivenOn, String consentContext) {
        this.id = id;
        this.userId = userId;
        this.consentType = consentType;
        this.consentDoc = consentDoc;
        this.consentVersion = consentVersion;
        this.consentStatus = consentStatus;
        this.consentGivenOn = consentGivenOn;
        this.consentContext = consentContext;
    }
}
