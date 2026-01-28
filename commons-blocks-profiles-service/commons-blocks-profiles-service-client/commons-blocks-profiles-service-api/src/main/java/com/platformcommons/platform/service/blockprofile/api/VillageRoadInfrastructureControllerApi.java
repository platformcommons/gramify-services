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

import com.platformcommons.platform.service.blockprofile.dto.VillageRoadInfrastructureDTO;

/**
 * API contract for VillageRoadInfrastructureController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-road-infrastructures")
@Tag(name = "VillageRoadInfrastructure", description = "Operations for VillageRoadInfrastructure that delegate to VillageRoadInfrastructureFacade")
public interface VillageRoadInfrastructureControllerApi {

    @Operation(summary = "Create VillageRoadInfrastructure (delegates to VillageRoadInfrastructureFacade.save)", tags = {"VillageRoadInfrastructure"},
            description = "Creates a new resource using VillageRoadInfrastructureFacade.save with a VillageRoadInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageRoadInfrastructure payload used for creation", required = true)
            @RequestBody VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO);

    @Operation(summary = "Update VillageRoadInfrastructure (delegates to VillageRoadInfrastructureFacade.update)", tags = {"VillageRoadInfrastructure"},
            description = "Updates a resource using VillageRoadInfrastructureFacade.update with a VillageRoadInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageRoadInfrastructureDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageRoadInfrastructureDTO> update(
            @Parameter(description = "VillageRoadInfrastructure payload used for update", required = true)
            @RequestBody VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO);

    @Operation(summary = "Get VillageRoadInfrastructures page (delegates to VillageRoadInfrastructureFacade.getAllPage)", tags = {"VillageRoadInfrastructure"},
            description = "Returns a paginated list using VillageRoadInfrastructureFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageRoadInfrastructureDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageRoadInfrastructure by ID (delegates to VillageRoadInfrastructureFacade.getById)" , tags = {"VillageRoadInfrastructure"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageRoadInfrastructureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageRoadInfrastructureDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageRoadInfrastructure (delegates to VillageRoadInfrastructureFacade.delete)" , tags = {"VillageRoadInfrastructure"})
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

    @Operation(summary = "Get VillageRoadInfrastructure by IDs (delegates to VillageRoadInfrastructureFacade.getAllByIds)" , tags = {"VillageRoadInfrastructure"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageRoadInfrastructureDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
