package com.platformcommons.platform.service.iam.messaging.model;

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
@AllArgsConstructor
@Builder
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

    private String recipientName;

    private String recipientContext;

    private Long recipientUserId;

    private Long countryCode;

}
