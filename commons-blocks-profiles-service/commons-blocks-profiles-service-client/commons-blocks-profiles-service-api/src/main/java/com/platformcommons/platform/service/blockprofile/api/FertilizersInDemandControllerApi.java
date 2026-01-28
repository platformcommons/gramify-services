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

import com.platformcommons.platform.service.blockprofile.dto.FertilizersInDemandDTO;

/**
 * API contract for FertilizersInDemandController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/fertilizers-in-demands")
@Tag(name = "FertilizersInDemand", description = "Operations for FertilizersInDemand that delegate to FertilizersInDemandFacade")
public interface FertilizersInDemandControllerApi {

    @Operation(summary = "Create FertilizersInDemand (delegates to FertilizersInDemandFacade.save)", tags = {"FertilizersInDemand"},
            description = "Creates a new resource using FertilizersInDemandFacade.save with a FertilizersInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "FertilizersInDemand payload used for creation", required = true)
            @RequestBody FertilizersInDemandDTO FertilizersInDemandDTO);

    @Operation(summary = "Update FertilizersInDemand (delegates to FertilizersInDemandFacade.update)", tags = {"FertilizersInDemand"},
            description = "Updates a resource using FertilizersInDemandFacade.update with a FertilizersInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = FertilizersInDemandDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FertilizersInDemandDTO> update(
            @Parameter(description = "FertilizersInDemand payload used for update", required = true)
            @RequestBody FertilizersInDemandDTO FertilizersInDemandDTO);

    @Operation(summary = "Get FertilizersInDemands page (delegates to FertilizersInDemandFacade.getAllPage)", tags = {"FertilizersInDemand"},
            description = "Returns a paginated list using FertilizersInDemandFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<FertilizersInDemandDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get FertilizersInDemand by ID (delegates to FertilizersInDemandFacade.getById)" , tags = {"FertilizersInDemand"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = FertilizersInDemandDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<FertilizersInDemandDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete FertilizersInDemand (delegates to FertilizersInDemandFacade.delete)" , tags = {"FertilizersInDemand"})
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

    @Operation(summary = "Get FertilizersInDemand by IDs (delegates to FertilizersInDemandFacade.getAllByIds)" , tags = {"FertilizersInDemand"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<FertilizersInDemandDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
