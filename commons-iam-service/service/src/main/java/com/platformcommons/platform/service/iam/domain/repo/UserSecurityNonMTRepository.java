package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.UserSecurity;

import java.util.Optional;

public interface UserSecurityNonMTRepository extends NonMultiTenantBaseRepository<UserSecurity, Long> {
    Optional<UserSecurity> findByUser_Id(Long id);
}