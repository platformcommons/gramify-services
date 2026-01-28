package com.platformcommons.platform.service.report.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.report.domain.DatasetCronMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatasetCronMetaRepository extends NonMultiTenantBaseRepository<DatasetCronMeta, Long> {
    @Query("select d from DatasetCronMeta d where d.dataset.id = ?1")
    Page<DatasetCronMeta> findByDataset(Long id, Pageable pageable);
    @Query("select d from DatasetCronMeta d where d.dataset.id = ?1")
    List<DatasetCronMeta> findByDataset_Id(Long id);

    @Query("select d from DatasetCronMeta d where d.dataset.isCronEnabled=:cronEnabled")
    List<DatasetCronMeta> getActiveCronMetaData(Boolean cronEnabled);
}