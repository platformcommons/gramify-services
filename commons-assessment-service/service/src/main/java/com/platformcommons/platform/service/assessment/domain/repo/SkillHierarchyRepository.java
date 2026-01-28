package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.Skill;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.SkillHierarchy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface SkillHierarchyRepository extends BaseRepository<SkillHierarchy, Long> {
    @Query("SELECT distinct s.childSkill from SkillHierarchy s where s.parentSkillId.skillCode=:parentSkillCode")
    Page<Skill> getAllChildSkills(Pageable of, String parentSkillCode);

    void deleteById(Long id);
}