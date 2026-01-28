package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.domain.Instance;
import com.platformcommons.platform.service.app.dto.InstanceDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.data.domain.Page;

public interface InstanceFacade {

    Long createInstance(InstanceDTO instanceDTO);

    PageDTO<InstanceDTO> getAllInstances(Integer page, Integer size);

    InstanceDTO getInstanceById(Long id);

    void deleteInstance(Long id);

    InstanceDTO updateInstance(InstanceDTO instanceDTO);

}
