package com.platformcommons.platform.service.blockprofile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;

import com.platformcommons.platform.service.entity.common.*;

import lombok.*;
import java.util.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "scheme_implementation_challenge")
public class SchemeImplementationChallenge extends  BaseTransactionalEntity implements DomainEntity<SchemeImplementationChallenge> {

    @Column(
             name = "scheme_implementation_challenge"
    )
    private String schemeImplementationChallenge;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "govtSchemes_id")
    private GovtSchemes govtSchemes;





    @Builder
    public SchemeImplementationChallenge(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String schemeImplementationChallenge,
        Long id,
        GovtSchemes govtSchemes
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.schemeImplementationChallenge = schemeImplementationChallenge;
        this.id = id;
        this.govtSchemes = govtSchemes;


    }


    public void update(SchemeImplementationChallenge toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(SchemeImplementationChallenge toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getSchemeImplementationChallenge() != null) {
            this.setSchemeImplementationChallenge(toBeUpdated.getSchemeImplementationChallenge());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getGovtSchemes() != null) {
            this.setGovtSchemes(toBeUpdated.getGovtSchemes());
        }
    }

    public void init() {

    }
}
