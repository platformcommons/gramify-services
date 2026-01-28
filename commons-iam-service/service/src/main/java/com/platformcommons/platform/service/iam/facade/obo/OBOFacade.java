package com.platformcommons.platform.service.iam.facade.obo;

import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.security.filter.session.CurrentExecutiveDTO;
import com.platformcommons.platform.service.iam.domain.vo.UserSelfRegistrationExcelVO;
import com.platformcommons.platform.service.iam.domain.vo.UserVO;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.dto.brbase.PersonProfessionalHistoryDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserContactVO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserSelfRegistrationDTO;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OBOFacade {


    String SESSION = "sessionId";

    String CROSS_SESSION = "crossSessionId";

    String getSessionId(LoginRequestDTO loginRequestDTO,String appId,String appVersion, String appKey, String deviceId);

    UserDTO getLoggedInUserDetails(String sessionId);

    UserSelfRegistrationDTO register(UserSelfRegistrationDTO userSelfRegistrationDTO, String password, String tenantName,
                                     String appId, Boolean sendAndAuthenticateDifferentOTPForEmailAndSms,String appCode, String marketContext);

    String activate(String otp, String modKey, String tenantName, Integer userId, Boolean isOTPAndModKeyOfEmail, Boolean skipUserActiveValidation);

    String pass(String userLogin, String tenant, String appContext, String appId);

    String resetPassword(String userLogin,String tenant, String newPassword, String key, String otp);

    String checkUser(String tenantName, String loginName);

    String resendOTP(String userLogin, String tenant, String appContext, String appId);

    UserSelfRegistrationDTO activateInactive(String userName, String tenantName, String appId, String appContext, Boolean sendOTPForEmail);

    String getCrossTenantSessionId(String tenant, String appId, String appKey, String appVersion, String deviceId, String sessionId);

    String getCrossTenantSessionId(String tenantLogin, String sessionId);

    UserDTO updateUserProfessionalHistoryList(Long userId, PersonProfessionalHistoryDTO professionalHistoryDTO);

    Map<String,String> crossLogin(LoginRequestDTO loginRequestDTO, String crossTenant,String appId, String appVersion, String appKey, String deviceId);

    Map<String,String> crossLoginV2(LoginRequestDTO loginRequestDTO,String appId, String appVersion, String appKey, String deviceId);

    CurrentExecutiveDTO getLoggedInUserSessionDetails();


    String getSessionForSocialLoginTLD(String token,String platform, String providerTenantId,String appId,String appVersion,
                                       String appKey, String deviceId,String tenantLogin,Boolean createUser);

    void activateUser(String userLogin, String tenantLogin, String password, Integer sourceUserId, Integer targetTenantId,
                      String OTP, String messageId, BigDecimal userSystemUUID);

    String sendOTP(String email, String name);

    Map<String,String> activateUserCrossTenantQuickLogin(CrossLoginRequestDTO crossTenantLogin, Integer x_APPID, String x_APPKEY, String x_APPVERSION, String x_DEVICEID);

    Object userSelfAssignRole(String roleCode);

    UserRoleMapDTO assignRoleToUser(String roleCode, String userLogin);

    Object getOAuthIdToken(String authCode, String clientId, String redirectURI, String platform);

    Map<String,String> assignRoleToUsersInBulk(Set<UserRoleAssignDTO> userRoleAssignDTOSet);

    void patchUpdateUserWithWorkNode(UserDTO userDTO, String role,String cohort,String placementCity);

    com.mindtree.bridge.platform.dto.UserDTO patchUpdateUser(com.mindtree.bridge.platform.dto.UserDTO userDTO);

    void patchUpdateUserViaWebhook(UserSyncCustomDTO userSyncCustomDTO);

    void produceChangesToTenantEndPoint(com.mindtree.bridge.platform.dto.UserDTO userDTO);

    TFIResponseDTO tfiUserVerification(TFIUserVerificationDTO tfiDataSyncDTO);


    void selfAssignRolesForLoggedInUser(Set<String> roleCodes,Boolean isFlowComplete);

    void approveOrRejectUserRoles(Long userId, Set<String> assignRoleCodes, Set<String> rejectRoleCodes);

    void selfDeactivation(String reason);

    UserSelfRegistrationDTO changeContactValueAndTriggerOTP(UserContactVO userContactVO, String tenantName, String appId, String appContext);

    UserSelfRegistrationDTO validateAndAddPrimaryContactValueWithOTPTrigger(Long userId, String contactValue, String tenantName, String appId, String appContext, Boolean replaceContactValue);

    String validateAndActivateUserContact(String otp, String modKey, String tenantName, Integer userId, Boolean isOTPAndModKeyOfEmail, Boolean skipUserActiveValidation);

    void setNewPasswordBySelf(String newPass, String firstName, String lastName, String email, String mobile);

    String getPasswordStatusByUserName(String userName);

    UserTenantCheckDTO computeTenantForUserLogin(String userLogin, String primaryTenantLogin, String crossTenantLogin);

    void assignRolesToUser(Long userId, Set<String> roleCodes);

    void deActivateRolesFromAnUser(Long userId, Set<String> roleCodes,String reason,Boolean ignoreError);
}
