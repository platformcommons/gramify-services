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

import com.platformcommons.platform.service.blockprofile.dto.HHInfrastructureAspirationDTO;

/**
 * API contract for HHInfrastructureAspirationController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-infrastructure-aspirations")
@Tag(name = "HHInfrastructureAspiration", description = "Operations for HHInfrastructureAspiration that delegate to HHInfrastructureAspirationFacade")
public interface HHInfrastructureAspirationControllerApi {

    @Operation(summary = "Create HHInfrastructureAspiration (delegates to HHInfrastructureAspirationFacade.save)", tags = {"HHInfrastructureAspiration"},
            description = "Creates a new resource using HHInfrastructureAspirationFacade.save with a HHInfrastructureAspirationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHInfrastructureAspiration payload used for creation", required = true)
            @RequestBody HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO);

    @Operation(summary = "Update HHInfrastructureAspiration (delegates to HHInfrastructureAspirationFacade.update)", tags = {"HHInfrastructureAspiration"},
            description = "Updates a resource using HHInfrastructureAspirationFacade.update with a HHInfrastructureAspirationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHInfrastructureAspirationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHInfrastructureAspirationDTO> update(
            @Parameter(description = "HHInfrastructureAspiration payload used for update", required = true)
            @RequestBody HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO);

    @Operation(summary = "Get HHInfrastructureAspirations page (delegates to HHInfrastructureAspirationFacade.getAllPage)", tags = {"HHInfrastructureAspiration"},
            description = "Returns a paginated list using HHInfrastructureAspirationFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHInfrastructureAspirationDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHInfrastructureAspiration by ID (delegates to HHInfrastructureAspirationFacade.getById)" , tags = {"HHInfrastructureAspiration"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHInfrastructureAspirationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHInfrastructureAspirationDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHInfrastructureAspiration (delegates to HHInfrastructureAspirationFacade.delete)" , tags = {"HHInfrastructureAspiration"})
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

    @Operation(summary = "Get HHInfrastructureAspiration by IDs (delegates to HHInfrastructureAspirationFacade.getAllByIds)" , tags = {"HHInfrastructureAspiration"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHInfrastructureAspirationDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
