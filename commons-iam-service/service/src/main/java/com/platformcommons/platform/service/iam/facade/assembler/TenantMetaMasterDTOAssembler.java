package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;
import com.platformcommons.platform.service.iam.dto.TenantMetaMasterDTO;

public interface TenantMetaMasterDTOAssembler {

    TenantMetaMaster fromDTO(TenantMetaMasterDTO tenantMetaMasterDTO);

    TenantMetaMasterDTO toDTO(TenantMetaMaster tenant);

}
