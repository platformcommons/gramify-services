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
@Table(name = "central_scheme_list")
public class CentralSchemeList extends  BaseTransactionalEntity implements DomainEntity<CentralSchemeList> {

    @ManyToOne
    @JoinColumn(name = "govtSchemes_id")
    private GovtSchemes govtSchemes;


    @Column(
             name = "central_scheme"
    )
    private String centralScheme;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public CentralSchemeList(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        GovtSchemes govtSchemes,
        String centralScheme,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.govtSchemes = govtSchemes;
        this.centralScheme = centralScheme;
        this.id = id;


    }


    public void update(CentralSchemeList toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(CentralSchemeList toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getGovtSchemes() != null) {
            this.setGovtSchemes(toBeUpdated.getGovtSchemes());
        }
        if (toBeUpdated.getCentralScheme() != null) {
            this.setCentralScheme(toBeUpdated.getCentralScheme());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
