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

import com.platformcommons.platform.service.blockprofile.dto.ConsumptionDTO;

/**
 * API contract for ConsumptionController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/consumptions")
@Tag(name = "Consumption", description = "Operations for Consumption that delegate to ConsumptionFacade")
public interface ConsumptionControllerApi {

    @Operation(summary = "Create Consumption (delegates to ConsumptionFacade.save)", tags = {"Consumption"},
            description = "Creates a new resource using ConsumptionFacade.save with a ConsumptionDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "Consumption payload used for creation", required = true)
            @RequestBody ConsumptionDTO ConsumptionDTO);

    @Operation(summary = "Update Consumption (delegates to ConsumptionFacade.update)", tags = {"Consumption"},
            description = "Updates a resource using ConsumptionFacade.update with a ConsumptionDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = ConsumptionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConsumptionDTO> update(
            @Parameter(description = "Consumption payload used for update", required = true)
            @RequestBody ConsumptionDTO ConsumptionDTO);

    @Operation(summary = "Get Consumptions page (delegates to ConsumptionFacade.getAllPage)", tags = {"Consumption"},
            description = "Returns a paginated list using ConsumptionFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<ConsumptionDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get Consumption by ID (delegates to ConsumptionFacade.getById)" , tags = {"Consumption"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = ConsumptionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<ConsumptionDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete Consumption (delegates to ConsumptionFacade.delete)" , tags = {"Consumption"})
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

    @Operation(summary = "Get Consumption by IDs (delegates to ConsumptionFacade.getAllByIds)" , tags = {"Consumption"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ConsumptionDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
