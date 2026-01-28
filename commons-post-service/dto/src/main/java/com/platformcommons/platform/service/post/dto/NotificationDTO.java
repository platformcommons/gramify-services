package com.platformcommons.platform.service.post.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Validated
public class NotificationDTO extends BaseTransactionalDTO {

    private String fromUser;

    private Long fromTenantId;

    private String messageId;

    @NotNull(message = "Notification code cannot be empty")
    private String notificationCode;

    @NotNull(message = "To user cannot be empty. Please provide atleast one to user")
    private List<String> toUsers;

    private List<String> ccUsers;

    private List<String> bccUsers;

    private Map<String, String> params;

    private List<String> attachmentPaths;


    @Builder

    public NotificationDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, String fromUser, Long fromTenantId,
                           String messageId, String notificationCode, List<String> toUsers, List<String> ccUsers,
                           List<String> bccUsers, Map<String, String> params, List<String> attachmentPaths) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.fromUser = fromUser;
        this.fromTenantId = fromTenantId;
        this.messageId = messageId;
        this.notificationCode = notificationCode;
        this.toUsers = toUsers;
        this.ccUsers = ccUsers;
        this.bccUsers = bccUsers;
        this.params = params;
        this.attachmentPaths = attachmentPaths;
    }
}
