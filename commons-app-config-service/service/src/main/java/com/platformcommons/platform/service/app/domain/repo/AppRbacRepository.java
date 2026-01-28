package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AppRbacRepository extends NonMultiTenantBaseRepository<AppRbac, Long> {

    @Query(" FROM AppRbac a " +
            " WHERE a.appCode = :appCode " +
            " AND a.role = :role " +
            " AND (:marketContext IS NULL OR a.marketContext = :marketContext) " +
            " AND a.isActive = 1 ")
    Optional<AppRbac> findByAppCodeAndRole(String appCode,String role,String marketContext);

    @Query(" from AppRbac a " +
            " WHERE a.appCode = :appCode " +
            " AND a.role IN :roles " +
            " AND (:marketContext IS NULL OR a.marketContext = :marketContext) " +
            " AND a.isActive = 1 ")
    Page<AppRbac> findByAppCodeAndMarketContext(String appCode, Set<String> roles,String marketContext, Pageable pageable);

    @Query("from AppRbac a WHERE a.appCode = :appCode " +
            " AND a.context IS NULL" +
            " AND a.isDefault = true " +
            " AND a.isActive = 1 ")
    Optional<AppRbac> findDefaultByAppCode(String appCode) ;

    @Query(" SELECT a FROM AppRbac a " +
            " WHERE a.appCode = :appCode " +
            " AND a.context = :context " +
            " AND a.isDefault = true " +
            " AND ((:entityType IS NULL AND a.entityType IS NULL) OR (:entityType IS NOT NULL AND a.entityType = :entityType ))" +
            " AND a.isActive = 1 ")
    Optional<AppRbac> findDefaultByAppCodeAndContext(String appCode,String context,String entityType) ;

    @Query("SELECT a FROM AppRbac a WHERE a.appCode = :appCode " +
            " AND a.isDefault = false " +
            " AND a.role IN (:roleCodes)  " +
            " AND a.isActive = 1 ")
    Set<AppRbac> findByAppCodeAndRoleCodesAndNonDefault(String appCode,Set<String> roleCodes) ;

    @Query("SELECT a FROM AppRbac a WHERE a.appCode IN (:appCodes) " +
            " AND a.isDefault = false " +
            " AND a.role = :roleCode  " +
            " AND a.isActive = 1 ")
    Set<AppRbac> findByAppCodesAndRoleCodesAndNonDefault(Set<String> appCodes, String roleCode) ;

    @Query("SELECT COUNT(a)>0 FROM #{#entityName} a " +
            " WHERE a.appCode = :appCode " +
            " AND a.role = :role " +
            " AND (:marketContext IS NULL OR a.marketContext = :marketContext) " +
            " AND a.isActive = 1 ")
    Boolean existsByAppCodeAndRole(String appCode, String role,String marketContext);

    @Query("SELECT a FROM AppRbac a WHERE a.appCode = :appCode " +
            " AND a.isDefault = false " +
            " AND a.role IN (:tenantRoleCodes) " +
            " AND a.isActive = 1 ")
    List<AppRbac> findByAppCodeAndRoleCodeAndNonDefault(String appCode, Set<String> tenantRoleCodes);

    @Query(" from AppRbac a WHERE a.appCode = :appCode " +
            " AND a.context = :context " +
            " AND (:marketContext IS NULL OR a.marketContext = :marketContext ) " +
            " AND a.isDefault = false " +
            " AND ((:entityType IS NULL AND a.entityType IS NULL) OR (:entityType IS NOT NULL AND a.entityType = :entityType ))" +
            " AND a.isActive = 1 ")
    Optional<AppRbac> findByAppCodeAndContext(String appCode, String context,String entityType,String marketContext);

    @Query(" SELECT a FROM AppRbac a WHERE a.appCode = :appCode " +
            " AND a.context = :context " +
            " AND a.isDefault = false " +
            " AND ((:entityType IS NULL AND a.entityType IS NULL) OR (:entityType IS NOT NULL AND a.entityType = :entityType )) " +
            " AND a.isActive = 1 ")
    Set<AppRbac> findByAppCodeAndContextAndNonDefault(String appCode, String context,String entityType);

    @Query("from AppRbac a " +
            " WHERE a.appCode = :appCode " +
            " AND a.marketContext = :marketContext " +
            " AND a.context IS NULL " +
            " AND a.isMarketDefault = true " +
            " AND a.isActive = 1 ")
    Optional<AppRbac> findDefaultByAppCodeAndMarketContext(String appCode, String marketContext);

    @Query(" SELECT DISTINCT a.role FROM AppRbac a " +
            " WHERE a.appCode = :appCode " +
            " AND (:marketContext IS NULL OR a.marketContext = :marketContext) " +
            " AND a.context IS NULL " +
            " AND a.role IN (:roleCodes) " +
            " AND a.isActive = 1 ")
    Set<String> findRoleCodesForRbacExists(String appCode, Set<String> roleCodes, String marketContext);

    @Query(" FROM #{#entityName} ar WHERE ar.appCode = :appCode and ar.priority <= :priority " +
            "and ar.tenantId = :tenantId  and ar.role is not null and ar.isActive = 1 ")
    Set<AppRbac> getAppRbacOfHigherPriority(String appCode, Integer priority, Long tenantId);

    @Query(" FROM #{#entityName} ar WHERE ar.appCode = :appCode and ar.priority >= :priority " +
            "and ar.tenantId = :tenantId and ar.role is not null and ar.isActive = 1 ")
    Set<AppRbac> getAppRbacOfLowerPriority(String appCode, Integer priority, Long tenantId);

    Page<AppRbac> findByAppCode(String appCode, Pageable pageable);
}