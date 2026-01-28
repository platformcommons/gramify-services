package com.platformcommons.platform.service.iam.application.utility;

import com.mindtree.bridge.platform.dto.*;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaMasterFacade;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.platformcommons.platform.service.iam.application.constant.TenantMetaConstant.META_MASTER_USER_MOBILE_AS_LOGIN;

@Component
public class UserOnBoardUtil {


    @Autowired
    private TenantMetaMasterFacade tenantMetaMasterFacade;

    public UserWrapperDTO createUserWrapperDTOFromLeadDTO(LeadDTO leadDTO, String password) {
        UserWrapperDTO userWrapperDTO = new UserWrapperDTO();
        userWrapperDTO.setPassword(password);
        userWrapperDTO.setUserAddressDTOList(Collections.emptyList());

        userWrapperDTO.setUserRoleCode("PROLE.VOLUNTEER");
        userWrapperDTO.setFunctionName("VMS");

        List<UserContactDTO> userContactDTOList = createUserContactDTOList(leadDTO);
        userWrapperDTO.setUserContactDTOList(userContactDTOList);

        UserDTO userDTO = createUserDTO(leadDTO);
        userWrapperDTO.setUserDTO(userDTO);

        return userWrapperDTO;
    }

    public UserDTO getUserObject(String firstName,String lastName, String login,String email,String mobile){
        return getUserDTO(firstName,lastName,login,email,mobile);
    }

    private UserDTO createUserDTO(LeadDTO leadDTO) {
        String login = Objects.equals(leadDTO.getUseMobileAsUserLogin(),Boolean.TRUE) ? leadDTO.getMobile() : leadDTO.getEmail();
        UserDTO  result = getUserDTO(leadDTO.getFirstName(),leadDTO.getLastName(),login, leadDTO.getEmail(), leadDTO.getMobile());
        List<PersonContactDTO> personContactDTOList = createPersonContactDTOList(leadDTO);
        result.getPerson().setPersonContacts(personContactDTOList);
        return result;
    }

    public UserDTO getUserDTO(String firstName, String lastName,String login, String email, String mobile) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(0);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setLogin(login);
        userDTO.setIsActive(Boolean.TRUE);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(0);
        personDTO.setIsActive(Boolean.TRUE);
        userDTO.setPerson(personDTO);

        PersonProfileDTO personProfileDTO = new PersonProfileDTO();
        personProfileDTO.setId(0);
        personProfileDTO.setFirstName(firstName);
        personProfileDTO.setLastName(lastName);
        personProfileDTO.setId(0);
        personDTO.setPersonProfile(personProfileDTO);

        personDTO.setPersonContacts(createPersonContactDTOList(email,mobile));

