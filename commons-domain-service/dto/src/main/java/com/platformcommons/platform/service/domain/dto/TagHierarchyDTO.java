package com.platformcommons.platform.service.domain.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class TagHierarchyDTO extends BaseTransactionalDTO {

    private TagDTO tag;
    private Set<Long> parentIds;

    @Builder
    public TagHierarchyDTO(TagDTO tag, Set<Long> parentIds) {
        this.tag = tag;
        this.parentIds = parentIds;
    }
}
