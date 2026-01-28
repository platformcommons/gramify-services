package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.dto.DomainDTO;


import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.mapstruct.Mapper;


public interface DomainDTOAssembler {

    DomainDTO toDTO(Domain entity);

    DomainDTO toDTOWithSuggestedApp(Domain entity,Boolean withSuggestedApp);

    Domain fromDTO(DomainDTO dto);

    AttachmentDTO toDTO(Attachment attachment);
}