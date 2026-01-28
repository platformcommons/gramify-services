package com.platformcommons.platform.service.profile.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.profile.domain.CostSpecificationMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface CostSpecificationMasterNonMTRepository extends NonMultiTenantBaseRepository<CostSpecificationMaster, Long> {

    @Query(" SELECT c FROM CostSpecificationMaster c "
            + "WHERE c.isActive = true "
            + "AND (:context IS NULL OR c.context = :context) "
            + "AND (:contextId IS NULL OR c.contextId = :contextId) ")
    Page<CostSpecificationMaster> findByContextAndContextId(String context, String contextId, Pageable pageable);

}
