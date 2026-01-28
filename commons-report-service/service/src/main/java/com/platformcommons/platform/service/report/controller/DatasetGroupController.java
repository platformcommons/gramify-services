package com.platformcommons.platform.service.report.controller;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.report.client.DatasetGroupAPI;
import com.platformcommons.platform.service.report.dto.DatasetDTO;
import com.platformcommons.platform.service.report.dto.DatasetGroupDTO;
import com.platformcommons.platform.service.report.dto.TenantInfoDTO;
import com.platformcommons.platform.service.report.facade.DatasetGroupFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "DatasetGroup")
@RestController
public class DatasetGroupController implements DatasetGroupAPI {

    @Autowired
    private DatasetGroupFacade facade;

    @Override
    public ResponseEntity<Long> addDatasetGroup(DatasetGroupDTO body) {
        PlatformSecurityUtil.validatePlatformAdmin();
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.addDatasetGroup(body));
    }

    @Override
    public ResponseEntity<Void> addDatasetToDatasetGroup(Long datasetGroupId, Long datasetId) {
        facade.addDatasetToGroup(datasetGroupId,datasetId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> addTenantInfoToDatasetGroup(Long datasetGroupId, TenantInfoDTO body) {
        facade.addTenantInfoToDatasetGroup(datasetGroupId,body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> addTenantInfoToDatasetGroupCode(String datasetGroupCode, TenantInfoDTO body) {
        facade.addTenantInfoToDatasetGroupCode(datasetGroupCode,body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<DatasetGroupDTO> getDatasetGroup(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getDatasetGroupById(id));
    }
}
