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

import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonalityDTO;

/**
 * API contract for ProductionSeasonalityController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/production-seasonalities")
@Tag(name = "ProductionSeasonality", description = "Operations for ProductionSeasonality that delegate to ProductionSeasonalityFacade")
public interface ProductionSeasonalityControllerApi {

    @Operation(summary = "Create ProductionSeasonality (delegates to ProductionSeasonalityFacade.save)", tags = {"ProductionSeasonality"},
            description = "Creates a new resource using ProductionSeasonalityFacade.save with a ProductionSeasonalityDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "ProductionSeasonality payload used for creation", required = true)
            @RequestBody ProductionSeasonalityDTO ProductionSeasonalityDTO);

    @Operation(summary = "Update ProductionSeasonality (delegates to ProductionSeasonalityFacade.update)", tags = {"ProductionSeasonality"},
            description = "Updates a resource using ProductionSeasonalityFacade.update with a ProductionSeasonalityDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductionSeasonalityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductionSeasonalityDTO> update(
            @Parameter(description = "ProductionSeasonality payload used for update", required = true)
            @RequestBody ProductionSeasonalityDTO ProductionSeasonalityDTO);

    @Operation(summary = "Get ProductionSeasonalitys page (delegates to ProductionSeasonalityFacade.getAllPage)", tags = {"ProductionSeasonality"},
            description = "Returns a paginated list using ProductionSeasonalityFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ProductionSeasonalityDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get ProductionSeasonality by ID (delegates to ProductionSeasonalityFacade.getById)" , tags = {"ProductionSeasonality"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ProductionSeasonalityDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ProductionSeasonalityDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete ProductionSeasonality (delegates to ProductionSeasonalityFacade.delete)" , tags = {"ProductionSeasonality"})
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

    @Operation(summary = "Get ProductionSeasonality by IDs (delegates to ProductionSeasonalityFacade.getAllByIds)" , tags = {"ProductionSeasonality"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ProductionSeasonalityDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
