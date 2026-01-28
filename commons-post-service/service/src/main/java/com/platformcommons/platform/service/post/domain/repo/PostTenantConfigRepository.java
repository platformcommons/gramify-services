package com.platformcommons.platform.service.post.domain.repo;


import com.platformcommons.platform.service.post.domain.PostTenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTenantConfigRepository extends JpaRepository<PostTenantConfig,Long> {

    Optional<PostTenantConfig> findByTenantLogin(String tenantLogin);
}
