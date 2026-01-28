package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.LeadOTPResponseDTO;
import com.platformcommons.platform.service.iam.dto.OTPResponse;
import com.platformcommons.platform.service.iam.facade.LeadFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

@RestController
@Tag(name = "lead-controller")
public class LeadController {

    @Autowired
    private LeadFacade facade;

    @ApiOperation(value = "Create a new Lead")
    @PostMapping("api/v1/lead")
    public ResponseEntity<OTPResponse> createLead(@RequestBody @Valid LeadDTO leadDTO,
                                                  @RequestParam(name = "sendEmailOTP", required = false) Boolean sendEmailOTP,
                                                  @RequestParam(name = "sendMobileOTP", required = false) Boolean sendMobileOTP) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.saveLead(leadDTO,sendEmailOTP,sendMobileOTP));
    }

    @ApiOperation(value = "Get Lead By Key")
    @GetMapping("api/v1/lead/key")
    public ResponseEntity<LeadDTO> getLeadByKey(@RequestParam String key) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getLeadByKey(key));
    }

    @ApiOperation(value = "Update Lead")
    @PatchMapping("api/v1/lead")
    public ResponseEntity<OTPResponse> patchUpdate(@RequestBody @Valid LeadDTO leadDTO,
                                                   @RequestParam(name = "sendEmailOTP", required = false) Boolean sendEmailOTP,
                                                   @RequestParam(name = "sendMobileOTP", required = false) Boolean sendMobileOTP) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdate(leadDTO,sendEmailOTP,sendMobileOTP));
    }


    @ApiOperation(value = "Resend OTP")
    @PatchMapping("api/v1/lead/otp/re-send")
    public ResponseEntity<OTPResponse> reSendOTP(@RequestParam(name = "key") String key,
                                                 @RequestParam(name = "reSendEmailOTP", required = false) Boolean reSendEmailOTP,
                                                 @RequestParam(name = "reSendMobileOTP", required = false) Boolean reSendMobileOTP) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.reSendOTP(key, reSendEmailOTP, reSendMobileOTP));
    }

    @ApiOperation(value = "Change Lead Contact And Send OTP")
    @PatchMapping("api/v1/lead/contact-change/otp")
    public ResponseEntity<LeadOTPResponseDTO> changeContactAndSendOTP(@RequestParam(name = "key") String key,
                                                                      @RequestParam(name = "newEmail", required = false) String newEmail,
                                                                      @RequestParam(name = "newMobileNumber", required = false) String newMobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.changeContactAndSendOTP(key, newEmail, newMobileNumber));
    }

    @ApiOperation(value = "Get Leads In Pending State By Email Or Mobile")
    @GetMapping("api/v1/lead/pending")
    public ResponseEntity<Set<LeadDTO>> getLeadsInPendingState(@RequestParam(name = "email", required = false) String email,
                                                               @RequestParam(name = "mobile", required = false) String mobile,
                                                               @RequestParam(name = "appContext") String appContext,
                                                               @RequestParam(name = "marketUUID",required = false) String marketUUID) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getLeadsInPendingState(email, mobile, appContext,marketUUID));
    }

    @ApiOperation(value = "Add Lead Organisation Logo", response = AttachmentDTO.class)
    @PostMapping("api/v1/lead/organisation-logo")
    public ResponseEntity<AttachmentDTO> addLeadOrganisationLogo(@RequestPart(value = "file") MultipartFile file,
                                                                 @RequestParam(value = "lead_key") String leadKey) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.addLeadOrganisationLogo(file,leadKey));
    }

}