package com.platformcommons.platform.service.iam.application.utility;

import com.mindtree.bridge.platform.dto.PersonContactDTO;
import com.mindtree.bridge.platform.dto.PersonDTO;
import com.mindtree.bridge.platform.dto.UserDTO;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.constant.NotificationConstant;
import com.platformcommons.platform.service.iam.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.dto.UserDetailsDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import com.platformcommons.platform.service.iam.facade.client.utility.CommonsReportUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class TriggerNotificationUtil {

    private final CommonsReportUtil commonsReportUtil;
    private final TenantMetaConfigFacade tenantMetaConfigFacade;

    @Value("${commons.platform.changemaker.app-base-url:http://commons.social/}")
    private String defaultAppBaseUrl;

    public Map<String,String> getTenantAdminsAndUsersToNotify(String tenantLogin) {
        Map<String, String> result = new HashMap<>(commonsReportUtil.getTenantAdminNameAndEmail(tenantLogin));
        TenantMetaAdditionalPropertyDTO config = tenantMetaConfigFacade.getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(
                TenantConfigConstant.CONFIG_KEY_TENANT_MAILS_FOR_NOTIFICATION ,tenantLogin,IAMConstant.DEFAULT_APP_CONTEXT);
        if(config != null) {
            result.putAll(config.getAttributes());
        }
        return result;
    }

    public static String computeFullName(String firstName, String lastName) {
        return StringUtils.trim((firstName == null ? "" : firstName)
                + " "
                + (lastName == null ? "" : lastName));
    }

    public static Map<String, String> fetchUserContactsFromUserDTO(UserDTO userDTO) {
        Map<String, String> contactValue = new HashMap<>();
        if(userDTO != null && userDTO.getPerson() != null) {
            List<PersonContactDTO> personContactDTOS = userDTO.getPerson().getPersonContacts();
            if(personContactDTOS!=null && !personContactDTOS.isEmpty()){
                contactValue = convertToMap(personContactDTOS);
            }
        }
        return contactValue;
    }

    public static Map<String, String> convertToMap(List<PersonContactDTO> personContacts){
        Map<String,String> contactValues = new LinkedHashMap<>();
        personContacts.forEach(it-> {
            if(it.getContact()!=null) {
                contactValues.put(it.getContact().getContactType().getDataCode(),it.getContact().getContactValue());
            }
        });
        return  contactValues;
    }


    public String deduceAppBaseUrl(String appBaseUrl) {
        if(appBaseUrl == null) {
            appBaseUrl = defaultAppBaseUrl;
        }

        if (!appBaseUrl.startsWith("https://") && !appBaseUrl.startsWith("http://")) {
            appBaseUrl = "https://" + appBaseUrl;
        }
        return appBaseUrl;
    }

    public void addTenantSupportEmailAndMobileNumber(Map<String,String> params, Long tenantId) {
        Optional<TenantMetaConfigDTO> tenantMetaConfigDTOOptional;
        tenantMetaConfigDTOOptional = tenantMetaConfigFacade.getTenantMetaConfigOptional(tenantId,null);
        if(tenantMetaConfigDTOOptional.isPresent()) {
            TenantMetaConfigDTO tenantMetaConfig = tenantMetaConfigDTOOptional.get();
            appendTenantSupportEmailAndMobileNumber(params, tenantMetaConfig);
        }
    }

    public void appendTenantSupportEmailAndMobileNumber(Map<String,String> map, TenantMetaConfigDTO tenantMetaConfigDTO) {
        Set<String> supportEmailsSet = tenantMetaConfigFacade.getMetaValueForMultiValuedMetaByConfigDTO(tenantMetaConfigDTO,
                TenantConfigConstant.CONFIG_KEY_TENANT_SUPPORT_EMAILS,null);
        Set<String> supportMobileSet = tenantMetaConfigFacade.getMetaValueForMultiValuedMetaByConfigDTO(tenantMetaConfigDTO,
                TenantConfigConstant.CONFIG_KEY_TENANT_SUPPORT_CONTACT_NUMBERS,null);

        String supportEmails = String.join(", ",supportEmailsSet);
        String supportMobiles = String.join(", ",supportMobileSet);

        map.put(NotificationConstant.TENANT_SUPPORT_EMAILS, supportEmails);
        map.put(NotificationConstant.TENANT_SUPPORT_CONTACT_NUMBERS, supportMobiles);
    }

    public UserDetailsDTO getUserDetailsByUserId(Long userId) {
        UserDetailsDTO userDetailsDTO = null;
        List<UserDetailsDTO> userDetailsDTOList = commonsReportUtil.getUserDetailsByUserIdsInBulk(Collections.singleton(userId));
        userDetailsDTO = userDetailsDTOList.stream().findFirst().orElse(null);
        if (userDetailsDTO == null || org.apache.commons.lang3.StringUtils.isEmpty(userDetailsDTO.getEmailAddress())) {
            log.debug(String.format("ERROR =>>>>>>>>> UserDetailsDTO for user with id - %d not fetched OR Email Address is empty", userId));
            userDetailsDTO = null;
        }
        return userDetailsDTO;
    }

    public static UserDetailsDTO getUserDetailsByUserDTO(UserDTO userDTO) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        if(userDTO != null && userDTO.getPerson() != null) {
            PersonDTO personDTO = userDTO.getPerson();

            if(personDTO.getPersonProfile() != null) {
                String firstName = personDTO.getPersonProfile().getFirstName();
                String lastName = personDTO.getPersonProfile().getLastName();
                String fullName = computeFullName(firstName,lastName);
                userDetailsDTO.setFullName(fullName);
            }

            userDetailsDTO.setUserId(Long.valueOf(userDTO.getId()));
            userDetailsDTO.setUserLogin(userDTO.getLogin());

            Map<String,String> userContacts = fetchUserContactsFromUserDTO(userDTO);
            String emailAddress = userContacts.get(IAMConstant.CONTACT_TYPE_MAIL);
            userDetailsDTO.setEmailAddress(emailAddress);

            userDetailsDTO.setUserId(Long.valueOf(userDTO.getId()));
        }
        return userDetailsDTO;
    }
}
