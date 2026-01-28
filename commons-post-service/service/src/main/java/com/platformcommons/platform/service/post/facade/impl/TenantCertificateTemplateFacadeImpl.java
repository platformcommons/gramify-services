package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.application.TenantCertificateTemplateService;
import com.platformcommons.platform.service.post.domain.TenantCertificateTemplate;
import com.platformcommons.platform.service.post.dto.TenantCertificateTemplateDTO;
import com.platformcommons.platform.service.post.facade.TenantCertificateTemplateFacade;
import com.platformcommons.platform.service.post.facade.assembler.TenantCertificateTemplateDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class TenantCertificateTemplateFacadeImpl implements TenantCertificateTemplateFacade {

    @Autowired
    private TenantCertificateTemplateService service;

    @Autowired
    private TenantCertificateTemplateDTOAssembler assembler;

    @Override
    public PageDTO<TenantCertificateTemplateDTO> getCertificateTemplatesForLoggedInTenant(Integer page, Integer size, String sortBy, String direction) {
        Page<TenantCertificateTemplate> result = service.getCertificateTemplatesForLoggedInTenant(PageRequest.of(page,size,
                JPAUtility.convertToSort(sortBy,direction)));
        return new PageDTO<>(result.toSet()
                .stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),result.hasNext(),result.getTotalElements());
    }

    @Override
    public Long postCertificateTemplate(TenantCertificateTemplateDTO tenantCertificateTemplateDTO) {
        if(PlatformSecurityUtil.isTenantAdmin()) {
            return service.postCertificateTemplate(assembler.fromDTO(tenantCertificateTemplateDTO));
        }
        else {
            throw new UnAuthorizedAccessException("User is not authorised to post template");
        }
    }

    @Override
    public TenantCertificateTemplateDTO patchUpdate(TenantCertificateTemplateDTO body) {
        if(PlatformSecurityUtil.isTenantAdmin()) {
            return assembler.toDTO(service.patchUpdate(assembler.fromDTO(body)));
        }
        else {
            throw new UnAuthorizedAccessException("User is not authorised to post template");
        }
    }
}
