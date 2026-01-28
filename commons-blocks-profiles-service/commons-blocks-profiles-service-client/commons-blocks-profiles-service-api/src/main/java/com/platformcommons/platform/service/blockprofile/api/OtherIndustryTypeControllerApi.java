package com.platformcommons.platform.service.blockprofile.api;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.OtherIndustryTypeDTO;

/**
 * API contract for OtherIndustryTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/other-industry-types")
@Tag(name = "OtherIndustryType", description = "Operations for OtherIndustryType that delegate to OtherIndustryTypeFacade")
public interface OtherIndustryTypeControllerApi {

    @Operation(summary = "Create OtherIndustryType (delegates to OtherIndustryTypeFacade.save)", tags = {"OtherIndustryType"},
            description = "Creates a new resource using OtherIndustryTypeFacade.save with a OtherIndustryTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "OtherIndustryType payload used for creation", required = true)
            @RequestBody OtherIndustryTypeDTO OtherIndustryTypeDTO);

    @Operation(summary = "Update OtherIndustryType (delegates to OtherIndustryTypeFacade.update)", tags = {"OtherIndustryType"},
            description = "Updates a resource using OtherIndustryTypeFacade.update with a OtherIndustryTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = OtherIndustryTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OtherIndustryTypeDTO> update(
            @Parameter(description = "OtherIndustryType payload used for update", required = true)
            @RequestBody OtherIndustryTypeDTO OtherIndustryTypeDTO);

    @Operation(summary = "Get OtherIndustryTypes page (delegates to OtherIndustryTypeFacade.getAllPage)", tags = {"OtherIndustryType"},
            description = "Returns a paginated list using OtherIndustryTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<OtherIndustryTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get OtherIndustryType by ID (delegates to OtherIndustryTypeFacade.getById)" , tags = {"OtherIndustryType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = OtherIndustryTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<OtherIndustryTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete OtherIndustryType (delegates to OtherIndustryTypeFacade.delete)" , tags = {"OtherIndustryType"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Deleted", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Reason for deletion")
            @RequestParam(required = false) String reason);

    @Operation(summary = "Get OtherIndustryType by IDs (delegates to OtherIndustryTypeFacade.getAllByIds)" , tags = {"OtherIndustryType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<OtherIndustryTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
