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
@Table(name = "dr_objective_response")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class DrObjectiveResponse extends BaseTransactionalEntity implements DomainEntity<DrObjectiveResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aiadefault_response", nullable = true, updatable = false)
    private AiaDefaultResponse aiadefaultResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_option", nullable = true, updatable = false)
    private DefaultOptions defaultOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MtfOptionId", nullable = true, updatable = false)
    private MtfOption mtfOptionId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;

    @Transient
    private boolean isNew;

    @Builder
    public DrObjectiveResponse(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, AiaDefaultResponse aiadefaultResponse, DefaultOptions defaultOption, String remarks,String linkedSystemId,String linkedSystemType, MtfOption mtfOptionId) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.aiadefaultResponse = aiadefaultResponse;
        this.defaultOption = defaultOption;
        this.remarks = remarks;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;
        this.mtfOptionId = mtfOptionId;
    }

    public void init() {
        this.id = null;
    }

    public void update(DrObjectiveResponse toBeUpdated) {
        if (toBeUpdated == null) return;
        if(Objects.equals(Boolean.FALSE,toBeUpdated.getIsActive())){
            deactivate(toBeUpdated.getInactiveReason());
        }
        if(Objects.nonNull(toBeUpdated.getRemarks())){
            this.remarks = toBeUpdated.getRemarks();
        }
    }

}