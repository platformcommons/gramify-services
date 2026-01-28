package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface AuthorityRepository extends NonMultiTenantBaseRepository<Authority, String> {

    @Query("SELECT a FROM Authority a" +
           " WHERE (a.process = :process OR a.process is null)" )
    Page<Authority> getALlAuthorities(String process, Pageable page);
}
