package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;
import com.platformcommons.platform.service.iam.dto.TenantMetaMasterDTO;
import com.platformcommons.platform.service.iam.facade.assembler.TenantMetaMasterDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class TenantMetaMasterDTOAssemblerImpl implements TenantMetaMasterDTOAssembler {
    @Override
    public TenantMetaMaster fromDTO(TenantMetaMasterDTO tenantMetaMasterDTO) {
        return TenantMetaMaster.builder()
                .metaCode(tenantMetaMasterDTO.getMetaCode())
                .metaName(tenantMetaMasterDTO.getMetaName())
                .metaDescription(tenantMetaMasterDTO.getMetaDescription())
                .metaDataType(tenantMetaMasterDTO.getMetaDataType())
                .metaAllowedValue(tenantMetaMasterDTO.getMetaAllowedValue())
                .metaDefaultValues(tenantMetaMasterDTO.getMetaDefaultValues())
                .appContext(tenantMetaMasterDTO.getAppContext())
                .build();
    }

    @Override
    public TenantMetaMasterDTO toDTO(TenantMetaMaster tenant) {
        return null;
    }
}
