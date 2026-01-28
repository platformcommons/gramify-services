package com.platformcommons.platform.service.domain.application;

public interface TagHierarchyService {
    void createHierarchy(Long childTagId, Long parentTagId);
    void deleteHierarchy(Long parentTagId, Long childTagId);
}
