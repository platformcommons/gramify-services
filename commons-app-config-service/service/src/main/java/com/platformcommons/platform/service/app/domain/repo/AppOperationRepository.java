package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.AppOperation;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AppOperationRepository extends NonMultiTenantBaseRepository<AppOperation, Long> {

    @Query("SELECT ao FROM #{#entityName} ao WHERE ao.appRbac.appCode = :appCode " +
            "AND ( ao.appRbac.role = :role OR :role is NULL ) AND ( ao.entity = :entity OR :entity is NULL ) " +
            "AND ao.isActive = 1")
    Page<AppOperation> findByAppCodeAppRoleAndEntity(String appCode, String role, String entity, Pageable pageable);

    @Query("SELECT ao FROM #{#entityName} ao WHERE ao.appRbac.appCode = :appCode " +
            "AND ao.entity = :entity AND ao.appRbac.role is not null  AND ao.appRbac.priority >= :priority " +
            "AND ao.isActive = 1 and ao.appRbac.isActive = 1 and ao.tenantId = :tenantId")
    Set<AppOperation> getLowerRbacAppOperation(String appCode, String entity, Integer priority, Long tenantId);

    @Query("SELECT ao FROM #{#entityName} ao WHERE ao.appRbac.appCode = :appCode " +
            "AND ao.entity = :entity  AND ao.appRbac.role is not null AND ao.appRbac.priority <= :priority " +
            "AND ao.isActive = 1 and ao.appRbac.isActive = 1 and ao.tenantId = :tenantId")
    Set<AppOperation> getHigherRbacAppOperation(String appCode, String entity, Integer priority, Long tenantId);
}