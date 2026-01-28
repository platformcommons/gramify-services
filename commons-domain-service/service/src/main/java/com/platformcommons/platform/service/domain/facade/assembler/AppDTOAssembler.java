package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.domain.App;
import com.platformcommons.platform.service.domain.dto.AppDTO;


import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.facade.assembler.UoMDTOAssembler;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          DomainDTOAssembler.class,
          TagDTOAssembler.class,
          UseCaseDTOAssembler.class,
          BenefitDTOAssembler.class,
          FeatureDTOAssembler.class,
          PlatformDTOAssembler.class,
          TestimonialDTOAssembler.class,
          SearchKeywordDTOAssembler.class,
        UoMDTOAssembler.class
    })
public interface AppDTOAssembler {

    AppDTO toDTO(App entity);

    App fromDTO(AppDTO dto);

    AttachmentDTO toDTO(Attachment attachment);
}