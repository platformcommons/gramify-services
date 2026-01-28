package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class
NotificationDTO {

    private String fromUser;
    private Long fromTenantId;

    private boolean groupEmail;

    private Long sentDate;

    private String messageId;

    private boolean status;

    private String notificationCode;

    private List<String> toUsers;

    private List<String> ccUsers;

    private List<String> bccUsers;

    private Map<String, String> params;

    private List<String> attachmentPaths;

    @Builder
    public NotificationDTO(String fromUser, Long fromTenantId, String messageId, String notificationCode, List<String> toUsers, List<String> ccUsers, List<String> bccUsers, Map<String, String> params, List<String> attachmentPaths) {
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
