package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.cache.PlatformAppAuthCacheManager;
import com.platformcommons.platform.security.filter.appkey.AppDetailsDTO;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.iam.dto.AppDetailDTO;
import com.platformcommons.platform.service.iam.facade.assembler.AppDetailsFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Tag(name = "app-key-controller")
@RequiredArgsConstructor
public class AppKeyController {

    @Autowired
    private AppDetailsFacade appDetailsFacade;

    @Autowired
    private PolicyEvaluator policyEvaluator;

    @Autowired(required = false)
    private PlatformAppAuthCacheManager platformAppAuthCacheManager;

    @GetMapping("/api/v1/app-keys")
    public ResponseEntity<AppDetailsDTO> getApp(@RequestParam("appKey") String appKey, @RequestParam("appCode") String appCode){
        return ResponseEntity.status(HttpStatus.OK).body(appDetailsFacade.getAppDetails(appKey,appCode));
    }

    @GetMapping("/api/v1/api-keys/session")
    public ResponseEntity<AppDetailDTO> getSessionByApiKey(@RequestParam("apiKey") String apiKey, @RequestParam("tenantLogin") String tenantLogin){
        AppDetailDTO appDetailDTO = appDetailsFacade.getSessionByApiKey(apiKey,tenantLogin);
        String sessionId = appDetailDTO.getSessionId();
        appDetailDTO.setSessionId(null);
        return ResponseEntity.status(HttpStatus.OK)
                .header(PlatformSecurityConstant.SESSIONID,sessionId)
                .body(appDetailDTO);
    }

    @PostMapping("/api/v1/app")
    public ResponseEntity<AppDetailDTO> postApp(@RequestBody AppDetailDTO appDetailDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(appDetailsFacade.createAppDetails(appDetailDTO));
    }

    @PatchMapping("/api/v1/app/open-apis")
    public ResponseEntity<Void> addOpenApisToAppDetails(@RequestParam Long appDetailsId,@RequestBody Set<String> openApis){
        appDetailsFacade.addOpenApisToAppDetails(appDetailsId,openApis);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/api/v1/clear")
    public ResponseEntity deleteAppKeyCache() {
        PlatformSecurityUtil.validatePlatformAdmin();
        if (null != platformAppAuthCacheManager)
            platformAppAuthCacheManager.getAPPCache().removeAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
