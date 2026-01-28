package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.service.iam.dto.CompanyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ClearBit-Client",
        url = "https://autocomplete.clearbit.com")
public interface ClearBitClient {

    @RequestMapping(value = "/v1/companies/suggest",
            method = RequestMethod.GET)
    ResponseEntity<List<CompanyDTO>> getCompanyBySearchText(@RequestParam(value = "query") String query,
                                                            @RequestParam(value = "size") Integer size,
                                                            @RequestParam(value = "page") Integer page);
}
