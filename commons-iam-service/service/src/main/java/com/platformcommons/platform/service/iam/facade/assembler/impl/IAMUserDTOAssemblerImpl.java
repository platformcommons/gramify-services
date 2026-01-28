package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.domain.*;
import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.dto.IAMUserRoleMapDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.TenantVO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.facade.assembler.IAMUserDTOAssembler;
import com.platformcommons.platform.service.sdk.person.domain.Contact;
import com.platformcommons.platform.service.sdk.person.interfaces.assesmbler.PersonDTOAssemblerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IAMUserDTOAssemblerImpl implements IAMUserDTOAssembler {

    private final PersonDTOAssemblerImpl personDTOAssembler;

    @Override
    public User fromDTO(UserDTO userDetails, Long tenantId) {
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
                .build();
    }

    public IAMUserDTO toDTO(User user, Set<UserRoleMap> userRoleMaps, Tenant tenant) {
        return IAMUserDTO.builder()
                .id(user.getId())
                .login(user.getUserLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus()==null ? IAMConstant.USER_STATUS_ACTIVE : user.getStatus())
                .tenant(TenantVO.builder()
                        .id(tenant.getId())
                        .name(tenant.getTenantName())
                        .login(tenant.getTenantLogin())
                        .domainURL(tenant.getTenantDomain())
                        .build())
                .roles(userRoleMap(userRoleMaps))
                .person(user.getPerson()!=null ? personDTOAssembler.toDTO(user.getPerson()) : null)
                .build();
    }

    private Set<IAMUserRoleMapDTO> userRoleMap(Set<UserRoleMap> userRoleMaps) {
        if(userRoleMaps==null || userRoleMaps.isEmpty())
            return new HashSet<>();
        return userRoleMaps.stream().map(userRoleMap -> IAMUserRoleMapDTO.builder()
                .id(userRoleMap.getId())
                .roleCode(userRoleMap.getRole().getCode())
                .roleName(userRoleMap.getRole().getRoleName())
                .build()).collect(Collectors.toSet());

    }

    @Override
    public User fromDTO(IAMUserDTO iamUserDTO) {
        Set<UserContact> userContacts = new HashSet<>();
        if (iamUserDTO.getEmail() != null)
            userContacts.add(buildUserContactDomain(iamUserDTO.getEmail(), IAMConstant.CONTACT_TYPE_MAIL));
        if (iamUserDTO.getMobile() != null)
            userContacts.add(buildUserContactDomain(iamUserDTO.getMobile(), IAMConstant.CONTACT_TYPE_MOBILE));
        return User.builder()
                .id(iamUserDTO.getId())
                .userLogin(iamUserDTO.getLogin())
                .firstName(iamUserDTO.getFirstName())
                .lastName(iamUserDTO.getLastName())
                .status(iamUserDTO.getStatus()==null ? IAMConstant.USER_STATUS_ACTIVE : iamUserDTO.getStatus())
                .userContacts(userContacts)
                .person(personDTOAssembler.fromDTO(iamUserDTO.getPerson()))
                .build();
    }

    @Override
    public User buildUserDomainForDefaultUser(Tenant tenant, UserDTO userDTO) {
        Set<UserContact> userContacts = new HashSet<>();
        if (!userDTO.getLogin().equals(IAMConstant.ADMIN) && !userDTO.getLogin().equals(IAMConstant.OPS_SUPPORT)) {
            if (tenant.getEmail() != null)
                userContacts.add(buildUserContactDomain(tenant.getEmail(), IAMConstant.CONTACT_TYPE_MAIL));
            if (tenant.getMobile() != null)
                userContacts.add(buildUserContactDomain(tenant.getMobile(), IAMConstant.CONTACT_TYPE_MOBILE));
        }
        return User.builder()
                .id(null == userDTO.getId() ? null : userDTO.getId().longValue())
                .userLogin(userDTO.getLogin())
                .firstName(userDTO.getFirstName())
                .tenant(tenant)
                .tenantId(tenant.getId())
                .userContacts(userContacts)
                .build();
    }

    @Override
    public UserDTO buildUserDTO(String login, String firstName) {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(login);
        userDTO.setFirstName(firstName);
        return userDTO;
    }

    @Override
    public UserContact buildUserContactDomain(String contact, String contactType) {
        return UserContact.builder()
                .contact(Contact.builder()
                        .contactType(contactType)
                        .contactValue(contact)
                        .isVerified(true)
                        .build())
                .primaryContact(true)
                .build();
    }

    @Override
    public UserRoleMap buildUserRoleMap(User user, Role role) {
        return UserRoleMap.builder()
                .user(user)
                .role(role)
                .id(null)
                .build();
    }


    @Override
    public IAMUserDTO fromDTO(LeadDTO leadDTO, String userLogin, String userStatus) {
        return IAMUserDTO.builder().
                 firstName(leadDTO.getFirstName())
                .lastName(leadDTO.getLastName())
                .login(userLogin)
                .status(userStatus)
                .email(leadDTO.getEmail())
                .mobile(leadDTO.getMobile())
                .build();
    }


    @Override
    public IAMUserDTO toDTO(User user) {
        // TODO: add user contacts
        return IAMUserDTO.builder()
                .id(user.getId())
                .login(user.getUserLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus()==null ? IAMConstant.USER_STATUS_ACTIVE : user.getStatus())
                .person(personDTOAssembler.toDTO(user.getPerson()))
                .build();
    }
}
