package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceAccessibilityVO;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AssessmentInstanceNontMTRepository extends NonMultiTenantBaseRepository<AssessmentInstance, Long> {

    String GET_ASSESSMENT_INSTANCE_INFO_QUERY_FIELDS =
                    "select " +
                    "             max(mltext.text)            as assessmentName, " +
                    "             a.domain                    as domainCode, " +
                    "             a.sub_domain                as subDomainCode, " +
                    "             ai.id                       as assessmentInstanceId, " +
                    "             ai.imageurl                 as assessmentImage, " +
                    "             count(DISTINCT sq.id)       as noOfQuestion, " +
                    "             CAST(ai.created_by_user AS CHAR) as author";
    String GET_ASSESSMENT_INSTANCE_INFO_ID_QUERY_FIELDS =
            " select " +
                    "       DISTINCT ai.id as assessmentInstanceId ";
    String GET_ASSESSMENT_INSTANCE_INFO_QUERY=
            " from assessment_instance ai " +
            "         LEFT join assessment a on ai.assessment = a.id " +
            "         LEFT join assessmentinstanceaccessor accessors on ai.id = accessors.assessment_instance_id and " +
            "                                                           accessors.login = :currentUserLogin " +
            "         LEFT JOIN (select assessment_instance_id, " +
            "                           count(text) as hasLanguage " +
            "                    from assessment_instance_name name " +
            "                             join mltext on name.asmt_inst_name_id = mltext.id " +
            "                    where language_code = :language " +
            "                    group by assessment_instance_id) as an on ai.id = an.assessment_instance_id " +
            "         LEFT join assessment_instance_name name on ai.id = name.assessment_instance_id " +
            "         LEFT JOIN mltext on name.asmt_inst_name_id = mltext.id and mltext.language_code = " +
            "                                                                    if(an.hasLanguage, :language, IFNULL(a.base_language, 'ENG')) " +
            "         join assessment_question_paper questinopaper on a.id = questinopaper.assessment " +
            "         LEFT join question_paper_section section on questinopaper.id = section.assessment_question_paper " +
            "         left join section_questions sq on section.id = sq.question_paper_section " +
            "where (a.domain = :domain or :domain is null) " +
            "  and (a.context = :context OR :context is null) " +
            "  and (a.sub_domain = :subDomain or :subDomain is null) " +
            "  and (ai.schedule_status = :status or :status is null) " +
            "  and ((ai.created_by_user = :currentUserId) OR " +
            "       (ai.created_by_user <> :currentUserId and ai.schedule_status in ('ASSESSMENT.RELEASE', 'ASSESSMENT.PUBLISH'))) " +
            "  and ( " +
            "        (:ownedBy = 'ALL') " +
            "        OR " +
            "        (:ownedBy = 'ORGANIZATION' AND ai.created_by_user != :currentUserId) " +
            "        OR " +
            "        (:ownedBy = 'USER' AND ai.created_by_user = :currentUserId) " +
            "    ) " +
            "  AND :orgid = ai.tenant_id " +
            "  and sq.is_active = true " +
            "  and ai.is_active";

    @Query(nativeQuery = true,value = GET_ASSESSMENT_INSTANCE_INFO_ID_QUERY_FIELDS+GET_ASSESSMENT_INSTANCE_INFO_QUERY)
    List<Long> getAssessmentInstanceInfoIdForAll(String domain, Long currentUserId,
                                                 Long orgid, String subDomain,
                                                 String status, String language,
                                                 String currentUserLogin,String ownedBy,String context);


    @Query(value =GET_ASSESSMENT_INSTANCE_INFO_QUERY_FIELDS+GET_ASSESSMENT_INSTANCE_INFO_QUERY +
            " group by ai.id " +
            "ORDER BY CASE WHEN :sortBy = 'createdAt' AND :sortOrder = 'asc' THEN ai.created_timestamp END ASC,  " +
            "         CASE WHEN :sortBy = 'createdAt' AND :sortOrder = 'desc' THEN ai.created_timestamp END DESC,  " +
            "         CASE WHEN :sortBy = 'name.text' AND :sortOrder = 'asc' THEN max(mltext.text) END ASC,  " +
            "         CASE WHEN :sortBy = 'name.text' AND :sortOrder = 'desc' THEN max(mltext.text) END DESC, "+
            "         CASE WHEN true THEN max(mltext.text) END ASC "+
            "limit :page,:size",nativeQuery = true)
    List<Map<String,Object>> getAssessmentInstanceInfoForAll(String domain, Long currentUserId,
                                                             Long orgid, String subDomain,
                                                             String status, String language,
                                                             String currentUserLogin, String ownedBy,
                                                             String sortBy, String sortOrder,
                                                             Integer page, Integer size, String context);

    @Query(value = "SELECT count(*) as count from ( " +GET_ASSESSMENT_INSTANCE_INFO_QUERY_FIELDS+GET_ASSESSMENT_INSTANCE_INFO_QUERY+ " group by ai.id  ) as t1",nativeQuery = true)
    int getAssessmentInstanceInfoForAllCount(String domain, Long currentUserId,
                                             Long orgid, String subDomain,
                                             String status, String language,
                                             String currentUserLogin,String ownedBy,String context);

    @Transactional
    @Modifying
    @Query("update AssessmentInstance a set a.duplicatedCount = IFNULL(a.duplicatedCount,0)+1  where a.id = ?1")
    void updateDuplicatedCountById(Long duplicatedCount);

    @Query("select a.id from AssessmentInstance a where a.assessment.id = ?1")
    Long getAssessmentInstanceIdByAssessmentId(Long assessmentId);


    @Query("select new com.platformcommons.platform.service.assessment.domain.vo." +
            "AssessmentInstanceAccessibilityVO(a.id,a.tenantId,a.scheduleStatus,a.isPublic,a.specificVisibility,a.createdByUser) " +
            " from AssessmentInstance a where a.id = ?1 ")
    Optional<AssessmentInstanceAccessibilityVO> findAccessibilityVOById(Long assessmentInstanceId);

    @Query(value = "SELECT a FROM AssessmentInstance a " +
            " JOIN FETCH a.assessment " +
            "WHERE a.id = :assessmentInstanceId ")
    Optional<AssessmentInstance> findByIdFetchAssessment(Long assessmentInstanceId);
}
