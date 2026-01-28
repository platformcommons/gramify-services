package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_active=1")
@Table(name = "column_preference", indexes = {
        @Index(name = "idx_schema_code", columnList = "schema_code")
})
public class ColumnPreference extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<ColumnPreference> {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schema_code", nullable = false)
    private String schemaCode;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "owner_type",nullable = false)
    private String ownerType;

    @Column(name = "parent_column_preference_id")
    private Long parentColumnPreferenceId;

    @OneToMany(mappedBy = "columnPreference", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ColumnSetting> columnSettings;

    @Builder
    public ColumnPreference(String uuid, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                            Long id, String schemaCode, String entityId, String entityType, String ownerId, String ownerType,
                            Long parentColumnPreferenceId, Set<ColumnSetting> columnSettings) {
        super(uuid, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.schemaCode = schemaCode;
        this.entityId = entityId;
        this.entityType = entityType;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.parentColumnPreferenceId = parentColumnPreferenceId;
        this.columnSettings = columnSettings;
    }

    public void init() {
        id = null;
        if(columnSettings != null) {
            columnSettings.forEach(it ->it.init(this));
        }
    }

    public void patch(ColumnPreference columnPreference) {
        if(columnPreference.getSchemaCode() != null) {
            this.schemaCode = columnPreference.getSchemaCode();
        }
        if(columnPreference.getEntityId() != null) {
            this.entityId = columnPreference.getEntityId();
        }
        if(columnPreference.getEntityType() != null) {
            this.entityType = columnPreference.getEntityType();
        }
        if(columnPreference.getOwnerId() != null) {
            this.ownerId = columnPreference.getOwnerId();
        }
        if(columnPreference.getOwnerType() != null) {
            this.ownerType = columnPreference.getOwnerType();
        }
        if(columnPreference.getColumnSettings() != null) {
            this.getColumnSettings().forEach(columnSetting -> {
                columnPreference.getColumnSettings().forEach(toBeUpdatedColumnPreference -> {
                    if(Objects.equals(columnSetting.getId(),toBeUpdatedColumnPreference.getId())) {
                        columnSetting.patch(toBeUpdatedColumnPreference);
                    }
                });
            });
            columnPreference.getColumnSettings().forEach(toBeSaved -> {
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init(this);
                    this.getColumnSettings().add(toBeSaved);
                }
            });
        }
        PlatformUtil.deactivateAnObject(this,columnPreference.getIsActive(),columnPreference.getInactiveReason());
    }

}