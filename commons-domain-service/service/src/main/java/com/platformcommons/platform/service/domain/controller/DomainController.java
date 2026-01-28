package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.dto.DomainDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.facade.DomainFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import io.swagger.annotations.ApiOperation;
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
import java.util.Map;

@RestController
@Tag(name = "Domain")
@RequestMapping("api/v1/domains")
public class DomainController   {

    @Autowired
    private DomainFacade facade;



    @ApiOperation(value = "createDomain", nickname = "createDomain", tags={ "Domain"})
    @PostMapping
    public ResponseEntity<Long> createDomain(@RequestBody DomainDTO domainDTO ,
                                             @RequestParam(value = "parentDomainCode",required = false) String parentDomainCode){
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createDomain(domainDTO,parentDomainCode));
    }

    @ApiOperation(value = "updateDomain", nickname = "updateDomain", tags={ "Domain"})
    @PatchMapping
    public ResponseEntity<Void> updateDomain(@RequestBody DomainDTO domainDTO){
        facade.update(domainDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "createDomainBatch", nickname = "createDomain", tags={ "Domain"})
    @PostMapping("/batch")
    public ResponseEntity<Void> createDomainBatch(@RequestBody List<DomainDTO> domainDTOs){
        facade.createDomainBatch(domainDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ApiOperation(value = "createDomainBatch", nickname = "createDomain", tags={ "Domain"})
    @PatchMapping("/batch")
    public ResponseEntity<Void> addDomainHierarchyBatch(@RequestParam List<Long> childDomainIds,@RequestParam Long parentDomainId){
        facade.addBatchDomainHierarchyBatch(childDomainIds,parentDomainId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ApiOperation(value = "getDomains", nickname = "getDomains", tags={ "Domain"})
    @GetMapping
    public ResponseEntity<PageDTO<DomainDTO>> getDomains(@RequestParam Integer page,
                                                         @RequestParam Integer size,
                                                         @RequestParam String context,
                                                         @RequestParam(required = false) Boolean isRoot){

        PageDTO<DomainDTO> results = facade.getDomains(page,size,context,isRoot);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "getSubDomains", nickname = "getSubDomains", tags={ "Domain"})
    @GetMapping("/sub-domains")
    public ResponseEntity<PageDTO<DomainDTO>> getSubDomains(@RequestParam Integer page,
                                                         @RequestParam Integer size,
                                                         @RequestParam String context,
                                                         @RequestParam(required = false) String sortBy,
                                                         @RequestParam(required = false) String direction,
                                                         @RequestParam(required = false) String parentDomainCode,
                                                         @RequestParam(required = false) Integer depth
                                                         ){
        PageDTO<DomainDTO> results = facade.getSubDomains(page,size,sortBy,direction,parentDomainCode,depth,context);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "getDomainsByCodes", nickname = "getDomainsByCodes", tags={ "Domain"})
    @GetMapping("/get/codes")
    public ResponseEntity<List<DomainDTO>> getByCodes(@RequestParam List<String> domainCodes,
                                                      @RequestParam(required = false) String context){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByCodes(domainCodes,context));
    }


    @ApiOperation(value = "getDomainsByCodes", nickname = "getDomainsByCodes", tags={ "Domain"})
    @GetMapping("/codes")
    public ResponseEntity<Map<String,DomainDTO>> getDomainsByCodes(@RequestParam List<String> domainCodes){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getDomainByCodes(domainCodes));
    }

    @ApiOperation(value = "Create Attachment For Domain", nickname = "createAttachmentForDomain", notes = "",
            response = AttachmentDTO.class, tags={ "Domain"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = AttachmentDTO.class) })
    @RequestMapping(value = "/attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<AttachmentDTO> createAttachmentForDomain( @NotNull @Valid @RequestParam(value="domainID",required = true) Long domainId,
                                                                    @RequestPart(value = "file",required = true) MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createAttachment(domainId,file));
    }

    @ApiOperation(value = "Get Attachments For Domain", nickname = "getAttachmentsForDomain", notes = "", response = List.class,
            tags={ "Domain"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class) })
    @RequestMapping(value = "/attachment",
            method = RequestMethod.GET)
    public ResponseEntity<List<AttachmentDTO>> getAttachmentForDomain(@NotNull @Valid @RequestParam(value = "domainId", required = true) Long domainId) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAttachments(domainId));
    }

    @ApiOperation(value = "Get all tags for given Domain Code", nickname = "getTagsForDomain",
            notes = "This API gives all the tags within the boundary of pagination. This API will return all the tags based on domain code.",
            response = TagDTO.class, responseContainer = "Set", tags={ "Domain", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagDTO.class, responseContainer = "Set"),
            @ApiResponse(code = 206, message = "Partial Content", response = TagDTO.class, responseContainer = "Set"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/tags",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<PageDTO<TagDTO>> getTagsForDomain(@NotNull @Valid @RequestParam(value = "domainCode", required = true) String domainCode,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size){
        PageDTO<TagDTO> results=facade.getTagsForDomain(domainCode,page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Upload SubDomain and UseCase Data From Excel File",  notes = "",
            response = BulkUploadRequestDTO.class, tags={ "Domain"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = BulkUploadRequestDTO.class) })
    @RequestMapping(value = "/themes-with-usecases/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<BulkUploadRequestDTO> uploadThemesWithUseCase(@RequestPart(value = "file",required = true) MultipartFile file,
                                                                                 @NotNull @Valid @RequestParam(value = "parentDomainId",required = true) Long parentDomainId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.uploadThemesWithUseCases(parentDomainId,file));
    }



    @ApiOperation(value = "Upload UseCases Data From Excel File",  notes = "",
            response = BulkUploadRequestDTO.class, tags={ "Domain"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = BulkUploadRequestDTO.class) })
    @RequestMapping(value = "/usecases/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<BulkUploadRequestDTO> uploadUseCases(@RequestPart(value = "file",required = true) MultipartFile file,
                                                                        @NotNull @Valid @RequestParam(value = "parentDomainId",required = true) Long parentDomainId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.uploadUseCases(parentDomainId,file));
    }


    @ApiOperation(value = "Upload UseCases Data From Excel File",  notes = "",
            response = BulkUploadRequestDTO.class, tags={ "Domain"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = BulkUploadRequestDTO.class) })
    @RequestMapping(value = "/usecases-sequence/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<BulkUploadRequestDTO> uploadUseCasesSequence(@RequestPart(value = "file",required = true) MultipartFile file,
                                                               @NotNull @Valid @RequestParam(value = "parentDomainId",required = true) Long parentDomainId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.updateUseCaseSequence(parentDomainId,file));
    }

    @ApiOperation(value = "Upload SubDomain Data From Excel File",  notes = "",
            response = BulkUploadRequestDTO.class, tags={ "Domain"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = BulkUploadRequestDTO.class) })
    @RequestMapping(value = "/themes/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<BulkUploadRequestDTO> uploadThemes(@RequestPart(value = "file",required = true) MultipartFile file,
                                                               @NotNull @Valid @RequestParam(value = "parentDomainId",required = true) Long parentDomainId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.uploadThemes(parentDomainId,file));
    }
}
