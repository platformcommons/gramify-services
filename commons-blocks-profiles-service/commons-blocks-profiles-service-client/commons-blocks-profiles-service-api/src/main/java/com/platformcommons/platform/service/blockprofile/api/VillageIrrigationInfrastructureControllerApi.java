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

import com.platformcommons.platform.service.blockprofile.dto.VillageIrrigationInfrastructureDTO;

/**
 * API contract for VillageIrrigationInfrastructureController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-irrigation-infrastructures")
@Tag(name = "VillageIrrigationInfrastructure", description = "Operations for VillageIrrigationInfrastructure that delegate to VillageIrrigationInfrastructureFacade")
public interface VillageIrrigationInfrastructureControllerApi {

    @Operation(summary = "Create VillageIrrigationInfrastructure (delegates to VillageIrrigationInfrastructureFacade.save)", tags = {"VillageIrrigationInfrastructure"},
            description = "Creates a new resource using VillageIrrigationInfrastructureFacade.save with a VillageIrrigationInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageIrrigationInfrastructure payload used for creation", required = true)
            @RequestBody VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO);

    @Operation(summary = "Update VillageIrrigationInfrastructure (delegates to VillageIrrigationInfrastructureFacade.update)", tags = {"VillageIrrigationInfrastructure"},
            description = "Updates a resource using VillageIrrigationInfrastructureFacade.update with a VillageIrrigationInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageIrrigationInfrastructureDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageIrrigationInfrastructureDTO> update(
            @Parameter(description = "VillageIrrigationInfrastructure payload used for update", required = true)
            @RequestBody VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO);

    @Operation(summary = "Get VillageIrrigationInfrastructures page (delegates to VillageIrrigationInfrastructureFacade.getAllPage)", tags = {"VillageIrrigationInfrastructure"},
            description = "Returns a paginated list using VillageIrrigationInfrastructureFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageIrrigationInfrastructureDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageIrrigationInfrastructure by ID (delegates to VillageIrrigationInfrastructureFacade.getById)" , tags = {"VillageIrrigationInfrastructure"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageIrrigationInfrastructureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageIrrigationInfrastructureDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageIrrigationInfrastructure (delegates to VillageIrrigationInfrastructureFacade.delete)" , tags = {"VillageIrrigationInfrastructure"})
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

    @Operation(summary = "Get VillageIrrigationInfrastructure by IDs (delegates to VillageIrrigationInfrastructureFacade.getAllByIds)" , tags = {"VillageIrrigationInfrastructure"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageIrrigationInfrastructureDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
