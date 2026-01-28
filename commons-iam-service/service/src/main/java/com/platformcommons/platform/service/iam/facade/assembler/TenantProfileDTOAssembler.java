package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantProfile;
import com.platformcommons.platform.service.iam.dto.TenantProfileDTO;

public interface TenantProfileDTOAssembler {

    TenantProfile fromDTO(TenantProfileDTO tenantProfileDTO);

    TenantProfileDTO toDTO(TenantProfile tenantProfile);
}
