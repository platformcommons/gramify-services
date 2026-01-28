package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;

import java.util.Set;


public interface TagHierarchyDTOAssembler {

    Set<TagHierarchyDTO> toDTOs(Set<TagHierarchy> tagList);
}