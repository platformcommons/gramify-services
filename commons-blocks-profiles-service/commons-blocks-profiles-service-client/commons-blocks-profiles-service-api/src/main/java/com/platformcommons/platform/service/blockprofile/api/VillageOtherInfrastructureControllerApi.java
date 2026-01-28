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

import com.platformcommons.platform.service.blockprofile.dto.VillageOtherInfrastructureDTO;

/**
 * API contract for VillageOtherInfrastructureController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-other-infrastructures")
@Tag(name = "VillageOtherInfrastructure", description = "Operations for VillageOtherInfrastructure that delegate to VillageOtherInfrastructureFacade")
public interface VillageOtherInfrastructureControllerApi {

    @Operation(summary = "Create VillageOtherInfrastructure (delegates to VillageOtherInfrastructureFacade.save)", tags = {"VillageOtherInfrastructure"},
            description = "Creates a new resource using VillageOtherInfrastructureFacade.save with a VillageOtherInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageOtherInfrastructure payload used for creation", required = true)
            @RequestBody VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO);

    @Operation(summary = "Update VillageOtherInfrastructure (delegates to VillageOtherInfrastructureFacade.update)", tags = {"VillageOtherInfrastructure"},
            description = "Updates a resource using VillageOtherInfrastructureFacade.update with a VillageOtherInfrastructureDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageOtherInfrastructureDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageOtherInfrastructureDTO> update(
            @Parameter(description = "VillageOtherInfrastructure payload used for update", required = true)
            @RequestBody VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO);

    @Operation(summary = "Get VillageOtherInfrastructures page (delegates to VillageOtherInfrastructureFacade.getAllPage)", tags = {"VillageOtherInfrastructure"},
            description = "Returns a paginated list using VillageOtherInfrastructureFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageOtherInfrastructureDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageOtherInfrastructure by ID (delegates to VillageOtherInfrastructureFacade.getById)" , tags = {"VillageOtherInfrastructure"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageOtherInfrastructureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageOtherInfrastructureDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageOtherInfrastructure (delegates to VillageOtherInfrastructureFacade.delete)" , tags = {"VillageOtherInfrastructure"})
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

    @Operation(summary = "Get VillageOtherInfrastructure by IDs (delegates to VillageOtherInfrastructureFacade.getAllByIds)" , tags = {"VillageOtherInfrastructure"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageOtherInfrastructureDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
