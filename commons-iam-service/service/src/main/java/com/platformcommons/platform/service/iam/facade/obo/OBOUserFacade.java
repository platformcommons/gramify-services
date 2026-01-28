package com.platformcommons.platform.service.iam.facade.obo;

import com.mindtree.bridge.platform.dto.UserDTO;
import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.UserSignUpRequestDTO;
import com.platformcommons.platform.service.iam.dto.UserWrapperCustomDTO;

import java.util.Set;

public interface OBOUserFacade {

    void addAminUser(Tenant tenant, String adminPass, String sessionId, String appContext);

    void addAminUserWithContacts(Tenant tenant, String adminPass, String sessionId, String appContext, String marketContext);

    Set<com.platformcommons.platform.service.iam.dto.brbase.UserDTO> getUsersFromLinkedSystem(Long tenantId, String sessionId);

    Set<UserRoleMapDTO> getUserRolesFromLinkedSystem(Set<Long> users, String sessionId);

    Long addUser(IAMUserDTO userDTO, String userPassword, String sessionId);

    Long addUseForTenant(IAMUserDTO userDTO, String userPassword,Long tenantId, String sessionId);

    String createUserInLinkedSystemInVmsByLead(UserSignUpRequestDTO userSignUpRequestDTO, String password);

    UserDTO getOrCreateUserFromLead(LeadDTO leadDTO, String password,String tenant);

    void initiateProcessOfUserBulkUpload(Long bulkUploadRequestId, String appContext, String baseDomain, Boolean sendNotification,
                                         String marketId);

    Long addUser(UserWrapperCustomDTO userWrapperCustomDTO, String appContext, String baseDomain, Boolean sendNotification,
                 String marketId);
}
