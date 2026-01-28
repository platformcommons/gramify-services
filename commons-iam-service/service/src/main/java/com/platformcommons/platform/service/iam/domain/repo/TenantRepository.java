package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.Tenant;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends NonMultiTenantBaseRepository<Tenant, Long> {
    Tenant findByTenantLogin(String login);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.Tenant" +
            "(t.id, t.tenantLogin, t.tenantName, t.iconpic, t.description, t.website, t.tenantDomain, t.slug ) " +
            "FROM #{#entityName} t  where t.email=:email")
    List<Tenant> findByTenantEmail(String email);

    @Query("FROM  #{#entityName} t WHERE t.id=:tenantId")
    Optional<Tenant> findByTenantId(Long tenantId);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.Tenant" +
            "(t.id, t.tenantLogin, t.tenantName, t.iconpic, t.description, t.website, t.tenantDomain, t.slug ) FROM #{#entityName} t " +
            " where t.tenantLogin=:tenantLogin")
    Tenant findByTenantLoginForPublic(String tenantLogin);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.Tenant" +
            "(t.id, t.tenantLogin, t.tenantName, t.iconpic, t.description, t.website, t.tenantDomain, t.slug ) FROM #{#entityName} t " +
            " where t.id=:tenantId")
    Optional<Tenant> findByTenantIdForPublic(Long tenantId);

    boolean existsByTenantDomain(String domain);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.Tenant" +
            "(t.id, t.tenantLogin, t.tenantName, t.iconpic, t.description, t.website, t.tenantDomain, t.slug ) " +
            "FROM #{#entityName} t  where t.mobile=:mobile")
    List<Tenant> findByTenantMobile(String mobile);

    @Query("FROM  #{#entityName} t WHERE t.slug=:slug")
    Optional<Tenant> findByTenantSlug(String slug);


    @Query("SELECT new com.platformcommons.platform.service.iam.domain.Tenant" +
            "(t.id, t.tenantLogin, t.tenantName, t.iconpic, t.description, t.website, t.tenantDomain, t.slug ) " +
            " FROM #{#entityName} t " +
            " where t.tenantLogin= :tenantName")
    Optional<Tenant> findByTenantIdForPublic(String tenantName);


    @Query("select case when count(e)> 0 then true else false end from #{#entityName} e where e.tenantLogin = ?1")
    boolean existsByTenantLogin(String tenantLogin);

    @Query(
            "SELECT COUNT(t) > 0 " +
                    "FROM #{#entityName} t " +
                    "WHERE t.slug = :slug AND t.id != :tenantId"
    )
    Boolean existsByTenantSlug(String slug, Long tenantId);
}
