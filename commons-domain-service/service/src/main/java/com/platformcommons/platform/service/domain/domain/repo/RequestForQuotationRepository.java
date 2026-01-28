package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.RequestForQuotation;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RequestForQuotationRepository extends NonMultiTenantBaseRepository<RequestForQuotation, Long> {

    Optional< RequestForQuotation > findById( Long id );

    @Query("FROM #{#entityName} rfq WHERE (rfq.forEntityId = :forEntityId OR :forEntityId is NULL ) AND (rfq.forEntityType = :forEntityType OR :forEntityType is NULL )" )
    Page<RequestForQuotation> getByForEntityIdAndForEntityTypeFilter(String forEntityId, String forEntityType, Pageable pageable);
}
