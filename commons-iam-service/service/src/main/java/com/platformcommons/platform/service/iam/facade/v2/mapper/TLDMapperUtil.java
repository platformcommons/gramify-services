package com.platformcommons.platform.service.iam.facade.v2.mapper;

import com.mindtree.bridge.platform.dto.UserWrapperDTO;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.sdk.person.domain.Contact;
import com.platformcommons.platform.service.sdk.person.domain.Person;
import com.platformcommons.platform.service.sdk.person.domain.PersonContact;
import com.platformcommons.platform.service.sdk.person.domain.PersonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TLDMapperUtil {

    @Autowired
    private  TLDClient tldClient;


    public UserWrapperDTO getUserWrapperDTO(Long userId){
        return tldClient.getUserViaWrapper(Math.toIntExact(userId), PlatformSecurityUtil.getToken()).getBody();
    }


    public User fromDTO(com.mindtree.bridge.platform.dto.UserDTO userDetails, Long tenantId) {
        return User.builder()
                .id(userDetails.getId().longValue())
                .userLogin(userDetails.getLogin())
                .tenantId(tenantId)
                .tenant(Tenant.builder().id(tenantId).build())
                .uuid(userDetails.getUuid())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .appCreatedTimestamp(Optional.ofNullable(userDetails.getAppCreatedDateTime()).map(Date::getTime).orElse(null))
                .appLastModifiedTimestamp(Optional.ofNullable(userDetails.getAppUpdatedDateTime()).map(Date::getTime).orElse(null))
                .person(buildPerson(userDetails.getPerson()))
                .build();
    }


    private Person buildPerson(com.mindtree.bridge.platform.dto.PersonDTO personDTO){
        return Person.builder()
                .id(Long.valueOf(personDTO.getId()))
                .personProfile(buildPersonContact(personDTO.getPersonProfile()))
                .contacts(personDTO.getPersonContacts()!=null && !personDTO.getPersonContacts().isEmpty() ?
                        personDTO.getPersonContacts().stream().map(this::buildPersonContact).collect(Collectors.toSet()) : null)

                .build();
    }


    private PersonContact buildPersonContact(com.mindtree.bridge.platform.dto.PersonContactDTO personContactDTO){
        PersonContact personContact = new PersonContact();
        personContact.setContact(buildContact(personContactDTO.getContact()));
        personContact.setId(null);
        return  personContact;
    }

    private PersonProfile buildPersonContact(com.mindtree.bridge.platform.dto.PersonProfileDTO personProfileDTO){
        return PersonProfile.builder()
                .id(Long.valueOf(personProfileDTO.getId()))
                .firstName(personProfileDTO.getFirstName())
                .lastName(personProfileDTO.getLastName())
                .middleName(personProfileDTO.getMiddleName())
                .build();
    }

    private Contact buildContact(com.mindtree.bridge.platform.dto.ContactDTO contactDTO){
        return Contact.builder()
                .id(Long.valueOf(contactDTO.getId()))
                .contactType(contactDTO.getContactType().getDataCode())
                .contactValue(contactDTO.getContactValue())
                .isVerified(contactDTO.getVerified())
                .build();
    }
}
