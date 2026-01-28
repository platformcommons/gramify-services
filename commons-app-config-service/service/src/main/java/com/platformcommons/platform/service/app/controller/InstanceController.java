package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.InstanceAPI;
import com.platformcommons.platform.service.app.dto.InstanceDTO;
import com.platformcommons.platform.service.app.facade.InstanceFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Instance")
public class InstanceController implements InstanceAPI {

    @Autowired
    private InstanceFacade instanceFacade;

    @Override
    public ResponseEntity<Void> deleteInstance(Long id, String reason) {
        return null;
    }

    @Override
    public ResponseEntity<InstanceDTO> getInstance(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(instanceFacade.getInstanceById(id));
    }

    @Override
    public ResponseEntity<PageDTO<InstanceDTO>> getInstancePage(Integer page, Integer size) {
        PageDTO<InstanceDTO> results = instanceFacade.getAllInstances(page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<Long> postInstance(InstanceDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instanceFacade.createInstance(body));
    }

    @Override
    public ResponseEntity<InstanceDTO> putInstance(InstanceDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(instanceFacade.updateInstance(body));
    }
}
