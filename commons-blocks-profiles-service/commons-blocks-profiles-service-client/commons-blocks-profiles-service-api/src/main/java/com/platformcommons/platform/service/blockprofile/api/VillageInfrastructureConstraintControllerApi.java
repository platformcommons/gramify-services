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

import com.platformcommons.platform.service.blockprofile.dto.VillageInfrastructureConstraintDTO;

/**
 * API contract for VillageInfrastructureConstraintController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-infrastructure-constraints")
@Tag(name = "VillageInfrastructureConstraint", description = "Operations for VillageInfrastructureConstraint that delegate to VillageInfrastructureConstraintFacade")
public interface VillageInfrastructureConstraintControllerApi {

    @Operation(summary = "Create VillageInfrastructureConstraint (delegates to VillageInfrastructureConstraintFacade.save)", tags = {"VillageInfrastructureConstraint"},
            description = "Creates a new resource using VillageInfrastructureConstraintFacade.save with a VillageInfrastructureConstraintDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageInfrastructureConstraint payload used for creation", required = true)
            @RequestBody VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO);

    @Operation(summary = "Update VillageInfrastructureConstraint (delegates to VillageInfrastructureConstraintFacade.update)", tags = {"VillageInfrastructureConstraint"},
            description = "Updates a resource using VillageInfrastructureConstraintFacade.update with a VillageInfrastructureConstraintDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageInfrastructureConstraintDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageInfrastructureConstraintDTO> update(
            @Parameter(description = "VillageInfrastructureConstraint payload used for update", required = true)
            @RequestBody VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO);

    @Operation(summary = "Get VillageInfrastructureConstraints page (delegates to VillageInfrastructureConstraintFacade.getAllPage)", tags = {"VillageInfrastructureConstraint"},
            description = "Returns a paginated list using VillageInfrastructureConstraintFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageInfrastructureConstraintDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageInfrastructureConstraint by ID (delegates to VillageInfrastructureConstraintFacade.getById)" , tags = {"VillageInfrastructureConstraint"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageInfrastructureConstraintDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageInfrastructureConstraintDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageInfrastructureConstraint (delegates to VillageInfrastructureConstraintFacade.delete)" , tags = {"VillageInfrastructureConstraint"})
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

    @Operation(summary = "Get VillageInfrastructureConstraint by IDs (delegates to VillageInfrastructureConstraintFacade.getAllByIds)" , tags = {"VillageInfrastructureConstraint"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageInfrastructureConstraintDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
