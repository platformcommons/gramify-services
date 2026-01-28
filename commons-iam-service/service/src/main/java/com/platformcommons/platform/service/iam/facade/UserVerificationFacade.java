package com.platformcommons.platform.service.iam.facade;


import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.iam.dto.UserVerificationDTO;

import java.util.Optional;
import java.util.Set;

public interface UserVerificationFacade {

    UserVerificationDTO getByUserIdAndAppContext(Long userId,String appContext,String marketContext);

    Optional<UserVerificationDTO> getOptionalByUserIdAndAppContext(Long userId, String appContext, String marketContext);

    void userVerificationStatusUpdate(UserVerificationDTO userVerificationDTO, Boolean deleteAllAssignedRoles);

    void addEntryForUserVerification(String tenantLogin, Long userId, String appContext, String marketContext);

    void addEntryForUserVerificationOnSelfSignUp(String tenantLogin, Long userId, String appContext, String marketContext);
}
