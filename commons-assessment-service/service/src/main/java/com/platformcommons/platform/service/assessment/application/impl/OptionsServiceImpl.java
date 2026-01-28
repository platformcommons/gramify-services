package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.service.assessment.application.OptionsService;
import com.platformcommons.platform.service.assessment.domain.Options;
import com.platformcommons.platform.service.assessment.domain.repo.OptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OptionsServiceImpl implements OptionsService {

    private final OptionsRepository repository;

    @Override
    public List<Options> getByIds(Set<Long> options) {
        return repository.findAllById(options);
    }
}
