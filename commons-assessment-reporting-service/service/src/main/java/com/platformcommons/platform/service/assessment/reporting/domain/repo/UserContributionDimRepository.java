package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.UserContributionDim;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface UserContributionDimRepository extends BaseReportRepository<UserContributionDim, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE user_contribution_dim u" +
                   " SET u.question_count = u.question_count + 1 " +
                   " where u.dim_type= :dimType and u.user_id= :userId and u.tenant_id= :tenantId ",nativeQuery = true)
    int updateQuestionCountByDimTypeAndUserIdAndTenantId(String dimType, Long userId, Long tenantId);

    @Query("select u from UserContributionDim u where u.dimType = ?1 and u.dimValue= ?2")
    Set<UserContributionDim> findByFilterFindByTypeAndValue(String dimType, String value,Long userId,Long tenantId);

    @Query(value = "select u from UserContributionDim u where u.dimType = :dimType and u.userId=:userId and u.tenantId= :tenantId")
    Set<UserContributionDim> findByFilterFindByType(String dimType,Long userId,Long tenantId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_contribution_dim u" +
            " SET u.assessment_count = u.assessment_count + 1 " +
            " where u.dim_type= :dimType and u.user_id= :userId and u.tenant_id= :tenantId ",nativeQuery = true)
    int updateAssessmentCountByDimTypeAndUserIdAndTenantId(String dimType, Long userId, Long tenantId);
}