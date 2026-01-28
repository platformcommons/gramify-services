package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import org.mapstruct.Mapper;

public interface TenantMetaConfigDTOAssembler {

    TenantMetaConfigDTO toDTO(TenantMetaConfig entity);

    TenantMetaConfig fromDTO(TenantMetaConfigDTO dto);
}
