package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.Feature;
import com.platformcommons.platform.service.domain.dto.FeatureDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface FeatureFacade {

    Long save(FeatureDTO featureDTO );

    FeatureDTO update(FeatureDTO featureDTO );

    PageDTO<FeatureDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    FeatureDTO getById(Long id);


}
