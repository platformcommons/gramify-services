package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostContent;
import com.platformcommons.platform.service.post.dto.PostContentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostContentDTOAssembler {

    PostContent fromDTO(PostContentDTO dto);

    PostContentDTO toDTO(PostContent entity);
}
