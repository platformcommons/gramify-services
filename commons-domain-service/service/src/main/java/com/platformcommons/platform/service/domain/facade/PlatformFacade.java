package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.Platform;
import com.platformcommons.platform.service.domain.dto.PlatformDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PlatformFacade {

    Long save(PlatformDTO platformDTO );

    PlatformDTO update(PlatformDTO platformDTO );

    PageDTO<PlatformDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PlatformDTO getById(Long id);


}
