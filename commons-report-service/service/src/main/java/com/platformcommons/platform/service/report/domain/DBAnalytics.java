package com.platformcommons.platform.service.report.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "db_analytics")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DBAnalytics extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<DBAnalytics> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "analytics_date")
    private Date analyticsDate;

    @Column(name = "tenant_identifier_column")
    private String tenantIdentifierColumn;

    @Column(name = "tenant_id")
    private Long tenantId;

    @ElementCollection
    @MapKeyColumn(name="column_name")
    @Column(name="count")
    @CollectionTable(name="column_row_counts", joinColumns=@JoinColumn(name="db_analytics_id"))
    private Map<String,Long>   columnRowCountsForTenant;


    @Builder
    public DBAnalytics(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                       Date analyticsDate, String tenantIdentifierColumn, Long tenantId,
                       Map<String, Long> columnRowCountsForTenant) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.analyticsDate = analyticsDate;
        this.tenantIdentifierColumn = tenantIdentifierColumn;
        this.tenantId = tenantId;
        this.columnRowCountsForTenant = columnRowCountsForTenant;
    }
}
