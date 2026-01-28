package com.platformcommons.platform.service.report.application.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.filter.session.TLDPlatformTokenProvider;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.report.application.DataSetCronMetaService;
import com.platformcommons.platform.service.report.application.constant.FileType;
import com.platformcommons.platform.service.report.application.impl.DatasetServiceImpl;
import com.platformcommons.platform.service.report.application.utility.CommonsMultipartFile;
import com.platformcommons.platform.service.report.application.utility.CryptoUtlity;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import com.platformcommons.platform.service.report.dto.LoginRequestDTO;
import com.platformcommons.platform.service.report.dto.NotificationDTO;
import com.platformcommons.platform.service.report.facade.client.IAmClient;
import com.platformcommons.platform.service.report.facade.client.NotificationClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class DatasetEmailJobExecutor {

    private final Logger log = LoggerFactory.getLogger(DatasetEmailJobExecutor.class);
    private final TLDPlatformTokenProvider tldPlatformTokenProvider;
    private DatasetServiceImpl datasetService;
    private DataSetCronMetaService metaService;
    private final AttachmentService attachmentService;
    private final NotificationClient notificationClient;
    private final CryptoUtlity cryptoUtlity;
    private final IAmClient iAmClient;
    private final ObjectMapper mapper;

    public void executeCronJob(Long dataSetCronMeta) {
        DatasetCronMeta cronMeta = metaService.getDatasetCronMetasById(dataSetCronMeta);
        Map<String, String> dataMap = cronMeta.getDataSetCronData();

        log.info("Executing Job with key {}", dataSetCronMeta);
        try {

            LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                    .userLogin(cryptoUtlity.decrypt(dataMap.get("username")))
                    .password(cryptoUtlity.decrypt(dataMap.get("password")))
                    .tenantLogin(dataMap.get("tenant"))
                    .build();
            String sessionId = iAmClient.getTLDSessionId(loginRequestDTO, null, null, null, null)
                    .getHeaders()
                    .get(PlatformSecurityConstant.SESSIONID)
                    .get(0);

            PlatformToken platformToken = tldPlatformTokenProvider.getPlatformTokenFromSessionId(sessionId);
            SecurityContextHolder.getContext().setAuthentication(platformToken);

            final byte[] excel = datasetService.executeQueryDownload(cronMeta.getDataset().getName(), dataMap.get("datasetParams"), FileType.CSV);
            final String fileName = ("output" + "_" + PlatformSecurityUtil.getCurrentTenantId() + "_" +
                    LocalDateTime.now().toLocalDate() + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + FileType.CSV.getExtension()).replaceAll("[- *]", "_");
            final boolean uploadToPublic = Boolean.parseBoolean(getBoolean(dataMap.get("uploadPublic")));
            Attachment attachment = new Attachment();
            attachment.setFileName(fileName);
            attachment.setMimeType(FileType.CSV.getContentType());
            attachment.setPublic(uploadToPublic);
            attachment.setEntityType("ENTITY_TYPE.DATASET_CRON_META");
            attachment.setEntityId(cronMeta.getId());


            CommonsMultipartFile multipartFile = new CommonsMultipartFile(excel, fileName, FileType.CSV.getContentType());
            Attachment uploadedAttachment = attachmentService.uploadAttachment(multipartFile, attachment);
            boolean sendEmail = Boolean.parseBoolean(getBoolean(dataMap.get("sendEmail")));
            if (!sendEmail) {
                return;
            }
            final String url;
            if (uploadToPublic) {
                url = uploadedAttachment.getCompleteURL();
            } else {
                url = attachmentService.getAttachmentSignedURLById(uploadedAttachment.getId());
            }

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .fromUser(dataMap.get("fromUser"))
                    .notificationCode(dataMap.get("notificationCode"))
                    .params(mapper.readValue(dataMap.get("notificationParams"), Map.class))
                    .toUsers(Arrays.stream(dataMap.get("emails").split(",")).collect(Collectors.toSet()))
                    .attachmentPaths(new HashSet<>())
                    .build();
            notificationDTO.getAttachmentPaths().add(url);
            notificationClient.sendNotification(notificationDTO, sessionId);
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getBoolean(String bool) {
        if (bool == null) return "true";
        try {
            return Boolean.valueOf(Boolean.parseBoolean(bool)).toString();
        } catch (Exception e) {
            return "true";
        }
    }

    @Autowired
    public void setDatasetService(DatasetServiceImpl datasetService) {
        this.datasetService = datasetService;
    }

    @Autowired
    public void setMetaService(DataSetCronMetaService metaService) {
        this.metaService = metaService;
    }
}
