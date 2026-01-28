package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppRbacMetaAPI;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.dto.AppRbacMetaDTO;
import com.platformcommons.platform.service.app.facade.AppRbacMetaFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Tag(name = "AppRbacMeta")
public class AppRbacMetaController implements AppRbacMetaAPI {

    @Autowired
    private AppRbacMetaFacade facade;

    @Override
    public ResponseEntity<Void> addAppMenuToAppRbac(Set<AppMenuDTO> body, Long appRbacMetaId) {
        facade.addAppMenuToAppRbacMeta(appRbacMetaId,body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Long> createAppRbacMeta(AppRbacMetaDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.createAppRbacMeta(body));
    }

    @Override
    public ResponseEntity<AppRbacMetaDTO> getByAppCode(String appCode,String roleType, String entityType) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByAppCode(appCode,entityType, roleType));
    }

    @Override
    public ResponseEntity<AppRbacMetaDTO> patchAppRbacMeta(AppRbacMetaDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdate(body));
    }


}
