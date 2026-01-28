package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.iam.domain.UserSecurity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserSecurityRepository extends BaseRepository<UserSecurity, Long> {
    @Transactional
    @Modifying
    @Query(value = " update user_security u  " +
            " set u.failed_login_attempts = :failedLoginAttempts, " +
            "     u.is_account_locked     = :isAccountLocked, " +
            "     u.account_lock_reason   = :accountLockReason " +
            " where u.id = :id",nativeQuery = true)
    void lockAccount(Integer failedLoginAttempts, Boolean isAccountLocked, String accountLockReason, Long id);

    @Transactional
    @Modifying
    @Query(value = "update user_security u " +
                   " set u.failed_login_attempts = :failedLoginAttempts " +
                   " where u.id = :id",nativeQuery = true)
    void updateLoginAttempts(Integer failedLoginAttempts, Long id);

    @Query("select u from UserSecurity u where u.user.id = ?1")
    Optional<UserSecurity> findByUser_Id(Long id);

}
