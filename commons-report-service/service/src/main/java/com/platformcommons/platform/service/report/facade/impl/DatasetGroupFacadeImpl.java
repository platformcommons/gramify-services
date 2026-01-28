package com.platformcommons.platform.service.report.facade.impl;

import com.platformcommons.platform.service.report.application.DatasetGroupService;
import com.platformcommons.platform.service.report.dto.DatasetDTO;
import com.platformcommons.platform.service.report.dto.DatasetGroupDTO;
import com.platformcommons.platform.service.report.dto.TenantInfoDTO;
import com.platformcommons.platform.service.report.facade.DatasetGroupFacade;
import com.platformcommons.platform.service.report.facade.assembler.DatasetDTOAssembler;
import com.platformcommons.platform.service.report.facade.assembler.DatasetGroupDTOAssembler;
import com.platformcommons.platform.service.report.facade.assembler.TenantInfoDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class DatasetGroupFacadeImpl implements DatasetGroupFacade {

    @Autowired
    private DatasetDTOAssembler datasetDTOAssembler;

    @Autowired
    private DatasetGroupDTOAssembler datasetGroupDTOAssembler;

    @Autowired
    private TenantInfoDTOAssembler tenantInfoDTOAssembler;

    @Autowired
    private DatasetGroupService datasetGroupService;


    @Override
    public Long addDatasetGroup(DatasetGroupDTO datasetGroupDTO) {
        return datasetGroupService.addDatasetGroup(datasetGroupDTOAssembler.fromDTO(datasetGroupDTO));
    }

    @Override
    public void addDatasetToGroup(Long datasetGroupId, Long datasetId) {
        datasetGroupService.addDatasetToGroup(datasetGroupId, datasetId);
    }

    @Override
    public void addTenantInfoToDatasetGroup(Long datasetGroupId, TenantInfoDTO tenantInfoDTO) {
        datasetGroupService.addTenantInfoToDatasetGroup(datasetGroupId, tenantInfoDTOAssembler.fromDTO(tenantInfoDTO));
    }


    @Override
    public DatasetGroupDTO getDatasetGroupById(Long id) {
        return datasetGroupDTOAssembler.toDTO(datasetGroupService.getDatasetGroupById(id));
    }

    @Override
    public void addTenantInfoToDatasetGroupCode(String datasetGroupCode, TenantInfoDTO tenantInfoDTO) {
        datasetGroupService.addTenantInfoToDatasetGroupCode(datasetGroupCode, tenantInfoDTOAssembler.fromDTO(tenantInfoDTO));
    }
}
