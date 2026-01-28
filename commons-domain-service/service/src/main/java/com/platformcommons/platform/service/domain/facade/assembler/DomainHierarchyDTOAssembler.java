package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.DomainHierarchy;
import com.platformcommons.platform.service.domain.dto.DomainHierarchyDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface DomainHierarchyDTOAssembler {

    DomainHierarchyDTO toDTO(DomainHierarchy entity);

    DomainHierarchy fromDTO(DomainHierarchyDTO dto);
}