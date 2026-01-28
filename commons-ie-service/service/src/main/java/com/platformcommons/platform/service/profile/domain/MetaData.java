package com.platformcommons.platform.service.profile.domain;

import javax.persistence.*;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "meta_data")
public class MetaData extends BaseTransactionalEntity implements DomainEntity<MetaData> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "meta_key", nullable = false)
    private String metaKey;

    @Column(name = "meta_value", nullable = false)
    private String metaValue;

    @Builder
    public MetaData(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                    Long id, String metaKey, String metaValue) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
    }


    public void init() {
    }


    public void update(MetaData updatedMetaData) {
        if (updatedMetaData.getMetaKey() != null) {
            this.setMetaKey(updatedMetaData.getMetaKey());
        }
        if (updatedMetaData.getMetaValue() != null) {
            this.setMetaValue(updatedMetaData.getMetaValue());
        }
    }


}

