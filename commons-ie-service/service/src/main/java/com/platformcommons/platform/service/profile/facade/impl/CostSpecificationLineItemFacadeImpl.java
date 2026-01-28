package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.application.CostSpecificationLineItemService;
import com.platformcommons.platform.service.profile.domain.CostSpecificationLineItem;
import com.platformcommons.platform.service.profile.dto.CostSpecificationLineItemDTO;
import com.platformcommons.platform.service.profile.facade.CostSpecificationLineItemFacade;
import com.platformcommons.platform.service.profile.facade.assembler.CostSpecificationLineItemDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
@Transactional
public class CostSpecificationLineItemFacadeImpl implements CostSpecificationLineItemFacade {

    @Autowired
    private CostSpecificationLineItemService costSpecificationLineItemService;

    @Autowired
    private CostSpecificationLineItemDTOAssembler costSpecificationLineItemDTOAssembler;

    @Override
    public void delete(Long id, String reason) {
        costSpecificationLineItemService.delete(id, reason);
    }

    @Override
    public CostSpecificationLineItemDTO getById(Long id) {
        return costSpecificationLineItemDTOAssembler.toDTO(costSpecificationLineItemService.getById(id));
    }

    @Override
    public PageDTO<CostSpecificationLineItemDTO> getAllPage(Integer page, Integer size) {
        Page<CostSpecificationLineItem> result = costSpecificationLineItemService.getAllPage(page, size);
        return new PageDTO<>(result.stream()
                .map(costSpecificationLineItemDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public CostSpecificationLineItemDTO update(CostSpecificationLineItemDTO body) {
        return costSpecificationLineItemDTOAssembler.toDTO(
                costSpecificationLineItemService.update(costSpecificationLineItemDTOAssembler.fromDTO(body))
        );
    }
}
