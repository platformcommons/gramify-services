package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceSearchDTO;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AssessmentInstanceRepository extends BaseRepository<AssessmentInstance, Long> {
    @Query("select (count(a) > 0) from AssessmentInstance a where a.assessment.id = ?1")
    boolean existsByAssessment_Id(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE AssessmentInstance ai SET ai.isActive = false WHERE ai.id = :id")
    void deactivateById(Long id);

    @Query("SELECT count(ai.id)>0 from AssessmentInstance ai where ai.id=:id and ai.createdByUser = :currentUserId")
    boolean existsByIdAndUserId(Long id, Long currentUserId);

    @Query("SELECT ai from AssessmentInstance ai where ai.id=:id and ai.createdByUser = :currentUserId")
    Optional<AssessmentInstance> findByIdAndUserId(Long id,Long currentUserId);

    @Query("SELECT ai from AssessmentInstance ai where ai.assessment.id=:assessmentId")
    Optional<AssessmentInstance> findByAssessmentId(Long assessmentId);

    @Query(value = "select * " +
                   " from assessment_instance " +
                   " join assessment on assessment_instance.assessment = assessment.id " +
                   " where assessment_instance.is_active = ?1 " +
                   "   and assessment.is_active = ?2 " +
                   "   and assessment_type = ?3 " +
                   "   and assessment_instance.tenant_id = ?4 ",nativeQuery = true)
    Set<AssessmentInstance> findByIsActiveAndAndAssessment_IsActiveAndAssessment_AssessmentType_Code(Boolean isActive, Boolean assessmentIsActive, String assessmentType,Long tenantId);

    @Query("select new com.platformcommons.platform.service.assessment.dto.AssessmentInstanceSearchDTO(" +
            "ai.id,                 ai.assessment.id," +
            "ai.assessment.domain,  ai.assessment.subDomain," +
            "names.text,            ai.assessment.assessmentType, " +
            "ai.assessment.context, ai.scheduleStatus,"+
            "ai.imageURL,           ai.createdTimestamp," +
            "ai.tenantId,           ai.createdByUser) " +
            "from AssessmentInstance ai " +
            "left join ai.assessment.assessmentName names " +
            "where names.languageCode= coalesce(ai.assessment.baseLanguage,'ENG') ")
    Page<AssessmentInstanceSearchDTO> findAllSearchDTO(Pageable of);

    @Query(value = "select assessment_instance.* " +
            " from assessment_instance " +
            " join assessment on assessment_instance.assessment = assessment.id " +
            " where assessment_instance.is_active = :instanceIsActive " +
            "   and assessment.is_active = :assessmentIsActive " +
            "   and (assessment_type in (:assessmentTypes) OR null in (:assessmentTypes)) " +
            "   and (:context is null or context = :context) " +
            "   and (:assessmentSubType is null or assessment_sub_type = :assessmentSubType) " +
            "   and (:assessmentKind is null or assessment_kind in (:assessmentKind)) " +
            "   AND (for_entity_id = :forEntityId OR :forEntityId is null)  "+
            "   AND (for_entity_type = :forEntityType OR :forEntityType is null)  " +
            " limit :offset, :size ",nativeQuery = true)
    Set<AssessmentInstance> findByAssessment_AssessmentTypeInAndAssessment_IsActiveAndIsActive(
            Collection<String> assessmentTypes, Set<String> assessmentKind,
            String assessmentSubType, String context,
            Boolean assessmentIsActive, Boolean instanceIsActive,
            String forEntityId, String forEntityType, Integer offset, Integer size);

    @Query(value = "select count(assessment_instance.id) as `count` " +
            " from assessment_instance " +
            " join assessment on assessment_instance.assessment = assessment.id " +
            " where assessment_instance.is_active = :instanceIsActive " +
            "   and assessment.is_active = :assessmentIsActive " +
            "   and (assessment_type in (:assessmentTypes) OR null in (:assessmentTypes)) " +
            "   and (:context is null or context = :context) " +
            "   and (:assessmentSubType is null or assessment_sub_type = :assessmentSubType) " +
            "   and (:assessmentKind is null or assessment_kind in (:assessmentKind))" +
            "   AND (for_entity_id = :forEntityId OR :forEntityId is null)  "+
            "   AND (for_entity_type = :forEntityType OR :forEntityType is null)  ",
            nativeQuery = true)
    long findByAssessment_AssessmentTypeInAndAssessment_IsActiveAndIsActiveCount(
            Collection<String> assessmentTypes, Set<String> assessmentKind,
            String assessmentSubType, String context,
            Boolean assessmentIsActive, Boolean instanceIsActive, String forEntityId, String forEntityType);

    boolean existsByLinkedSystemIdAndLinkedSystemType(String linkedSystemId, String linkedSystemType);
}