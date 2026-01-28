package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.UserConsent;

import java.util.Optional;
import java.util.Set;

public interface UserConsentService {

    UserConsent postOrUpdateForLoggedInUser(UserConsent userConsent);

    Set<UserConsent> getByConsentTypesForLoggedInUser(Set<String> consentTypes);
}
