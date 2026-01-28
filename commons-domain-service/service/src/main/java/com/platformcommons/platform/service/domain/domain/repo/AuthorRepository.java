package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.Author;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends NonMultiTenantBaseRepository<Author, Long> {

    @Query("From #{#entityName} a where a.tenant =:tenant and a.isActive = 1")
    Optional<Author> getCurrentTenantAuthor(Long tenant);
}