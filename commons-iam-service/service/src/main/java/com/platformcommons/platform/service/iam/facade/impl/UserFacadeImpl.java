package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.iam.application.TenantMetaService;
import com.platformcommons.platform.service.iam.application.TenantPartnerService;
import com.platformcommons.platform.service.iam.application.UserMapService;
import com.platformcommons.platform.service.iam.application.UserService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.constant.TenantMetaConstant;
import com.platformcommons.platform.service.iam.application.utility.IAMSecurityUtility;
import com.platformcommons.platform.service.iam.application.utility.OBOSecurityUtil;
import com.platformcommons.platform.service.iam.domain.*;
import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.UserExistResponse;
import com.platformcommons.platform.service.iam.dto.UserMetaDataDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.facade.TenantFacade;
import com.platformcommons.platform.service.iam.facade.UserFacade;
import com.platformcommons.platform.service.iam.facade.UserMetaDataFacade;
import com.platformcommons.platform.service.iam.facade.UserVerificationFacade;
import com.platformcommons.platform.service.iam.facade.assembler.impl.IAMUserDTOAssemblerImpl;
import com.platformcommons.platform.service.iam.facade.obo.OBOUserFacade;
import com.platformcommons.platform.service.sdk.person.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final OBOUserFacade oboUserFacade;
    private final IAMSecurityUtility iamSecurityUtility;
    private final IAMUserDTOAssemblerImpl userDTOAssembler;

    private final PolicyEvaluator policyEvaluator;
    private final UserMapService userMapService;
    private final UserMetaDataFacade userMetaDataFacade;
    private final TenantMetaService tenantMetaService;
    private final TenantPartnerService tenantPartnerService;
    private final UserVerificationFacade userVerificationFacade;

    private final OBOSecurityUtil oboSecurityUtil;

    @Autowired
    @Lazy
    private TenantFacade tenantFacade;

    @Override
    public Set<User> addDefaultUsers(Set<UserDTO> users, Tenant tenant, String adminPass) {
        Set<User> usersCreated = new HashSet<>();
        users.forEach(userDTO -> {
            String password = adminPass;
            User user;
            if (userDTO.getLogin().equals(IAMConstant.ADMIN)) {
                password = iamSecurityUtility.buildAdminPassword(tenant.getTenantUUID());
            } else if (userDTO.getLogin().equals(IAMConstant.OPS_SUPPORT)) {
                password = iamSecurityUtility.buildOpsPassword(tenant.getTenantUUID());
            }
            user = userDTOAssembler.buildUserDomainForDefaultUser(tenant, userDTO);
            usersCreated.add(userService.addUser(user, password));
        });
        return usersCreated;
    }


    @Override
    public Long addUser(IAMUserDTO iamUserDTO, String password, boolean addInLinkedSystem) {
        //  policyEvaluator.evaluate(AuthConstant.PERMISSION_MANAGE_USER);
        if (addInLinkedSystem) {
            String sessionId = PlatformSecurityUtil.getToken();
            Long userId = oboUserFacade.addUser(iamUserDTO, password, sessionId);
            iamUserDTO.setId(userId);
        }
        return createAndSaveUser(iamUserDTO, password,null);
    }

    @Override
    public Long addUserForTenant(IAMUserDTO iamUserDTO, String password, Long tenantId, boolean addInLinkedSystem) {
        //  policyEvaluator.evaluate(AuthConstant.PERMISSION_MANAGE_USER);
        if (addInLinkedSystem) {
            String sessionId = oboSecurityUtil.getPrivilegeUserSession();
            Long userId = oboUserFacade.addUseForTenant(iamUserDTO, password,tenantId, sessionId);
            iamUserDTO.setId(userId);

        }
        return createAndSaveUser(iamUserDTO, password, tenantId);
    }

    @Override
    public User addUserWithoutPassword(User user) {
        return userService.addUser(user, null);
    }

    @Override
    public User getUserReferenceByLogin(String userLogin) {
        return userService.getUserReferenceByLogin(userLogin);
    }

    @Override
    public User getUserReferenceByLoginAndTenantId(String userLogin,Long tenantId) {
        return userService.getUserReferenceByLoginAndTenantId(userLogin,tenantId);
    }

    @Override
    public User getUserReferenceById(Long userId) {
        return userService.getUserReferenceById(userId);
    }

    @Override
    public IAMUserDTO getUser(Long userId, String userLogin, Boolean includeRole) {
        Set<UserRoleMap> userRoleMaps = new HashSet<>();
        User user;
        if (userId == null && userLogin == null) {
            user = userService.getUserById(PlatformSecurityUtil.getCurrentUserId());
        } else {
            user = userId != null ?
                    userService.getUserById(userId) :
                    userService.getUserByLogin(userLogin);
        }
        if(includeRole){
           userRoleMaps = user.getUserRoleMaps();
        }
        return userDTOAssembler.toDTO(user,userRoleMaps,user.getTenant());
    }

    @Override
    public Long selfRegisterUser(IAMUserDTO iamUserDTO, String password, TenantDTO tenantDTO,  boolean addInLinkedSystem) {
        PlatformSecurityUtil.mimicTenant(tenantDTO.getId(),1L);
        return addUserForTenant(iamUserDTO,password,tenantDTO.getId(),addInLinkedSystem);
    }


    @Override
    public UserExistResponse existsUser(String email, String mobile,String login,String tenantLogin) {
        return userService.existUser(email,mobile,login,tenantLogin);
    }

    @Override
    public IAMUserDTO patch(IAMUserDTO iamUserDTO) {
        return userDTOAssembler.toDTO(userService.patch(userDTOAssembler.fromDTO(iamUserDTO)));
    }

    @Override
    public IAMUserDTO joinToTenancy(Set<UserMetaDataDTO> userMetaData, String tenantLogin, String appContext,
                                    Boolean addInLinkedSystem) {
        PlatformToken requestPlatformToken = PlatformSecurityUtil.getContext();

        Optional<User> targetUserOptional = userService.getUserReferenceByLoginAndTenantLogin(PlatformSecurityUtil.getCurrentUserLogin(),tenantLogin);
        User targetUser = targetUserOptional.orElse(null);
        TenantDTO targetTenant = tenantFacade.getTenantByLogin(tenantLogin);
        String status = validateIfRequestToJoinIsAllowed(targetTenant.getTenantLogin());

        //add target-user
        if (targetUser == null) {
            User sourceUser = userService.getUserByLogin(PlatformSecurityUtil.getCurrentUserLogin());
            targetUser =  buildTargetUser(sourceUser,status,targetTenant);

            PlatformSecurityUtil.mimicTenant(targetTenant.getId(),1L);
            targetUser = addUserWithoutPassword(targetUser);
            userMetaDataFacade.postOrUpdateInBulkForUser(userMetaData,targetUser.getId());
        }

        //add user-map
        boolean existsUserMap = userMapService.existsForSourceAndTarget(requestPlatformToken.getTenantContext().getTenantId(),
                requestPlatformToken.getUserContext().getUserId(),
                targetTenant.getId(),
                targetUser.getId());
        if(!existsUserMap){
            Long tenantPartnerId = tenantPartnerService.getTenantPartner(targetTenant.getId(),
                                requestPlatformToken.getTenantContext().getTenantId());
            userMapService.addUserMap(tenantPartnerId,
                    targetUser.getId(),
                    requestPlatformToken.getUserContext().getUserId(),
                    status);

            //TODO
           // if (!status.equals(IAMConstant.USER_STATUS_ACTIVE))
            //    userVerificationFacade.createUserVerificationOnRegistration(targetUser.getId(), appContext);
        }

        return userDTOAssembler.toDTO(targetUser);
    }




    private Long createAndSaveUser(IAMUserDTO iamUserDTO, String password, Long tenantId) {
        if (userService.existByLogin(iamUserDTO.getLogin())) {
            throw new DuplicateResourceException(String.format("User with login %s already exists", iamUserDTO.getLogin()));
        }
        User user = userDTOAssembler.fromDTO(iamUserDTO);
        user.setTenant(Tenant.builder().id(tenantId!=null?tenantId:PlatformSecurityUtil.getCurrentTenantId()).build());
        return userService.addUser(user, password).getId();
    }

    private User buildTargetUser(User sourceUser, String status,TenantDTO targetTenant) {
        User targetUser = new User();
        targetUser.setUserLogin(sourceUser.getUserLogin());
        targetUser.setFirstName(sourceUser.getFirstName());
        targetUser.setLastName(sourceUser.getLastName());
        targetUser.setStatus(status);
        targetUser.setTenant(Tenant.builder().id(targetTenant.getId()).tenantLogin(targetTenant.getTenantLogin()).build());
        targetUser.setPerson(Person.builder().id(sourceUser.getPerson() == null ? null : sourceUser.getPerson().getId()).build());
        return targetUser;
    }

    private String validateIfRequestToJoinIsAllowed(String tenantLogin) {
        TenantMeta isRequestToJoinIsAllowed = tenantMetaService.getTenantMetaData(tenantLogin, TenantMetaConstant.META_MASTER_ALLOWED_JOIN_USER);
        if (isRequestToJoinIsAllowed == null || !Boolean.parseBoolean(isRequestToJoinIsAllowed.getMetaValue())) {
            throw new UnAuthorizedAccessException("Joining to tenancy is not allowed for tenant");
        }
        TenantMeta defaultUSerRegistrationStatus = tenantMetaService.getTenantMetaData(tenantLogin, TenantMetaConstant.META_MASTER_DEFAULT_JOIN_USER_STATUS);
        return defaultUSerRegistrationStatus == null ? IAMConstant.USER_STATUS_ACTIVE : defaultUSerRegistrationStatus.getMetaValue();

    }
}
