package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.ReportFlag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ReportFlagRepository extends BaseRepository<ReportFlag, Long> {


    @Query("FROM #{#entityName} rf where rf.reportedOnEntityType in  ( :reportedOnEntityTypes ) " +
            " AND rf.isActive = 1 " +
            " AND rf.reportedByActorId = :reportedByEntityId " +
            " AND rf.reportedByActorType = :reportedByEntityType ")
    Set<ReportFlag> findByReportedOnEntityTypeAndReportedByEntityId(Set<String> reportedOnEntityTypes, Long reportedByEntityId, String reportedByEntityType);

    @Query("SELECT r FROM ReportFlag r WHERE (:reportType IS NULL OR r.reportType = :reportType) AND (:marketCode IS NULL OR r.marketCode = :marketCode)")
    Page<ReportFlag> findByFilters(String reportType, String marketCode, Pageable pageable);
}