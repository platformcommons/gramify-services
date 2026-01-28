package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.UserExistResponse;
import com.platformcommons.platform.service.iam.dto.UserMetaDataDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;

import javax.validation.Valid;
import java.util.Set;

public interface UserFacade {

    Set<User> addDefaultUsers(Set<UserDTO> users, Tenant tenant, String adminPass);

    Long addUser(IAMUserDTO userDTO, String adminPass, boolean addInLinkedSystem);

    Long addUserForTenant(IAMUserDTO iamUserDTO, String password, Long tenantId, boolean addInLinkedSystem);

    User addUserWithoutPassword(User user);

    User getUserReferenceByLogin(String userLogin);
    User getUserReferenceByLoginAndTenantId(String userLogin,Long tenantId);

    User getUserReferenceById(Long userId);

    IAMUserDTO getUser(Long userId, String userLogin, Boolean includeRole);

    Long selfRegisterUser(IAMUserDTO iamUserDTO, String password, TenantDTO tenantDTO, boolean addInLinkedSystem);

    UserExistResponse existsUser(String email, String mobile, String login, String tenantLogin);

    IAMUserDTO patch(@Valid IAMUserDTO iamUserDTO);

    IAMUserDTO joinToTenancy(Set<UserMetaDataDTO> userMetaData, String tenantLogin, String appContext,Boolean addInLinkedSystem);

}
