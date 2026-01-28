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

import com.platformcommons.platform.service.blockprofile.dto.HHEmploymentTypeDTO;

/**
 * API contract for HHEmploymentTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-employment-types")
@Tag(name = "HHEmploymentType", description = "Operations for HHEmploymentType that delegate to HHEmploymentTypeFacade")
public interface HHEmploymentTypeControllerApi {

    @Operation(summary = "Create HHEmploymentType (delegates to HHEmploymentTypeFacade.save)", tags = {"HHEmploymentType"},
            description = "Creates a new resource using HHEmploymentTypeFacade.save with a HHEmploymentTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHEmploymentType payload used for creation", required = true)
            @RequestBody HHEmploymentTypeDTO HHEmploymentTypeDTO);

    @Operation(summary = "Update HHEmploymentType (delegates to HHEmploymentTypeFacade.update)", tags = {"HHEmploymentType"},
            description = "Updates a resource using HHEmploymentTypeFacade.update with a HHEmploymentTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHEmploymentTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHEmploymentTypeDTO> update(
            @Parameter(description = "HHEmploymentType payload used for update", required = true)
            @RequestBody HHEmploymentTypeDTO HHEmploymentTypeDTO);

    @Operation(summary = "Get HHEmploymentTypes page (delegates to HHEmploymentTypeFacade.getAllPage)", tags = {"HHEmploymentType"},
            description = "Returns a paginated list using HHEmploymentTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHEmploymentTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHEmploymentType by ID (delegates to HHEmploymentTypeFacade.getById)" , tags = {"HHEmploymentType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHEmploymentTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHEmploymentTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHEmploymentType (delegates to HHEmploymentTypeFacade.delete)" , tags = {"HHEmploymentType"})
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

    @Operation(summary = "Get HHEmploymentType by IDs (delegates to HHEmploymentTypeFacade.getAllByIds)" , tags = {"HHEmploymentType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHEmploymentTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
