package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.person.dto.IeInfoVO;
import com.platformcommons.platform.service.profile.client.IeAPI;
import com.platformcommons.platform.service.profile.domain.Ie;
import com.platformcommons.platform.service.profile.dto.IeDTO;
import com.platformcommons.platform.service.profile.facade.IeFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@Tag(name = "Ie")
public class IeController implements IeAPI {

    @Autowired
    private IeFacade facade;

    @Override
    public ResponseEntity<Void> deleteIe(Long id, String reason) {
        facade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<IeDTO> getIe(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<IeDTO>> getIePage(Integer page, Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAllPage(page,size));
    }

    @Override
    public ResponseEntity<IeDTO> patchIe(IeDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdate(body));
    }

    @Override
    public ResponseEntity<IeDTO> postIe(IeDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }

    @Override
    public ResponseEntity<IeDTO> putIe(IeDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(body));
    }

    @ApiOperation(value = "Get Ie by worknode and workforce", nickname = "getIeByFilter", notes = "", response = IeDTO.class, responseContainer = "Set", tags={ "Ie", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IeDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/ie/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<IeDTO>> getIeByFilter(@Valid @RequestParam(value = "worknodeIds", required = false) Set<Long> worknodeIds,
                                                    @Valid @RequestParam(value = "workforceIds", required = false) Set<Long> workforceIds,
                                                    @NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                    @NotNull @Valid @RequestParam(value = "size", required = true) Integer size,
                                                    @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
                                                    @Valid @RequestParam(value = "direction", required = false) String direction,
                                                    @RequestParam(value = "taggedToServiceAreaCodes", required = false) Set<String> taggedToServiceAreaCodes,
                                                    @RequestParam(value = "taggedToServiceAreaType", required = false) String taggedToServiceAreaType) {
        if (isSetAbsent(workforceIds) && isSetAbsent(worknodeIds))
            throw new InvalidInputException("either one of worknodeIds or workforceIds should be present");

        boolean hasCodes = taggedToServiceAreaCodes != null && !taggedToServiceAreaCodes.isEmpty();
        boolean hasType = taggedToServiceAreaType != null && !taggedToServiceAreaType.isEmpty();
        if (hasCodes ^ hasType) // XOR → only one is present
            throw new IllegalArgumentException("Both taggedToServiceAreaCodes and taggedToServiceAreaType must be provided together.");

        PageDTO<IeDTO> result=facade.getIeByWorkforce(worknodeIds,workforceIds,taggedToServiceAreaCodes,taggedToServiceAreaType, page,size,sortBy,direction);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }

    private boolean isSetAbsent(Set<Long> set) {
        return Optional.ofNullable(set).map(Set::isEmpty).orElse(true);
    }

    @ApiOperation(value = "Get Info of Ie By Id", nickname = "getInfoOfIe", notes = "", response = IeInfoVO.class, tags={ "Ie", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IeInfoVO.class) })
    @RequestMapping(value = "/api/v1/ie/info",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<IeInfoVO> getInfoOfIe(@NotNull @Valid @RequestParam(value = "id", required = true) Long id){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getInfoOfIe(id));
    }


    @ApiOperation(value = "upload QrCode for ie", nickname = "uploadQrCodeForIe", notes = "",tags={ "Ie", })
    @RequestMapping(value = "/api/v1/ie/qr-code",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<AttachmentDTO> uploadQrCodeForIe(@NotNull @Valid @RequestParam(value = "ieId", required = true) Long ieId,
                                                    @RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(facade.uploadQrCodeForIe(ieId,file));
    }

    @ApiOperation(value = "Generate Id Card by ID", nickname = "generateIdCardById", notes = "Generates a IdCard from the template data based on the provided ID", tags = { "Ie", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v1/ie/id-card/{id}",
            method = RequestMethod.POST)
    public ResponseEntity<Void> generatePdfById(@PathVariable Long id) throws IOException {
        facade.generateIdCard(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @ApiOperation(value = "patchIEDataOfExcelUploadAPI", notes = "Patches incorrect data loaded by Excel API", tags = { "Ie", })
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content") })
    @PatchMapping(value = "/api/v1/ie/ie-excel-upload")
    public ResponseEntity<Void> patchIEDataOfExcelUploadAPI(@RequestParam(required = false) Integer startPage,
                                                            @RequestParam(required = false) Integer endPage,
                                                            @RequestParam(required = false) Integer size) {
        if(!PlatformSecurityUtil.isTenantAdmin()) throw new UnAuthorizedAccessException("Allowed for tenant admins only.");
        facade.patchIEDataOfExcelUploadAPI(startPage,endPage,size);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Generate Id Card for multiple Ie", nickname = "generateIdCardForMultipleIe",
            notes = "Generates IdCards from the template data based on the provided IDs", tags = { "Ie" })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content")
    })
    @RequestMapping(value = "/api/v1/ie/id-cards",
            method = RequestMethod.POST
            )
    public ResponseEntity<Void> generatePdfForMultipleIds(@RequestBody Set<Long> ids) throws IOException {
        facade.generateIdCards(ids);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Download Combined Id Cards as PDF for Multiple Ie", nickname = "downloadCombinedIdCardForMultipleIe",
            notes = "Generates and combines IdCards into a single PDF based on the provided IDs", tags = { "Ie" })
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/api/v1/ie/id-cards/combined-pdf",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadCombinedPdfForMultipleIds(@RequestBody Set<Long> ids) throws IOException {
        byte[] combinedPdf = facade.downloadCombinedPdfForMultipleIds(ids);
        if (combinedPdf == null || combinedPdf.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String dynamicFilename = "Combined_IdCards_" + System.currentTimeMillis() + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dynamicFilename + "\"")
                .body(combinedPdf);
    }
}
