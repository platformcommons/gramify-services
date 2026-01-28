package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.TenantCertificateTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface TenantCertificateTemplateService {
    Page<TenantCertificateTemplate> getCertificateTemplatesForLoggedInTenant(Pageable pageable);

    Long postCertificateTemplate(TenantCertificateTemplate tenantCertificateTemplate);

    TenantCertificateTemplate getById(Long id);

    TenantCertificateTemplate getByTemplateCode(String code);

    TenantCertificateTemplate patchUpdate(TenantCertificateTemplate tenantCertificateTemplate);
}
