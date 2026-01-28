package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.DomainHierarchy;
import com.platformcommons.platform.service.domain.dto.DomainHierarchyDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface DomainHierarchyFacade {

    Long save(DomainHierarchyDTO domainHierarchyDTO );

    DomainHierarchyDTO update(DomainHierarchyDTO domainHierarchyDTO );

    PageDTO<DomainHierarchyDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    DomainHierarchyDTO getById(Long id);


}
