package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TagRepository extends NonMultiTenantBaseRepository<Tag, Long> {

    @Query("SELECT t FROM #{#entityName} t WHERE t.code IN ( :codeList)")
    Set<Tag> findByCodes(Set<String> codeList);

    @Query("SELECT t FROM Tag t " +
            "WHERE (:context IS NULL OR t.context = :context) AND " +
            "      (:isRoot IS NULL OR t.isRoot = :isRoot) AND " +
            "      (:type IS NULL OR t.type = :type) AND t.isActive = 1 ")
    Page<Tag> getTags(String context, Boolean isRoot, String type, Pageable of);

    @Query("SELECT t FROM #{#entityName} t WHERE t.type = :type AND t.isActive = 1")
    Page<Tag> getTagsByType(String type, Pageable pageable);



    @Query(value = "FROM #{#entityName} t where  t.isActive = 1 AND ( t.context = :context or :context is NULL ) ")
    Page<Tag> findAllWithContext(String context, Pageable pageable);

    @Query(" SELECT t FROM #{#entityName} t WHERE t.code = :code AND t.isActive = 1 ")
    Set<Tag> findByCode(String code);

}