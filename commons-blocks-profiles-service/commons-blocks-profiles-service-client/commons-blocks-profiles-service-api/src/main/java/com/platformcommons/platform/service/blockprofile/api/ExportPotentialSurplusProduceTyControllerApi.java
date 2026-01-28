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

import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialSurplusProduceTyDTO;

/**
 * API contract for ExportPotentialSurplusProduceTyController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/export-potential-surplus-produce-ties")
@Tag(name = "ExportPotentialSurplusProduceTy", description = "Operations for ExportPotentialSurplusProduceTy that delegate to ExportPotentialSurplusProduceTyFacade")
public interface ExportPotentialSurplusProduceTyControllerApi {

    @Operation(summary = "Create ExportPotentialSurplusProduceTy (delegates to ExportPotentialSurplusProduceTyFacade.save)", tags = {"ExportPotentialSurplusProduceTy"},
            description = "Creates a new resource using ExportPotentialSurplusProduceTyFacade.save with a ExportPotentialSurplusProduceTyDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "ExportPotentialSurplusProduceTy payload used for creation", required = true)
            @RequestBody ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO);

    @Operation(summary = "Update ExportPotentialSurplusProduceTy (delegates to ExportPotentialSurplusProduceTyFacade.update)", tags = {"ExportPotentialSurplusProduceTy"},
            description = "Updates a resource using ExportPotentialSurplusProduceTyFacade.update with a ExportPotentialSurplusProduceTyDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ExportPotentialSurplusProduceTyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExportPotentialSurplusProduceTyDTO> update(
            @Parameter(description = "ExportPotentialSurplusProduceTy payload used for update", required = true)
            @RequestBody ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO);

    @Operation(summary = "Get ExportPotentialSurplusProduceTys page (delegates to ExportPotentialSurplusProduceTyFacade.getAllPage)", tags = {"ExportPotentialSurplusProduceTy"},
            description = "Returns a paginated list using ExportPotentialSurplusProduceTyFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ExportPotentialSurplusProduceTyDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get ExportPotentialSurplusProduceTy by ID (delegates to ExportPotentialSurplusProduceTyFacade.getById)" , tags = {"ExportPotentialSurplusProduceTy"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ExportPotentialSurplusProduceTyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ExportPotentialSurplusProduceTyDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete ExportPotentialSurplusProduceTy (delegates to ExportPotentialSurplusProduceTyFacade.delete)" , tags = {"ExportPotentialSurplusProduceTy"})
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

    @Operation(summary = "Get ExportPotentialSurplusProduceTy by IDs (delegates to ExportPotentialSurplusProduceTyFacade.getAllByIds)" , tags = {"ExportPotentialSurplusProduceTy"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ExportPotentialSurplusProduceTyDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
