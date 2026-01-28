package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.RequestForQuotation;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.dto.RequestForQuotationDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestForQuotationAssembler {

    RequestForQuotationDTO toDTO(RequestForQuotation entity);

    RequestForQuotation fromDTO(RequestForQuotationDTO dto);

}
