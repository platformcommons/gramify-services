package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
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
public class TLDRestTemplate {

    @Value("${commons.platform.tld.base-url}")
    private  String tldBaseURL;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<UserDTO> patchUser(UserDTO userDTO){
        String patchURL= tldBaseURL+"ctld/api/tenant/user/v1";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(PlatformSecurityConstant.SESSIONID, Collections.singletonList(PlatformSecurityUtil.getToken()));
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO,httpHeaders);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate.exchange(patchURL, HttpMethod.PATCH,request,UserDTO.class);
    }

    public ResponseEntity<com.mindtree.bridge.platform.dto.UserDTO> patchBridgeUser(com.mindtree.bridge.platform.dto.UserDTO userDTO){
        String patchURL= tldBaseURL+"ctld/api/tenant/user/v1";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(PlatformSecurityConstant.SESSIONID, Collections.singletonList(PlatformSecurityUtil.getToken()));
        HttpEntity<com.mindtree.bridge.platform.dto.UserDTO> request = new HttpEntity<>(userDTO,httpHeaders);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate.exchange(patchURL, HttpMethod.PATCH,request,com.mindtree.bridge.platform.dto.UserDTO.class);
    }

}
