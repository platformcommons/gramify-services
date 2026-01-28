package com.platformcommons.platform.service.post.application.utility;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.NotificationEventConfigService;
import com.platformcommons.platform.service.post.application.PostTenantConfigService;
import com.platformcommons.platform.service.post.application.TenantCertificateTemplateService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.domain.*;
import com.platformcommons.platform.service.post.dto.NotificationDTO;
import com.platformcommons.platform.service.post.facade.client.NotificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NotificationUtility {

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private TenantCertificateTemplateService tenantCertificateTemplateService;

    @Autowired
    private PostTenantConfigService postTenantConfigService;

    @Autowired
    private NotificationEventConfigService notificationEventConfigService;


    public void sendPostMail(PostActorCertificate postActorCertificate, Post post, PostActor postActor) {
        if (postActor.getEmailAddress() != null) {
            Map<String, String> params = createCommonNotificationParams();
            NotificationEventConfig notificationEventConfig = getNotificationEventConfig(post);
            NotificationDTO notificationDTO = buildNotificationDTOForPost(postActorCertificate, postActor, post, params, notificationEventConfig);
            String messageId = notificationClient.sendNotification(notificationDTO, PlatformSecurityUtil.getToken()).toString();
            log.debug("MessageId =>>>>" + messageId);
        }
    }

    public void sendPostNotificationInBulk(List<PostActorCertificate> postActorCertificatelist,List<Post> postList) {
        Map<String,String> params = createCommonNotificationParams();
        Post defaultPost = postList.iterator().next();
        NotificationEventConfig notificationEventConfig = getNotificationEventConfig(defaultPost);
        Map<Long,PostActorCertificate> postActorCertificateMap =  new HashMap<>();
        if(postActorCertificatelist != null && !postActorCertificatelist.isEmpty()) {
            postActorCertificateMap = postActorCertificatelist.stream()
                    .collect(Collectors.toMap(it->it.getPost().getId(), Function.identity(),(a,b)->a));
        }
        for (Post post : postList) {
            PostActor postActor = post.getPostedBy();
            PostActorCertificate postActorCertificate = postActorCertificateMap.getOrDefault(post.getId(), null);
            if (postActor.getEmailAddress() != null) {
                NotificationDTO notificationDTO = buildNotificationDTOForPost(postActorCertificate, postActor,
                        post, params,notificationEventConfig);
                String messageId = notificationClient.sendNotification(notificationDTO, PlatformSecurityUtil.getToken()).toString();
                log.debug("MessageId =>>>>" + messageId);
            }
        }
    }

    public NotificationDTO buildNotificationDTOForPost(PostActorCertificate postActorCertificate, PostActor postActor,
                                                       Post post,Map<String,String> commonsParams,NotificationEventConfig notificationEventConfig) {
        Map<String,String> params=new HashMap<>();
        String notificationCode = notificationEventConfig.getNotificationCode();
        params.put("MSG_SUBJECT", notificationEventConfig.getSubjectLineFormat());
        if(commonsParams != null) {
            params.putAll(commonsParams);
        }
        params.put("userName", postActor.getName());
        params.put("createdBy",PlatformSecurityUtil.getCurrentUserName());
        if(postActorCertificate != null && postActorCertificate.getUrl() != null) {
            params.put("certificateLink", postActorCertificate.getUrl());
            notificationCode = notificationCode + ".WITH_CERTIFICATE_LINK";
        }
        return NotificationDTO.builder()
                .notificationCode(notificationCode)
                .toUsers(Collections.singletonList(postActor.getEmailAddress()))
                .fromUser(PlatformSecurityUtil.getCurrentTenantContext().getTenantName())
                .params(params)
                .build();
    }

    public NotificationEventConfig getNotificationEventConfig(Post post){
        Optional<NotificationEventConfig> optionalNotificationEventConfig;
        optionalNotificationEventConfig =  notificationEventConfigService.getByPostParametersForLoggedInTenant(post);
        if(!optionalNotificationEventConfig.isPresent()) {
            optionalNotificationEventConfig =  notificationEventConfigService.getByPostParametersByDefault(post);
        }

        NotificationEventConfig notificationEventConfig= optionalNotificationEventConfig.orElse(null);
        if(notificationEventConfig==null){
            log.warn("Notification Can not be Sent as Notification Event Config not Found");
        }
        return notificationEventConfig;
    }

    public Map<String, String> createCommonNotificationParams() {
        Map<String,String> params = new HashMap<>();
        params.putAll(createTenantParams());
        return params;
    }

    public Map<String, String> createTenantParams() {
        PostTenantConfig postTenantConfig = postTenantConfigService.getForLoggedInTenant();
        Map<String,String> params = new HashMap<>();
        params.put("tenantName",PlatformSecurityUtil.getCurrentTenantContext().getTenantName());
        params.put("tenantLogoLink",postTenantConfig.getTenantLogoUrl());
        return params;
    }

}
