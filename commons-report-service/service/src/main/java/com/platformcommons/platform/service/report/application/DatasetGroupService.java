package com.platformcommons.platform.service.report.application;

import com.platformcommons.platform.service.report.domain.DatasetGroup;
import com.platformcommons.platform.service.report.domain.TenantInfo;

public interface DatasetGroupService {

    Long addDatasetGroup(DatasetGroup datasetGroup);

    void addDatasetToGroup(Long datasetGroupId, Long datasetId);

    void addTenantInfoToDatasetGroup(Long datasetGroupId, TenantInfo tenantInfo);

    DatasetGroup getDatasetGroupById(Long id);

    DatasetGroup getDatasetGroupByCode(String code);

    void addTenantInfoToDatasetGroupCode(String datasetGroupCode, TenantInfo fromDTO);
}
