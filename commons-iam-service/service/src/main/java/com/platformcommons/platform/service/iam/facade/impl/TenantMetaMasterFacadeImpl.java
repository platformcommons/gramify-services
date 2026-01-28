package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.TenantMetaMasterService;
import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;
import com.platformcommons.platform.service.iam.dto.TenantMetaMasterDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaMasterFacade;
import com.platformcommons.platform.service.iam.facade.assembler.TenantMetaMasterDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TenantMetaMasterFacadeImpl implements TenantMetaMasterFacade {

    private final TenantMetaMasterDTOAssembler tenantMetaMasterDTOAssembler;

    private final TenantMetaMasterService tenantMetaMasterService;

    @Override
    public void addMetaMasters(Set<TenantMetaMasterDTO> tenantMetaMasterDTOS) {
        PlatformSecurityUtil.validatePlatformAdmin();
        tenantMetaMasterDTOS.forEach(tenantMetaMasterDTO -> tenantMetaMasterService.saveTenantMetaMaster(tenantMetaMasterDTOAssembler.fromDTO(tenantMetaMasterDTO)));

    }

    @Override
    public Set<String> getMetaMasterValue(String appContext, String key) {
        TenantMetaMaster tenantMetaMaster = tenantMetaMasterService.getByAppContextAndCode(appContext,key).orElse(null);
        if(tenantMetaMaster!=null){
            return  tenantMetaMaster.getMetaAllowedValue();
        }
        else {
            return  null;
        }

    }
}
