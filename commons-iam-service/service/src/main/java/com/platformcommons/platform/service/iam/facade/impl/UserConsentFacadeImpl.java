package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.service.iam.application.UserConsentService;
import com.platformcommons.platform.service.iam.dto.UserConsentDTO;
import com.platformcommons.platform.service.iam.facade.UserConsentFacade;
import com.platformcommons.platform.service.iam.facade.assembler.UserConsentDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
public class UserConsentFacadeImpl implements UserConsentFacade {

    @Autowired
    private UserConsentService service;

    @Autowired
    private UserConsentDTOAssembler assembler;


    @Override
    public Set<UserConsentDTO> getByConsentTypesForLoggedInUser(Set<String> consentTypes) {
        return assembler.toDTOs(service.getByConsentTypesForLoggedInUser(consentTypes));
    }

    @Override
    public UserConsentDTO postOrUpdateForLoggedInUser(UserConsentDTO userConsentDTO) {
        return assembler.toDTO(service.postOrUpdateForLoggedInUser(assembler.fromDTO(userConsentDTO)));
    }

}
