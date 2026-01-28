package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.Options;

import java.util.List;
import java.util.Set;

public interface OptionsService {
    List<Options> getByIds(Set<Long> options);
}
