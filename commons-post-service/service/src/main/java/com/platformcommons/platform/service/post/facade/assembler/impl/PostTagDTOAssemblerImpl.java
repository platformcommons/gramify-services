package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.post.application.validator.tag.TagDTOValidator;
import com.platformcommons.platform.service.post.domain.PostTag;
import com.platformcommons.platform.service.post.dto.PostTagDTO;
import com.platformcommons.platform.service.post.facade.assembler.PostTagDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PostTagDTOAssemblerImpl implements PostTagDTOAssembler {

    private final TagDTOValidator validator;

    @Override
    public PostTagDTO toDTO(PostTag entity) {
        if (entity == null) {
            return null;
        }

        TagDTO tagDTO = validator.toDTO(entity.getTagCode());
        return PostTagDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .tagCode(entity.getTagCode())
                .tag(tagDTO)
                .build();
    }

    @Override
    public PostTag fromDTO(PostTagDTO dto) {
        if (dto == null) {
            return null;
        }
        return PostTag.builder()
                .id(dto.getId())
                .type(dto.getType())
                .tagCode(dto.getTagCode())
                .build();
    }
}
