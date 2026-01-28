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
@Table(name = "village_common_fauna")
public class VillageCommonFauna extends  BaseTransactionalEntity implements DomainEntity<VillageCommonFauna> {

    @Column(
             name = "village_common_fauna"
    )
    private String villageCommonFauna;



    @ManyToOne
    @JoinColumn(name = "villageMineralAndBiodiversityPr_id")
    private VillageMineralAndBiodiversityPr villageMineralAndBiodiversityPr;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public VillageCommonFauna(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String villageCommonFauna,
        VillageMineralAndBiodiversityPr villageMineralAndBiodiversityPr,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageCommonFauna = villageCommonFauna;
        this.villageMineralAndBiodiversityPr = villageMineralAndBiodiversityPr;
        this.id = id;


    }


    public void update(VillageCommonFauna toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageCommonFauna toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageCommonFauna() != null) {
            this.setVillageCommonFauna(toBeUpdated.getVillageCommonFauna());
        }
        if (toBeUpdated.getVillageMineralAndBiodiversityPr() != null) {
            this.setVillageMineralAndBiodiversityPr(toBeUpdated.getVillageMineralAndBiodiversityPr());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
