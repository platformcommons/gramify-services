package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppConfigScopeMasterAPI;
import com.platformcommons.platform.service.app.dto.AppConfigScopeMasterDTO;
import com.platformcommons.platform.service.app.facade.AppConfigScopeMasterFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AppConfigScopeMaster")
public class AppConfigScopeMasterController implements AppConfigScopeMasterAPI {

    @Autowired
    private AppConfigScopeMasterFacade appConfigScopeMasterFacade;


    @Override
    public ResponseEntity<Void> deleteAppConfigScopeMaster(Long id, String reason) {
        appConfigScopeMasterFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AppConfigScopeMasterDTO> getAppConfigScopeMaster(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(appConfigScopeMasterFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<AppConfigScopeMasterDTO>> getAppConfigScopeMasterPage(Integer page, Integer size) {

        PageDTO<AppConfigScopeMasterDTO> result= appConfigScopeMasterFacade.getAllPage(page,size);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<Long> postAppConfigScopeMaster(AppConfigScopeMasterDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appConfigScopeMasterFacade.save(body));
    }

    @Override
    public ResponseEntity<AppConfigScopeMasterDTO> putAppConfigScopeMaster(AppConfigScopeMasterDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appConfigScopeMasterFacade.update(body));
    }
}
