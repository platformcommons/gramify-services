package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.CostSpecificationLineItem;
import org.springframework.data.domain.Page;

public interface CostSpecificationLineItemService {
    void delete(Long id, String reason);

    CostSpecificationLineItem update(CostSpecificationLineItem costSpecificationLineItem);

    Page<CostSpecificationLineItem> getAllPage(Integer page, Integer size);

    CostSpecificationLineItem getById(Long id);
}
