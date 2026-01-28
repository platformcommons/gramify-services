package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.iam.dto.AuthorityDTO;
import com.platformcommons.platform.service.iam.dto.PageAuthDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaMasterDTO;
import com.platformcommons.platform.service.iam.facade.AuthorityFacade;
import com.platformcommons.platform.service.iam.facade.TenantMetaMasterFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/platform")
@Slf4j
@Tag(name = "platform-controller")
@RequiredArgsConstructor
public class PlatformController {

    private final AuthorityFacade authorityFacade;
    private final TenantMetaMasterFacade tenantMetaMasterFacade;

    @PostMapping("/authorities")
    public ResponseEntity<Void> addAuthorities(@RequestBody Set<AuthorityDTO> authorities) {
        log.info("AuthorityController.addAuthorities() .. {}", authorities);
        authorityFacade.addAuthorities(authorities);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/tenant-meta-masters")
    public ResponseEntity<Void> addMetaMasters(@RequestBody Set<TenantMetaMasterDTO> tenantMetaMasterDTOS) {
        tenantMetaMasterFacade.addMetaMasters(tenantMetaMasterDTOS);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
