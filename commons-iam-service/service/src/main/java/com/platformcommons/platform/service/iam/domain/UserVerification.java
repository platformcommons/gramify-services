package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "tenant_id"})})
public class UserVerification extends BaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(name = "verificationStatus")
    private String verificationStatus;

    @Column(name = "verification_request_message")
    private String verificationRequestMessage;

    @Column(name = "reviewed_by_user")
    private Long reviewedByUser;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "reviewed_on_date")
    private Date reviewedOnDate;

    @Column(name = "app_context")
    private String appContext;

    @Column(name = "market_context")
    private String marketContext;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_verification_assigned_roles",
            joinColumns = @JoinColumn(name = "user_verification_id"))
    @Column(name = "role_code")
    private Set<String> assignedRoles;

    @Builder
    public UserVerification(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                            Long id, Long userId, String verificationStatus, String verificationRequestMessage, Long verifiedByUser,
                            String remarks, Date verifedOnDate,String appContext,Set<String> assignedRoles, String marketContext) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.userId = userId;
        this.verificationStatus = verificationStatus;
        this.verificationRequestMessage = verificationRequestMessage;
        this.reviewedByUser = verifiedByUser;
        this.remarks = remarks;
        this.reviewedOnDate = verifedOnDate;
        this.appContext = appContext;
        this.marketContext = marketContext;
        this.assignedRoles = assignedRoles;
    }

    public void statusChange(UserVerification toBeUpdated) {
        if(toBeUpdated.getVerificationStatus() == null) {
            throw new InvalidInputException("Status must not be null");
        }
        this.verificationStatus = toBeUpdated.getVerificationStatus();
        this.reviewedByUser = PlatformSecurityUtil.getCurrentUserId();
        this.reviewedOnDate = new Date();
        if(toBeUpdated.getRemarks() != null) {
            this.remarks = toBeUpdated.getRemarks();
        }
        if(toBeUpdated.getAssignedRoles() != null) {
            this.assignedRoles = toBeUpdated.getAssignedRoles();
        }

    }
}
