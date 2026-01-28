package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.iam.domain.RoleHierarchyStructure;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleHierarchyStructureRepository extends BaseRepository<RoleHierarchyStructure, Long> {
    @Query("FROM  #{#entityName} r where r.function=:function")
    Set<RoleHierarchyStructure> findByFunction(String function);
}
