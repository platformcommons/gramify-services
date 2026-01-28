package com.platformcommons.platform.service.domain.facade.assembler.impl;

import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import com.platformcommons.platform.service.domain.facade.TagHierarchyDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagHierarchyDTOAssemblerImpl implements TagHierarchyDTOAssembler {

    private final TagDTOAssembler tagDTOAssembler;

    @Override
    public Set<TagHierarchyDTO> toDTOs(Set<TagHierarchy> tagList) {
        Map<Long, List<TagHierarchy>> map = tagList.stream().collect(Collectors.groupingBy(tagHierarchy -> tagHierarchy.getTag().getId()));
        return map.values()
                .stream()
                .map(tagHierarchyList -> TagHierarchyDTO.builder()
                                            .tag(tagDTOAssembler.toDTO(tagHierarchyList.get(0).getTag()))
                                            .parentIds(tagHierarchyList.stream()
                                            .map(tagHierarchy -> tagHierarchy.getParentTag().getId())
                                            .collect(Collectors.toSet()))
                                            .build())
                .collect(Collectors.toSet());
    }
}
