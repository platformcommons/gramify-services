package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAccessor;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface AssessmentInstanceAccessorRepository extends BaseRepository<AssessmentInstanceAccessor, Long> {
    @Query("select a from AssessmentInstanceAccessor a where a.login in ?1 and a.assessmentInstance.id = ?2")
    List<AssessmentInstanceAccessor> findByLoginInAndAssessmentInstance(Collection<String> logins, Long assessmentInstance);
    @Transactional
    @Modifying
    @Query("delete from AssessmentInstanceAccessor a where a.login in (?1) and a.assessmentInstance.id = ?2")
    void deleteByLoginInAndAssessmentInstance_Id(Collection<String> logins,Long instance);
    @Query("select a from AssessmentInstanceAccessor a where a.assessmentInstance.id = ?1")
    Page<AssessmentInstanceAccessor> findByAssessmentInstance_Id(Long id, Pageable pageable);
    @Query("select (count(a) > 0) from AssessmentInstanceAccessor a where a.assessmentInstance.id = ?1 and a.login = ?2")
    boolean existsByAssessmentInstance_IdAndLogin(Long id, String login);

    @Query("select distinct a.login from AssessmentInstanceAccessor a where a.login in (?1) and a.assessmentInstance.id = ?2")
    Set<String> findByUserLogins(Set<String> logins,Long instance);

}