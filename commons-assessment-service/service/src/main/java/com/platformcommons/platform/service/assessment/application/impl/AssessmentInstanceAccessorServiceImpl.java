package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceAccessorService;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceService;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAccessor;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentInstanceAccessorRepository;
import com.platformcommons.platform.service.assessment.dto.NotificationDTO;
import com.platformcommons.platform.service.assessment.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.assessment.facade.client.DatasetClient;
import com.platformcommons.platform.service.assessment.facade.client.NotificationClient;
import com.platformcommons.platform.service.assessment.facade.client.TenantMetaConfigClient;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentInstanceAccessorServiceImpl implements AssessmentInstanceAccessorService {

    private static final String MAKE_FORM_ACCESSIBLE = "MAKE_FORM_ACCESSIBLE";
    private static final String REVOKE_FORM_ACCESS = "REVOKE_FORM_ACCESS";

    private final AssessmentInstanceAccessorRepository repository;
    private AssessmentInstanceService instanceService;
    private final DatasetClient datasetClient;
    private final NotificationClient notificationClient;
    private final TenantMetaConfigClient tenantMetaConfigClient;

    @Value("${commons.form.accessibility.notification.fromuser:hi@commons.team}")
    private String fromUser;
    @Value("${commons.form.accessibility.notification.contactinformation:admin@platformcommons.com}")
    private String defaultContactInformation;
    private final static String GET_USER = "ASSESSEMENT_ORG-GetUserDetails";

    @Value("${commons.form.accessibility.notification.subject:Form Access Update}")
    private String defaultMakeFormAccessibleSubject;
    @Value("${commons.form.accessibility.notification.subject:Form Access Update}")
    private String defaultRevokeFormAccessSubject;


    @SuppressWarnings("CommentedOutCode")
    @Override
    public List<AssessmentInstanceAccessor> createAssessmentInstanceAccessor(Long instanceId, Set<String> logins) {

        AssessmentInstance instance=instanceService.getAssessmentInstanceByIdv2(instanceId);
        if(!instance.getCreatedByUser().equals(PlatformSecurityUtil.getCurrentUserId())){
            throw new UnAuthorizedAccessException("Not authorized to modify the instance");
        }
        if(instance.getSpecificVisibility()==null || !instance.getSpecificVisibility()){
            instance.setSpecificVisibility(true);
            instanceService.updateAssessmentInstanceV2(instance);
        }
        logins.removeAll(repository.findByUserLogins(logins,instanceId));
        List<Map<String,Object>> result = getAssessmentInstanceAccessorsName(logins);

//        if(logins.size()!= result.size()){
//            throw new AssessmentNotFoundException("Some user login not present");
//        }

        List<AssessmentInstanceAccessor> aiaList=new ArrayList<>();
        result.forEach( user -> {
            logins.remove(nullCheck(user.get("login")));
            aiaList.add(AssessmentInstanceAccessor.builder()
                    .id(0L)
                    .assessmentInstance(instance)
                    .login(nullCheck(user.get("login")))
                    .firstName(nullCheck(user.get("firstName")))
                    .lastName(nullCheck(user.get("lastName")))
                    .build());
        });
        logins.forEach(
                login ->
                    aiaList.add(AssessmentInstanceAccessor.builder()
                            .id(0L)
                            .assessmentInstance(instance)
                            .login(login)
                            .build())
        );
        List<AssessmentInstanceAccessor> assessmentInstanceAccessors=repository.saveAll(aiaList);
        manipulateName(assessmentInstanceAccessors);
        sendMailAssessmentAccess(assessmentInstanceAccessors,instance);
        return assessmentInstanceAccessors;
    }
    private List<Map<String, Object>> getAssessmentInstanceAccessorsName(Set<String> logins) {
        String params="IN_PARAM_USER_LOGINS="+ String.join(",", logins);
        return datasetClient.executeQueryV3(GET_USER, params,0, logins.size(),PlatformSecurityUtil.getToken());
    }

    private void manipulateName(List<AssessmentInstanceAccessor> assessmentInstanceAccessors) {
        assessmentInstanceAccessors.forEach( aia ->{
            if(aia.getFirstName()==null){
                String x =aia.getLogin().split("@")[0];
                int start,end;
                for (start = 0; start < x.length(); start++) {
                    if (Character.isLetter(x.charAt(start))) break;
                }
                for (end = x.length() - 1; end >= 0; end--) {
                    if (Character.isLetter(x.charAt(end))) break;
                }
                aia.setFirstName(x.substring(start, end + 1));
            }
        });
    }

    private void sendMailAssessmentAccess(List<AssessmentInstanceAccessor> aias, AssessmentInstance instance) {

        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigClient.getTenantMetaConfig(PlatformSecurityUtil.getToken());
        Map<String,String> config = tenantMetaConfigDTO.getConfig()==null?new HashMap<>():tenantMetaConfigDTO.getConfig();

        for (AssessmentInstanceAccessor aia:aias) {

            NotificationDTO notificationDTO=new NotificationDTO();

            notificationDTO.setFromUser(fromUser);
            notificationDTO.setNotificationCode(MAKE_FORM_ACCESSIBLE);

            Map<String,String> params = new HashMap<>();

            params.put("MSG_SUBJECT",config.get("MAKE_FORM_ACCESSIBLE_SUBJECT")==null? defaultMakeFormAccessibleSubject:config.get("MAKE_FORM_ACCESSIBLE_SUBJECT"));
            params.put("RECIPIENT_NAME", aia.getUserDisplayName());
            params.put("FORM_NAME",instance.getAsmtInstName().stream()
                    .filter(el->el.getLanguageCode().equals(instance.getAssessment().getBaseLanguage()==null?"ENG":instance.getAssessment().getBaseLanguage()))
                    .findFirst()
                    .orElse(MLText.builder().build())
                    .getText());
            params.put("FULL_NAME", PlatformSecurityUtil.getContext().getUserContext().getUsername() );
            params.put("COMPANY_NAME",config.get("COMPANY_NAME")==null? tenantMetaConfigDTO.getTenantName():config.get("COMPANY_NAME"));
            params.put("CONTACT_INFORMATION",config.get("CONTACT_INFORMATION")==null? defaultContactInformation:config.get("CONTACT_INFORMATION"));
            notificationDTO.setParams(params);
            notificationDTO.setToUsers(new ArrayList<>());
            notificationDTO.getToUsers().add(aia.getLogin());
            notificationClient.sendNotificationWithSessionId(notificationDTO,PlatformSecurityUtil.getToken());
        }

    }

    private void sendRevokeAccessMail(List<AssessmentInstanceAccessor> aias, AssessmentInstance instance) {

        TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigClient.getTenantMetaConfig(PlatformSecurityUtil.getToken());
        Map<String,String> config = tenantMetaConfigDTO.getConfig()==null?new HashMap<>():tenantMetaConfigDTO.getConfig();

        for (AssessmentInstanceAccessor aia:aias) {

            NotificationDTO notificationDTO=new NotificationDTO();

            notificationDTO.setFromUser(fromUser);
            notificationDTO.setNotificationCode(REVOKE_FORM_ACCESS);

            Map<String,String> params = new HashMap<>();

            params.put("MSG_SUBJECT",config.get("REVOKE_FORM_ACCESS_SUBJECT")==null? defaultRevokeFormAccessSubject:config.get("REVOKE_FORM_ACCESS_SUBJECT"));
            params.put("RECIPIENT_NAME", aia.getUserDisplayName());
            params.put("FORM_NAME",instance.getAsmtInstName().stream()
                    .filter(el->el.getLanguageCode().equals(instance.getAssessment().getBaseLanguage()==null?"ENG":instance.getAssessment().getBaseLanguage()))
                    .findFirst()
                    .orElse(MLText.builder().build())
                    .getText());
            params.put("FULL_NAME", PlatformSecurityUtil.getContext().getUserContext().getUsername() );
            params.put("COMPANY_NAME",config.get("COMPANY_NAME")==null? tenantMetaConfigDTO.getTenantName():config.get("COMPANY_NAME"));
            params.put("CONTACT_INFORMATION",config.get("CONTACT_INFORMATION")==null? defaultContactInformation:config.get("CONTACT_INFORMATION"));
            notificationDTO.setParams(params);
            notificationDTO.setToUsers(new ArrayList<>());
            notificationDTO.getToUsers().add(aia.getLogin());
            notificationClient.sendNotificationWithSessionId(notificationDTO,PlatformSecurityUtil.getToken());
        }

    }

    @Override
    public Boolean accessibleInstance(Long instance) {
        return repository.existsByAssessmentInstance_IdAndLogin(instance,PlatformSecurityUtil.getCurrentUserLogin());
    }

    @Override
    public Page<AssessmentInstanceAccessor> getAssessmentInstanceAccessors(Long instanceId, Integer page, Integer size) {
        AssessmentInstance instance=instanceService.getAssessmentInstanceByIdv2(instanceId);
        if(!instance.getCreatedByUser().equals(PlatformSecurityUtil.getCurrentUserId())){
            throw new UnAuthorizedAccessException("Not allowed to perform the operation");
        }
        Page<AssessmentInstanceAccessor> aiaPage = repository.findByAssessmentInstance_Id( instanceId , PageRequest.of(page,size));
        List<Map<String,Object>> names = getAssessmentInstanceAccessorsName(aiaPage.get().map(AssessmentInstanceAccessor::getLogin).collect(Collectors.toSet()));
        Map<String,AssessmentInstanceAccessor> aias = aiaPage.get().collect(Collectors.toMap(AssessmentInstanceAccessor::getLogin, Function.identity()));
        names.forEach( name -> {
            AssessmentInstanceAccessor aia = aias.get(nullCheck(name.get("login")));
            if(aia!=null){
                aia.setFirstName(nullCheck(name.get("firstName")));
                aia.setLastName(nullCheck(name.get("lastName")));
            }
        });
        manipulateName(aiaPage.get().collect(Collectors.toList()));
        return aiaPage;
    }

    @Override
    public void removeAssessmentInstanceAccessors(Set<String> logins, Long instanceId) {
        AssessmentInstance instance=instanceService.getAssessmentInstanceByIdv2(instanceId);
        if(!instance.getCreatedByUser().equals(PlatformSecurityUtil.getCurrentUserId())){
            throw new UnAuthorizedAccessException("Not allowed to perform the operation");
        }
        sendRevokeAccessMail(repository.findByLoginInAndAssessmentInstance(logins,instanceId),instance);
        repository.deleteByLoginInAndAssessmentInstance_Id(logins,instanceId);
    }

    private <T> String nullCheck(T o){
        return o==null ? null : o.toString();
    }


    @Autowired
    private void setInstanceService(AssessmentInstanceService instanceService){
        this.instanceService = instanceService;
    }
}
