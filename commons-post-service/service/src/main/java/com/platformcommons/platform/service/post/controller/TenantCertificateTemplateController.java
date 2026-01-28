package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.client.TenantCertificateTemplateAPI;
import com.platformcommons.platform.service.post.dto.TenantCertificateTemplateDTO;
import com.platformcommons.platform.service.post.facade.TenantCertificateTemplateFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Tag(name = "TenantCertificateTemplate")
public class TenantCertificateTemplateController implements TenantCertificateTemplateAPI {

    @Autowired
    private TenantCertificateTemplateFacade facade;

    @Override
    public ResponseEntity<Long> postCertificateTemplateForLoggedInTenant(TenantCertificateTemplateDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.postCertificateTemplate(body));
    }

    @Override
    public ResponseEntity<TenantCertificateTemplateDTO> patchUpdate(TenantCertificateTemplateDTO body){
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdate(body));
    }

    @Override
    public ResponseEntity<PageDTO<TenantCertificateTemplateDTO>> getCertificateTemplatesForLoggedInTenant(Integer page, Integer size,
                                                                                                          String sortBy, String direction) {
        PageDTO<TenantCertificateTemplateDTO> list = facade.getCertificateTemplatesForLoggedInTenant(page,size,sortBy,direction);
        return ResponseEntity.status(list.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(list);
    }


}
