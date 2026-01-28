package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.AppFeatureConfiguration;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeatureConfigurationNonMTRepository extends NonMultiTenantBaseRepository<AppFeatureConfiguration,Long> {

    @Query(value = "SELECT * " +
            "FROM app_feature_configuration e " +
            "WHERE ((:ownerEntityId IS NULL AND e.owner_entity_id IS NULL) OR e.owner_entity_id = :ownerEntityId) " +
            "AND ((:ownerEntityType IS NULL AND e.owner_entity_type IS NULL) OR e.owner_entity_type = :ownerEntityType) " +
            "AND (:forEntityType IS NULL  OR e.for_entity_type = :forEntityType) " +
            "AND e.is_active = 1",
            nativeQuery = true)
    Optional<AppFeatureConfiguration> findByParams(@Param("ownerEntityId") String ownerEntityId,
                                                   @Param("ownerEntityType") String ownerEntityType,
                                                   @Param("forEntityType") String forEntityType);

    @Query(" SELECT af FROM #{#entityName} af " +
            " WHERE af.forEntityType = :forEntityType " +
            " AND af.ownerEntityType = :ownerEntityType " +
            " AND af.isActive = 1 ")
    List<AppFeatureConfiguration> findAllByOwnerEntityType(String ownerEntityType, String forEntityType);
}
