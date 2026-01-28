package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.UoMValue;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(
        name = "post_impact",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="UNIQUE_POST_ID_BENEFICIARY_TYPE",
                        columnNames = {"post_id","beneficiary_type","is_active"}
                )})
@NoArgsConstructor
@Where(clause = "is_active=1")
public class PostImpact extends BaseTransactionalEntity implements DomainEntity<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    private Post post;

    @Column(name = "beneficiary_type")
    private String beneficiaryType;

    @Column(name = "label")
    private String label;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "actual_impact", referencedColumnName = "id")
    private UoMValue actualImpact;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_impact", referencedColumnName = "id")
    private UoMValue approvedImpact;


    @Builder
    public PostImpact(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                      Long id, Post post, String beneficiaryType, String label, UoMValue actualImpact,UoMValue approvedImpact) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.post = post;
        this.beneficiaryType = beneficiaryType;
        this.label = label;
        this.actualImpact = actualImpact;
        this.approvedImpact = approvedImpact;
    }


    public void init(Post post) {
        this.id = 0L;
        this.post = post;
        this.approvedImpact = null;
    }

    public void putUpdate(PostImpact toBeUpdated) {
        this.beneficiaryType = toBeUpdated.getBeneficiaryType();
        this.actualImpact = toBeUpdated.getActualImpact();
        this.label = toBeUpdated.getLabel();
        this.approvedImpact = toBeUpdated.getApprovedImpact();
    }

    public void patchUpdate(PostImpact toBeUpdated) {
        if (!StringUtils.isBlank(toBeUpdated.getBeneficiaryType())) {
            this.beneficiaryType = toBeUpdated.getBeneficiaryType();
        }
        if (!StringUtils.isBlank(toBeUpdated.getLabel())) {
            this.label = toBeUpdated.getLabel();
        }
        if (toBeUpdated.getActualImpact() != null) {
            this.actualImpact = toBeUpdated.getActualImpact();
        }
        if (toBeUpdated.getApprovedImpact() != null) {
            this.approvedImpact = toBeUpdated.getApprovedImpact();
        }
    }

    public void patchUpdateForPostImpactCreator(PostImpact toBeUpdated) {
        if (!StringUtils.isBlank(toBeUpdated.getBeneficiaryType())) {
            this.beneficiaryType = toBeUpdated.getBeneficiaryType();
        }
        if (!StringUtils.isBlank(toBeUpdated.getLabel())) {
            this.label = toBeUpdated.getLabel();
        }
        if (toBeUpdated.getActualImpact() != null) {
            this.actualImpact = toBeUpdated.getActualImpact();
        }
    }
}
