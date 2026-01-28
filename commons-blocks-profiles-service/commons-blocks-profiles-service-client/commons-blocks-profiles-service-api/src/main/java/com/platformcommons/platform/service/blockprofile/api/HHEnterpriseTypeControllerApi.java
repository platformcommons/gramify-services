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

import com.platformcommons.platform.service.blockprofile.dto.HHEnterpriseTypeDTO;

/**
 * API contract for HHEnterpriseTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-enterprise-types")
@Tag(name = "HHEnterpriseType", description = "Operations for HHEnterpriseType that delegate to HHEnterpriseTypeFacade")
public interface HHEnterpriseTypeControllerApi {

    @Operation(summary = "Create HHEnterpriseType (delegates to HHEnterpriseTypeFacade.save)", tags = {"HHEnterpriseType"},
            description = "Creates a new resource using HHEnterpriseTypeFacade.save with a HHEnterpriseTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHEnterpriseType payload used for creation", required = true)
            @RequestBody HHEnterpriseTypeDTO HHEnterpriseTypeDTO);

    @Operation(summary = "Update HHEnterpriseType (delegates to HHEnterpriseTypeFacade.update)", tags = {"HHEnterpriseType"},
            description = "Updates a resource using HHEnterpriseTypeFacade.update with a HHEnterpriseTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHEnterpriseTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHEnterpriseTypeDTO> update(
            @Parameter(description = "HHEnterpriseType payload used for update", required = true)
            @RequestBody HHEnterpriseTypeDTO HHEnterpriseTypeDTO);

    @Operation(summary = "Get HHEnterpriseTypes page (delegates to HHEnterpriseTypeFacade.getAllPage)", tags = {"HHEnterpriseType"},
            description = "Returns a paginated list using HHEnterpriseTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHEnterpriseTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHEnterpriseType by ID (delegates to HHEnterpriseTypeFacade.getById)" , tags = {"HHEnterpriseType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHEnterpriseTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHEnterpriseTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHEnterpriseType (delegates to HHEnterpriseTypeFacade.delete)" , tags = {"HHEnterpriseType"})
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

    @Operation(summary = "Get HHEnterpriseType by IDs (delegates to HHEnterpriseTypeFacade.getAllByIds)" , tags = {"HHEnterpriseType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHEnterpriseTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
