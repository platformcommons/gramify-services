package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.service.assessment.dto.MimicRefDataDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicRefDataDTOAssembler;
import com.platformcommons.platform.service.dto.commons.RefDataContext;
import com.platformcommons.platform.service.entity.common.RefData;
import com.platformcommons.platform.service.service.RefDataService;
import com.platformcommons.platform.service.utility.JPAUtility;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Api(tags = "RefData")
@RequestMapping("api/v1/wrapper/ref-data")
@RequiredArgsConstructor
public class WrapperRefDataController {

    private final RefDataService service;
    private final MimicRefDataDTOAssembler refDataDTOAssembler;
    @ResponseStatus(
            code = HttpStatus.OK
    )
    @GetMapping({"/class/{classCode}"})
    @ApiOperation("Returns queried Ref Data List Wrapping MimicRefDataDTO")
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "All RefData found for queried class code"
    ), @ApiResponse(
            code = 206,
            message = "Partial RefData found for queried class code"
    )})
    public ResponseEntity<Set<MimicRefDataDTO>> getRefDataByClass(@ApiParam(required = true,value = "Class Code for RefData")
                                                                          @PathVariable String classCode,
                                                                      @ApiParam(required = true,value = "Page index",defaultValue = "0")
                                                                          @RequestParam Integer page,
                                                                      @ApiParam(required = true,value = "Page size",defaultValue = "10")
                                                                          @RequestParam @Max(100L) Integer size,
                                                                      @ApiParam("Sorting attributes (In CSV)")
                                                                          @RequestParam(required = false) String sortBy,
                                                                      @ApiParam(value = "Sorting direction",defaultValue = "ASC",allowableValues = "ASC, DESC")
                                                                          @RequestParam(required = false) String direction,
                                                                      @ApiParam(value = "languageCode")
                                                                          @RequestParam(required = false) String languageCode,
                                                                      @ApiParam(value = "context")
                                                                          @RequestParam(required = false) RefDataContext context,
                                                                      @ApiParam(value = "contextId")
                                                                          @RequestParam(required = false) String contextId,
                                                                      @ApiParam(value = "fetchContextDataOnly",defaultValue = "false")
                                                                          @RequestParam(required = false) Boolean fetchContextDataOnly) {
        if (fetchContextDataOnly == null) fetchContextDataOnly = Boolean.FALSE;
        page--;
        Page<RefData> refDataPage = this.service.getRefDataByClass(classCode, languageCode, context, contextId, page, size, JPAUtility.convertToSort(sortBy, direction), fetchContextDataOnly);
        return new ResponseEntity<>( refDataPage.get().map(this.refDataDTOAssembler::toDTO).collect(Collectors.toSet()),refDataPage.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }

}
