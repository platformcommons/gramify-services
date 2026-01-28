package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.domain.SkillHierarchy;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SkillHierarchyRepository extends NonMultiTenantBaseRepository<SkillHierarchy, Long>  {
    @Query("select sh.skill from SkillHierarchy sh " +
            "where (sh.parentSkill.skillCode = :parentSkillCode OR :parentSkillCode IS NULL ) " +
            " and  (sh.parentSkill.context = :context OR :context IS NULL ) " +
            " and ( sh.depth <= :depth OR :depth is NULL ) " +
            " and sh.isActive = true " +
            " and sh.skill.isActive = true " +
            " and ( sh.skill.domainCode = :domainCode OR :domainCode is NULL )")
    Page<Skill> findSubDomains(@Param(value = "parentSkillCode") String parentSkillCode,
                               @Param(value = "depth") Long depth,
                               @Param(value = "context") String context,
                               @Param(value = "domainCode") String domainCode,
                               Pageable pageable);

    @Query("select sh from SkillHierarchy sh where sh.skill.id in ( :id ) ")
    List<SkillHierarchy> findBySkills(Set<Long> id);
}