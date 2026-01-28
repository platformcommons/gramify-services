package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.app.domain.App;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppRepository extends NonMultiTenantBaseRepository<App, Long> {

    @Query("From #{#entityName} a where a.code = :appCode")
    App findByAppCode(String appCode);

}