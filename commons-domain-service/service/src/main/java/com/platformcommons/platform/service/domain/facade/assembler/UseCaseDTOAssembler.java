package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.domain.UseCase;
import com.platformcommons.platform.service.domain.dto.UseCaseDTO;


import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          AppDTOAssembler.class,
          DomainDTOAssembler.class,
          TagDTOAssembler.class,
          FeatureDTOAssembler.class,
          PlatformDTOAssembler.class,
          AuthorDTOAssembler.class,
          SearchKeywordDTOAssembler.class
    })
public interface UseCaseDTOAssembler {

    UseCaseDTO toDTO(UseCase entity);

    UseCase fromDTO(UseCaseDTO dto);

    AttachmentDTO toDTO(Attachment attachment);
}