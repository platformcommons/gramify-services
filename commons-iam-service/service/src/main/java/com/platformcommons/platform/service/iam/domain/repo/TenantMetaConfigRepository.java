package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface TenantMetaConfigRepository extends NonMultiTenantBaseRepository<TenantMetaConfig, Long> {
    @Query("SELECT t from TenantMetaConfig t " +
            " WHERE ( t.tenantId = :tenantId OR t.tenantLogin = :tenantLogin ) " +
            " AND t.isActive = 1 ")
    Optional<TenantMetaConfig> findByTenantIdOrTenantLogin(Long tenantId, String tenantLogin);

    @Query("SELECT tm FROM TenantMetaConfig tm WHERE " +
            " tm.tenantLogin = :tenantLogin AND tm.isActive IS TRUE " )
    Optional<TenantMetaConfig> findByTenantLogin(String tenantLogin);

    @Query("SELECT DISTINCT tm FROM #{#entityName} tm " +
            " LEFT JOIN tm.tenantMetaAdditionalPropertySet c ON c.isActive = 1 " +
            " WHERE tm.isActive IS TRUE " +
            " AND (:metaKey IS NULL OR c.metaKey = :metaKey) " +
            " AND (:metaValue IS NULL OR c.metaValue = :metaValue) " )
    Page<TenantMetaConfig> findAllTenantMetaConfigs(String metaKey, String metaValue, Pageable pageable);

    @Query("SELECT DISTINCT tm FROM #{#entityName} tm " +
            " LEFT JOIN tm.tenantMetaAdditionalPropertySet c ON c.isActive = 1 " +
            " WHERE tm.isActive IS TRUE " +
            " AND (:metaKey IS NULL OR c.metaKey = :metaKey) " +
            " AND (:metaValue IS NULL OR c.metaValue = :metaValue) " +
            " GROUP BY tm.id" )
    Set<TenantMetaConfig> findAllTenantMetaConfigsSet(String metaKey, String metaValue);
}