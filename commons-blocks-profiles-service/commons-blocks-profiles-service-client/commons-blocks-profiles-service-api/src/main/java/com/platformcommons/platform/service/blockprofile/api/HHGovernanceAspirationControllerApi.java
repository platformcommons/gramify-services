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

import com.platformcommons.platform.service.blockprofile.dto.HHGovernanceAspirationDTO;

/**
 * API contract for HHGovernanceAspirationController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/hh-governance-aspirations")
@Tag(name = "HHGovernanceAspiration", description = "Operations for HHGovernanceAspiration that delegate to HHGovernanceAspirationFacade")
public interface HHGovernanceAspirationControllerApi {

    @Operation(summary = "Create HHGovernanceAspiration (delegates to HHGovernanceAspirationFacade.save)", tags = {"HHGovernanceAspiration"},
            description = "Creates a new resource using HHGovernanceAspirationFacade.save with a HHGovernanceAspirationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HHGovernanceAspiration payload used for creation", required = true)
            @RequestBody HHGovernanceAspirationDTO HHGovernanceAspirationDTO);

    @Operation(summary = "Update HHGovernanceAspiration (delegates to HHGovernanceAspirationFacade.update)", tags = {"HHGovernanceAspiration"},
            description = "Updates a resource using HHGovernanceAspirationFacade.update with a HHGovernanceAspirationDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HHGovernanceAspirationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HHGovernanceAspirationDTO> update(
            @Parameter(description = "HHGovernanceAspiration payload used for update", required = true)
            @RequestBody HHGovernanceAspirationDTO HHGovernanceAspirationDTO);

    @Operation(summary = "Get HHGovernanceAspirations page (delegates to HHGovernanceAspirationFacade.getAllPage)", tags = {"HHGovernanceAspiration"},
            description = "Returns a paginated list using HHGovernanceAspirationFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HHGovernanceAspirationDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HHGovernanceAspiration by ID (delegates to HHGovernanceAspirationFacade.getById)" , tags = {"HHGovernanceAspiration"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HHGovernanceAspirationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HHGovernanceAspirationDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HHGovernanceAspiration (delegates to HHGovernanceAspirationFacade.delete)" , tags = {"HHGovernanceAspiration"})
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

    @Operation(summary = "Get HHGovernanceAspiration by IDs (delegates to HHGovernanceAspirationFacade.getAllByIds)" , tags = {"HHGovernanceAspiration"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HHGovernanceAspirationDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
