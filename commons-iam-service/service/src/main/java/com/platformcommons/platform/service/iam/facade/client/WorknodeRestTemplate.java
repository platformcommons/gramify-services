package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.worknode.dto.WorkforceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class WorknodeRestTemplate {

    @Value("${commons.platform.ms.base-url}")
    private String commonsMsBaseUrl;

    @Value("${commons.service.commons-worknode-service.context-path:/commons-worknode-service}")
    private String worknodeServiceContextPath;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<WorkforceDTO> patchWorkforce(WorkforceDTO workforceDTO){
        String patchURL= commonsMsBaseUrl + worknodeServiceContextPath + "/api/v1/workforce";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(PlatformSecurityConstant.SESSIONID, Collections.singletonList(PlatformSecurityUtil.getToken()));
        HttpEntity<WorkforceDTO> request = new HttpEntity<>(workforceDTO,httpHeaders);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate.exchange(patchURL, HttpMethod.PATCH,request,WorkforceDTO.class);
    }
}
