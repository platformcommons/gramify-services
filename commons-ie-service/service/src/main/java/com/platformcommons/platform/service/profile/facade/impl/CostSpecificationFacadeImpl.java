package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.application.CostSpecificationService;
import com.platformcommons.platform.service.profile.domain.CostSpecification;
import com.platformcommons.platform.service.profile.dto.CostSpecificationDTO;
import com.platformcommons.platform.service.profile.facade.CostSpecificationFacade;
import com.platformcommons.platform.service.profile.facade.assembler.CostSpecificationDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class CostSpecificationFacadeImpl implements CostSpecificationFacade {

    @Autowired
    private CostSpecificationService costSpecificationService;

    @Autowired
    private CostSpecificationDTOAssembler costSpecificationDTOAssembler;

    @Override
    public void delete(Long id, String reason) {
        costSpecificationService.deleteById(id, reason);
    }

    @Override
    public CostSpecificationDTO getById(Long id) {
        return costSpecificationDTOAssembler.toDTO(costSpecificationService.getById(id));
    }

    @Override
    public PageDTO<CostSpecificationDTO> getAllPage(Integer page, Integer size) {
        Page<CostSpecification> result = costSpecificationService.getByPage(page, size);
        return new PageDTO<>(result.stream()
                .map(costSpecificationDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(), result.getTotalElements());
    }

    @Override
    public CostSpecificationDTO save(CostSpecificationDTO body) {
        Optional<CostSpecification> existingCostSpecification = costSpecificationService.findByUuid(body.getUuid());
        if (existingCostSpecification.isPresent()) {
            throw new DuplicateResourceException("Duplicate Code found: " + body.getUuid());
        }
        CostSpecification savedCostSpecification = costSpecificationService.save(costSpecificationDTOAssembler.fromDTO(body));
        return costSpecificationDTOAssembler.toDTO(savedCostSpecification);
    }


    @Override
    public CostSpecificationDTO update(CostSpecificationDTO body) {
        return costSpecificationDTOAssembler.toDTO(costSpecificationService.update(costSpecificationDTOAssembler.fromDTO(body)));
    }

    @Override
    public CostSpecificationDTO patchCostSpecification(CostSpecificationDTO body) {
        return costSpecificationDTOAssembler.toDTO(costSpecificationService.patch(costSpecificationDTOAssembler.fromDTO(body)));
    }
}
