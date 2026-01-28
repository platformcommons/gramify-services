package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@Setter
@Builder
@AllArgsConstructor
public class TenantDTO extends BaseTransactionalDTO {

    @Setter
    private Long id;

    private String tenantUUID;

    @Pattern(regexp = "^[a-zA-Z\\d-.]*$")
    @Length(min = 3, max = 45)
    private String tenantLogin;

    @Pattern(regexp = "^[a-zA-Z\\d-]*$")
    private String tenantDomain;

    private String tenantName;

    @Setter
    private String email;
    @Setter
    private String mobile;

    private String iconpic;

    private String description;

    private String website;

    private String tenantType;

    private LocalDateTime onBoardedDateTime;

    private String tenantStatus;

    private Boolean  useMobileAsLogin;

    private Boolean isActive;

    private String slug;

    private LeadDTO lead;

    private TenantProfileDTO tenantProfile;

    public void maskSensitiveInfo() {
        this.lead =null;
        this.tenantUUID=null;
        this.tenantType=null;
        this.tenantStatus = null;
        this.useMobileAsLogin = null;
    }
}


