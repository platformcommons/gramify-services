package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.Instance;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface InstanceRepository extends NonMultiTenantBaseRepository<Instance,Long> {

    @Query("From #{#entityName} i where i.status = :status")
    Page<Instance> findAllByStatus(String status, Pageable pageable);
}
