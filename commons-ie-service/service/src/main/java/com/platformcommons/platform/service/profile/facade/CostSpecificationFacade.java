package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.dto.CostSpecificationDTO;

public interface CostSpecificationFacade {
    void delete(Long id, String reason);

    CostSpecificationDTO getById(Long id);

    PageDTO<CostSpecificationDTO> getAllPage(Integer page, Integer size);

    CostSpecificationDTO save(CostSpecificationDTO body);

    CostSpecificationDTO update(CostSpecificationDTO body);

    CostSpecificationDTO patchCostSpecification(CostSpecificationDTO body);
}
