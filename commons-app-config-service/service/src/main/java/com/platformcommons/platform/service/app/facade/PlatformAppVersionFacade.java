package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.dto.PlatformAppVersionDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PlatformAppVersionFacade {

    Long save(PlatformAppVersionDTO platformAppVersionDTO);

    PlatformAppVersionDTO update(PlatformAppVersionDTO platformAppVersionDTO);

    PageDTO<PlatformAppVersionDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PlatformAppVersionDTO getById(Long id);

    PlatformAppVersionDTO getPlatformAppVersionLatest(Platform platform, String identifier);
}
