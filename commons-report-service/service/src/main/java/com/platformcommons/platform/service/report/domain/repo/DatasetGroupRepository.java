package com.platformcommons.platform.service.report.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.report.domain.DatasetGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface DatasetGroupRepository extends NonMultiTenantBaseRepository<DatasetGroup,Long> {

    @Query("From #{#entityName} dg where dg.code IN ( :datasetGroupCodes )")
    Set<DatasetGroup> findAllByCode(Set<String> datasetGroupCodes);

    @Query("From #{#entityName} dg where dg.code = :code")
    Optional<DatasetGroup> findByCode(String code);
}
