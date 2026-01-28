package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.TagV2DTO;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface TagFacade {

    Long save(TagDTO tagDTO );

    TagDTO update(TagDTO tagDTO );

    PageDTO<TagDTO> getAllPage(Integer page, Integer size, String context,Boolean includeRefLabels);

    void delete(Long id,String reason);

    TagDTO getById(Long id);

    TagDTO getTagByCode(String code);

    TagDTO getTagByCodeV2(String code);

    PageDTO<TagDTO> getTagByCodes(Set<String> codes);

    void saveAll(List<TagDTO> body);

    void addTagHierarchyBatch(List<Long> childTagIds, Long parentTagId);

    void deleteTagHierarchy(Long parentTagId, Long childTagId);

    PageDTO<TagDTO> getTags(Integer page, Integer size, String context, Boolean isRoot, String type);


    Set<TagHierarchyDTO> getSubTags(@NotNull Set<Long> parentTagIds, Long depth, String context, @NotNull String type);

    Set<TagDTO> getSubTagsByParentId(Long parentTagId, Long depth, String context, String type);

    TagV2DTO attachDomainsToTag(Long tagId, Set<Long> domainIds);

    PageDTO<TagV2DTO> getTagsByType(String type, Integer page, Integer size);

    TagDTO saveTagV2(TagDTO tagDTO);
}
