package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.Platform;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PlatformService {

    Long save(Platform platform );

    Platform update(Platform platform );

    Page<Platform> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Platform getById(Long id);


}
