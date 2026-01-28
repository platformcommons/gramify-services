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

import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialNicheProductBuyeDTO;

/**
 * API contract for ExportPotentialNicheProductBuyeController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/export-potential-niche-product-buyes")
@Tag(name = "ExportPotentialNicheProductBuye", description = "Operations for ExportPotentialNicheProductBuye that delegate to ExportPotentialNicheProductBuyeFacade")
public interface ExportPotentialNicheProductBuyeControllerApi {

    @Operation(summary = "Create ExportPotentialNicheProductBuye (delegates to ExportPotentialNicheProductBuyeFacade.save)", tags = {"ExportPotentialNicheProductBuye"},
            description = "Creates a new resource using ExportPotentialNicheProductBuyeFacade.save with a ExportPotentialNicheProductBuyeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "ExportPotentialNicheProductBuye payload used for creation", required = true)
            @RequestBody ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO);

    @Operation(summary = "Update ExportPotentialNicheProductBuye (delegates to ExportPotentialNicheProductBuyeFacade.update)", tags = {"ExportPotentialNicheProductBuye"},
            description = "Updates a resource using ExportPotentialNicheProductBuyeFacade.update with a ExportPotentialNicheProductBuyeDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ExportPotentialNicheProductBuyeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExportPotentialNicheProductBuyeDTO> update(
            @Parameter(description = "ExportPotentialNicheProductBuye payload used for update", required = true)
            @RequestBody ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO);

    @Operation(summary = "Get ExportPotentialNicheProductBuyes page (delegates to ExportPotentialNicheProductBuyeFacade.getAllPage)", tags = {"ExportPotentialNicheProductBuye"},
            description = "Returns a paginated list using ExportPotentialNicheProductBuyeFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ExportPotentialNicheProductBuyeDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get ExportPotentialNicheProductBuye by ID (delegates to ExportPotentialNicheProductBuyeFacade.getById)" , tags = {"ExportPotentialNicheProductBuye"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ExportPotentialNicheProductBuyeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ExportPotentialNicheProductBuyeDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete ExportPotentialNicheProductBuye (delegates to ExportPotentialNicheProductBuyeFacade.delete)" , tags = {"ExportPotentialNicheProductBuye"})
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

    @Operation(summary = "Get ExportPotentialNicheProductBuye by IDs (delegates to ExportPotentialNicheProductBuyeFacade.getAllByIds)" , tags = {"ExportPotentialNicheProductBuye"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ExportPotentialNicheProductBuyeDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
