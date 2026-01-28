package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.iam.dto.UserMetaDataDTO;
import com.platformcommons.platform.service.iam.facade.UserMetaDataFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@Tag(name = "user-meta-data-controller")
@Slf4j
@RequiredArgsConstructor
public class UserMetaDataController {

    private final UserMetaDataFacade facade;

    @ApiOperation(value = "Get By Meta Key In Bulk For Logged In User",  response = UserMetaDataDTO.class,responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserMetaDataDTO.class,responseContainer = "Set") })
    @GetMapping(value = "/api/v1/user-meta-data/bulk/me",
            produces = { "application/json" })
    ResponseEntity<Set<UserMetaDataDTO>> getByMetaKeyInBulkForLoggedInUser(@NotNull @Valid @RequestParam(value = "metaKeys") Set<String> metaKeys) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByMetaKeyInBulkForLoggedInUser(metaKeys));
    }


    @ApiOperation(value = "Post Or Update In Bulk For Logged In User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @PostMapping(value = "/api/v1/user-meta-data/bulk/me",
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<Void> postOrUpdateInBulkForLoggedInUser(@NotNull @Valid @RequestBody Set<UserMetaDataDTO> userMetaDataDTOS) {
        facade.postOrUpdateInBulkForLoggedInUser(userMetaDataDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
