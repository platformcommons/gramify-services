package com.platformcommons.platform.service.report.facade;

import com.platformcommons.platform.service.report.domain.TenantInfo;
import com.platformcommons.platform.service.report.dto.DatasetDTO;
import com.platformcommons.platform.service.report.dto.DatasetGroupDTO;
import com.platformcommons.platform.service.report.dto.TenantInfoDTO;

public interface DatasetGroupFacade {

    Long addDatasetGroup(DatasetGroupDTO datasetGroupDTO);

    void addDatasetToGroup(Long datasetGroupId, Long datasetId);

    void addTenantInfoToDatasetGroup(Long datasetGroupId, TenantInfoDTO tenantInfoDTO);

    DatasetGroupDTO getDatasetGroupById(Long id);


    void addTenantInfoToDatasetGroupCode(String datasetGroupCode, TenantInfoDTO body);
}
