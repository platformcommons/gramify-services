package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "response",
        indexes = {
                @Index(name = "IDX_response_on_entity_id",
                        columnList = "response_on_entity_id"),
                @Index(name = "IDX_response_on_entity_type",
                        columnList = "response_on_entity_type")
        })
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Response extends BaseTransactionalEntity implements DomainEntity<Response> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_by", nullable = false, updatable = false)
    private PostActor responseBy;

    @NotNull(message = "payload must not be null")
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "response_type")
    private String responseType; //POLL_OPTION

    @Column(name = "response_date_time")
    private Date responseDateTime;

    @NotNull(message = "responseOnEntityId must not be null")
    @Column(name = "response_on_entity_id")
    private Long responseOnEntityId;

    @NotNull(message = "responseOnEntityType must not be null")
    @Column(name = "response_on_entity_type")
    private String responseOnEntityType;
    @Column(name = "response_count")
    private Long responseCount;

    @Column(name = "react_count")
    private Long reactCount;

    @Transient
    private boolean isNew;



    @Builder
    public Response(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp,
                    Long appLastModifiedTimestamp, Long id, PostActor responseBy, String payload,
                    String responseType, Date responseDateTime, Long responseOnEntityId, String responseOnEntityType,
                    Long responseCount, Long reactCount, boolean isNew) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.responseBy = responseBy;
        this.payload = payload;
        this.responseType = responseType;
        this.responseDateTime = responseDateTime;
        this.responseOnEntityId = responseOnEntityId;
        this.responseOnEntityType = responseOnEntityType;
        this.responseCount = responseCount;
        this.reactCount = reactCount;
        this.isNew = isNew;
    }

    public Date getResponse_date_time() {
        return new Date(responseDateTime != null ? responseDateTime.getTime() : new Date().getTime());
    }

    public void setResponse_date_time(Date responseDateTime) {
        this.responseDateTime = new Date(responseDateTime != null ? responseDateTime.getTime() : new Date().getTime());
    }

    public void init(Long entityId, String entityType, PostActor postActor) {
        this.id = null;
        this.responseCount = null;
        this.reactCount = null;
        this.responseOnEntityId = entityId;
        this.responseOnEntityType = entityType;
        this.responseDateTime = new Date();
        this.responseCount = 0L;
        this.reactCount = 0L;
        this.responseBy = postActor;
    }

    public void isAuthorizedForUpdate() {
        boolean isAllowed = this.getCreatedByUser()
                .equals(PlatformSecurityUtil.getCurrentUserId())
                || PlatformSecurityUtil.isTenantAdmin();
        if (!isAllowed)
            throw new UnAuthorizedAccessException("User is not authorized to update this post.");
    }

    public void isAuthorizedForDelete() {
        boolean isAllowed = this.getCreatedByUser()
                .equals(PlatformSecurityUtil.getCurrentUserId())
                || PlatformSecurityUtil.isTenantAdmin();
        if (!isAllowed)
            throw new UnAuthorizedAccessException("User is not authorized to delete this response.");
    }

    public void update(Response response, boolean mapNulls) {
        if (!StringUtils.isEmpty(response.getPayload()))
            this.payload = response.getPayload();
        if (mapNulls) {
            putUpdate(response);
        } else {
            patchUpdate(response);
        }
    }

    private void putUpdate(Response response) {
        this.responseType = response.getResponseType();
        this.payload = response.getPayload();
    }

    private void patchUpdate(Response response) {
        if (!StringUtils.isEmpty(response.getResponseType())) {
            this.responseType = response.getResponseType();
        }
        if (!StringUtils.isEmpty(response.getPayload())) {
            this.payload = response.getPayload();
        }
    }
}