package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.post.application.TenantCertificateTemplateService;
import com.platformcommons.platform.service.post.domain.TenantCertificateTemplate;
import com.platformcommons.platform.service.post.domain.repo.TenantCertificateTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantCertificateTemplateServiceImpl implements TenantCertificateTemplateService {

    @Autowired
    private TenantCertificateTemplateRepository repository;

    @Override
    public Page<TenantCertificateTemplate> getCertificateTemplatesForLoggedInTenant(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Long postCertificateTemplate(TenantCertificateTemplate tenantCertificateTemplate) {
        tenantCertificateTemplate.init();
        return repository.save(tenantCertificateTemplate).getId();
    }

    @Override
    public TenantCertificateTemplate getById(Long id) {
        return repository.findById(id).orElseThrow(
                ()->new NotFoundException(String.format("Resource not found with id - %d",id)));
    }

    @Override
    public TenantCertificateTemplate getByTemplateCode(String code) {
        return repository.findByCode(code).orElseThrow(
                ()->new NotFoundException(String.format("Template not found with code - %s",code)));
    }

    @Override
    public TenantCertificateTemplate patchUpdate(TenantCertificateTemplate tenantCertificateTemplate) {
        TenantCertificateTemplate fetchedTemplate = getById(tenantCertificateTemplate.getId());
        fetchedTemplate.patchUpdate(tenantCertificateTemplate);
        return repository.save(fetchedTemplate);
    }
}