        return userDTO;
    }



    private List<PersonContactDTO> createPersonContactDTOList(LeadDTO leadDTO) {
        List<PersonContactDTO> personContactDTOList = new ArrayList<>();
        if(leadDTO.getEmail() != null) {
            ContactDTO contactDTO = createContactDTO(leadDTO.getEmail(),IAMConstant.CONTACT_TYPE_MAIL);
            PersonContactDTO personContactDTO = new PersonContactDTO();
            personContactDTO.setId(0);
            personContactDTO.setIsPrimary(Boolean.TRUE);
            personContactDTO.setContact(contactDTO);

            personContactDTOList.add(personContactDTO);
        }

        if(leadDTO.getMobile() != null) {
            ContactDTO contactDTO = createContactDTO(leadDTO.getMobile(),IAMConstant.CONTACT_TYPE_MOBILE);
            PersonContactDTO personContactDTO = new PersonContactDTO();
            personContactDTO.setId(0);
            personContactDTO.setIsPrimary(Boolean.TRUE);
            personContactDTO.setContact(contactDTO);

            personContactDTOList.add(personContactDTO);
        }
        return personContactDTOList;
    }

    private List<UserContactDTO> createUserContactDTOList(LeadDTO leadDTO) {
        List<UserContactDTO> userContactDTOList = new ArrayList<>();
        if(!StringUtils.isBlank(leadDTO.getEmail())) {
            ContactDTO contactDTO = createContactDTO(leadDTO.getEmail(),IAMConstant.CONTACT_TYPE_MAIL);
            UserContactDTO userContactDTO = new UserContactDTO();
            userContactDTO.setId(0);
            userContactDTO.setPrimaryContact(Boolean.TRUE);
            userContactDTO.setContact(contactDTO);

            userContactDTOList.add(userContactDTO);
        }

        if(!StringUtils.isBlank(leadDTO.getMobile())) {
            ContactDTO contactDTO = createContactDTO(leadDTO.getMobile(),IAMConstant.CONTACT_TYPE_MOBILE);
            UserContactDTO userContactDTO = new UserContactDTO();
            userContactDTO.setId(0);
            userContactDTO.setPrimaryContact(Boolean.TRUE);
            userContactDTO.setContact(contactDTO);

            userContactDTOList.add(userContactDTO);
        }
        return userContactDTOList;
    }


    private List<UserContactDTO> createUserContactDTOList(String email,String mobile) {
        List<UserContactDTO> userContactDTOList = new ArrayList<>();
        if(!StringUtils.isBlank(email)) {
            ContactDTO contactDTO = createContactDTO(email,IAMConstant.CONTACT_TYPE_MAIL);
            UserContactDTO userContactDTO = new UserContactDTO();
            userContactDTO.setId(0);
            userContactDTO.setPrimaryContact(Boolean.TRUE);
            userContactDTO.setContact(contactDTO);

            userContactDTOList.add(userContactDTO);
        }

        if(!StringUtils.isBlank(mobile)) {
            ContactDTO contactDTO = createContactDTO(mobile,IAMConstant.CONTACT_TYPE_MOBILE);
            UserContactDTO userContactDTO = new UserContactDTO();
            userContactDTO.setId(0);
            userContactDTO.setPrimaryContact(Boolean.TRUE);
            userContactDTO.setContact(contactDTO);

            userContactDTOList.add(userContactDTO);
        }
        return userContactDTOList;
    }


    private List<PersonContactDTO> createPersonContactDTOList(String email,String mobile) {
        List<PersonContactDTO> personContactDTOList = new ArrayList<>();
        if(email != null) {
            ContactDTO contactDTO = createContactDTO(email,IAMConstant.CONTACT_TYPE_MAIL);
            PersonContactDTO personContactDTO = new PersonContactDTO();
            personContactDTO.setId(0);
            personContactDTO.setIsPrimary(Boolean.TRUE);
            personContactDTO.setContact(contactDTO);

            personContactDTOList.add(personContactDTO);
        }

        if(mobile != null) {
            ContactDTO contactDTO = createContactDTO(mobile,IAMConstant.CONTACT_TYPE_MOBILE);
            PersonContactDTO personContactDTO = new PersonContactDTO();
            personContactDTO.setId(0);
            personContactDTO.setIsPrimary(Boolean.TRUE);
            personContactDTO.setContact(contactDTO);

            personContactDTOList.add(personContactDTO);
        }
        return personContactDTOList;
    }

    public ContactDTO createContactDTO(String contactValue,String contactTypeCode) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(0);
        contactDTO.setVerified(Boolean.TRUE);
        contactDTO.setContactType(buildGlobalRefDataDTO(contactTypeCode));
        contactDTO.setContactValue(contactValue);
        return contactDTO;
    }


    private com.mindtree.bridge.platform.dto.GlobalRefDataDTO buildGlobalRefDataDTO(String dataCode){

        GlobalRefDataDTO globalRefDataDTO = new GlobalRefDataDTO();
        globalRefDataDTO.setDataCode(dataCode);
        return  globalRefDataDTO;
    }


    public UserDTO getUserObjectFromIAMUserDTO(IAMUserDTO iamUserDTO) {
        return getUserDTO(iamUserDTO.getFirstName(),iamUserDTO.getLastName(),iamUserDTO.getLogin(),iamUserDTO.getEmail(),iamUserDTO.getMobile());
    }



    public String getUserLogin(Tenant tenant, String appContext) {
        String useMobileAsLogin = tenantMetaMasterFacade.getMetaMasterValue(appContext,META_MASTER_USER_MOBILE_AS_LOGIN).stream().findFirst().orElse(null);
        return (useMobileAsLogin != null && Boolean.parseBoolean(useMobileAsLogin))
                ? (tenant.getMobile() != null ? tenant.getMobile() : tenant.getEmail())
                : (tenant.getEmail() != null ? tenant.getEmail() : tenant.getMobile());
    }

}
