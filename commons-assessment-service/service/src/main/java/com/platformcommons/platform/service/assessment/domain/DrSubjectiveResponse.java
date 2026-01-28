package com.platformcommons.platform.service.assessment.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "dr_subjective_response")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class DrSubjectiveResponse extends BaseTransactionalEntity implements DomainEntity<DrSubjectiveResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aiadefault_response", nullable = true, updatable = false)
    private AiaDefaultResponse aiadefaultResponse;

    @Column(name = "response_text")
    private String responseText;

    @Column(name = "remarks")
    private String remarks;

    @Transient
    private boolean isNew;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Builder
    public DrSubjectiveResponse(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, AiaDefaultResponse aiadefaultResponse, String responseText, String remarks,String linkedSystemId,String linkedSystemType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.aiadefaultResponse = aiadefaultResponse;
        this.responseText = responseText;
        this.remarks = remarks;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;

    }

    public void init() {
        this.id = null;
    }

    public void update(DrSubjectiveResponse toBeUpdated) {
        if (toBeUpdated == null) return;
        if(Objects.equals(Boolean.FALSE,toBeUpdated.getIsActive())){
            deactivate(toBeUpdated.getInactiveReason());
            return;
        }
        if (toBeUpdated.getResponseText() != null) {
            this.setResponseText(toBeUpdated.getResponseText());
        }
        if (toBeUpdated.getRemarks() != null) {
            this.setRemarks(toBeUpdated.getRemarks());
        }
    }
}