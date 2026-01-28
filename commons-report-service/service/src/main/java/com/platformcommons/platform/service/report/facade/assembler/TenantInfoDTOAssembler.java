package com.platformcommons.platform.service.report.facade.assembler;

import com.platformcommons.platform.service.report.domain.TenantInfo;
import com.platformcommons.platform.service.report.dto.TenantInfoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
})
public interface TenantInfoDTOAssembler {

    TenantInfo fromDTO(TenantInfoDTO tenantInfoDTO);

    TenantInfoDTO toDTO(TenantInfo tenantInfo);

}
