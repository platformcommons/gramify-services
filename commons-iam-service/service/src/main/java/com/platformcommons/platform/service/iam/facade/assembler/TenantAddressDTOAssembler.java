package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantAddress;
import com.platformcommons.platform.service.iam.dto.TenantAddressDTO;

import java.util.List;

public interface TenantAddressDTOAssembler {

    TenantAddress fromDTO(TenantAddressDTO tenantAddressDTO);

    TenantAddressDTO toDTO(TenantAddress tenantAddress);

    List<TenantAddress> fromDTOs(List<TenantAddressDTO> tenantAddressDTOs);

    List<TenantAddressDTO> toDTOs(List<TenantAddress> tenantAddresses);

}
