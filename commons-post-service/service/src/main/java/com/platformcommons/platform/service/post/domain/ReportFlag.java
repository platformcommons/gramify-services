package com.platformcommons.platform.service.post.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "report_flag")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ReportFlag extends BaseTransactionalEntity implements DomainEntity<ReportFlag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "report_status")
    private String reportStatus;

    @Column(name = "reported_on_entity_id")
    private Long reportedOnEntityId;

    @Column(name = "reported_on_entity_type")
    private String reportedOnEntityType;

    @Column(name = "reportee_remarks")
    private String reporteeRemarks;

    @Column(name = "reported_on_date_time")
    private Date reportedOnDateTime;

    @Column(name = "reviewed_by_actor_id")
    private Long reviewedByActorId;

    @Column(name = "reviewed_by_actor_type")
    private String reviewedByActorType;

    @Column(name = "reviewer_remarks")
    private String reviewerRemarks;

    @Column(name = "report_text")
    private String reportText;

    @Column(name = "reported_by_actor_id")
    private Long reportedByActorId;

    @Column(name = "reported_by_actor_type")
    private String reportedByActorType;

    @Column(name = "market_code")
    private String marketCode;

    @Transient
    private boolean isNew;

    @Builder
    public ReportFlag(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String reportType, String reportStatus, Long reportedOnEntityId, String reportedOnEntityType, String reporteeRemarks, Date reportedOnDateTime, Long reviewedByActorId, String reviewedByActorType, String reviewerRemarks, String reportText, Long reportedByActorId, String reportedByActorType, String marketCode) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.reportType = reportType;
        this.reportStatus = reportStatus;
        this.reportedOnEntityId = reportedOnEntityId;
        this.reportedOnEntityType = reportedOnEntityType;
        this.reporteeRemarks = reporteeRemarks;
        this.reportedOnDateTime = reportedOnDateTime;
        this.reviewedByActorId = reviewedByActorId;
        this.reviewedByActorType = reviewedByActorType;
        this.reviewerRemarks = reviewerRemarks;
        this.reportText = reportText;
        this.reportedByActorId = reportedByActorId;
        this.reportedByActorType = reportedByActorType;
        this.marketCode = marketCode;
    }

    public Date getReported_on_date_time() {
        return new Date(reportedOnDateTime != null ? reportedOnDateTime.getTime() : new Date().getTime());
    }

    public void setReported_on_date_time(Date reportedOnDateTime) {
        this.reportedOnDateTime = new Date(reportedOnDateTime != null ? reportedOnDateTime.getTime() : new Date().getTime());
    }

    public void init() {
        this.reportedByActorId = PlatformSecurityUtil.getCurrentUserId();
        this.reportedByActorType = PlatformSecurityUtil.getActorContext().getActorContext();
    }

    public void update(ReportFlag toBeUpdated) {
        if (toBeUpdated.getReportType() != null) this.reportType = toBeUpdated.getReportType();
        if (toBeUpdated.getReportStatus() != null) this.reportStatus = toBeUpdated.getReportStatus();
        if (toBeUpdated.getReportedOnEntityId() != null) this.reportedOnEntityId = toBeUpdated.getReportedOnEntityId();
        if (toBeUpdated.getReportedOnEntityType() != null) this.reportedOnEntityType = toBeUpdated.getReportedOnEntityType();
        if (toBeUpdated.getReporteeRemarks() != null) this.reporteeRemarks = toBeUpdated.getReporteeRemarks();
        if (toBeUpdated.getReportedOnDateTime() != null) this.reportedOnDateTime = toBeUpdated.getReportedOnDateTime();
        if (toBeUpdated.getReviewedByActorId() != null) this.reviewedByActorId = toBeUpdated.getReviewedByActorId();
        if (toBeUpdated.getReviewedByActorType() != null) this.reviewedByActorType = toBeUpdated.getReviewedByActorType();
        if (toBeUpdated.getReviewerRemarks() != null) this.reviewerRemarks = toBeUpdated.getReviewerRemarks();
        if (toBeUpdated.getReportText() != null) this.reportText = toBeUpdated.getReportText();
        if (toBeUpdated.getReportedByActorId() != null) this.reportedByActorId = toBeUpdated.getReportedByActorId();
        if (toBeUpdated.getReportedByActorType() != null) this.reportedByActorType = toBeUpdated.getReportedByActorType();
        if (toBeUpdated.getMarketCode() != null) this.marketCode = toBeUpdated.getMarketCode();
    }
}