package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.security.cache.PlatformAppAuthCacheManager;
import com.platformcommons.platform.security.filter.appkey.AppDetailsDTO;
import com.platformcommons.platform.service.iam.application.AppDetailsService;
import com.platformcommons.platform.service.iam.application.utility.OBOSecurityUtil;
import com.platformcommons.platform.service.iam.domain.AppDetail;
import com.platformcommons.platform.service.iam.dto.AppDetailDTO;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.facade.assembler.AppDetailsFacade;
import com.platformcommons.platform.service.iam.facade.assembler.AppDetailDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.obo.AppDetailDTOIamAssembler;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Transactional
public class AppDetailsFacadeImpl implements AppDetailsFacade {

    @Autowired
    private AppDetailsService appDetailsService;

    @Autowired
    private AppDetailDTOAssembler assembler;

    @Autowired
    private AppDetailDTOIamAssembler appDetailDTOIamAssembler;

    @Autowired
    private OBOSecurityUtil oboSecurityUtil;

    @Autowired(required = false)
    private PlatformAppAuthCacheManager platformAppAuthCacheManager;


    @Override
    public AppDetailDTO createAppDetails(AppDetailDTO appDetailDTO) {
        return appDetailDTOIamAssembler.toDTO(appDetailsService.createAppDetails(appDetailDTOIamAssembler.fromDTO(appDetailDTO)));
    }

    @Override
    public AppDetailsDTO getAppDetails(String appKey, String appCode) {
        return assembler.toDTO(appDetailsService.getAppDetails(appKey,appCode));
    }

    @Override
    public void addOpenApisToAppDetails(Long appDetailsId, Set<String> openApis) {
        AppDetail appDetail =appDetailsService.addOpenApisToAppDetails(appDetailsId,openApis);
        String appKey = appDetail.getAppKey();
        String appCode = appDetail.getAppCode();
        if (null != platformAppAuthCacheManager)
            platformAppAuthCacheManager.getAPPCache().remove(appKey+appCode);
    }

    @Override
    public AppDetailDTO getSessionByApiKey(String apiKey, String tenantLogin) {
        AppDetail appDetail =  appDetailsService.getSessionByApiKey(apiKey,tenantLogin);
        String sessionId = oboSecurityUtil.getSessionIdFromLoginDetails(appDetail.getSupportUserLogin(),appDetail.getSupportUserPassword(),
                appDetail.getTenantLogin(),null,null,null,null);
        AppDetailDTO appDetailDTO = appDetailDTOIamAssembler.toDTO(appDetailsService.getSessionByApiKey(apiKey,tenantLogin));
        appDetailDTO.setSessionId(sessionId);
        return appDetailDTO;
    }
}
