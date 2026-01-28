package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.PostTenantConfigService;
import com.platformcommons.platform.service.post.dto.PostTenantConfigDTO;
import com.platformcommons.platform.service.post.facade.PostTenantConfigFacade;
import com.platformcommons.platform.service.post.facade.assembler.PostTenantConfigDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PostTenantConfigFacadeImpl implements PostTenantConfigFacade {

    @Autowired
    private PostTenantConfigService service;

    @Autowired
    private PostTenantConfigDTOAssembler assembler;

    @Override
    public PostTenantConfigDTO getForLoggedInTenant() {
        return assembler.toDTO(service.getForLoggedInTenant());
    }

    @Override
    public PostTenantConfigDTO getByTenantLogin(String tenantLogin) {
        return assembler.toDTO(service.getPostTenantConfigByTenantLogin(tenantLogin));
    }

    @Override
    public PostTenantConfigDTO save(PostTenantConfigDTO body) {
        if(PlatformSecurityUtil.isPlatformAdmin()) {
            return assembler.toDTO(service.save(assembler.fromDTO(body)));
        }
        else {
            throw new UnAuthorizedAccessException("Logged In Tenant is not Platform Admin");
        }
    }

    @Override
    public PostTenantConfigDTO patchUpdate(PostTenantConfigDTO body) {
        if(PlatformSecurityUtil.isPlatformAdmin()) {
            return assembler.toDTO(service.patchUpdate(assembler.fromDTO(body)));
        }
        else {
            throw new UnAuthorizedAccessException("Logged In Tenant is not Platform Admin");
        }
    }
}
