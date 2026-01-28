package com.platformcommons.platform.service.profile.application.impl;

import com.netflix.discovery.converters.Auto;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.profile.application.CostSpecificationService;
import com.platformcommons.platform.service.profile.domain.CostSpecification;
import com.platformcommons.platform.service.profile.domain.repo.CostSpecificationNonMTRepository;
import com.platformcommons.platform.service.profile.domain.repo.CostSpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CostSpecificationServiceImpl implements CostSpecificationService {

    @Autowired
    private CostSpecificationRepository costSpecificationRepository;

    @Autowired
    private CostSpecificationNonMTRepository costSpecificationNonMTRepository;

    @Override
    public CostSpecification save(CostSpecification costSpecification) {
        return costSpecificationRepository.save(costSpecification);
    }

    @Override
    public void deleteById(Long id, String reason) {
        CostSpecification fetchedCostSpecification = costSpecificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecification with ID %d not found", id)));
        fetchedCostSpecification.deactivate(reason);
        costSpecificationRepository.save(fetchedCostSpecification);
    }

    @Override
    public CostSpecification getById(Long id) {
        return costSpecificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecification with ID %d not found", id)));
    }

    @Override
    public Page<CostSpecification> getByPage(Integer page, Integer size) {
        return costSpecificationRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public CostSpecification update(CostSpecification costSpecification) {
        CostSpecification fetchedCostSpecification = costSpecificationRepository.findById(costSpecification.getId())
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecification with ID %d not found", costSpecification.getId())));

        fetchedCostSpecification.update(costSpecification);
        return costSpecificationRepository.save(fetchedCostSpecification);
    }

    @Override
    public Optional<CostSpecification> findByUuid(String uuid) {
        return costSpecificationNonMTRepository.findByUuid(uuid);
    }

    @Override
    public CostSpecification patch(CostSpecification costSpecification) {
        CostSpecification fetchedCostSpecification = costSpecificationRepository.findById(costSpecification.getId())
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecification with ID %d not found", costSpecification.getId())));

        fetchedCostSpecification.patch(costSpecification);
        return costSpecificationRepository.save(fetchedCostSpecification);
    }
}
