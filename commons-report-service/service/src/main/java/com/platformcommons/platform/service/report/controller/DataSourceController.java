package com.platformcommons.platform.service.report.controller;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.client.DataSourceAPI;
import com.platformcommons.platform.service.report.dto.DataSourceDTO;
import com.platformcommons.platform.service.report.facade.DataSourceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "DataSource")
public class DataSourceController implements DataSourceAPI {

    @Autowired
    private DataSourceFacade dataSourceFacade;


    @Override
    public ResponseEntity<Long> addDataSource(DataSourceDTO body) {
        PlatformSecurityUtil.validatePlatformAdmin();
        return ResponseEntity.ok(dataSourceFacade.save(body));
    }

    @Override
    public ResponseEntity<Void> deleteDataSource(Long id, String reason) {
        PlatformSecurityUtil.validatePlatformAdmin();
        dataSourceFacade.deleteDataSource(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<DataSourceDTO> getDataSourceById(Long id) {
        return ResponseEntity.ok(dataSourceFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<DataSourceDTO>> getDataSourcePage(Integer page, Integer size, String sortBy,
                                                                    String direction) {
        return ResponseEntity.status(HttpStatus.OK).body(dataSourceFacade.getAllDataSource(page,size,sortBy,direction));
    }

    @Override
    public ResponseEntity<Void> updateDataSource(DataSourceDTO body) {
        PlatformSecurityUtil.validatePlatformAdmin();
        dataSourceFacade.update(body);
        return ResponseEntity.accepted().build();
    }

}
