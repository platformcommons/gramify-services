package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface AssessmentInstanceDimRepository extends JpaRepository<AssessmentInstanceDim, Long> {
    @Transactional
    @Modifying
    @Query("delete from AssessmentInstanceDim a where a.assessmentId = ?1 and a.linkedSystem = ?2")
    int deleteByAssessmentId(Long assessmentId, String linkedSystem);
    @Transactional
    @Modifying
    @Query("update AssessmentInstanceDim a set " +
            "a.questionPaperId = ?1,           a.questionPaperIdCreatedAt = ?2, " +
            "a.questionPaperCode = ?3,         a.questionPaperName = ?4, " +
            "a.questionPaperDescription = ?5, a.totalWeight = ?6, " +
            "a.responseCount = ?7,            a.totalQuestion = ?8 " +
            "where a.assessmentId = ?9 and a.linkedSystem = ?10")
    void updateForAssessmentQuestionPaperCreateEvent(Long questionPaperId,            Long questionPaperIdCreatedAt,
                                                     String questionPaperCode,        String questionPaperName,
                                                     String questionPaperDescription, Double totalWeight,
                                                     Long responseCount,              Long totalQuestion,
                                                     Long assessmentId,               String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from AssessmentInstanceDim a where a.assessmentInstanceId = ?1 and a.linkedSystem = ?2")
    int deleteByAssessmentInstanceId(Long assessmentInstanceId, String linkedSystem);
    @Query("select a from AssessmentInstanceDim a where a.assessmentId = ?1 and a.linkedSystem = ?2")
    Optional<AssessmentInstanceDim> findByAssessmentId(Long assessmentId, String linkedSystem);

    @Query("SELECT a FROM AssessmentInstanceDim a WHERE a.assessmentId= :assessmentId AND a.linkedSystem= :linkedSystem")
    Optional<AssessmentInstanceDim> getByAssessmentId(@Param("assessmentId") Long assessmentId,  String linkedSystem);

    @Transactional
    @Modifying
    @Query(value = "UPDATE assessment_instance_dim a  " +
                   "set  a.assessment_instance_id           = :assessmentInstanceId,  " +
                   "     a.assessment_instance_created_at   = :assessmentInstanceCreatedAt,  " +
                   "     a.duplicated_count                 = :duplicatedCount,  " +
                   "     a.duplicated_from                  = :duplicatedFrom,  " +
                   "     a.schedule_status                  = :scheduleStatus,  " +
                   "     a.assessment_instance_end_date     = :assessmentInstanceEndDate,  " +
                   "     a.assessment_instance_start_date   = :assessmentInstanceStartDate,  " +
                   "     a.is_public                        = :isPublic,  " +
                   "     a.is_specific_visibility           = :isSpecificVisibility,  " +
                   "     a.monitered_by_actor_id            = :moniteredByActorId,  " +
                   "     a.monitered_by_actor_type          = :moniteredByActorType,  " +
                   "     a.conducted_by_actor_id            = :conductedByActorId,  " +
                   "     a.conducted_by_actor_type          = :conductedByActorType,  " +
                   "     a.for_entity_id                    = :forEntityId,  " +
                   "     a.for_entity_type                  = :forEntityType,  " +
                   "     a.assessment_instance_name         = :assessmentInstanceName,  " +
                   "     a.sequence                         = :sequence  " +
                   "where a.assessment_id = :assessmentId and a.linked_system = :linkedSystem",
            nativeQuery = true)
    void updateForAssessmentInstanceCreateEvent(@Param("assessmentInstanceId")        Long    assessmentInstanceId,
                                                @Param("assessmentInstanceCreatedAt") Long    assessmentInstanceCreatedAt,
                                                @Param("duplicatedCount")             Long    duplicatedCount,
                                                @Param("duplicatedFrom")              Long    duplicatedFrom,
                                                @Param("scheduleStatus")              String  scheduleStatus,
                                                @Param("assessmentInstanceEndDate")   Date    assessmentInstanceEndDate,
                                                @Param("assessmentInstanceStartDate") Date    assessmentInstanceStartDate,
                                                @Param("isPublic")                    Boolean isPublic,
                                                @Param("isSpecificVisibility")        Boolean isSpecificVisibility,
                                                @Param("moniteredByActorId")          String  moniteredByActorId,
                                                @Param("moniteredByActorType")        String  moniteredByActorType,
                                                @Param("conductedByActorId")          String  conductedByActorId,
                                                @Param("conductedByActorType")        String  conductedByActorType,
                                                @Param("forEntityId")                 String  forEntityId,
                                                @Param("forEntityType")               String  forEntityType,
                                                @Param("assessmentInstanceName")      String  assessmentInstanceName,
                                                @Param("assessmentId")                Long    assessmentId,
                                                @Param("sequence")                    Long    sequence,
                                                @Param("linkedSystem")                String  linkedSystem);



    Optional<AssessmentInstanceDim> findByAssessmentInstanceIdAndTenantIdAndLinkedSystem(Long assessmentInstanceId,Long tenantId, String linkedSystem);
}