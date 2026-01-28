package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.domain.PlatformAppVersion;
import org.springframework.data.domain.Page;

public interface PlatformAppVersionService {

    Long save(PlatformAppVersion platformAppversion);

    PlatformAppVersion update(PlatformAppVersion platformAppversion);

    void deleteById(Long id, String reason);

    PlatformAppVersion getById(Long id);

    Page<PlatformAppVersion> getByPage(Integer page, Integer size);

    PlatformAppVersion getPlatformAppVersionLatest(Platform platform, String identifier);
}