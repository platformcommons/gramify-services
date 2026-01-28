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

import com.platformcommons.platform.service.blockprofile.dto.HHSkilledWorkerTypeDTO;

/**
 * API contract for HHSkilledWorkerTypeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-skilled-worker-types")
@Tag(name = "HHSkilledWorkerType", description = "Operations for HHSkilledWorkerType that delegate to HHSkilledWorkerTypeFacade")
public interface HHSkilledWorkerTypeControllerApi {

    @Operation(summary = "Create HHSkilledWorkerType (delegates to HHSkilledWorkerTypeFacade.save)", tags = {"HHSkilledWorkerType"},
            description = "Creates a new resource using HHSkilledWorkerTypeFacade.save with a HHSkilledWorkerTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHSkilledWorkerType payload used for creation", required = true)
            @RequestBody HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO);

    @Operation(summary = "Update HHSkilledWorkerType (delegates to HHSkilledWorkerTypeFacade.update)", tags = {"HHSkilledWorkerType"},
            description = "Updates a resource using HHSkilledWorkerTypeFacade.update with a HHSkilledWorkerTypeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHSkilledWorkerTypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHSkilledWorkerTypeDTO> update(
            @Parameter(description = "HHSkilledWorkerType payload used for update", required = true)
            @RequestBody HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO);

    @Operation(summary = "Get HHSkilledWorkerTypes page (delegates to HHSkilledWorkerTypeFacade.getAllPage)", tags = {"HHSkilledWorkerType"},
            description = "Returns a paginated list using HHSkilledWorkerTypeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHSkilledWorkerTypeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHSkilledWorkerType by ID (delegates to HHSkilledWorkerTypeFacade.getById)" , tags = {"HHSkilledWorkerType"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHSkilledWorkerTypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHSkilledWorkerTypeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHSkilledWorkerType (delegates to HHSkilledWorkerTypeFacade.delete)" , tags = {"HHSkilledWorkerType"})
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

    @Operation(summary = "Get HHSkilledWorkerType by IDs (delegates to HHSkilledWorkerTypeFacade.getAllByIds)" , tags = {"HHSkilledWorkerType"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHSkilledWorkerTypeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
