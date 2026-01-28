package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.dto.LinkedPostDTO;

public interface LinkedPostFacade {
    Long createLinkPost(LinkedPostDTO linkedPostDTO);

    void deleteLinkedPostById(Long linkedPostId, String reason);

    PageDTO<LinkedPostDTO> getAllLinkedPost(Integer page, Integer size, String sortBy, String direction, String linkingType, Long postId);

    LinkedPostDTO getLinkedPostById(Long linkedPostId);

    LinkedPostDTO patchLinkedPost(LinkedPostDTO linkedPostDTO);

    LinkedPostDTO updateLinkedPost(LinkedPostDTO linkedPostDTO);
}