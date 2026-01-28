package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "proposal")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Proposal  extends BaseTransactionalEntity implements DomainEntity<Proposal> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "problem_statement", columnDefinition = "LONGTEXT")
    private String problemStatement;

    @Column(name = "proposed_solution",  columnDefinition = "LONGTEXT")
    private String proposedSolution;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "proposal")
    @BatchSize(size = 20)
    private Set<ProposalTag> tags;


    @Builder
    public Proposal(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                    Long id, String title, String problemStatement, String proposedSolution, Set<ProposalTag> tags) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.title = title;
        this.problemStatement = problemStatement;
        this.proposedSolution = proposedSolution;
        this.tags = tags;
        if(this.tags!=null && !this.tags.isEmpty()){
            this.tags.forEach(it-> it.setProposal(this));
        }
    }
}
