package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.iam.domain.UserConsent;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserConsentRepository extends BaseRepository<UserConsent,Long> {

    @Query("SELECT u FROM #{#entityName} u WHERE u.userId = :userId AND u.consentType = :consentType ")
    Optional<UserConsent> findByUserIdAndConsentType(Long userId,String consentType);

    @Query("SELECT u FROM #{#entityName} u WHERE u.userId = :userId AND u.consentType IN (:consentTypes) ")
    Set<UserConsent> findByConsentTypesAndUserId(Long userId, Set<String> consentTypes);
}
