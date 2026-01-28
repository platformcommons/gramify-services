package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.CostSpecificationMaster;
import org.springframework.data.domain.Page;

public interface CostSpecificationMasterService {
    Page<CostSpecificationMaster> getByPage(Integer page, Integer size);

    CostSpecificationMaster getById(Long id);

    void deleteById(Long id, String reason);

    CostSpecificationMaster save(CostSpecificationMaster costSpecificationMaster);

    CostSpecificationMaster update(CostSpecificationMaster costSpecificationMaster);

    Page<CostSpecificationMaster> getCostSpecificationMasterByContext(String context, String contextId, Integer page, Integer size);
}
