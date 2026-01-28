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
@Table(name = "village_common_flora")
public class VillageCommonFlora extends  BaseTransactionalEntity implements DomainEntity<VillageCommonFlora> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "village_common_flora"
    )
    private String villageCommonFlora;



    @ManyToOne
    @JoinColumn(name = "villageMineralAndBiodiversityPr_id")
    private VillageMineralAndBiodiversityPr villageMineralAndBiodiversityPr;





    @Builder
    public VillageCommonFlora(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String villageCommonFlora,
        VillageMineralAndBiodiversityPr villageMineralAndBiodiversityPr
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageCommonFlora = villageCommonFlora;
        this.villageMineralAndBiodiversityPr = villageMineralAndBiodiversityPr;


    }


    public void update(VillageCommonFlora toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageCommonFlora toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageCommonFlora() != null) {
            this.setVillageCommonFlora(toBeUpdated.getVillageCommonFlora());
        }
        if (toBeUpdated.getVillageMineralAndBiodiversityPr() != null) {
            this.setVillageMineralAndBiodiversityPr(toBeUpdated.getVillageMineralAndBiodiversityPr());
        }
    }

    public void init() {

    }
}
