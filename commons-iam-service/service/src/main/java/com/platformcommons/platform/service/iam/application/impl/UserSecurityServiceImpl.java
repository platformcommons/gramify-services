package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthenticatedAccessException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.TenantMetaMasterService;
import com.platformcommons.platform.service.iam.application.TenantMetaService;
import com.platformcommons.platform.service.iam.application.UserSecurityService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.constant.TenantMetaConstant;
import com.platformcommons.platform.service.iam.application.utility.IAMSecurityUtility;
import com.platformcommons.platform.service.iam.application.utility.TriggerNotification;
import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.UserSecurity;
import com.platformcommons.platform.service.iam.domain.repo.UserSecurityNonMTRepository;
import com.platformcommons.platform.service.iam.domain.repo.UserSecurityRepository;
import com.platformcommons.platform.service.iam.dto.OTPRequestDTO;
import com.platformcommons.platform.service.iam.exception.RequestThrottledException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserSecurityRepository userSecurityRepository;
    private final TenantMetaMasterService tenantMetaMasterService;
    private final IAMSecurityUtility iamSecurityUtility;
    private final SecureRandom random = new SecureRandom();
    private final UserSecurityNonMTRepository userSecurityNonMTRepository;
    private final TriggerNotification triggerNotification;
    private final TenantMetaService tenantMetaService;

    @Value("${commons.iam.login.max.attempts:5}")
    private int maxFailedLoginAttempts;

    @Value("${commons.iam.otp.max.attempts:2}")
    private int maxFailedOtpVerification;

    @Value("${commons.iam.otp.forget-pass.generation-timout:30}")
    private int forgetPasswordGenerationTimout;

    @Value("${commons.iam.otp.forget-pass.expiry:600}")
    private int forgetPassOTPExpiry;

    @Value("${commons.iam.otp.login.expiry:600}")
    private int otpExpiryForLogin;
    @Value("${commons.iam.otp.self-registration.expiry:600}")
    private int selfRegistrationOtpExpiry;


    @Override
    public UserSecurity create(User user, String password) {
        UserSecurity userSecurity = iamSecurityUtility.buildUserSecurityDomain(user, password);
        userSecurity.init();
        userSecurity.setPassword(passwordEncoder.encode(password));
        return userSecurityRepository.save(userSecurity);
    }

    @Override
    public UserSecurity getUserSecurity(User user) {
        UserSecurity userSecurity = userSecurityNonMTRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Invalid Credentials"));
        if (userSecurity.getIsAccountLocked() != null && userSecurity.getIsAccountLocked()) {
            throw new UnAuthenticatedAccessException(userSecurity.getAccountLockReason());
        }
        if (userSecurity.getIsExpired() != null && userSecurity.getIsExpired()) {
            throw new UnAuthenticatedAccessException("Credentials expired");
        }
        return userSecurity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = UnAuthenticatedAccessException.class)
    public void updateFailedLoginAttempts(String tenantLogin, UserSecurity userSecurity, @NotNull String appContext) {
        Optional<TenantMetaMaster> config = tenantMetaMasterService.getByAppContextAndCode(appContext, TenantMetaConstant.TENANT_METADATA_CODE_LOGIN_ATTEMPTS);
        int maxFailedLoginAttempts = parseOrDefault(
                config.flatMap(metaMaster ->
                                metaMaster.getMetaDefaultValues()
                                        .stream()
                                        .findAny())
                        .orElse(null), this.maxFailedLoginAttempts);
        userSecurity.updateLoginAttempts();
        boolean reachedLoginAttemptLimit = userSecurity.getFailedLoginAttempts() >= maxFailedLoginAttempts;
        if (reachedLoginAttemptLimit) {
            userSecurity.lockAccount("Reached LoginAttempts Limit");
            userSecurityRepository.save(userSecurity);
            throw new UnAuthenticatedAccessException(userSecurity.getAccountLockReason());
        } else {
            userSecurityRepository.save(userSecurity);
        }
    }

    private void updateFailedOTPVerification(UserSecurity userSecurity) {
        String appContext = iamSecurityUtility.getAppContext(null, null, null);
        Optional<TenantMetaMaster> config = tenantMetaMasterService.getByAppContextAndCode(appContext, TenantMetaConstant.TENANT_METADATA_CODE_OPT_VERIFICATION_TIMEOUT);
        int maxOtpFailedOtpVerification = parseOrDefault(config.flatMap(metaMaster -> metaMaster.getMetaDefaultValues().stream().findAny()).orElse(null),this.maxFailedOtpVerification);
        userSecurity.updateFailedOTPVerification();
        if(maxOtpFailedOtpVerification<=userSecurity.getFailedOTPVerification()){
            userSecurity.resetOtp();
            userSecurity.resetFailedOTPVerification();
            userSecurityRepository.save(userSecurity);
            throw new RequestThrottledException("Max otp verification attempt reached. Invalidated Otp!");
        }
        else{
            userSecurityRepository.save(userSecurity);
        }
    }

    @Override
    public void resetFailedLoginAttempts(UserSecurity userSecurity) {
        userSecurity.resetFailedLoginAttempts();
        userSecurityRepository.save(userSecurity);
    }

    @Override
    public String generateOTPForgetPassword(User user, String appContext) {
        UserSecurity userSecurity = userSecurityNonMTRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("ERR_SVC_US_NOT_FOUND"));
        PlatformSecurityUtil.mimicTenant(user.getTenant().getId(), user.getId());
        Optional<TenantMetaMaster> nextForgetPassOtpGenerationTimoutMetaMaster = tenantMetaMasterService.getByAppContextAndCode(appContext, TenantMetaConstant.TENANT_METADATA_FORGET_PASS_TIMOUT);
        Optional<TenantMetaMaster> forgetPassOtpExpiryMetaMaster = tenantMetaMasterService.getByAppContextAndCode(appContext, TenantMetaConstant.TENANT_METADATA_FORGET_PASS_OTP_EXPIRY);

        int nextForgetPassOtpGenerationTimeout = parseOrDefault(nextForgetPassOtpGenerationTimoutMetaMaster.flatMap(metaMaster -> metaMaster.getMetaDefaultValues().stream().findAny()).orElse(null),this.forgetPasswordGenerationTimout);
        int forgetPassOtpExpiry = parseOrDefault(forgetPassOtpExpiryMetaMaster.flatMap(metaMaster -> metaMaster.getMetaDefaultValues().stream().findAny()).orElse(null),this.forgetPassOTPExpiry);
        if(Objects.equals(userSecurity.getOtpFor(), IAMConstant.OTP_FOR_FORGET_PASS) &&
                userSecurity.getOtpGeneratedAt()!=null) {

            long expirationTimeMillis = userSecurity.getOtpGeneratedAt() + (nextForgetPassOtpGenerationTimeout * 1000L);
            boolean isOtpValid = Instant.now().toEpochMilli() >= expirationTimeMillis;
            if(!isOtpValid){
                throw new RequestThrottledException("Please wait few moment");
            }
        }
        String otp = generateOtp();
        String hashedOTP = passwordEncoder.encode(otp);
        userSecurity.generateOtp(hashedOTP, generateOtpKey(), getValidity(forgetPassOtpExpiry), IAMConstant.OTP_FOR_FORGET_PASS);
        userSecurity = userSecurityRepository.save(userSecurity);
        forgetPasswordNotification(user, otp,String.valueOf(forgetPassOtpExpiry));
        return userSecurity.getOtpKey();
    }

    private void forgetPasswordNotification(User user, String otp, String validity) {
        String email = user.getUserContacts().stream()
                .filter(userContact -> Objects.equals(userContact.getContact().getContactType(), IAMConstant.CONTACT_TYPE_MAIL))
                .findAny()
                .map(userContact -> userContact.getContact().getContactValue())
                .orElseGet(() -> {
                    boolean isValid = EmailValidator.getInstance().isValid(user.getUserLogin());
                    return isValid ? user.getUserLogin() : null;
                });

        String mobileNumber = user.getUserContacts().stream()
                .filter(userContact -> Objects.equals(userContact.getContact().getContactType(), IAMConstant.CONTACT_TYPE_MOBILE))
                .findAny()
                .map(userContact -> userContact.getContact().getContactValue())
                .orElse(null);

        if (email != null) {
            String subject = tenantMetaService.getMetaData(TenantMetaConstant.TENANT_METADATA_CODE_RESET_PASSWORD_MAIL_SUBJECT, user.getTenantId());
            String code = tenantMetaService.getMetaData(TenantMetaConstant.TENANT_METADATA_CODE_RESET_PASSWORD_MAIL_CODE, user.getTenantId());
            triggerNotification.sendNotificationOnForgetPassword(email, otp, validity, subject, code);
        }
        if (mobileNumber != null) {

            //TODO Optimize do only one repo call
            String subject = tenantMetaService.getMetaData(TenantMetaConstant.TENANT_METADATA_CODE_RESET_PASSWORD_MAIL_SUBJECT, user.getTenantId());
            String code = tenantMetaService.getMetaData(TenantMetaConstant.TENANT_METADATA_CODE_RESET_PASSWORD_SMS_CODE, user.getTenantId());
            String defaultCountryCode = tenantMetaService.getMetaData(TenantMetaConstant.TENANT_METADATA_DEFAULT_COUNTRY_DIAL_CODE, user.getTenantId());

            //TODO check if contact has country code or not , if not then add default code
            mobileNumber = defaultCountryCode+"-"+mobileNumber;

            triggerNotification.sendNotificationOnForgetPassword(mobileNumber, otp, validity, subject, code);
        }

    }

    private Date getValidity(long validity) {
        return new Date(System.currentTimeMillis() + validity * 1000L);
    }

    @Override
    public void resetPassword(OTPRequestDTO dto, User user, String appContext) {
        UserSecurity userSecurity = userSecurityNonMTRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("ERR_SVC_US_NOT_FOUND"));
        PlatformSecurityUtil.mimicTenant(user.getTenantId(), user.getId());
        checkOTP(userSecurity, dto);
        userSecurity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userSecurity.resetFailedLoginAttempts();
        userSecurity.resetFailedOTPVerification();
        userSecurity.resetOtp();
        userSecurity.unlock();
        userSecurityRepository.save(userSecurity);
    }

    private void checkOTP(UserSecurity userSecurity, OTPRequestDTO dto) {
        if (userSecurity.getOtpFor() == null) {
            throw new UnAuthorizedAccessException("First make a request for password reset");
        }
        if (!userSecurity.getOtpFor().equals(IAMConstant.OTP_FOR_FORGET_PASS)) {
            throw new UnAuthorizedAccessException("Something Went Wrong");
        }
        if (userSecurity.checkOTPExpired()) {
            throw new UnAuthorizedAccessException("Expired OTP");
        }
        if (!userSecurity.getOtpKey().equals(dto.getOtpKey())) {
            throw new UnAuthorizedAccessException("Incorrect OTP Key");
        }
        if (!(passwordEncoder.matches(dto.getOtp(),userSecurity.getOtp()))) {
            updateFailedOTPVerification(userSecurity);
            throw new UnAuthorizedAccessException("Incorrect OTP try again with correct OTP");
        }
    }


    @Override
    public String generateOTPForLoginRequest(User user, String appContext) {
        UserSecurity userSecurity = userSecurityNonMTRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("ERR_SVC_US_NOT_FOUND"));
        PlatformSecurityUtil.mimicTenant(user.getTenantId(), user.getId());
        Optional<TenantMetaMaster> otpExpiryForLoginMetaMaster = tenantMetaMasterService.getByAppContextAndCode(appContext, TenantMetaConstant.TENANT_METADATA_LOGIN_REQUEST_OTP_EXPIRY);
        int otpExpiryForLogin = parseOrDefault(otpExpiryForLoginMetaMaster.flatMap(metaMaster -> metaMaster.getMetaDefaultValues().stream().findAny())
                .orElse(null),this.otpExpiryForLogin);
        String otp = generateOtp();
        String hashedOTP = passwordEncoder.encode(otp);
        userSecurity.generateOtp(hashedOTP, generateOtpKey(), getValidity(otpExpiryForLogin), IAMConstant.OTP_FOR_LOGIN);
        userSecurity = userSecurityRepository.save(userSecurity);

        // TODO: Send Otp notification
        return userSecurity.getOtpKey();
    }

    @Override
    public String validateOTPAndLogin(User user, OTPRequestDTO otpRequestDTO, String appContext) {
        UserSecurity userSecurity = userSecurityNonMTRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("ERR_SVC_US_NOT_FOUND"));

        if (!userSecurity.getOtpKey().equals(otpRequestDTO.getOtpKey()))
            throw new UnAuthorizedAccessException("Incorrect OTP Key");
        if (userSecurity.getOtpFor() == null)
            throw new UnAuthorizedAccessException("First make a request for OTP");
        if (!userSecurity.getOtpFor().equals(IAMConstant.OTP_FOR_LOGIN))
            throw new UnAuthorizedAccessException("Something Went Wrong");
        if (userSecurity.checkOTPExpired())
            throw new UnAuthorizedAccessException("Expired OTP");
        if (!passwordEncoder.matches(otpRequestDTO.getOtp(),userSecurity.getOtp()))
            throw new UnAuthorizedAccessException("Incorrect OTP try again with correct OTP");

        userSecurity.resetFailedLoginAttempts();
        userSecurity.resetOtp();
        userSecurityRepository.save(userSecurity);
        iamSecurityUtility.generatePlatformTokenAndSetToContext(user, null);
        return PlatformSecurityUtil.getToken();
    }

    @Override
    public UserSecurity createForSelfRegisteredUser(User createdUser, Long id, String password, boolean createOTP, String appContext) {
        UserSecurity userSecurity = iamSecurityUtility.createSelfRegisteredUserSecurity(createdUser, id, password);
        Optional<TenantMetaMaster> selfRegistrationOtpExpiryTenantMetaMaster = tenantMetaMasterService.getByAppContextAndCode(appContext, TenantMetaConstant.TENANT_METADATA_SELF_REGISTRATION_OTP_EXPIRY);
        int selfRegistrationOtpExpiry = parseOrDefault(selfRegistrationOtpExpiryTenantMetaMaster.flatMap(metaMaster -> metaMaster.getMetaDefaultValues().stream().findAny()).orElse(null),this.selfRegistrationOtpExpiry);
        String otp = generateOtp();
        String hashedOTP = passwordEncoder.encode(otp);
        userSecurity.generateOtp(hashedOTP, generateOtpKey(), getValidity(selfRegistrationOtpExpiry), IAMConstant.OTP_FOR_SELF_REGISTER);
        return create(createdUser, password);
    }

    @Override
    public void validateOTPForActivateUser(User user, String otp, String modKey) {
        UserSecurity userSecurity = userSecurityRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("User security not found"));
        if (userSecurity.getOtpFor() == null) {
            throw new UnAuthorizedAccessException("First make a request for password reset");
        }
        if (!userSecurity.getOtpFor().equals(IAMConstant.OTP_FOR_SELF_REGISTER)) {
            throw new UnAuthorizedAccessException("Something Went Wrong");
        }
        if (userSecurity.checkOTPExpired()) {
            throw new UnAuthorizedAccessException("Expired OTP");
        }
        if (!userSecurity.getOtpKey().equals(modKey)) {
            throw new UnAuthorizedAccessException("Incorrect OTP Key");
        }
        if (!passwordEncoder.matches(otp,userSecurity.getOtp())) {
            throw new UnAuthorizedAccessException("Incorrect OTP try again with correct OTP");
        }
        userSecurity.resetOtp();
        userSecurityRepository.save(userSecurity);
    }

    @Override
    public void resendOTP(User user, String appContext) {
        UserSecurity userSecurity = userSecurityRepository.findByUser_Id(user.getId()).orElseThrow(() -> new NotFoundException("User security not found"));

        if (userSecurity.getOtpFor() == null) {
            throw new UnAuthorizedAccessException("First make a request for password reset");
        }
        if (!userSecurity.getOtpFor().equals(IAMConstant.OTP_FOR_SELF_REGISTER)) {
            throw new UnAuthorizedAccessException("Something Went Wrong");
        }
        Optional<TenantMetaMaster> selfRegistrationOTPExpiryMetaMaster = tenantMetaMasterService.getByAppContextAndCode(appContext, TenantMetaConstant.TENANT_METADATA_SELF_REGISTRATION_OTP_EXPIRY);
        int selfRegistrationOTPExpiry = parseOrDefault(selfRegistrationOTPExpiryMetaMaster.flatMap(metaMaster -> metaMaster.getMetaDefaultValues().stream().findAny()).orElse(null),this.selfRegistrationOtpExpiry);
        String otp = generateOtp();
        String hashedOTP = passwordEncoder.encode(otp);
        userSecurity.generateOtp(hashedOTP, generateOtpKey(), getValidity(selfRegistrationOTPExpiry), IAMConstant.OTP_FOR_SELF_REGISTER);

    }

    private int parseOrDefault(String config, int _default) {
        try {
            return Integer.parseInt(config);
        } catch (Exception e) {
            return _default;
        }
    }


    private String generateOtp() {
        return String.valueOf(100000 + random.nextInt(900000));
    }

    private String generateOtpKey() {
        byte[] otpKeyBytes = new byte[16];
        random.nextBytes(otpKeyBytes);
        return Base64.getEncoder().encodeToString(otpKeyBytes);
    }

}
