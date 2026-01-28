package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostTag;
import com.platformcommons.platform.service.post.dto.PostTagDTO;

public interface PostTagDTOAssembler {

    PostTagDTO toDTO(PostTag entity);

    PostTag fromDTO(PostTagDTO dto);

}
