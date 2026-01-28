package com.platformcommons.platform.service.iam.messaging.notifier;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.facade.client.NotificationClient;
import com.platformcommons.platform.service.iam.messaging.model.NotificationDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailNotifier {


    @Value("${commons.platform.notification.fromUser}")
    String fromUsers;

    @Value("${commons.platform.notification.apikey}")
    String apiKey;

    @Value("${commons.platform.notification.toUser}")
    String[] toUser;
    @Autowired
    private Environment environment;

    @Autowired
    private NotificationClient notificationClient;

    public static final String NOTIFICATION_CODE_GENERAL_EMAIL_NOTIFIER = "NOTIFICATION_CODE.GENERAL_EMAIL_NOTIFIER";
    public static final String NOTIFICATION_PARAMS_MAIL_BODY = "MAIL_BODY";

    public void triggerNotification(String data,String notificationCode,String subject) {
        String tenantName  = PlatformSecurityUtil.getCurrentTenantContext().getTenantName();
        Map<String, String> params = new HashMap<>();
        StringBuffer mailBody = new StringBuffer();
        mailBody.append("TENANT - ").append(tenantName);
        if(data != null) {
            mailBody.append("<br> Data -> ").append(data);
        }
        params.put("MSG_SUBJECT",subject);
        params.put(NOTIFICATION_PARAMS_MAIL_BODY, mailBody.toString());
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .params(params)
                .fromTenantId(1L)
                .fromUser(fromUsers)
                .notificationCode(notificationCode)
                .toUsers(Arrays.asList(toUser))
                .build();
        notificationClient.sendNotification(notificationDTO, apiKey);
    }

    public void sendNotificationForTFIUserPatchFailure(String userDTOStringJson,Exception exception,String role, String cohort, String placementCity) {
        String subject = StringUtils.join(environment.getActiveProfiles(),",") + " - " + "TFI USER PATCH FAILURE.. ALERT!!!!";
        StringBuilder data = new StringBuilder();
        data.append("<br>  USERDTO ->>>  ").append(userDTOStringJson)
                .append("<br> ROLE ->>> ").append(role)
                .append("<br> COHORT ->>> ").append(cohort)
                .append("<br> PLACEMENT CITY ->>> ").append(placementCity)
                .append("<br> ERROR MESSAGE ->>>  ").append(ExceptionUtils.getMessage(exception))
                .append("<br> ERROR STACK TRACE ->>> ").append(ExceptionUtils.getStackTrace(exception));
        triggerNotification(data.toString(), NOTIFICATION_CODE_GENERAL_EMAIL_NOTIFIER,subject);
    }


}
