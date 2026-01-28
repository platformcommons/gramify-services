package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.domain.vo.UseCaseVO;
import com.platformcommons.platform.service.domain.dto.DomainDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.UseCaseDTO;
import com.platformcommons.platform.service.domain.facade.UseCaseFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import io.swagger.annotations.ApiOperation;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Tag(name="UseCase")
@RequestMapping("/api/v1/use-cases")
public class UseCaseController {

    @Autowired
    private UseCaseFacade useCaseFacade;

    @ApiOperation(value = "", tags = {"UseCase"})
    @PostMapping
    public ResponseEntity<Long> createUseCase(@RequestBody UseCaseDTO useCaseDTO ){
        return ResponseEntity.status(HttpStatus.CREATED).body(useCaseFacade.save(useCaseDTO));
    }


    @ApiOperation(value = "", tags = {"UseCase"})
    @PostMapping("/batch")
    public ResponseEntity<Void> createUseCaseBatch(@RequestBody List<UseCaseDTO> useCaseDTOs ){
        useCaseFacade.createUseCaseBatch(useCaseDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "", tags = {"UseCase"})
    @PatchMapping
    public ResponseEntity<UseCaseDTO> updateUseCase(@RequestBody UseCaseDTO useCaseDTO){
        return ResponseEntity.status(HttpStatus.OK).body(useCaseFacade.update(useCaseDTO));
    }

    @ApiOperation(value = "", tags = {"UseCase"})
    @PatchMapping("/domains")
    public ResponseEntity<Void> mapUseCaseToDomains(@RequestParam Long useCaseId,@RequestBody List<String> domainCodes){
        useCaseFacade.mapUseCaseToDomains(useCaseId,domainCodes);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "", tags = {"UseCase"})
    @PatchMapping("/tags")
    public ResponseEntity<Void> mapUseCaseToTags(@RequestParam Long useCaseId,@RequestBody List<Long> tagIds){
        useCaseFacade.mapUseCaseToTags(useCaseId,tagIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "", tags = {"UseCase"})
    @PatchMapping("/apps")
    public ResponseEntity<Void> mapUseCaseToApps(@RequestParam Long useCaseId,@RequestBody List<Long> appIds){
        useCaseFacade.mapUseCaseToApps(useCaseId,appIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "", tags = {"UseCase"})
    @GetMapping("/domains")
    public ResponseEntity<PageDTO<DomainDTO>> getDomainsByUseCaseId(@RequestParam Long useCaseId,
                                                                    @RequestParam Integer page,
                                                                    @RequestParam Integer size){
        PageDTO<DomainDTO> results =useCaseFacade.getDomainsByUseCaseId(useCaseId,page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "", tags = {"UseCase"})
    @GetMapping("/ids")
    public ResponseEntity<List<UseCaseVO>> getUseCaseByIds(@RequestParam List<Long> useCaseIds){
        return ResponseEntity.status(HttpStatus.OK).body(useCaseFacade.getUseCaseByIds(useCaseIds));
    }

    @ApiOperation(value = "", tags = {"UseCase"})
    @GetMapping
    public ResponseEntity<UseCaseDTO> getUseCaseById(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(useCaseFacade.getById(id));
    }

    @ApiOperation(value = "Create Attachment For UseCase", nickname = "createAttachmentForUseCase", notes = "",
            response = AttachmentDTO.class, tags={ "UseCase"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = AttachmentDTO.class) })
    @RequestMapping(value = "/attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<AttachmentDTO> createAttachmentForUseCase( @NotNull @Valid @RequestParam(value="useCaseId",required = true) Long useCaseId,
                                                                    @RequestPart(value = "file",required = true) MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCaseFacade.createAttachment(useCaseId,file));
    }

    @ApiOperation(value = "Get Attachments For UseCase", nickname = "getAttachmentsForUseCase", notes = "", response = List.class,
            tags={ "UseCase"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class) })
    @RequestMapping(value = "/attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<List<AttachmentDTO>> getAttachmentForUseCase(@NotNull @Valid @RequestParam(value = "useCaseId", required = true) Long useCaseId) {
        return ResponseEntity.status(HttpStatus.OK).body(useCaseFacade.getAttachments(useCaseId));
    }

    @ApiOperation(value = "Get all tags for given usecase Id", nickname = "getTagsForUseCase",
            notes = "This API gives all the tags within the boundary of pagination. This API will return all the tags based on usecase id.",
            response = TagDTO.class, responseContainer = "Set", tags={ "UseCase", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagDTO.class, responseContainer = "Set"),
            @ApiResponse(code = 206, message = "Partial Content", response = TagDTO.class, responseContainer = "Set"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/tags",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<PageDTO<TagDTO>> getTagsForUseCase(@NotNull @Valid @RequestParam(value = "useCaseId", required = true) Long useCaseId,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String direction){
        PageDTO<TagDTO> results=useCaseFacade.getTagsForUseCase(useCaseId,page,size,sortBy,direction);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }
    @ApiOperation(value = "Upload use case data", nickname = "Uploads UseCase from .xls file",
            notes = "This API uploads all the use case data provided with from excel file",
            response = BulkUploadRequestDTO.class, responseContainer = "Set", tags={ "UseCase", })
    @PostMapping("/upload-usecases")
    public ResponseEntity<BulkUploadRequestDTO> uploadApplications(@RequestPart MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCaseFacade.uploadUsecase(file));
    }

}
