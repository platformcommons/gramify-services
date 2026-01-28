package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(
        name = "user_consent",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="UniqueUserAndConsent",
                        columnNames = {"user_id","is_active","consent_type","consent_version"}
                )})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserConsent extends BaseTransactionalEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id",nullable = false,updatable = false)
    private Long userId;

    @NotNull
    @Column(name = "consent_type")
    private String consentType;

    @Column(name = "consent_doc")
    private String consentDoc;

    @Column(name = "consent_version")
    private String consentVersion;

    @Column(name = "consent_status",nullable = false)
    private String consentStatus;

    @Column(name = "consent_given_on")
    private Date consentGivenOn;

    @Column(name = "consent_context")
    private String consentContext;

    @Builder
    public UserConsent(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp,
                       Long appLastModifiedTimestamp, Long id, Long userId, String consentType, String consentDoc,
                       String consentVersion, String consentStatus, Date consentGivenOn, String consentContext) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.userId = userId;
        this.consentType = consentType;
        this.consentDoc = consentDoc;
        this.consentVersion = consentVersion;
        this.consentStatus = consentStatus;
        this.consentGivenOn = consentGivenOn;
        this.consentContext = consentContext;
    }

    public void init() {
        this.id = 0L;
        this.consentGivenOn =  new Date();
    }

    public void patch(UserConsent toBeUpdated) {
        if(toBeUpdated.getConsentType() != null) {
            this.consentType =  toBeUpdated.getConsentType();
        }
        if(toBeUpdated.getConsentDoc() != null) {
            this.consentDoc =  toBeUpdated.getConsentDoc();
        }
        if(toBeUpdated.getConsentVersion() != null) {
            this.consentVersion =  toBeUpdated.getConsentVersion();
        }
        if(toBeUpdated.getConsentStatus() != null) {
            this.consentStatus =  toBeUpdated.getConsentStatus();
        }
        if(toBeUpdated.getConsentContext() != null) {
            this.consentContext =  toBeUpdated.getConsentContext();
        }

    }
}
