package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.dto.EntityMetaVO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    @Query("select new com.platformcommons.platform.service.iam.dto.EntityMetaVO(userContacts.contact.contactValue,userContacts.contact.contactType) from User u inner join u.userContacts userContacts " +
            "where userContacts.primaryContact = true " +
            "     and userContacts.contact.contactValue in ?1 " +
            "     and userContacts.contact.contactType in ?2")
    List<EntityMetaVO> getContactsByContactValueAndType(Collection<String> contactValues, Collection<String> contactTypes);

    @Query("select (count(u) > 0) from User u where u.userLogin = ?1 and u.tenant.tenantLogin = ?2")
    boolean existsByUserLoginAndTenant_TenantLogin(String userLogin, String tenantLogin);

    @Query(value = "update User set is_active=true, inactive_reason=null " +
            " where id=:id and tenant=:tenantId ", nativeQuery = true)
    @Modifying
    @Transactional
    void activateUser(Long id, Long tenantId);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findUserById(Long userId);

    @Query("SELECT (count(u)>0) FROM User u WHERE u.userLogin = :login")
    boolean existsByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.userLogin = :userLogin")
    Optional<User> findByLogin(String userLogin);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.User" +
            "(u.id,status) FROM #{#entityName} u " +
            " where u.userLogin=:userLogin")
    Optional<User> getUserReferenceByLogin(String userLogin);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.User" +
            "(u.id) FROM #{#entityName} u " +
            " where u.id=:id")
    Optional<User> getUserReferenceById(Long id);
}
