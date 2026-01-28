package com.platformcommons.platform.service.profile.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.profile.domain.CostSpecification;
import com.platformcommons.platform.service.profile.domain.IEIdCardTemplate;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CostSpecificationNonMTRepository extends NonMultiTenantBaseRepository<CostSpecification, Long> {

    @Query("SELECT cs FROM #{#entityName} cs WHERE cs.isActive = 1 and cs.uuid = :uuid")
    Optional<CostSpecification> findByUuid(String uuid);
}
