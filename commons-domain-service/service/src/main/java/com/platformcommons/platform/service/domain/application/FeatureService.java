package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.Feature;

import org.springframework.data.domain.Page;

import java.util.*;

public interface FeatureService {

    Long save(Feature feature );

    Feature update(Feature feature );

    Page<Feature> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Feature getById(Long id);


}
