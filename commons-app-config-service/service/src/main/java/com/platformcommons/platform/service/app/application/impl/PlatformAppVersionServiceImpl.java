package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.app.application.PlatformAppVersionService;
import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.domain.PlatformAppVersion;
import com.platformcommons.platform.service.app.domain.repo.PlatformAppVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlatformAppVersionServiceImpl implements PlatformAppVersionService {

    @Autowired
    public PlatformAppVersionRepository platformAppversionRepository;


    @Override
    public Long save(PlatformAppVersion platformAppversion) {
        platformAppversion.setRelease_date(new Date());
        platformAppversion.setIsDeprecated(Boolean.FALSE);
        return platformAppversionRepository.save(platformAppversion).getId();
    }

    @Override
    public PlatformAppVersion update(PlatformAppVersion platformAppversion) {
        PlatformAppVersion fetchedPlatformAppVersion = platformAppversionRepository.findById(platformAppversion.getId())
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
        return platformAppversionRepository.save(platformAppversion);
    }

    @Override
    public void deleteById(Long id, String reason) {
        PlatformAppVersion fetchedPlatformAppVersion = platformAppversionRepository.findById(id)
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
       // fetchedAppVersion.deactivate(reason);
        platformAppversionRepository.save(fetchedPlatformAppVersion);
    }

    @Override
    public PlatformAppVersion getById(Long id) {
        return platformAppversionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Request id  not found"));
    }

    @Override
    public Page<PlatformAppVersion> getByPage(Integer page, Integer size) {
        return platformAppversionRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public PlatformAppVersion getPlatformAppVersionLatest(Platform platform, String identifier) {
        return platformAppversionRepository.findLatestByPlatformAndIdentifier(platform,identifier)
                .orElseThrow(()-> new NotFoundException("Latest PlatformAppVersion  not found"));
    }


}