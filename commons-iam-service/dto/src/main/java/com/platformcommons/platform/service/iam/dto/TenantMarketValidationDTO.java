package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantMarketValidationDTO extends BaseTransactionalDTO {

    private Boolean isMarketGovernor;

    private Boolean isMarketPartner;

    private String marketPartnerStatus;

    private Boolean isTenantAdmin;

    private TenantDTO tenantDTO;

}
