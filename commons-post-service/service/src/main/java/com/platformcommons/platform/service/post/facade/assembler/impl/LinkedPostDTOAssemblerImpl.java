package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.post.domain.LinkedPost;
import com.platformcommons.platform.service.post.dto.LinkedPostDTO;
import com.platformcommons.platform.service.post.facade.assembler.LinkedPostDTOAssembler;
import com.platformcommons.platform.service.post.facade.assembler.PostDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class LinkedPostDTOAssemblerImpl implements LinkedPostDTOAssembler {

    @Autowired
    PostDTOAssembler postDTOAssembler;

    @Override
    public LinkedPost fromDTO(LinkedPostDTO linkedPostDTO) {
        if (linkedPostDTO == null) {
            return null;
        }
        return LinkedPost.builder()
                .id(linkedPostDTO.getId())
                .linkingType(linkedPostDTO.getLinkingType())
                .sourcePost(postDTOAssembler.fromDTO(linkedPostDTO.getSourcePost()))
                .targetPost(postDTOAssembler.fromDTO(linkedPostDTO.getTargetPost()))
                .uuid(linkedPostDTO.getUuid())
                .appCreatedTimestamp(linkedPostDTO.getAppCreatedAt())
                .appLastModifiedTimestamp(linkedPostDTO.getAppLastModifiedAt())
                .build();
    }

    @Override
    public Set<LinkedPost> fromDTOs(Set<LinkedPostDTO> linkedPostDTOS) {
        if (linkedPostDTOS == null) {
            return null;
        }

        Set<LinkedPost> set = new HashSet<LinkedPost>(Math.max((int) (linkedPostDTOS.size() / .75f) + 1, 16));
        for (LinkedPostDTO linkedPostDTO : linkedPostDTOS) {
            set.add(fromDTO(linkedPostDTO));
        }

        return set;
    }

    @Override
    public LinkedPostDTO toDTO(LinkedPost linkedPost) {
        if (linkedPost == null) {
            return null;
        }
        return LinkedPostDTO.builder()
                .id(linkedPost.getId())
                .linkingType(linkedPost.getLinkingType())
                .sourcePost(postDTOAssembler.toDTO(linkedPost.getSourcePost()))
                .targetPost(postDTOAssembler.toDTO(linkedPost.getTargetPost()))
                .uuid(linkedPost.getUuid())
                .appCreatedAt(linkedPost.getCreatedTimestamp())
                .appLastModifiedAt(linkedPost.getAppLastModifiedTimestamp())
                .build();
    }

    @Override
    public Set<LinkedPostDTO> toDTOs(Set<LinkedPost> linkedPosts) {
        if (linkedPosts == null) {
            return null;
        }

        Set<LinkedPostDTO> set = new HashSet<LinkedPostDTO>(Math.max((int) (linkedPosts.size() / .75f) + 1, 16));
        for (LinkedPost linkedPost : linkedPosts) {
            set.add(toDTO(linkedPost));
        }

        return set;
    }
}