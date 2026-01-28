package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.profile.application.CostSpecificationLineItemService;
import com.platformcommons.platform.service.profile.domain.CostSpecificationLineItem;
import com.platformcommons.platform.service.profile.domain.repo.CostSpecificationLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CostSpecificationLineItemServiceImpl implements CostSpecificationLineItemService {

    @Autowired
    private CostSpecificationLineItemRepository costSpecificationLineItemRepository;

    @Override
    public void delete(Long id, String reason) {
        CostSpecificationLineItem fetchedLineItem = costSpecificationLineItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecificationLineItem with ID %d not found", id)));
        fetchedLineItem.deactivate(reason);
        costSpecificationLineItemRepository.save(fetchedLineItem);
    }

    @Override
    public CostSpecificationLineItem update(CostSpecificationLineItem costSpecificationLineItem) {
        CostSpecificationLineItem fetchedLineItem = costSpecificationLineItemRepository.findById(costSpecificationLineItem.getId())
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecificationLineItem with ID %d not found", costSpecificationLineItem.getId())));

        fetchedLineItem.update(costSpecificationLineItem);
        return costSpecificationLineItemRepository.save(fetchedLineItem);
    }

    @Override
    public Page<CostSpecificationLineItem> getAllPage(Integer page, Integer size) {
        return costSpecificationLineItemRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public CostSpecificationLineItem getById(Long id) {
        return costSpecificationLineItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecificationLineItem with ID %d not found", id)));
    }
}
