package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.SkillHierarchy;

import org.springframework.data.domain.Page;

public interface SkillHierarchyService {

    Long createLTLDSkillHierarchy(SkillHierarchy lTLDSkillHierarchy);

    Page<SkillHierarchy> getAllLTLDSkillHierarchies(Integer page, Integer size);

    SkillHierarchy getLTLDSkillHierarchyById(Long id);

    void updateLTLDSkillHierarchy(SkillHierarchy lTLDSkill);

    void deleteLTLDSkillHierarchy(Long id);


}
