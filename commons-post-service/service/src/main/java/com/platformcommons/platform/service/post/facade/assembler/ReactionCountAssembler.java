package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.vo.ReactionCountVO;
import com.platformcommons.platform.service.post.dto.ReactionCountDTO;

public interface ReactionCountAssembler {

    ReactionCountDTO toDTO(ReactionCountVO reactionCountVO, Boolean isReactedByMe);
}
