package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.Tag;

import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.domain.dto.TagHierarchyDTO;
import org.springframework.data.domain.Page;

import java.util.*;

public interface TagService {

    Tag save(Tag tag );

    Tag update(Tag tag );

    Page<Tag> getByPage(Integer page, Integer size,String context);

    void deleteById(Long id,String reason);

    Tag getById(Long id);


    List<Tag> saveAll(LinkedList<Tag> tags);

    Set<Tag> getByCodes(Set<String> codeList);

    Set<Tag> getByCode(String code);

    Page<Tag> getTags(Integer page, Integer size, String context, Boolean isRoot, String type);

    Set<TagHierarchy> getSubTags(Set<Long> parentTagId, Long depth, String context, String type);

    Set<TagHierarchy> getSubTagsByExactDepth(Long parentTagId, Long depth, String context, String type);

    Tag attachDomainsToTag(Long tagId, Set<Long> domainIds);

    Page<Tag> getTagsByType(String type, Integer page, Integer size);
}
