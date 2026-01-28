package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.Proposal;
import com.platformcommons.platform.service.post.dto.ProposalDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProposalTagDTOAssembler.class})
public interface ProposalDTOAssembler {

    ProposalDTO toDTO(Proposal proposal);

    Proposal fromDTO(ProposalDTO proposalDTO);
}
