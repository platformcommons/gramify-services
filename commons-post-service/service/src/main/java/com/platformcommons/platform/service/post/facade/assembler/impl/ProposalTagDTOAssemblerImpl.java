package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.post.application.validator.tag.TagDTOValidator;
import com.platformcommons.platform.service.post.domain.PostTag;
import com.platformcommons.platform.service.post.domain.ProposalTag;
import com.platformcommons.platform.service.post.dto.PostTagDTO;
import com.platformcommons.platform.service.post.dto.ProposalTagDTO;
import com.platformcommons.platform.service.post.facade.assembler.PostTagDTOAssembler;
import com.platformcommons.platform.service.post.facade.assembler.ProposalTagDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProposalTagDTOAssemblerImpl implements ProposalTagDTOAssembler {

    private final TagDTOValidator validator;

    @Override
    public ProposalTagDTO toDTO(ProposalTag entity) {
        if (entity == null) {
            return null;
        }
        TagDTO tagDTO = validator.toDTO(entity.getTagCode());
        return ProposalTagDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .tagCode(entity.getTagCode())
                .tagPurpose(entity.getTagPurpose())
                .tag(tagDTO)
                .build();
    }

    @Override
    public ProposalTag fromDTO(ProposalTagDTO dto) {
        if (dto == null) {
            return null;
        }
        return ProposalTag.builder()
                .id(dto.getId())
                .type(dto.getType())
                .tagCode(dto.getTagCode())
                .tagPurpose(dto.getTagPurpose())
                .build();
    }
}
