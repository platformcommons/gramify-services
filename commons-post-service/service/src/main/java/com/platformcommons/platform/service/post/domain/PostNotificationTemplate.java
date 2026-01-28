package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(
        name = "post_notification_template",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_TENANT_ID", columnNames = {"tenant_id","is_active"})})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostNotificationTemplate extends BaseTransactionalEntity implements DomainEntity<PostNotificationTemplate> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "is_default",nullable = false)
    private Boolean isDefault;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postNotificationTemplate", fetch = FetchType.LAZY)
    private Set<NotificationEventConfig> notificationEventConfigs;

    @Builder
    public PostNotificationTemplate(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                                    Long id, Boolean isDefault, Set<NotificationEventConfig> notificationEventConfigs) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.isDefault = isDefault;
        this.notificationEventConfigs = notificationEventConfigs;
        if(this.notificationEventConfigs != null) {
            this.notificationEventConfigs.forEach(it->it.setPostNotificationTemplate(this));
        }
    }

    public void init() {
        this.id = 0L;
        if(this.isDefault == null) {
            this.isDefault = Boolean.FALSE;
        }
    }

    public void patchUpdate(PostNotificationTemplate toBeUpdated) {
        if(toBeUpdated.getIsDefault()!=null){
            this.isDefault = toBeUpdated.getIsDefault();
        }
        if(toBeUpdated.getNotificationEventConfigs()!=null){
            this.getNotificationEventConfigs().forEach(notificationEventConfig -> {
                toBeUpdated.getNotificationEventConfigs().forEach(toBeUpdateConfig->{
                    if(Objects.equals(notificationEventConfig.getId(),toBeUpdateConfig.getId())) {
                        notificationEventConfig.patchUpdate(toBeUpdateConfig);
                    }
                });
            });
            toBeUpdated.getNotificationEventConfigs().forEach(toBeSaved->{
                if(toBeSaved.getId() == null || toBeSaved.getId() == 0L) {
                    toBeSaved.init(this);
                    this.getNotificationEventConfigs().add(toBeSaved);
                }
            });
        }
    }

}
