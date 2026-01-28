package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.dto.LeadDTO;


public interface LeadDTOAssembler {

    Lead fromDTO(LeadDTO leadDTO);

    LeadDTO toDTO(Lead lead);

    LeadDTO toMaskedDTO(Lead lead);
}
