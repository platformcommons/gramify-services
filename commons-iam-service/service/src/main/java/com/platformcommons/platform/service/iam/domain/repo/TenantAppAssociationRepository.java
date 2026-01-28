package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantAppAssociation;

import java.util.Optional;


public interface TenantAppAssociationRepository extends NonMultiTenantBaseRepository<TenantAppAssociation, Long> {
    Optional<TenantAppAssociation> findByTenant_idAndAppContext(Long currentTenantId, String appContext);
}
