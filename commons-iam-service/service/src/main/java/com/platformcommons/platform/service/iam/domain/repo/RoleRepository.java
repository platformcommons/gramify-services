package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends NonMultiTenantBaseRepository<Role, String> {
    @Query("select r from Role r where r.code in (:codes) AND (r.roleType != 'TENANT' OR (r.roleType = 'TENANT' AND r.tenantId = :tenantId))")
    Set<Role> findByCodeIn(Collection<String> codes,Long tenantId);

    @Query("select r from Role r where r.code = :code AND (r.roleType != 'TENANT' OR (r.roleType = 'TENANT' AND r.tenantId = :tenantId))")
    Optional<Role> findByCode(String code,Long tenantId);

    @Query("FROM  #{#entityName} r where (r.roleType != 'TENANT' OR (r.roleType = 'TENANT' AND r.tenantId = :tenantId))")
    List<Role> findAllRoles(Long tenantId);


    @Query("SELECT new com.platformcommons.platform.service.iam.domain.Role" +
            "(r.id,r.code) FROM #{#entityName} r " +
            " where r.code=:roleCode AND (r.roleType != 'TENANT' OR (r.roleType = 'TENANT' AND r.tenantId = :tenantId))")
    Optional<Role> getRoleReferenceByCode(String roleCode,Long tenantId);
}
