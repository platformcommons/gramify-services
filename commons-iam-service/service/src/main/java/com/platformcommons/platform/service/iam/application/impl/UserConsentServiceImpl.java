package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.UserConsentService;
import com.platformcommons.platform.service.iam.domain.UserConsent;
import com.platformcommons.platform.service.iam.domain.repo.UserConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserConsentServiceImpl implements UserConsentService {

    @Autowired
    private UserConsentRepository repository;

    public Optional<UserConsent> getByUserIdAndConsentType(Long userId, String consentType) {
        return repository.findByUserIdAndConsentType(userId,consentType);
    }

    public UserConsent postOrUpdateForLoggedInUser(UserConsent userConsent) {
        UserConsent finalUserConsent;
        Long userId = PlatformSecurityUtil.getCurrentUserId();
        Optional<UserConsent> optionalUserConsent = getByUserIdAndConsentType(userId,userConsent.getConsentType());
        if(optionalUserConsent.isPresent()) {
            finalUserConsent = optionalUserConsent.get();
            finalUserConsent.patch(userConsent);
        }
        else {
            finalUserConsent = UserConsent.builder()
                    .id(0L)
                    .userId(userId)
                    .consentType(userConsent.getConsentType())
                    .consentDoc(userConsent.getConsentDoc())
                    .consentStatus(userConsent.getConsentStatus())
                    .consentVersion(userConsent.getConsentVersion())
                    .consentContext(userConsent.getConsentContext())
                    .build();
            finalUserConsent.init();
            finalUserConsent = repository.save(finalUserConsent);
        }
        return finalUserConsent;
    }

    @Override
    public Set<UserConsent> getByConsentTypesForLoggedInUser(Set<String> consentTypes) {
        Long userId = PlatformSecurityUtil.getCurrentUserId();
        return repository.findByConsentTypesAndUserId(userId,consentTypes);
    }
}
