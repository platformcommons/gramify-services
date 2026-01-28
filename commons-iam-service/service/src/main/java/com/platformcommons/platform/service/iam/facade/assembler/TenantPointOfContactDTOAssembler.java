package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantPointOfContact;
import com.platformcommons.platform.service.iam.dto.TenantPointOfContactDTO;

import java.util.Set;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TenantPointOfContactDTOAssembler {

    TenantPointOfContact fromDTO(TenantPointOfContactDTO tenantPointOfContactDTO);

    TenantPointOfContactDTO toDTO(TenantPointOfContact tenantPointOfContact);

    Set<TenantPointOfContact> fromDTOs(Set<TenantPointOfContactDTO> tenantPointOfContactDTOs);

    Set<TenantPointOfContactDTO> toDTOs(Set<TenantPointOfContact> tenantPointOfContacts);
}
