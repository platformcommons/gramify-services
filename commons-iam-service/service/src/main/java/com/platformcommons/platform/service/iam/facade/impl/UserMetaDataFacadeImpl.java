package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.iam.application.UserMetaDataService;
import com.platformcommons.platform.service.iam.dto.UserMetaDataDTO;
import com.platformcommons.platform.service.iam.facade.UserMetaDataFacade;
import com.platformcommons.platform.service.iam.facade.assembler.UserMetaDataDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class UserMetaDataFacadeImpl implements UserMetaDataFacade {


    private final UserMetaDataService service;
    private final UserMetaDataDTOAssembler assembler;
    private final PolicyEvaluator policyEvaluator;

    @Override
    public Set<UserMetaDataDTO> getByMetaKeyInBulkForLoggedInUser(Set<String> metaKeys) {
        return assembler.toDTOs(service.getByMetaKeyInBulkForLoggedInUser(metaKeys,PlatformSecurityUtil.getCurrentUserId()));
    }

    @Override
    public void postOrUpdateInBulkForLoggedInUser(Set<UserMetaDataDTO> userMetaDataDTOS) {
        service.postOrUpdateForUser(assembler.fromDTOs(userMetaDataDTOS),
                PlatformSecurityUtil.getCurrentUserId());
    }

    @Override
    public void postOrUpdateInBulkForUser(Set<UserMetaDataDTO> userMetaDataDTOS,Long userId) {
       //policyEvaluator.evaluate(AuthConstant.PERMISSION_MANAGE_USER);
        service.postOrUpdateForUser(assembler.fromDTOs(userMetaDataDTOS),userId);

    }
}
