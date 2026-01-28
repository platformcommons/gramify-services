package com.platformcommons.platform.service.post.facade.assembler;


import com.platformcommons.platform.service.post.domain.TenantCertificateTemplate;
import com.platformcommons.platform.service.post.dto.TenantCertificateTemplateDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring",uses = { CertificateTextDetailsDTOAssembler.class})
public interface TenantCertificateTemplateDTOAssembler {

    TenantCertificateTemplateDTO toDTO(TenantCertificateTemplate entity);

    Set<TenantCertificateTemplateDTO> toDTOs(Set<TenantCertificateTemplate> entities);

    TenantCertificateTemplate fromDTO(TenantCertificateTemplateDTO dto);
}
