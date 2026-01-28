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

import com.platformcommons.platform.service.blockprofile.dto.VillageTopInfrastructureNeedDTO;

/**
 * API contract for VillageTopInfrastructureNeedController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-top-infrastructure-needs")
@Tag(name = "VillageTopInfrastructureNeed", description = "Operations for VillageTopInfrastructureNeed that delegate to VillageTopInfrastructureNeedFacade")
public interface VillageTopInfrastructureNeedControllerApi {

    @Operation(summary = "Create VillageTopInfrastructureNeed (delegates to VillageTopInfrastructureNeedFacade.save)", tags = {"VillageTopInfrastructureNeed"},
            description = "Creates a new resource using VillageTopInfrastructureNeedFacade.save with a VillageTopInfrastructureNeedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageTopInfrastructureNeed payload used for creation", required = true)
            @RequestBody VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO);

    @Operation(summary = "Update VillageTopInfrastructureNeed (delegates to VillageTopInfrastructureNeedFacade.update)", tags = {"VillageTopInfrastructureNeed"},
            description = "Updates a resource using VillageTopInfrastructureNeedFacade.update with a VillageTopInfrastructureNeedDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageTopInfrastructureNeedDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageTopInfrastructureNeedDTO> update(
            @Parameter(description = "VillageTopInfrastructureNeed payload used for update", required = true)
            @RequestBody VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO);

    @Operation(summary = "Get VillageTopInfrastructureNeeds page (delegates to VillageTopInfrastructureNeedFacade.getAllPage)", tags = {"VillageTopInfrastructureNeed"},
            description = "Returns a paginated list using VillageTopInfrastructureNeedFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageTopInfrastructureNeedDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageTopInfrastructureNeed by ID (delegates to VillageTopInfrastructureNeedFacade.getById)" , tags = {"VillageTopInfrastructureNeed"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageTopInfrastructureNeedDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageTopInfrastructureNeedDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageTopInfrastructureNeed (delegates to VillageTopInfrastructureNeedFacade.delete)" , tags = {"VillageTopInfrastructureNeed"})
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

    @Operation(summary = "Get VillageTopInfrastructureNeed by IDs (delegates to VillageTopInfrastructureNeedFacade.getAllByIds)" , tags = {"VillageTopInfrastructureNeed"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageTopInfrastructureNeedDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
