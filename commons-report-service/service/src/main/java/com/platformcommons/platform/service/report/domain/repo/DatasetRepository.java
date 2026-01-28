package com.platformcommons.platform.service.report.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.report.domain.Dataset;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DatasetRepository extends NonMultiTenantBaseRepository<Dataset, Long> {
    Optional<Dataset> findByName(String name);

    @Query("Select count( distinct at.tenantId ) > 0 from Dataset d  " +
            " LEFT JOIN  d.datasetGroups dg " +
            " LEFT JOIN dg.allowedTenants at " +
            "  where d.id = :datasetId and at.tenantId = :tenantId ")
    boolean isAllowedTenant(Long tenantId,Long datasetId);

    @Query("FROM #{#entityName} e where e.name IN (:names)")
    List<Dataset> getByListOfNames(List<String> names);

    @Query("FROM Dataset d where d.isCronEnabled = true")
    List<Dataset> findCronDatasets();
}