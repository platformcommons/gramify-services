package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.service.assessment.application.DefaultOptionsService;
import com.platformcommons.platform.service.assessment.domain.DefaultOptions;
import com.platformcommons.platform.service.assessment.domain.repo.DefaultOptionsRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Component
public class DefaultOptionsServiceImpl implements DefaultOptionsService {


    private final DefaultOptionsRepository defaultOptionsRepository;

    public DefaultOptionsServiceImpl(DefaultOptionsRepository defaultOptionsRepository) {
        this.defaultOptionsRepository = defaultOptionsRepository;
    }

    @Override
    public Set<DefaultOptions> getDefaultOptionsIds(Set<Long> defaultOptionsIds) {
        return defaultOptionsRepository.getByIds(defaultOptionsIds);
    }
}
