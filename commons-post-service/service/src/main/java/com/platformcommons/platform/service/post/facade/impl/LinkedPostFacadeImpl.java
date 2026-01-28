package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.application.LinkedPostService;
import com.platformcommons.platform.service.post.domain.LinkedPost;
import com.platformcommons.platform.service.post.dto.LinkedPostDTO;
import com.platformcommons.platform.service.post.facade.LinkedPostFacade;
import com.platformcommons.platform.service.post.facade.assembler.LinkedPostDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional()
public class LinkedPostFacadeImpl implements LinkedPostFacade {

    @Autowired
    LinkedPostService linkedPostService;

    @Autowired
    LinkedPostDTOAssembler assembler;

    @Override
    public Long createLinkPost(LinkedPostDTO linkedPostDTO) {
        return linkedPostService.createLinkPost(assembler.fromDTO(linkedPostDTO)).getId();
    }

    @Override
    public void deleteLinkedPostById(Long linkedPostId, String reason) {
        linkedPostService.deleteLinkedPostById(linkedPostId, reason);
    }

    @Override
    public PageDTO<LinkedPostDTO> getAllLinkedPost(Integer page, Integer size, String sortBy, String direction, String linkingType, Long postId) {

        Page<LinkedPost> result = linkedPostService.getAllLinkedPost(page, size, JPAUtility.convertToSort(sortBy, direction), linkingType, postId);
        return new PageDTO<>(result.stream().map(linkedPost -> assembler.toDTO(linkedPost)).collect(Collectors.toCollection(LinkedHashSet::new)),
                result.hasNext(), result.getTotalElements());
    }

    @Override
    public LinkedPostDTO getLinkedPostById(Long linkedPostId) {
        return assembler.toDTO(linkedPostService.getLinkedPostById(linkedPostId));
    }

    @Override
    public LinkedPostDTO patchLinkedPost(LinkedPostDTO linkedPostDTO) {
        return assembler.toDTO(linkedPostService.patchLinkedPost(assembler.fromDTO(linkedPostDTO)));
    }

    @Override
    public LinkedPostDTO updateLinkedPost(LinkedPostDTO linkedPostDTO) {
        return assembler.toDTO(linkedPostService.updateLinkedPost(assembler.fromDTO(linkedPostDTO)));
    }
}