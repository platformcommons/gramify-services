package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.Client;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends NonMultiTenantBaseRepository<Client, Long> {

    @Query("select c from Client c where c.tenantId=?1 AND c.clientId = ?2")
    Client findByTenantIdAndClientId(Long tenantId, String clientId);
}
