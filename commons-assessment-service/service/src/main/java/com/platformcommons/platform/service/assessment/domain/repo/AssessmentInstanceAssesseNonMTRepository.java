package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AssessmentInstanceAssesseNonMTRepository extends NonMultiTenantBaseRepository<AssessmentInstanceAssesse,Long> {

    String FIND_BY_ASSESSMENT_INSTANCE_ID_AND_ASSESSOR = "select aia.* " +
            " from commons_assessment_db.assessment_instance_assesse aia " +
            " join commons_assessment_db.assessment_instance ai on aia.assessment_instance = ai.id " +
            " join commons_assessment_db.assessment a on ai.assessment = a.id " +
            " left join commons_assessment_db.assessment_actor on aia.id = assessment_actor.assessment_instance_assesse_id " +
            " where ( coalesce(:instanceIds) is null OR ai.id in (:instanceIds)) " +
            "   and (a.context = :context or :context is null) " +
            "   and (a.assessment_sub_type = :subtype or :subtype is null) " +
            "   and (aia.assessee_actor_id = :assesseId or assessment_actor.actor_id = :assesseId OR :assesseId is null) " +
            "   and (aia.assessee_actor_type = :assesseType or assessment_actor.actor_type = :assesseType OR :assesseType is null) " +
            "   and assessor_actor_id = :accessorId " +
            "   and assessor_actor_type = :accessorType " +
            "   and aia.is_active=1   " +
            " group by aia.id";

    String LIMIT = " limit :offset, :size";
    String FIND_BY_ASSESSMENT_INSTANCE_ID_AND_ASSESSOR_COUNT = "SELECT count(*) as count from ("+FIND_BY_ASSESSMENT_INSTANCE_ID_AND_ASSESSOR+") countTable";
    @Query(nativeQuery = true, value = FIND_BY_ASSESSMENT_INSTANCE_ID_AND_ASSESSOR + LIMIT)
    Set<AssessmentInstanceAssesse> findByAssessmentInstanceIdAndAssessor(Set<Long> instanceIds, String accessorId,
                                                                          String accessorType, String assesseId,
                                                                          String assesseType, String subtype,
                                                                          String context, Integer offset, Integer size);

    @Query(nativeQuery = true, value =  FIND_BY_ASSESSMENT_INSTANCE_ID_AND_ASSESSOR_COUNT)
    long findByAssessmentInstanceIdAndAssessorCount(Set<Long> instanceIds, String accessorId,
                                                                          String accessorType, String assesseId,
                                                                          String assesseType, String subtype,
                                                                          String context);

    @Query("select a from AssessmentInstanceAssesse a where a.assessmentInstance.assessment.id = ?1")
    Page<AssessmentInstanceAssesse> findByAssessmentId(Long assessmentId, PageRequest of);

}
