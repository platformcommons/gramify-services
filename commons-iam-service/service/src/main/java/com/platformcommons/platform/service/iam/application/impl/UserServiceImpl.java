package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthenticatedAccessException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.UserSecurityService;
import com.platformcommons.platform.service.iam.application.UserService;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.repo.UserNonMTRepository;
import com.platformcommons.platform.service.iam.domain.repo.UserRepository;
import com.platformcommons.platform.service.iam.dto.UserExistResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_NOT_ACTIVE = "User is not active";
    private final UserRepository userRepository;
    private final UserSecurityService userSecurityService;
    private final UserNonMTRepository nonMTUserRepository;


    @Override
    public User addUser(User user, String password) {
        User createdUser = userRepository.save(user);
        if (!StringUtils.isEmpty(password))
            userSecurityService.create(createdUser, password);
        return createdUser;
    }

    @Override
    public User findByUserAndTenantLogin(String userLogin, String tenantLogin) {
        User user = nonMTUserRepository.findByUserLoginAndTenant_TenantLogin(userLogin, tenantLogin)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (!user.getIsActive()) throw new UnAuthenticatedAccessException(USER_NOT_ACTIVE);
        return user;
    }

    @Override
    public User findByUserIdAndTenantLogin(Long userId, String tenantLogin) {
        User user = nonMTUserRepository.findByIdAndTenant_TenantLogin(userId, tenantLogin)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (!user.getIsActive()) throw new UnAuthenticatedAccessException(USER_NOT_ACTIVE);
        return user;
    }

    @Override
    public User getUserByLogin(String userLogin) {
        return userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User getUserReferenceByLogin(String userLogin) {
        return userRepository.getUserReferenceByLogin(userLogin)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User getUserReferenceByLoginAndTenantId(String userLogin,Long tenantId) {
        return nonMTUserRepository.getUserReferenceByLoginAndTenantId(userLogin,tenantId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User patch(User toBeUpdate) {
        User user =  userRepository.findUserById(toBeUpdate.getId()).orElseThrow(() -> new NotFoundException("User not found exception"));
        if(!(Objects.equals(user.getId(),PlatformSecurityUtil.getCurrentUserId()) || PlatformSecurityUtil.isTenantAdmin() || PlatformSecurityUtil.isPlatformAdmin())){
            throw new UnAuthorizedAccessException("Unauthorized to perform the operation");
        }
        user.patch(toBeUpdate);
        // TODO: Add Validator For the Data to avoid unexpected application behaviour
        return userRepository.save(user);
    }

    @Override
    public UserExistResponse existUser(String email, String mobile, String login, String tenantLogin) {
        boolean isUserExistByLogin = nonMTUserRepository.existsUserByLoginAndTenantLogin(login, tenantLogin);
        UserExistResponse.UserExistResponseBuilder builder = UserExistResponse.builder();
        builder.isUserPresent(isUserExistByLogin);
        if (isUserExistByLogin) {
            return builder.build();
        }
        if (email != null) {
            builder.isEmailUsed(nonMTUserRepository.existsUserByContactAndTenantLogin(email, tenantLogin));
        }
        if (mobile != null) {
            builder.isMobileUsed(nonMTUserRepository.existsUserByContactAndTenantLogin(mobile, tenantLogin));
        }
        return builder.build();
    }

    @Override
    public User getUserReferenceById(Long id) {
        return userRepository.getUserReferenceById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User findByUserContactAndTenantLogin(String tenantLogin, String contact) {
        throw new IllegalStateException("Not Implemented");
    }


    @Override
    public Boolean existById(Long userId) {
        return nonMTUserRepository.existsUserById(userId);
    }

    @Override
    public boolean existByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Optional<User> getUserReferenceByLoginAndTenantLogin(String login, String tenantLogin) {
        return nonMTUserRepository.getUserReferenceByLoginAndTenantLogin(login,tenantLogin);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findUserById(userId).orElseThrow(()
                -> new NotFoundException(USER_NOT_FOUND));
    }
}
