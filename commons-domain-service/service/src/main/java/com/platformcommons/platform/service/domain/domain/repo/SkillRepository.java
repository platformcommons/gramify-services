package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SkillRepository extends BaseRepository<Skill, Long> {
    @Query("select (count(s) > 0) from Skill s where s.skillCode = ?1 and s.context = ?2 and s.tenantId = ?3")
    boolean existsBySkillCodeAndContextAndTenantId(String skillCode, String context, Long tenantId);
    @Query("select s from Skill s where s.skillCode = ?1 and s.context = ?2")
    Optional<Skill> findBySkillCodeAndContext(String skillCode, String context);

    @Query( "FROM #{#entityName} s " +
            "where ( s.isRoot = :isRoot OR :isRoot is null ) AND " +
            "s.context = :context AND " +
            "s.isActive = 1 ")
    Page<Skill> getSkills(String context, Boolean isRoot, Pageable pageable);

    @Query("select s from Skill s where s.domainCode = ?1 and ( s.isRoot = ?2 OR ?2 is null ) and (s.context = ?3 OR ?3 is null )")
    Page<Skill> findByDomainCode(String domainCode, Boolean isRoot, String context, Pageable pageable);

    @Query("FROM #{#entityName} s " +
            "where s.isRoot = true and s.id in (:id)")
    List<Skill> getParentSkills(Set<Long> id);
}
