package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.ProposalTag;
import com.platformcommons.platform.service.post.dto.ProposalTagDTO;

public interface ProposalTagDTOAssembler {

    ProposalTagDTO toDTO(ProposalTag entity);

    ProposalTag fromDTO(ProposalTagDTO dto);

}
