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

import com.platformcommons.platform.service.blockprofile.dto.GovtSchemesDTO;

/**
 * API contract for GovtSchemesController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/govt-schemeses")
@Tag(name = "GovtSchemes", description = "Operations for GovtSchemes that delegate to GovtSchemesFacade")
public interface GovtSchemesControllerApi {

    @Operation(summary = "Create GovtSchemes (delegates to GovtSchemesFacade.save)", tags = {"GovtSchemes"},
            description = "Creates a new resource using GovtSchemesFacade.save with a GovtSchemesDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "GovtSchemes payload used for creation", required = true)
            @RequestBody GovtSchemesDTO GovtSchemesDTO);

    @Operation(summary = "Update GovtSchemes (delegates to GovtSchemesFacade.update)", tags = {"GovtSchemes"},
            description = "Updates a resource using GovtSchemesFacade.update with a GovtSchemesDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = GovtSchemesDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GovtSchemesDTO> update(
            @Parameter(description = "GovtSchemes payload used for update", required = true)
            @RequestBody GovtSchemesDTO GovtSchemesDTO);

    @Operation(summary = "Get GovtSchemess page (delegates to GovtSchemesFacade.getAllPage)", tags = {"GovtSchemes"},
            description = "Returns a paginated list using GovtSchemesFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<GovtSchemesDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get GovtSchemes by ID (delegates to GovtSchemesFacade.getById)" , tags = {"GovtSchemes"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = GovtSchemesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<GovtSchemesDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete GovtSchemes (delegates to GovtSchemesFacade.delete)" , tags = {"GovtSchemes"})
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

    @Operation(summary = "Get GovtSchemes by IDs (delegates to GovtSchemesFacade.getAllByIds)" , tags = {"GovtSchemes"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<GovtSchemesDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
