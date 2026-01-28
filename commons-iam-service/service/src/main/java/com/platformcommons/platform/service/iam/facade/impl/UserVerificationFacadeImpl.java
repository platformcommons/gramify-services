package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.UserVerificationService;
import com.platformcommons.platform.service.iam.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.iam.application.utility.TriggerNotification;
import com.platformcommons.platform.service.iam.domain.UserVerification;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.UserVerificationDTO;
import com.platformcommons.platform.service.iam.facade.TenantFacade;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import com.platformcommons.platform.service.iam.facade.UserVerificationFacade;
import com.platformcommons.platform.service.iam.facade.assembler.UserVerificationDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@EnableAsync
@RequiredArgsConstructor
public class UserVerificationFacadeImpl implements UserVerificationFacade {

    private final UserVerificationService service;
    private final UserVerificationDTOAssembler assembler;
    private final TriggerNotification triggerNotification;
    private final TenantMetaConfigFacade tenantMetaConfigFacade;
    private final TenantFacade tenantFacade;

    @Override
    public UserVerificationDTO getByUserIdAndAppContext(Long userId, String appContext,String marketContext) {
        return assembler.toDTO(service.getByUserIdAndAppContext(userId,appContext,marketContext));
    }

    @Override
    public Optional<UserVerificationDTO> getOptionalByUserIdAndAppContext(Long userId, String appContext, String marketContext) {
        Optional<UserVerificationDTO> userVerificationDTOOptional = Optional.empty();
        Optional<UserVerification> userVerificationOptional = service.getOptionalByUserIdAndAppContext(userId,
                appContext,marketContext);
        if(userVerificationOptional.isPresent()) {
            userVerificationDTOOptional = Optional.of(assembler.toDTO(userVerificationOptional.get()));
        }
        return userVerificationDTOOptional;
    }

    @Override
    public void userVerificationStatusUpdate(UserVerificationDTO userVerificationDTO,Boolean deleteAllAssignedRoles) {
        userVerificationDTO = assembler.toDTO(service.userVerificationStatusUpdate(assembler.fromDTO(userVerificationDTO),
                deleteAllAssignedRoles));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        triggerNotification.sendNotificationToUserOnVerificationStatusUpdate(userVerificationDTO,authentication);
    }

    @Override
    public void addEntryForUserVerification(String tenantLogin, Long userId, String appContext, String marketContext) {
        TenantDTO tenantDTO = tenantFacade.getTenantByLogin(tenantLogin);
        TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO = tenantMetaConfigFacade.getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(
                TenantConfigConstant.CONFIG_KEY_USER_VERIFICATION_REQUIRED, tenantLogin,appContext);
        if (tenantMetaAdditionalPropertyDTO != null && Objects.equals(tenantMetaAdditionalPropertyDTO.getMetaValue(), "1")) {
            PlatformSecurityUtil.mimicTenant(tenantDTO.getId(),1L);
            service.createUserVerificationOnRegistration(userId,appContext, marketContext);
        }
    }



    @Override
    public void addEntryForUserVerificationOnSelfSignUp(String tenantLogin, Long userId, String appContext, String marketContext) {
        //TODO has to be derived from appConfig
        TenantDTO tenantDTO = tenantFacade.getTenantByLogin(tenantLogin);
        PlatformSecurityUtil.mimicTenant(tenantDTO.getId(),1L);
        service.createUserVerificationOnRegistration(userId,appContext, marketContext);
    }


}
