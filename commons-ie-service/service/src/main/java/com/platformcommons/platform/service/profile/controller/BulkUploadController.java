package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.profile.facade.ExcelUploadFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "BulkUpload")
@RequiredArgsConstructor
public class BulkUploadController {

    private final ExcelUploadFacade bulkUploadFacade;

    @ApiOperation(value = "UploadIE", nickname = "UploadIE", tags = {"BulkUpload"})
    @PostMapping("/api/v1/ie/upload")
    public ResponseEntity<Void> uploadIE(@RequestPart MultipartFile file) throws Exception {
        bulkUploadFacade.uploadIE(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "UploadIE", nickname = "UploadIE", tags = {"BulkUpload"})
    @PostMapping("/api/v2/ie/upload")
    public ResponseEntity<Void> uploadIEV2(@RequestPart MultipartFile file) throws Exception {
        bulkUploadFacade.uploadIEV2(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "UploadAsset", nickname = "UploadAsset", tags = {"BulkUpload"})
    @PostMapping("/api/v1/asset/upload")
    public ResponseEntity<Void> uploadAsset(@RequestPart MultipartFile file) throws Exception {
        bulkUploadFacade.uploadAsset(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "UploadPortfolio", nickname = "UploadPortfolio", tags = {"BulkUpload"})
    @PostMapping("/api/v1/portfolio/upload")
    public ResponseEntity<Void> uploadPortfolio(@RequestPart MultipartFile file) throws Exception {
        bulkUploadFacade.uploadPortfolio(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "UploadABA", nickname = "UploadABA", tags = {"BulkUpload"})
    @PostMapping("/api/v1/aba/upload")
    public ResponseEntity<Void> uploadABA(@RequestPart MultipartFile file) throws Exception {
        bulkUploadFacade.uploadABA(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "UploadIE", nickname = "UploadIE", tags = {"BulkUpload"})
    @PostMapping("/api/v3/ie/upload")
    public ResponseEntity<Void> uploadIEV3(@RequestPart MultipartFile file) throws Exception {
        bulkUploadFacade.uploadIEV3(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}