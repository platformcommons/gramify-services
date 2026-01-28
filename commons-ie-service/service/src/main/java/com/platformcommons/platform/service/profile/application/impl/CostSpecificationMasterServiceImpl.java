package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.profile.application.CostSpecificationMasterService;
import com.platformcommons.platform.service.profile.domain.CostSpecificationMaster;
import com.platformcommons.platform.service.profile.domain.repo.CostSpecificationMasterNonMTRepository;
import com.platformcommons.platform.service.profile.domain.repo.CostSpecificationMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CostSpecificationMasterServiceImpl implements CostSpecificationMasterService {

    @Autowired
    private CostSpecificationMasterRepository costSpecificationMasterRepository;

    @Autowired
    private CostSpecificationMasterNonMTRepository costSpecificationMasterNonMTRepository;

    @Override
    public Page<CostSpecificationMaster> getByPage(Integer page, Integer size) {
        return costSpecificationMasterRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public CostSpecificationMaster getById(Long id) {
        return costSpecificationMasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecificationMaster with ID %d not found", id)));
    }

    @Override
    public void deleteById(Long id, String reason) {
        CostSpecificationMaster fetchedCostSpecificationMaster = costSpecificationMasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecificationMaster with ID %d not found", id)));
        fetchedCostSpecificationMaster.deactivate(reason);
        costSpecificationMasterRepository.save(fetchedCostSpecificationMaster);
    }

    @Override
    public CostSpecificationMaster save(CostSpecificationMaster costSpecificationMaster) {
            return costSpecificationMasterRepository.save(costSpecificationMaster);
    }

    @Override
    public CostSpecificationMaster update(CostSpecificationMaster costSpecificationMaster) {
        CostSpecificationMaster fetchedCostSpecificationMaster = costSpecificationMasterRepository.findById(costSpecificationMaster.getId())
                .orElseThrow(() -> new NotFoundException(String.format("CostSpecificationMaster with ID %d not found", costSpecificationMaster.getId())));

        fetchedCostSpecificationMaster.update(costSpecificationMaster);
        return costSpecificationMasterRepository.save(fetchedCostSpecificationMaster);
    }

    @Override
    public Page<CostSpecificationMaster> getCostSpecificationMasterByContext(String context, String contextId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return costSpecificationMasterNonMTRepository.findByContextAndContextId(context, contextId, pageable);
    }
}
