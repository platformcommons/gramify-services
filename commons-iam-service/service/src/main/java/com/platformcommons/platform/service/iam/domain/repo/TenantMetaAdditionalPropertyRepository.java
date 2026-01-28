package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantMetaAdditionalProperty;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TenantMetaAdditionalPropertyRepository extends NonMultiTenantBaseRepository<TenantMetaAdditionalProperty, Long> {

    @Query(
            value = " SELECT main.* " +
                    " FROM ( " +
                    "     SELECT p.*, " +
                    "            ROW_NUMBER() OVER ( " +
                    "              PARTITION BY p.meta_key " +
                    "              ORDER BY CASE " +
                    "                         WHEN p.app_context = :appContext THEN 0 " +
                    "                         WHEN p.app_context = :defaultAppContext THEN 1 " +
                    "                         ELSE 2 " +
                    "                       END " +
                    "            ) as rn " +
                    "     FROM tenant_meta_additional_property p " +
                    "     JOIN tenant_meta_config c ON c.id = p.tenant_meta_config " +
                    "     WHERE c.tenant_login = :tenantLogin " +
                    "       AND p.meta_key IN (:metaKeys) " +
                    "       AND p.app_context IN (:appContext, :defaultAppContext) " +
                    " ) main " +
                    " WHERE rn = 1 ",
            nativeQuery = true
    )
    Set<TenantMetaAdditionalProperty> findAllByMetaKeysAndTenantLogin(Set<String> metaKeys, String tenantLogin, String appContext,
            String defaultAppContext);

}