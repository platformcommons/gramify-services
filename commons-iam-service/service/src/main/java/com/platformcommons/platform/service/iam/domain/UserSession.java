package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

// TODO: move to clickhouse
@Entity
@Table(name = "user_session")
@Getter @Setter
@NoArgsConstructor
public class UserSession extends BaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
    private String loginType;
    private String session;
    private Date lastLoginDateTime;
    private String ipAddress;
    private String userAgent;
    private String deviceType;
    private String appId;
    private String appKey;
    private String appVersion;
    private Boolean status;

    @Builder
    public UserSession(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, User userId, String loginType, String session, Date lastLoginDateTime, String ipAddress, String userAgent, String deviceType, String appId, String appKey, String appVersion, Boolean status) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.userId = userId;
        this.loginType = loginType;
        this.session = session;
        this.lastLoginDateTime = lastLoginDateTime;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceType = deviceType;
        this.appId = appId;
        this.appKey = appKey;
        this.appVersion = appVersion;
        this.status = status;
    }

    public void invalidateSessionDetails() {
        this.status=false;
    }
}
