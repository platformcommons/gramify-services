package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.post.domain.vo.ReactionCountVO;
import com.platformcommons.platform.service.post.dto.ReactionCountDTO;
import com.platformcommons.platform.service.post.facade.assembler.ReactionCountAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReactionCountDTOAssembler implements ReactionCountAssembler {

    @Override
    public ReactionCountDTO toDTO(ReactionCountVO reactionCountVO, Boolean isReactedByMe){
        return ReactionCountDTO.builder()
                .count(reactionCountVO.getCount())
                .entityId(reactionCountVO.getEntityId())
                .entityType(reactionCountVO.getEntityType())
                .isReactedByMe(isReactedByMe)
                .build();
    }
}
