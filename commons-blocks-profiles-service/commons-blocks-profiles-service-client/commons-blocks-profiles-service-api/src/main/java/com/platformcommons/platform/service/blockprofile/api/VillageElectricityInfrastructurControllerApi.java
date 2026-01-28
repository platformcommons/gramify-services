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

import com.platformcommons.platform.service.blockprofile.dto.VillageElectricityInfrastructurDTO;

/**
 * API contract for VillageElectricityInfrastructurController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-electricity-infrastructurs")
@Tag(name = "VillageElectricityInfrastructur", description = "Operations for VillageElectricityInfrastructur that delegate to VillageElectricityInfrastructurFacade")
public interface VillageElectricityInfrastructurControllerApi {

    @Operation(summary = "Create VillageElectricityInfrastructur (delegates to VillageElectricityInfrastructurFacade.save)", tags = {"VillageElectricityInfrastructur"},
            description = "Creates a new resource using VillageElectricityInfrastructurFacade.save with a VillageElectricityInfrastructurDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageElectricityInfrastructur payload used for creation", required = true)
            @RequestBody VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO);

    @Operation(summary = "Update VillageElectricityInfrastructur (delegates to VillageElectricityInfrastructurFacade.update)", tags = {"VillageElectricityInfrastructur"},
            description = "Updates a resource using VillageElectricityInfrastructurFacade.update with a VillageElectricityInfrastructurDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageElectricityInfrastructurDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageElectricityInfrastructurDTO> update(
            @Parameter(description = "VillageElectricityInfrastructur payload used for update", required = true)
            @RequestBody VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO);

    @Operation(summary = "Get VillageElectricityInfrastructurs page (delegates to VillageElectricityInfrastructurFacade.getAllPage)", tags = {"VillageElectricityInfrastructur"},
            description = "Returns a paginated list using VillageElectricityInfrastructurFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageElectricityInfrastructurDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageElectricityInfrastructur by ID (delegates to VillageElectricityInfrastructurFacade.getById)" , tags = {"VillageElectricityInfrastructur"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageElectricityInfrastructurDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageElectricityInfrastructurDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageElectricityInfrastructur (delegates to VillageElectricityInfrastructurFacade.delete)" , tags = {"VillageElectricityInfrastructur"})
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

    @Operation(summary = "Get VillageElectricityInfrastructur by IDs (delegates to VillageElectricityInfrastructurFacade.getAllByIds)" , tags = {"VillageElectricityInfrastructur"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageElectricityInfrastructurDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
