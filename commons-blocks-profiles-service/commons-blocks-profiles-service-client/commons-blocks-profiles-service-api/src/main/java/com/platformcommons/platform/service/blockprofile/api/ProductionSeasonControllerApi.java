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

import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonDTO;

/**
 * API contract for ProductionSeasonController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/production-seasons")
@Tag(name = "ProductionSeason", description = "Operations for ProductionSeason that delegate to ProductionSeasonFacade")
public interface ProductionSeasonControllerApi {

    @Operation(summary = "Create ProductionSeason (delegates to ProductionSeasonFacade.save)", tags = {"ProductionSeason"},
            description = "Creates a new resource using ProductionSeasonFacade.save with a ProductionSeasonDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "ProductionSeason payload used for creation", required = true)
            @RequestBody ProductionSeasonDTO ProductionSeasonDTO);

    @Operation(summary = "Update ProductionSeason (delegates to ProductionSeasonFacade.update)", tags = {"ProductionSeason"},
            description = "Updates a resource using ProductionSeasonFacade.update with a ProductionSeasonDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductionSeasonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductionSeasonDTO> update(
            @Parameter(description = "ProductionSeason payload used for update", required = true)
            @RequestBody ProductionSeasonDTO ProductionSeasonDTO);

    @Operation(summary = "Get ProductionSeasons page (delegates to ProductionSeasonFacade.getAllPage)", tags = {"ProductionSeason"},
            description = "Returns a paginated list using ProductionSeasonFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ProductionSeasonDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get ProductionSeason by ID (delegates to ProductionSeasonFacade.getById)" , tags = {"ProductionSeason"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ProductionSeasonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ProductionSeasonDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete ProductionSeason (delegates to ProductionSeasonFacade.delete)" , tags = {"ProductionSeason"})
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

    @Operation(summary = "Get ProductionSeason by IDs (delegates to ProductionSeasonFacade.getAllByIds)" , tags = {"ProductionSeason"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ProductionSeasonDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
