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

import com.platformcommons.platform.service.blockprofile.dto.VillageEducationInfrastructureDTO;

/**
 * API contract for VillageEducationInfrastructureController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-education-infrastructures")
@Tag(name = "VillageEducationInfrastructure", description = "Operations for VillageEducationInfrastructure that delegate to VillageEducationInfrastructureFacade")
public interface VillageEducationInfrastructureControllerApi {

    @Operation(summary = "Create VillageEducationInfrastructure (delegates to VillageEducationInfrastructureFacade.save)", tags = {"VillageEducationInfrastructure"},
            description = "Creates a new resource using VillageEducationInfrastructureFacade.save with a VillageEducationInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageEducationInfrastructure payload used for creation", required = true)
            @RequestBody VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO);

    @Operation(summary = "Update VillageEducationInfrastructure (delegates to VillageEducationInfrastructureFacade.update)", tags = {"VillageEducationInfrastructure"},
            description = "Updates a resource using VillageEducationInfrastructureFacade.update with a VillageEducationInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageEducationInfrastructureDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageEducationInfrastructureDTO> update(
            @Parameter(description = "VillageEducationInfrastructure payload used for update", required = true)
            @RequestBody VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO);

    @Operation(summary = "Get VillageEducationInfrastructures page (delegates to VillageEducationInfrastructureFacade.getAllPage)", tags = {"VillageEducationInfrastructure"},
            description = "Returns a paginated list using VillageEducationInfrastructureFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageEducationInfrastructureDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageEducationInfrastructure by ID (delegates to VillageEducationInfrastructureFacade.getById)" , tags = {"VillageEducationInfrastructure"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageEducationInfrastructureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageEducationInfrastructureDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageEducationInfrastructure (delegates to VillageEducationInfrastructureFacade.delete)" , tags = {"VillageEducationInfrastructure"})
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

    @Operation(summary = "Get VillageEducationInfrastructure by IDs (delegates to VillageEducationInfrastructureFacade.getAllByIds)" , tags = {"VillageEducationInfrastructure"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageEducationInfrastructureDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
