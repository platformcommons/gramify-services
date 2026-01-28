package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(
        name = "user_meta_data",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="UniqueUserAndMeta",
                        columnNames = {"user_id","is_active","meta_key"}
                )})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserMetaData extends BaseTransactionalEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id",nullable = false,updatable = false)
    private Long userId;

    @NotNull
    @Column(name = "meta_key",nullable = false)
    private String metaKey;

    @NotNull
    @Column(name = "meta_value",nullable = false)
    private String metaValue;

    @Builder
    public UserMetaData(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp,
                        Long appLastModifiedTimestamp, Long id, Long userId, String metaKey, String metaValue) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.userId = userId;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
    }

    public void init() {
        this.id = 0L;
    }

    public void patch(UserMetaData toBeUpdated) {
        if(toBeUpdated.getMetaValue() != null) {
            this.metaValue = toBeUpdated.getMetaValue();
        }
    }
}
