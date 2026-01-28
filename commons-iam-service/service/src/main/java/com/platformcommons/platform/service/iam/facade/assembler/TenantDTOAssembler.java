package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.dto.TenantDTO;

import java.util.List;
import java.util.Set;

public interface TenantDTOAssembler {

    Tenant fromDTO(TenantDTO tenantDTO);

    TenantDTO toDTO(Tenant tenant);

    List<TenantDTO> toDTOs(List<Tenant> tenantSet);
}
