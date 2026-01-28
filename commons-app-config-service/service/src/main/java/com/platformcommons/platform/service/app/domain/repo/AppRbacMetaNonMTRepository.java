package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.AppRbacMeta;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppRbacMetaNonMTRepository extends NonMultiTenantBaseRepository<AppRbacMeta, Long> {

    @Query(" FROM AppRbacMeta arm " +
            " WHERE arm.appCode = :appCode " +
            " AND (:entityType IS NULL OR arm.entityType = :entityType) " +
            " AND (:roleType IS NULL OR arm.roleType = :roleType) " +
            " AND arm.isActive = 1 " )
    Optional<AppRbacMeta> getByAppCode(String appCode,String entityType, String roleType);

    @Query(
            " FROM AppRbacMeta arm " +
                    " WHERE arm.appCode = :appCode " +
                    " AND ( " +
                    "       (:entityType IS NULL AND arm.entityType IS NULL) " +
                    "    OR (:entityType IS NOT NULL AND arm.entityType = :entityType) " +
                    "     ) " +
                    " AND ( " +
                    "       (:roleType IS NULL AND arm.roleType IS NULL) " +
                    "    OR (:roleType IS NOT NULL AND arm.roleType = :roleType) " +
                    "     ) " +
                    " AND arm.isActive = 1 "
    )
    Optional<AppRbacMeta> getByAppCodeAndEntityTypeAndExactMatch(@Param("appCode") String appCode,
                                                    @Param("entityType") String entityType,
                                                    @Param("roleType") String roleType);


}