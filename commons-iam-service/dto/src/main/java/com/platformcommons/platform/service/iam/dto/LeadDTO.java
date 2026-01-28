package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadDTO {

    private Long id;

    private String organizationName;

    private String email;

    private String mobile;

    private String mobileCountryCode;

    @NotNull(message = "type can not be null")
    private String type;

    private String activationStatus;

    private String key;

    @NotNull(message = "appContext can not be null")
    private String appContext;

    private String marketContext;

    private Date registeredOn;

    private Date activatedOn;

    private Date tenantCreatedOn;

    private String leadContactPersonName;

    private Boolean isMobileNumberChanged;

    private Boolean useMobileAsUserLogin;

    private String firstName;

    private String lastName;

    private Long tenantId;

    private String whatsappNumber;

    private String whatsappNumberCountryCode;

    private RefDataDTO organisationType;

    private Set<RefDataDTO> organisationIndustries;

    private String organisationSize;

    private String organisationLogo;

    private String website;

    private Boolean isEmailVerified;

    private Boolean isMobileVerified;

    private Set<ExtraAttributesDTO> extraAttributes;

    public String getLeadLogin() {
        return Objects.equals(useMobileAsUserLogin,Boolean.TRUE) ? mobile : email;
    }

}
