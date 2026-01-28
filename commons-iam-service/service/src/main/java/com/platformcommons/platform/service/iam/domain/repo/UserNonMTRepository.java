package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserNonMTRepository extends NonMultiTenantBaseRepository<User, Long> {

    @Query("select u from User u " +
            " left join fetch u.userContacts userContacts " +
            " left join fetch userContacts.contact contact " +
            " where userContacts.primaryContact = true and " +
            " u.tenant.tenantLogin = ?1 and " +
            " userContacts.contact.contactValue = ?2")
    List<User> getUserWithContact(String tenantLogin, String contactValue);


    @Query("SELECT u FROM User u " +
            "WHERE u.userLogin = :userLogin AND u.tenant.tenantLogin = :tenantLogin")
    Optional<User> findByUserLoginAndTenant_TenantLogin(String userLogin, String tenantLogin);

    @Query("select u from User u  where u.id = ?1 and u.tenant.tenantLogin = ?2")
    Optional<User> findByIdAndTenant_TenantLogin(Long id, String tenantLogin);

    @Query(nativeQuery = true,value = "select t.id as tenantId, " +
                                      "       u.id as userId " +
                                      " from user u " +
                                      " join tenant t on u.tenant = t.id " +
                                      " where u.user_login='admin' and t.tenant_login= :tenantName")
    List<Map<String, Number>> getAdminId(@Param("tenantName") String tenantName);

    Optional<User> findUserByUserLoginAndTenant_TenantLogin(String userLogin, String tenantTenantLogin);

    @Query("SELECT (count(u)>0) FROM User u WHERE u.id = :id")
    boolean existsUserById(Long id);

    @Query("SELECT (count(u)>0) FROM User u WHERE u.userLogin = :login and u.tenant.tenantLogin= :tenantLogin")
    boolean existsUserByLoginAndTenantLogin(String login, String tenantLogin);

    @Query("SELECT COUNT(u) > 0 FROM User u " +
            "WHERE EXISTS (" +
            "    SELECT 1 FROM UserContact uc " +
            "    JOIN uc.contact c " +
            "    WHERE uc.user = u " +
            "    AND uc.primaryContact = true " +
            "    AND u.tenant.tenantLogin = :tenantLogin " +
            "    AND c.contactValue = :contact" +
            ")")
    boolean existsUserByContactAndTenantLogin(String contact, String tenantLogin);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.User" +
            "(u.id,status) FROM #{#entityName} u " +
            " where u.userLogin=:userLogin AND u.tenant.id=:tenantId")
    Optional<User> getUserReferenceByLoginAndTenantId(String userLogin, Long tenantId);

    @Query("SELECT (count(u)>0) FROM User u WHERE u.userLogin = :login AND tenant.tenantLogin=:tenantLogin ")
    boolean existByLoginAndTenantLogin(String login, String tenantLogin);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.User" +
            "(u.id,status) FROM #{#entityName} u " +
            " where u.userLogin=:login AND u.tenant.tenantLogin=:tenantLogin")
    Optional<User> getUserReferenceByLoginAndTenantLogin(String login, String tenantLogin);
}