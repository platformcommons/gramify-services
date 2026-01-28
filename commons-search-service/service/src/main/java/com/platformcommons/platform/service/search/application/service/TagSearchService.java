package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.TagSearch;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface TagSearchService {

    TagSearch createTagSearch(TagSearch tagSearch);

    TagSearch updateTagSearch(TagSearch tagSearch);

    Page<TagSearch> readTagSearch(String keyword, String context, Set<String> excludeTypes,Integer page, Integer size);

    void addOrUpdateAll(Set<TagSearch> tagSearches);

    void reindex();
}
