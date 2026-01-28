package com.platformcommons.platform.service.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTenantCheckDTO {

    private Boolean isActive;

    private String userLogin;

    private String preferredTenantLogin;

    private Boolean passwordPresent;
}
