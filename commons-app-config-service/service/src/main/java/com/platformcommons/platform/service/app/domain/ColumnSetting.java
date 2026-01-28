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


@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_active=1")
@Table(name = "column_setting",indexes = {
        @Index(name = "idx_code", columnList = "code"),
        @Index(name = "unique_code_column_preference_id", columnList = "column_preference_id,code")
})
public class ColumnSetting extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<ColumnSetting> {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code",nullable = false)
    private String code;

    @Column(name = "label")
    private String label;

    @Column(name = "sequence",nullable = false)
    private Long sequence;

    @Column(name = "visibility",nullable = false)
    private Boolean visibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_preference_id", nullable = false)
    private ColumnPreference columnPreference;

    @Builder
    public ColumnSetting(String uuid, Boolean isActive,String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                         String code, String label, Long sequence, Boolean visibility, ColumnPreference columnPreference) {
        super(uuid, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.code = code;
        this.label = label;
        this.sequence = sequence;
        this.visibility = visibility;
        this.columnPreference = columnPreference;
    }

    public void init(ColumnPreference columnPreference) {
        id = null;
        this.columnPreference = columnPreference;
    }

    public void patch(ColumnSetting columnSetting) {
        if(columnSetting.getCode() != null) {
            this.code = columnSetting.getCode();
        }
        if(columnSetting.getLabel() != null) {
            this.label = columnSetting.getLabel();
        }
        if(columnSetting.getSequence() != null) {
            this.sequence = columnSetting.getSequence();
        }
        if(columnSetting.getVisibility() != null) {
            this.visibility = columnSetting.getVisibility();
        }
        PlatformUtil.deactivateAnObject(this,columnSetting.getIsActive(),columnSetting.getInactiveReason());
    }

}