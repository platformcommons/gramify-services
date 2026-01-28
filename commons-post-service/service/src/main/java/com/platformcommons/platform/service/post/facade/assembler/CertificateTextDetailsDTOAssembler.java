package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.CertificateTextDetails;
import com.platformcommons.platform.service.post.dto.CertificateTextDetailsDTO;

import java.util.Set;

public interface CertificateTextDetailsDTOAssembler {

    CertificateTextDetailsDTO toDTO(CertificateTextDetails entity);

    Set<CertificateTextDetailsDTO> toDTOs(Set<CertificateTextDetails> entities);

    CertificateTextDetails fromDTO(CertificateTextDetailsDTO dto);

    Set<CertificateTextDetails> fromDTOs(Set<CertificateTextDetailsDTO> dtos);
}
