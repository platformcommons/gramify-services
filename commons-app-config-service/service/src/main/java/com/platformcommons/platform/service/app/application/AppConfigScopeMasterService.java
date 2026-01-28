package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppConfigScopeMaster;
import org.springframework.data.domain.Page;

public interface AppConfigScopeMasterService {

    Long save(AppConfigScopeMaster appconfigscopemaster);

    AppConfigScopeMaster update(AppConfigScopeMaster appconfigscopemaster);

    void deleteById(Long id, String reason);

    AppConfigScopeMaster getById(Long id);

    Page<AppConfigScopeMaster> getByPage(Integer page, Integer size);
}