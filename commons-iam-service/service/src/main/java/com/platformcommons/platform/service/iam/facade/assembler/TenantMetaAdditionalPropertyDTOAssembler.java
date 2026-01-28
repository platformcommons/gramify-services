package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantMetaAdditionalProperty;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TenantMetaAdditionalPropertyDTOAssembler {


    TenantMetaAdditionalProperty fromDTO(TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO);

    TenantMetaAdditionalPropertyDTO toDTO(TenantMetaAdditionalProperty tenantMetaAdditionalProperty);

    Set<TenantMetaAdditionalPropertyDTO> toDTOs(Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet);

}
