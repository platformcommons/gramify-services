package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.SkillHierarchy;

import java.util.List;
import java.util.Set;

public interface SkillHierarchyService {
    void createHierarchy(Long skillId, Long parentSkillId);

    List<SkillHierarchy> getChildSkill(Set<Long> id);
}
