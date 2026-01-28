package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.domain.domain.DomainHierarchy;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomainHierarchyRepository extends NonMultiTenantBaseRepository<DomainHierarchy, Long> {

    @Query("select dh.domain from DomainHierarchy dh where " +
            " dh.parentDomain.code = :parentDomainCode " +
            " and  dh.parentDomain.context = :context " +
            " and ( dh.depth = :depth OR :depth is NULL )" +
            " and dh.isActive = 1 and dh.domain.isActive = 1 ")
    Page<Domain> findSubDomains(String parentDomainCode, Long depth, String context, Pageable pageable);

    @Query("select dh.domain.id from DomainHierarchy dh where " +
            " dh.parentDomain.code = :parentDomainCode "  )
    List<Long> getSubDomainIds(String parentDomainCode);
}