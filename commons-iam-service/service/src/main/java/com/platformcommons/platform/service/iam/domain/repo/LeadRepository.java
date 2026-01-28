package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.Lead;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeadRepository extends NonMultiTenantBaseRepository<Lead, Long> {
    @Query("FROM  #{#entityName} l WHERE l.email=:email " +
            " AND ( l.appContext = :appContext OR :appContext is NULL ) " +
            " AND ( l.activationStatus!=:activationStatus  OR  :activationStatus is null )"  +
            " AND ( l.marketContext = :marketUUID OR :marketUUID is NULL ) " )
    List<Lead> getLeadByEmail(String email,String appContext, String activationStatus,String marketUUID);

    @Query("FROM  #{#entityName} l WHERE l.mobile=:mobile " +
            " AND ( l.appContext = :appContext OR :appContext is NULL ) " +
            " AND ( l.activationStatus!=:activationStatus  OR  :activationStatus is null ) " +
            " AND ( l.marketContext = :marketUUID OR :marketUUID is NULL ) " )
    List<Lead> getLeadByMobile(String mobile, String appContext, String activationStatus,String marketUUID);

    @Query("FROM  #{#entityName} l WHERE l.key=:key AND l.activationStatus=:activationStatus AND l.email=:email ")
    Optional<Lead> getLeadByKeyAndActivationStatusAndEmail(String key, String activationStatus, String email);

    @Query("FROM  #{#entityName} l WHERE l.key=:key AND l.activationStatus=:activationStatus AND l.mobile=:mobile ")
    Optional<Lead> getLeadByKeyAndActivationStatusAndMobile(String key, String activationStatus, String mobile);

    @Query("FROM  #{#entityName} l WHERE l.key=:key AND (l.email=:primaryLogin OR l.mobile=:primaryLogin) AND l.type=:type ")
    Optional<Lead> getLeadByKeyAndTypeAndEmail(String key, String primaryLogin, String type);

    @Query("FROM  #{#entityName} l WHERE l.key=:key ")
    Optional<Lead> findByKey(String key);
}
