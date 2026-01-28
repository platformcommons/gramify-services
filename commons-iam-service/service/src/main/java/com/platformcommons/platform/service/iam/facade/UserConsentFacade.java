package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.dto.UserConsentDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public interface UserConsentFacade {

    Set<UserConsentDTO> getByConsentTypesForLoggedInUser(Set<String> consentTypes);

    UserConsentDTO postOrUpdateForLoggedInUser(UserConsentDTO userConsentDTO);
}
