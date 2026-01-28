package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.Post;
import com.platformcommons.platform.service.search.dto.PostDTO;

public interface PostDTOAssembler {

    PostDTO toDTO(Post post);

    PostDTO toCustomDTO(Post post);

    Post fromDTO(PostDTO postDTO);
}

