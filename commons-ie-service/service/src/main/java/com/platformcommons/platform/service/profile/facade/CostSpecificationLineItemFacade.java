package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.dto.CostSpecificationLineItemDTO;

public interface CostSpecificationLineItemFacade {
    void delete(Long id, String reason);

    CostSpecificationLineItemDTO getById(Long id);

    PageDTO<CostSpecificationLineItemDTO> getAllPage(Integer page, Integer size);

    CostSpecificationLineItemDTO update(CostSpecificationLineItemDTO body);
}
