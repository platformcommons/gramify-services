package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.ColumnPreference;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnPreferenceNonMTRepository extends NonMultiTenantBaseRepository<ColumnPreference,Long> {

    @Query(value = " SELECT * FROM column_preference cp " +
            " WHERE ((:entityType IS NULL AND cp.entity_type IS NULL) OR cp.entity_type = :entityType) " +
            " AND ((:entityId IS NULL AND cp.entity_id IS NULL) OR cp.entity_id = :entityId) " +
            " AND ((:ownerId IS NULL AND cp.owner_id IS NULL) OR cp.owner_id = :ownerId) " +
            " AND cp.owner_type = :ownerType " +
            " AND cp.schema_code = :schemaCode " +
            " AND cp.is_active = TRUE ",
            nativeQuery = true)
    Optional<ColumnPreference> findByParamsForExactMatch(String schemaCode, String entityId, String entityType,
                                                         String ownerId, String ownerType);


    @Query(" SELECT cp FROM #{#entityName} cp " +
            " WHERE cp.schemaCode = :schemaCode " +
            " AND cp.ownerType = :ownerType " +
            " AND cp.isActive = 1 ")
    List<ColumnPreference> findBySchemaCodeAndOwnerType(String schemaCode, String ownerType);

    @Query(" SELECT cp FROM #{#entityName} cp " +
            " WHERE cp.parentColumnPreferenceId = :id " +
            " AND cp.isActive = 1 ")
    List<ColumnPreference> findByParentColumnPreferenceId(Long id);
}
