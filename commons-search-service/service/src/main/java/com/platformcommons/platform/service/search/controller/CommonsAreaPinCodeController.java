package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.client.CommonsAreaPinCodeAPI;
import com.platformcommons.platform.service.search.dto.CommonsAreaPinCodeDTO;
import com.platformcommons.platform.service.search.facade.CommonsAreaPinCodeFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "CommonsAreaPinCode")
public class CommonsAreaPinCodeController implements CommonsAreaPinCodeAPI {

    @Autowired
    private CommonsAreaPinCodeFacade facade;

    @Override
    public ResponseEntity<PageDTO<CommonsAreaPinCodeDTO>> readCommonsAreaPinCode(String keyword) {
        PageDTO<CommonsAreaPinCodeDTO> result = facade.readPincode(keyword);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }


    @Override
    public ResponseEntity<CommonsAreaPinCodeDTO> saveCommonsAreaPinCode(CommonsAreaPinCodeDTO body) {
        return ResponseEntity.ok(facade.save(body));
    }

    @Override
    public ResponseEntity<CommonsAreaPinCodeDTO> updateCommonsAreaPinCode(CommonsAreaPinCodeDTO body) {
        return ResponseEntity.ok(facade.update(body));
    }
}
