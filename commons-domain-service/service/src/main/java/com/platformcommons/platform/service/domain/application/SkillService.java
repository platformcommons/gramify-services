package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.domain.SkillHierarchy;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface SkillService {
    Skill save(Skill skill);

    Skill getSkillByCode(String skillCode, String context);

    Skill update(Skill toBeUpdated);

    List<Skill> createBatch(Collection<Skill> collect);

    Page<Skill> getSkills(Integer page, Integer size, String context, Boolean isRoot);

    Page<Skill> getSubSkills(Integer page, Integer size, String sortBy, String direction, String parentSkillCode, Long depth, String context, String domainCode);

    Page<Skill> getSkillsByDomain(Integer page, Integer size, Boolean isRoot, String context, String domainCode);

    List<Skill> getSkillHierarchyThroughIds(Set<Long> id);

    SearchResult<Skill> searchParentSkills(Integer page, Integer size, String context, String domainCode, String text);

    SearchResult<SkillHierarchy> searchChildSkills(Integer page, Integer size, String parentSkill, String context, String domainCode, String text);

    boolean checkSkillExist(String skillCode, String context);
}
