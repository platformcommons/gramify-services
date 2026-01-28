package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Author;
import com.platformcommons.platform.service.domain.dto.AuthorDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          AppDTOAssembler.class,
          UseCaseDTOAssembler.class,
    })
public interface AuthorDTOAssembler {

    AuthorDTO toDTO(Author entity);

    Author fromDTO(AuthorDTO dto);
}