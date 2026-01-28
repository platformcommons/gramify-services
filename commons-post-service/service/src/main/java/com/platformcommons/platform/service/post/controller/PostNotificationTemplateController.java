package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.post.client.PostNotificationTemplateAPI;
import com.platformcommons.platform.service.post.dto.PostNotificationTemplateDTO;
import com.platformcommons.platform.service.post.facade.PostNotificationTemplateFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name="PostNotificationTemplate")
public class PostNotificationTemplateController implements PostNotificationTemplateAPI {

    @Autowired
    private PostNotificationTemplateFacade facade;

    @Override
    public ResponseEntity<Long> createDefaultPostNotificationTemplate(PostNotificationTemplateDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createDefaultPostNotificationTemplate(body));
    }

    @Override
    public ResponseEntity<Long> createPostNotificationTemplate(PostNotificationTemplateDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createPostNotificationTemplate(body));
    }

    @Override
    public ResponseEntity<PostNotificationTemplateDTO> getForLoggedInTenant() {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getForLoggedInTenant());
    }

    @Override
    public ResponseEntity<PostNotificationTemplateDTO> patchUpdate(PostNotificationTemplateDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdate(body));
    }


}
