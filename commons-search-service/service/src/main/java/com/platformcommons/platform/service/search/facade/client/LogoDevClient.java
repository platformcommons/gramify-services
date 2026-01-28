package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.service.search.dto.LogoDevCompanyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class LogoDevClient {

    @Value("${commons.platform.logo-dev.base-url://api.logo.dev/search}")
    private String logoDevApi;

    @Value("${commons.platform.logo-dev.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LogoDevClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LogoDevCompanyDTO> searchCompany(String searchText) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(logoDevApi)
                    .queryParam("q", searchText)
                    .queryParam("strategy", "match")
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<LogoDevCompanyDTO[]> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            LogoDevCompanyDTO[].class
                    );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (Exception ex) {
            log.error("Error calling Logo.dev API for searchText={}", searchText, ex);
        }
        return Collections.emptyList();
    }
}
