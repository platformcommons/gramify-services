package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends BaseRepository<Skill, Long> {
    void deleteById(Long id);
    @Query("SELECT s FROM Skill s WHERE s.id not in (SELECT distinct s.childSkill.id from SkillHierarchy s)")
    Page<Skill> findParentSkill(Pageable of);
}