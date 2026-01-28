package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.iam.dto.UserConsentDTO;
import com.platformcommons.platform.service.iam.facade.UserConsentFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@Tag(name = "user-consent-controller")
public class UserConsentController {

    @Autowired
    private UserConsentFacade facade;

    @ApiOperation(value = "Get By Consent Types For Logged In User",  response = UserConsentDTO.class,responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserConsentDTO.class,responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/user-consent/bulk/me",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<UserConsentDTO>> getByConsentTypesForLoggedInUser(@NotNull @Valid @RequestParam(value = "consentTypes") Set<String> consentTypes) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByConsentTypesForLoggedInUser(consentTypes));
    }


    @ApiOperation(value = "Post Or Update For Logged In User", response = UserConsentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserConsentDTO.class) })
    @RequestMapping(value = "/api/v1/user-consent/me",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<UserConsentDTO> postOrUpdateForLoggedInUser(@Valid @RequestBody UserConsentDTO userConsentDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.postOrUpdateForLoggedInUser(userConsentDTO));
    }
}
