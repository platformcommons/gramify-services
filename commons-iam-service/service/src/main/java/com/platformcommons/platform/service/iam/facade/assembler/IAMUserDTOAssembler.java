package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.*;
import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;

import java.util.Set;

public interface IAMUserDTOAssembler {
    User fromDTO(UserDTO userDetails, Long tenantId);

    IAMUserDTO toDTO(User user,Set<UserRoleMap> userRoleMaps, Tenant tenant);

    User fromDTO(IAMUserDTO iamUserDTO);

    User buildUserDomainForDefaultUser(Tenant tenant, UserDTO userDTO);

    UserDTO buildUserDTO(String login, String firstName);

    UserContact buildUserContactDomain(String contact, String contactType);

    UserRoleMap buildUserRoleMap(User user, Role role);

    IAMUserDTO fromDTO(LeadDTO leadDTO, String userLogin, String userStatus);

    IAMUserDTO toDTO(User patch);
}
