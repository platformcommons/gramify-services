package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.LinkedPost;
import com.platformcommons.platform.service.post.dto.LinkedPostDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface LinkedPostDTOAssembler {
    LinkedPostDTO toDTO(LinkedPost entity);

    Set<LinkedPostDTO> toDTOs(Set<LinkedPost> entities);

    LinkedPost fromDTO(LinkedPostDTO dto);

    Set<LinkedPost> fromDTOs(Set<LinkedPostDTO> dtos);
}