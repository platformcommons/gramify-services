package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.AssessmentConfig;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.Assessment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AssessmentRepository extends BaseRepository<Assessment, Long> {

    @Query("select count(a.id)>0 from Assessment a where a.id = :id and a.createdByUser=:userId")
    boolean existsByIdAndUserId(Long id,Long userId);

    @Query("select a from Assessment a where a.id = :id and a.createdByUser=:userId")
    Optional<Assessment> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT config FROM Assessment a " +
            " JOIN a.assessmentInstanceList instance " +
            " JOIN a.assessmentConfigs config " +
            " WHERE instance.id = :assessmentInstanceId")
     Set<AssessmentConfig> getAssessmentConfig(Long assessmentInstanceId);

    @Query("select a from Assessment a where a.assessmentCode = :code")
    List<Assessment> findByAssessmentCode(String code);


    @Query("SELECT a.id FROM Assessment a WHERE a.uuid IN (:uuids) OR a.assessmentCode IN (:codes)")
    Set<Long> getAssessmentIdsByUUIDsOrCodes(Set<String> uuids, Set<String> codes);
}