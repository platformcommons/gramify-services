package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.domain.AssessmentConfig;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

public interface AssessmentNonMTRepository extends NonMultiTenantBaseRepository<Assessment, Long> {
    @Transactional
    @Modifying
    @Query("update Assessment a set a.duplicatedCount = IFNULL(a.duplicatedCount,0)+1 " +
            "where a.id = :id")
    void updateDuplicatedCountById(Long id);

    @Query("SELECT config FROM Assessment a " +
            " JOIN a.assessmentInstanceList instance " +
            " JOIN a.assessmentConfigs config " +
            " WHERE instance.id = :assessmentInstanceId")
    Set<AssessmentConfig> getAssessmentConfig(Long assessmentInstanceId);
}
