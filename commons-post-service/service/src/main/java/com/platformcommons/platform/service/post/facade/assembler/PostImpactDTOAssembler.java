package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostImpact;
import com.platformcommons.platform.service.post.dto.PostImpactDTO;

import java.util.Set;


public interface PostImpactDTOAssembler {

    PostImpactDTO toDTO(PostImpact entity);

    Set<PostImpactDTO> toDTO(Set<PostImpact> entities);

    PostImpact fromDTO(PostImpactDTO dto);

    Set<PostImpact> fromDTO(Set<PostImpactDTO> dtos);
}
