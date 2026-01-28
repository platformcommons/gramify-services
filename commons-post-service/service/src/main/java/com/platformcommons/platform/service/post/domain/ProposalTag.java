package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "proposal_tag")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ProposalTag  extends BaseTransactionalEntity implements DomainEntity<ProposalTag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proposal_id",updatable = false,nullable = false)
    private Proposal proposal;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "tag_purpose")
    private String tagPurpose;

    @Column(name = "tag_code", nullable = false)
    private String tagCode;


    @Builder
    public ProposalTag(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                       Long id, Proposal proposal, String type, String tagPurpose, String tagCode) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.proposal = proposal;
        this.type = type;
        this.tagPurpose = tagPurpose;
        this.tagCode = tagCode;
    }
}
