package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppAPI;
import com.platformcommons.platform.service.app.dto.AppDTO;
import com.platformcommons.platform.service.app.dto.InstanceDTO;
import com.platformcommons.platform.service.app.facade.AppFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Tag(name = "App")
public class AppController implements AppAPI {

    @Autowired
    private AppFacade appFacade;


    @Override
    public ResponseEntity<Void> deleteApps(Long id, String reason) {
        appFacade.delete(id,reason);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<AppDTO> getApps(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(appFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<AppDTO>> getAppsPage(Integer page, Integer size) {
        PageDTO<AppDTO> results= appFacade.getAllPage(page-1,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);

    }

    @Override
    public ResponseEntity<Long> postApps(AppDTO appDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appFacade.save(appDTO));
    }

    @Override
    public ResponseEntity<AppDTO> putApps(AppDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appFacade.update(body));
    }

    @ApiOperation(value = "addInstanceForApp", nickname = "addInstanceForApp", tags={ "App", })
    @RequestMapping(value = "/api/v1/apps/{appId}/instances/{instanceId}",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<Void> addInstanceForApp(@PathVariable Long appId, @PathVariable Long instanceId){
        appFacade.addInstanceForApp(appId,instanceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "getAvailableInstancesForApp", nickname = "getAvailableInstancesForApp", tags={ "App", })
    @RequestMapping(value = "/api/v1/apps/{appCode}/instances",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Set<InstanceDTO>> getAvailableInstancesForApp(@PathVariable String appCode) {
        return ResponseEntity.status(HttpStatus.OK).body(appFacade.getAvailableInstancesForApp(appCode));
    }

}
