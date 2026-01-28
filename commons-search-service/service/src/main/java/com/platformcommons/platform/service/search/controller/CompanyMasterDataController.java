package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.AppService;
import com.platformcommons.platform.service.search.client.CompanyMasterDataAPI;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.repo.AppRepository;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataDTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.facade.AppFacade;
import com.platformcommons.platform.service.search.facade.CompanyMasterDataFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.Field;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "CompanyMasterData")
public class CompanyMasterDataController implements CompanyMasterDataAPI {

    @Autowired
    CompanyMasterDataFacade companyMasterDataFacade;


    @Override
    public ResponseEntity<PageDTO<CompanyMasterDataDTO>> readCompany(String keyword) {
        return new ResponseEntity<>(companyMasterDataFacade.readCompanyByName(keyword), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageDTO<CompanyMasterDataDTO>> readCompanyByName(String keyword) {
        return new ResponseEntity<>(companyMasterDataFacade.readCompanyByName(keyword), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CompanyMasterDataDTO> saveCompanyMasterData(CompanyMasterDataDTO body) {
        return new ResponseEntity<>(companyMasterDataFacade.saveCompany(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateCompanyMasterData(CompanyMasterDataDTO body) {
        return new ResponseEntity<>(companyMasterDataFacade.updateCompany(body), HttpStatus.OK);
    }
}
