package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.UserVerification;

import java.util.Optional;
import java.util.Set;

public interface UserVerificationService {

    UserVerification getByUserIdAndAppContext(Long userId,String appContext,String marketContext);

    Optional<UserVerification> getOptionalByUserIdAndAppContext(Long userId, String appContext, String marketContext);

    void createUserVerificationOnRegistration(Long userId, String appContext, String marketContext);

    UserVerification userVerificationStatusUpdate(UserVerification userVerification, Boolean deleteAllAssignedRoles);
}
