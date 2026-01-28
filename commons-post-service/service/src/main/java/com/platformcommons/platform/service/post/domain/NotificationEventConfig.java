package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "notification_event_config",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_EVENT_TENANT",
                        columnNames = {"post_notification_template_id","is_active","post_type","post_sub_type","post_status"})})
@NoArgsConstructor
@Getter
@Setter
public class NotificationEventConfig extends BaseTransactionalEntity implements DomainEntity<NotificationEventConfig> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "event_code")
    private String eventCode;

    @Column(name = "post_type")
    private String postType;

    @Column(name = "post_sub_type")
    private String postSubType;

    @Column(name = "post_status")
    private String postStatus;

    @Column(name = "notification_code")
    private String notificationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_notification_template_id", referencedColumnName = "id")
    private PostNotificationTemplate postNotificationTemplate;

    @Column(name = "subject_line_format")
    private String subjectLineFormat;

    @Builder
    public NotificationEventConfig(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                                   Long id, String eventCode, String postType, String postSubType, String postStatus, String notificationCode,
                                   PostNotificationTemplate postNotificationTemplate, String subjectLineFormat) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.eventCode = eventCode;
        this.postType = postType;
        this.postSubType = postSubType;
        this.postStatus = postStatus;
        this.notificationCode = notificationCode;
        this.postNotificationTemplate = postNotificationTemplate;
        this.subjectLineFormat = subjectLineFormat;
    }

    public void init(PostNotificationTemplate postNotificationTemplate) {
        this.id =0L;
        this.postNotificationTemplate = postNotificationTemplate;
    }

    public void patchUpdate(NotificationEventConfig toBeUpdated) {
        if(toBeUpdated.getEventCode()!=null){
            this.eventCode = toBeUpdated.getEventCode();
        }
        if(toBeUpdated.getPostType()!=null){
            this.postType = toBeUpdated.getPostType();
        }
        if(toBeUpdated.getPostSubType()!=null){
            this.postSubType = toBeUpdated.getPostSubType();
        }
        if(toBeUpdated.getPostStatus()!=null){
            this.postStatus = toBeUpdated.getPostStatus();
        }
        if(toBeUpdated.getNotificationCode()!=null){
            this.notificationCode = toBeUpdated.getNotificationCode();
        }
        if(toBeUpdated.getSubjectLineFormat()!=null){
            this.subjectLineFormat = toBeUpdated.getSubjectLineFormat();
        }
        if(toBeUpdated.getPostNotificationTemplate()!=null){
            this.postNotificationTemplate = toBeUpdated.getPostNotificationTemplate();
        }

    }

}
