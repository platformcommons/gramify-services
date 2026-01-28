package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.dto.CompanyMasterDataV2DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "commons-search-service${commons.service.commons-search-service.context-path:/commons-search-service}",
        contextId = "commons-search-client")
public interface CommonsSearchClient {


    @RequestMapping(value = "api/v1/companyV2/search",
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<CompanyMasterDataV2DTO>> getCompanyBySearchText(@RequestParam(value = "keyword") String searchText,
                                                                           @RequestParam(value = "size") Integer size,
                                                                           @RequestParam(value = "page") Integer page,
                                                                           @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @RequestMapping(value = "/api/v1/companyV2",
            method = RequestMethod.POST)
    ResponseEntity<CompanyMasterDataV2DTO> saveCompany(@RequestBody CompanyMasterDataV2DTO body,
                                                       @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

}
