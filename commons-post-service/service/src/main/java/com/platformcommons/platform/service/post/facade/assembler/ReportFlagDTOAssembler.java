package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.ReportFlag;
import com.platformcommons.platform.service.post.dto.ReportFlagDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface ReportFlagDTOAssembler {

    ReportFlagDTO toDTO(ReportFlag entity);

    ReportFlag fromDTO(ReportFlagDTO dto);
}