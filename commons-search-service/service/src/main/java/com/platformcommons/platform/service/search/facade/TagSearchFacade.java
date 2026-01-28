package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.TagSearchDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.Set;


public interface TagSearchFacade {

    PageDTO<TagSearchDTO> readTagSearch(String keyword, String context, Set<String> excludeTypes, Integer page, Integer size);

//    @Async
//    void  syncTag(Integer startPage, Integer defaultSize);

    void reindex();
    @Async
    void  syncTag(Integer startPage, Integer defaultSize, String type);
}
