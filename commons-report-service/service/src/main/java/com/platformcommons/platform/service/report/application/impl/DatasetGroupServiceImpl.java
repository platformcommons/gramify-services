package com.platformcommons.platform.service.report.application.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.report.application.DatasetGroupService;
import com.platformcommons.platform.service.report.application.DatasetService;
import com.platformcommons.platform.service.report.domain.Dataset;
import com.platformcommons.platform.service.report.domain.DatasetGroup;
import com.platformcommons.platform.service.report.domain.TenantInfo;
import com.platformcommons.platform.service.report.domain.repo.DatasetGroupRepository;
import com.platformcommons.platform.service.report.domain.repo.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetGroupServiceImpl implements DatasetGroupService {

    @Autowired
    private DatasetGroupRepository datasetGroupRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DatasetService datasetService;

    @Override
    public Long addDatasetGroup(DatasetGroup datasetGroup) {
        datasetGroup.init();
        if (datasetGroup.getAllowedTenants() != null && !datasetGroup.getAllowedTenants().isEmpty()) {
            datasetGroup.getAllowedTenants().forEach(tenantInfo -> tenantInfo.init());
        }
        return datasetGroupRepository.save(datasetGroup).getId();
    }

    @Override
    public void addDatasetToGroup(Long datasetGroupId, Long datasetId) {
        DatasetGroup fetchedDatasetGroup = getDatasetGroupById(datasetGroupId);
        Dataset fetchedDataset = datasetService.getById(datasetId);
        fetchedDatasetGroup.getDatasets().add(fetchedDataset);
        datasetGroupRepository.save(fetchedDatasetGroup);
    }

    @Override
    public void addTenantInfoToDatasetGroup(Long datasetGroupId, TenantInfo tenantInfo) {
        DatasetGroup fetchedDatasetGroup = getDatasetGroupById(datasetGroupId);
        fetchedDatasetGroup.getAllowedTenants().forEach(allowedTenant -> {
            if (allowedTenant.getTenantId().equals(tenantInfo.getTenantId())) {
                throw new DuplicateResourceException(String.format("DuplicateResource found"));
            }
        });
        tenantInfo.init();
        fetchedDatasetGroup.getAllowedTenants().add(tenantInfo);
        datasetGroupRepository.save(fetchedDatasetGroup);
    }

    @Override
    public DatasetGroup getDatasetGroupById(Long id) {
        return datasetGroupRepository.findById(id).orElseThrow(()-> new NotFoundException
                (String.format("DatasetGroup not found with id %s",id)));
    }


    @Override
    public DatasetGroup getDatasetGroupByCode(String code) {
        return datasetGroupRepository.findByCode(code).orElseThrow(()-> new NotFoundException
                (String.format("DatasetGroup not found with code %s",code)));
    }


    @Override
    public void addTenantInfoToDatasetGroupCode(String datasetGroupCode, TenantInfo tenantInfo) {
        DatasetGroup fetchedDatasetGroup = this.getDatasetGroupByCode(datasetGroupCode);
        fetchedDatasetGroup.getAllowedTenants().forEach(allowedTenant -> {
            if (allowedTenant.getTenantId().equals(tenantInfo.getTenantId())) {
                throw new DuplicateResourceException(String.format("DuplicateResource found"));
            }
        });
        tenantInfo.init();
        fetchedDatasetGroup.getAllowedTenants().add(tenantInfo);
        datasetGroupRepository.save(fetchedDatasetGroup);
    }
}
