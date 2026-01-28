package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.DefaultOptions;

import java.util.Set;

public interface DefaultOptionsService {
    Set<DefaultOptions> getDefaultOptionsIds(Set<Long> defaultOptionsIds);
}
