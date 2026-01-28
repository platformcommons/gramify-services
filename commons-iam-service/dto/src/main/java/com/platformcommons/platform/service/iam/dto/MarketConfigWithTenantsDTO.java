package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketConfigWithTenantsDTO extends BaseTransactionalDTO {

    private String marketUUID;

    private String domainUrl;

    private String appContext;

    private String governorTenantName;

    private String governorTenantLogin;

    private String marketLogo;

    private Set<TenantDTO> tenantSet;

}
