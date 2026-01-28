package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.dto.TenantCertificateTemplateDTO;

public interface TenantCertificateTemplateFacade {
    PageDTO<TenantCertificateTemplateDTO> getCertificateTemplatesForLoggedInTenant(Integer page, Integer size, String sortBy, String direction);

    Long postCertificateTemplate(TenantCertificateTemplateDTO tenantCertificateTemplateDTO);

    TenantCertificateTemplateDTO patchUpdate(TenantCertificateTemplateDTO body);
}
