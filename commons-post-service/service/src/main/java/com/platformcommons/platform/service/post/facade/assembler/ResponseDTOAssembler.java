package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.Response;
import com.platformcommons.platform.service.post.dto.ResponseDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface ResponseDTOAssembler {

    ResponseDTO toDTO(Response entity);

    Response fromDTO(ResponseDTO dto);
}