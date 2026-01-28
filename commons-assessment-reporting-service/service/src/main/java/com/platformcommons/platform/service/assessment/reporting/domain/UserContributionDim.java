package com.platformcommons.platform.service.assessment.reporting.domain;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_contribution_dim")
@Getter @Setter
@NoArgsConstructor
@FilterDefs(value = {
        @FilterDef(
            name = "contextFilter",
            parameters = {
                    @ParamDef(name = "tenantId",type = "long"),
                    @ParamDef(name = "userId",type = "long"),
            }
        )
})
@Filters(value = {
        @Filter(
                name = "contextFilter",
                condition = "tenant_id = :tenantId and user_id = :userId",
                deduceAliasInjectionPoints = false
        )
})
public class UserContributionDim extends BaseReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "tenant_id")
    private Long tenantId;
    @Column(name = "assessment_count")
    private Long assessmentCount;
    @Column(name = "question_count")
    private Long questionCount;
    @Column(name = "dim_type")
    private String dimType;
    @Column(name = "dim_value")
    private String dimValue;

    @Builder
    public UserContributionDim(Long id, Long userId, Long tenantId,Long assessmentCount, Long questionCount,String dimType,String dimValue) {
        this.id = id;
        this.userId = userId;
        this.tenantId = tenantId;
        this.assessmentCount = assessmentCount;
        this.questionCount = questionCount;
        this.dimType = dimType;
        this.dimValue = dimValue;
    }

    public void init() {
        this.id=0L;
        this.userId=PlatformSecurityUtil.getCurrentUserId();
        this.tenantId=PlatformSecurityUtil.getCurrentTenantId();
    }
}
