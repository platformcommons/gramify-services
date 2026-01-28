package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.iam.dto.UserVerificationDTO;
import com.platformcommons.platform.service.iam.facade.UserVerificationFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@Tag(name = "user-verification-controller")
public class UserVerificationController {

    @Autowired
    private UserVerificationFacade facade;

    @PatchMapping("/api/v1/user-verification/status")
    ResponseEntity<Void> userVerificationStatusUpdate(@Valid @NonNull @RequestBody UserVerificationDTO userVerificationDTO,
                                                      @RequestParam(name = "deleteAllAssignedRoles",required = false) Boolean deleteAllAssignedRoles) {
        facade.userVerificationStatusUpdate(userVerificationDTO,deleteAllAssignedRoles);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
