package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.post.client.PostTenantConfigAPI;
import com.platformcommons.platform.service.post.dto.PostTenantConfigDTO;
import com.platformcommons.platform.service.post.facade.PostTenantConfigFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "PostTenantConfig")
public class PostTenantConfigController implements PostTenantConfigAPI {

    @Autowired
    private PostTenantConfigFacade facade;

    @Override
    public ResponseEntity<PostTenantConfigDTO> getByTenantLogin(String tenantLogin) {
        PostTenantConfigDTO postTenantConfigDTO = facade.getByTenantLogin(tenantLogin);
        return ResponseEntity.status(postTenantConfigDTO == null ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(postTenantConfigDTO);
    }

    public ResponseEntity<PostTenantConfigDTO> getForLoggedInTenant() {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getForLoggedInTenant());
    }

    @Override
    public ResponseEntity<PostTenantConfigDTO> patchUpdate(PostTenantConfigDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdate(body));
    }

    @Override
    public ResponseEntity<PostTenantConfigDTO> save(PostTenantConfigDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.save(body));
    }

}
