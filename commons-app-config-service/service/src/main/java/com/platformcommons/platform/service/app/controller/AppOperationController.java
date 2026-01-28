package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppOperationAPI;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.app.facade.AppOperationFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@Tag(name = "AppOperation")
public class AppOperationController implements AppOperationAPI {

    @Autowired
    private AppOperationFacade facade;

    @Override
    public ResponseEntity<Void> deleteAppOperation(Long id, String reason) {
        facade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AppOperationDTO> getAppOperation(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<AppOperationDTO>> getAppOperationPage(Integer page, Integer size) {
        PageDTO<AppOperationDTO> results = facade.getAllPage(page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<Long> postAppOperation(AppOperationDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }

    @Override
    public ResponseEntity<AppOperationDTO> putAppOperation(AppOperationDTO body, Boolean isPatch) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(body,isPatch));
    }

    @Override
    public ResponseEntity<AppOperationDTO> patchAppOperation(AppOperationDTO body, Boolean syncHigherRbacOperation, Boolean syncLowerRbacOperation) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patch(body, syncHigherRbacOperation, syncLowerRbacOperation));
    }

    @Override
    public ResponseEntity<PageDTO<AppOperationDTO>> getAppOperationByAppCodeAppRoleAndEntity(String appCode, Integer page, Integer size, String role, String entity) {
        PageDTO<AppOperationDTO> results = facade.getAppOperationByAppCodeAppRoleAndEntity(appCode,page,size,role,entity);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "update AppOperation in bulk", nickname = "updateAppOperationInBulk", notes = "", tags={ "AppOperation", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v1/app-operation/update/bulk",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateAppOperationInBulk(@Valid @RequestBody Set<AppOperationDTO> appOperationDTOS){
        facade.updateAppOperationInBulk(appOperationDTOS);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
