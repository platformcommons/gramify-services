package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_entity_id", columnList = "entity_id"),
        @Index(name = "idx_entity_type", columnList = "entity_type")
},
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_id_entity_id_entity_type", columnNames = {"user_id", "entity_id", "entity_type"})
        })
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserReadEventLog extends BaseTransactionalEntity implements DomainEntity<UserReadEventLog> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "entity_type")
    private String entityType;

    @Builder
    public UserReadEventLog(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp,
                            Long appLastModifiedTimestamp, Long id, Long userId, Long entityId, String entityType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.userId = userId;
        this.entityId = entityId;
        this.entityType = entityType;
    }

    public void init() {
        this.id = null;
        this.userId = PlatformSecurityUtil.getCurrentUserId();
    }


    void update() {}
}
