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

import com.platformcommons.platform.service.blockprofile.dto.SurplusMovedThroughDTO;

/**
 * API contract for SurplusMovedThroughController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/surplus-moved-throughs")
@Tag(name = "SurplusMovedThrough", description = "Operations for SurplusMovedThrough that delegate to SurplusMovedThroughFacade")
public interface SurplusMovedThroughControllerApi {

    @Operation(summary = "Create SurplusMovedThrough (delegates to SurplusMovedThroughFacade.save)", tags = {"SurplusMovedThrough"},
            description = "Creates a new resource using SurplusMovedThroughFacade.save with a SurplusMovedThroughDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "SurplusMovedThrough payload used for creation", required = true)
            @RequestBody SurplusMovedThroughDTO SurplusMovedThroughDTO);

    @Operation(summary = "Update SurplusMovedThrough (delegates to SurplusMovedThroughFacade.update)", tags = {"SurplusMovedThrough"},
            description = "Updates a resource using SurplusMovedThroughFacade.update with a SurplusMovedThroughDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = SurplusMovedThroughDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SurplusMovedThroughDTO> update(
            @Parameter(description = "SurplusMovedThrough payload used for update", required = true)
            @RequestBody SurplusMovedThroughDTO SurplusMovedThroughDTO);

    @Operation(summary = "Get SurplusMovedThroughs page (delegates to SurplusMovedThroughFacade.getAllPage)", tags = {"SurplusMovedThrough"},
            description = "Returns a paginated list using SurplusMovedThroughFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<SurplusMovedThroughDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get SurplusMovedThrough by ID (delegates to SurplusMovedThroughFacade.getById)" , tags = {"SurplusMovedThrough"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = SurplusMovedThroughDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<SurplusMovedThroughDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete SurplusMovedThrough (delegates to SurplusMovedThroughFacade.delete)" , tags = {"SurplusMovedThrough"})
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

    @Operation(summary = "Get SurplusMovedThrough by IDs (delegates to SurplusMovedThroughFacade.getAllByIds)" , tags = {"SurplusMovedThrough"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<SurplusMovedThroughDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
