package com.platformcommons.platform.service.iam.facade.client;


import com.platformcommons.platform.service.iam.dto.UserSyncCustomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class CustomRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Object> produceUserUpdateChangesToTenantCustomEndPoint(UserSyncCustomDTO userSyncCustomDTO, String completeUrl, String authorizationKey,
                                                                         String authorizationValue){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(authorizationKey, Collections.singletonList(authorizationValue));
        HttpEntity<UserSyncCustomDTO> request = new HttpEntity<>(userSyncCustomDTO,httpHeaders);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate.exchange(completeUrl, HttpMethod.POST,request,Object.class);
    }

}
