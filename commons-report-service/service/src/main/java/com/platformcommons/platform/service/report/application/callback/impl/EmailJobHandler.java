package com.platformcommons.platform.service.report.application.callback.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.filter.session.TLDPlatformTokenProvider;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.report.application.DataSetCronMetaService;
import com.platformcommons.platform.service.report.application.callback.JobHandler;
import com.platformcommons.platform.service.report.application.impl.DatasetServiceImpl;
import com.platformcommons.platform.service.report.application.scheduler.DatasetEmailJobExecutor;
import com.platformcommons.platform.service.report.application.utility.CryptoUtlity;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import com.platformcommons.platform.service.report.domain.repo.DatasetCronMetaRepository;
import com.platformcommons.platform.service.report.facade.client.IAmClient;
import com.platformcommons.platform.service.report.facade.client.NotificationClient;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class EmailJobHandler implements JobHandler {

    private Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    @Value("${commons.platform.schedule.job.emailJob.notificationCode:CRON_TEST}")
    private String notificationCode ;

    private final CryptoUtlity cryptoUtlity;
    private final ObjectMapper mapper;
    @Value("${commons.platform.schedule.job.emailJob.from-user:hi@commons.team}")
    private String fromUser;

    private DatasetEmailJobExecutor datasetEmailJobExecutor;

    public EmailJobHandler(CryptoUtlity cryptoUtlity,  ObjectMapper mapper) {
        this.cryptoUtlity = cryptoUtlity;
        this.mapper = mapper;
    }

    @Override
    public Runnable buildJob(DatasetCronMeta cronMeta) {
        try {

            Map<String, String> map = cronMeta.getDataSetCronData();
            Set<String> emails = Arrays.stream(map.get("emails").split(",")).collect(Collectors.toSet());
            for (String email : emails)
                if(!emailPattern.matcher(email).matches())
                    throw new InvalidInputException("Invalid email address: "+email);
            final String code=map.get("notificationCode")==null?notificationCode:map.get("notificationCode");
            map.put("notificationCode",code);
            map.put("sendEmail",getBoolean(map.get("sendEmail")));
            if(!map.containsKey("fromUser")){
                map.put("fromUser",fromUser);
            }
            if(!map.containsKey("notificationParams")){
                throw new InvalidInputException("Not Notification params found");
            }
            else {
                try {
                    mapper.readValue(map.get("notificationParams").toString(), Map.class);
                }
                catch (Exception e){
                    throw new InvalidInputException("Not valid json for notification params");
                }
            }
            if(!map.containsKey("datasetParams"))
                map.put("datasetParams","");


            String username = map.get("username");
            String password = map.get("password");
            String tenant = map.get("tenant");

            if(username==null) throw new InvalidInputException("Invalid credential");
            if(password==null) throw new InvalidInputException("Invalid credential");
            if(tenant==null) throw new InvalidInputException("Invalid credential");
            String encryptedUsername = cryptoUtlity.encrypt(username);
            String encryptedPassword = cryptoUtlity.encrypt(password);
            map.put("username", encryptedUsername);
            map.put("password", encryptedPassword);
            map.put("uploadPublic",getBoolean(map.get("uploadPublic")));

            return new EmailJob(cronMeta.getId(),datasetEmailJobExecutor);
        } catch (Exception e) {
            throw new InvalidInputException("Not valid json data for cron job.");
        }
    }

    @Override
    public void patchJobData(DatasetCronMeta dbData, DatasetCronMeta cronData) {
        Map<String, String> map = cronData.getDataSetCronData();
        Map<String, String> dbMap =  dbData.getDataSetCronData();
        String username = map.get("username");
        String password = map.get("password");
        String tenant = map.get("tenant");
        String datasetParams = map.get("datasetParams");
        String notificationParams = map.get("notificationParams");
        String emails = map.get("emails");
        String notificationCode = map.get("notificationCode");
        String sendEmail = map.get("sendEmail");
        String fromUser = map.get("fromUser");
        String uploadPublic = map.get("uploadPublic");
        if(username!=null)           dbMap.put("username", cryptoUtlity.encrypt(username));
        if(password!=null)           dbMap.put("password", cryptoUtlity.encrypt(password));
        if(tenant!=null)             dbMap.put("tenant", tenant);
        if(datasetParams!=null)      dbMap.put("datasetParams", datasetParams);
        if(notificationParams!=null) dbMap.put("notificationParams", notificationParams);
        if(emails!=null)             {
            Set<String> emailSet = Arrays.stream(emails.split(",")).collect(Collectors.toSet());
            for (String email : emailSet)
                if(!emailPattern.matcher(email).matches())
                    throw new InvalidInputException("Invalid email address: "+email);
            dbMap.put("emails", emails);
        }
        if(notificationCode!=null)   dbMap.put("notificationCode", notificationCode);
        if(sendEmail!=null)          dbMap.put("sendEmail", sendEmail);
        if(fromUser!=null)           dbMap.put("fromUser", fromUser);
        if(uploadPublic!=null)       dbMap.put("uploadPublic", uploadPublic);

        if(!dbMap.containsKey("username"))
            throw new InvalidInputException("Invalid credential");
        if(!dbMap.containsKey("password"))
            throw new InvalidInputException("Invalid credential");
        if(!dbMap.containsKey("tenant"))
            throw new InvalidInputException("Invalid credential");
        if(!dbMap.containsKey("notificationParams"))
            throw new InvalidInputException("Notification params Not found");
        else {
            try {
                mapper.readValue(dbMap.get("notificationParams").toString(), Map.class);
            }
            catch (Exception e){
                throw new InvalidInputException("Not valid json for notification params");
            }
        }
        if(!dbMap.containsKey("datasetParams"))
            dbMap.put("datasetParams","");
        if(!dbMap.containsKey("emails"))
            throw new InvalidInputException("Emails not found");
        if(!dbMap.containsKey("notificationCode"))
            throw new InvalidInputException("Notification code not found");
        if(!dbMap.containsKey("sendEmail"))
            dbMap.put("sendEmail","true");
        if(!dbMap.containsKey("fromUser"))
            dbMap.put("fromUser",fromUser);
        if(!dbMap.containsKey("uploadPublic"))
            dbMap.put("uploadPublic","true");


    }


    private String getBoolean(String bool){
        if(bool==null) return "true";
        try{
            return Boolean.valueOf(Boolean.parseBoolean(bool)).toString();
        }
        catch (Exception e){
            return "true";
        }
    }

    @Autowired
    public void setDatasetEmailJobExecutor(DatasetEmailJobExecutor datasetEmailJobExecutor) {
        this.datasetEmailJobExecutor = datasetEmailJobExecutor;
    }
}
