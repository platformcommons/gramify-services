package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.vo.UseCaseVO;
import com.platformcommons.platform.service.domain.domain.UseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;

public interface UseCaseRepository extends NonMultiTenantBaseRepository<UseCase, Long> {

    @Query("SELECT u.domainList FROM #{#entityName} u WHERE u.id= :useCaseId ")
    Page<Domain> getDomainsByUseCaseId(Long useCaseId, Pageable pageable);

    @Query("SELECT new com.platformcommons.platform.service.domain.domain.vo.UseCaseVO(u.id,u.name,u.authorId.id,u.authorId.name,u.shortDescription) " +
            "FROM #{#entityName} u WHERE u.id IN (:useCaseIds)")
    List<UseCaseVO> findByIds(List<Long> useCaseIds);

    @Query("SELECT COUNT(uc)>0 FROM #{#entityName} uc WHERE uc.id = :useCaseId  ")
    boolean existsById(Long useCaseId);

    @Query(value = "SELECT u.tagList FROM #{#entityName} u WHERE u.id= :useCaseId ",
            countQuery ="SELECT COUNT(distinct t.id) FROM #{#entityName} u inner join u.tagList t WHERE u.id= :useCaseId " )
    Page<Tag> getTagsByUseCaseId(Long useCaseId, Pageable pageable);

    @Query("FROM #{#entityName} e WHERE e.code IN( ?1 ) and e.isActive = 1")
    List<UseCase> findAllByCode(Iterable<String> codes);

    @Query("FROM #{#entityName} e WHERE e.code IN( ?1 ) and e.isActive = 1")
    List<UseCase> findByCode(String code);
}