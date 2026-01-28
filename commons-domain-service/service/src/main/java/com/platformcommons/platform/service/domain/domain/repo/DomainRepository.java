package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.vo.DomainVO;
import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;
import java.util.Set;

public interface DomainRepository extends NonMultiTenantBaseRepository<Domain, Long> {

    @Query("FROM #{#entityName} d where ( d.isRoot = :isRoot OR :isRoot is null ) " +
            " AND d.context = :context AND d.isActive = 1 ")
    Page<Domain> getDomains(String context, Boolean isRoot, Pageable pageable);

    @Query("FROM #{#entityName} e WHERE e.code IN( ?1 ) and e.isActive = 1")
    List<Domain> findAllByCode(Iterable<String> codes);

    @Query("FROM #{#entityName} d where  d.code = :domainCode " +
            " AND d.context = :context AND d.isActive = 1 ")
    Optional<Domain> getDomainByCode(String domainCode, String context);

    @Query("SELECT new com.platformcommons.platform.service.domain.domain.vo.DomainVO(d.id,d.name,d.code,d.description) " +
            "FROM #{#entityName} d WHERE d.code IN (:domainCodes)")
    List<DomainVO> findByCodes(List<String> domainCodes);


    @Query("FROM #{#entityName} d WHERE d.code IN (:domainCodes) and d.isActive = 1 and (:context is null or d.context = :context)")
    List<Domain> findByDomainsByCode(List<String> domainCodes,String context);

    @Query("SELECT COUNT(d)>0 FROM #{#entityName} d WHERE d.id = :domainId ")
    boolean existsById(Long domainId);


//    @Query(value = "SELECT d.tagList FROM #{#entityName} d where d.id IN (:subDomainIds)",
//            countQuery = "SELECT COUNT(distinct t.id) FROM #{#entityName} d INNER JOIN d.tagList t where d.id IN (:subDomainIds)" )
//    Page<Tag> findTagsByDomainIds(List<Long> subDomainIds,Pageable pageable);



    @Query(value = "SELECT distinct t FROM #{#entityName} d JOIN d.tagList t where d.id IN (:subDomainIds) " +
            " and t.type = :type " +
            " and t.isActive = 1 " +
            " and d.isActive = 1 ",
            countQuery = "SELECT count(distinct t) FROM #{#entityName} d JOIN d.tagList t where d.id IN (:subDomainIds) " +
                    " and t.type = :type " +
                    " and t.isActive = 1 " +
                    " and d.isActive = 1 " )
    Page<Tag> findTagsByDomainIds(List<Long> subDomainIds,String type,Pageable pageable);

    @Query("SELECT d FROM #{#entityName} d WHERE d.id IN (:ids) and d.isActive = 1")
    Set<Domain> getDomainsByIds(Set<Long> ids);
}