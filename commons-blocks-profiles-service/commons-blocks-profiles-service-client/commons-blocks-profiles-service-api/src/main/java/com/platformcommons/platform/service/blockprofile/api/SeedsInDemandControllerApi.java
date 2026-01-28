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

import com.platformcommons.platform.service.blockprofile.dto.SeedsInDemandDTO;

/**
 * API contract for SeedsInDemandController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/seeds-in-demands")
@Tag(name = "SeedsInDemand", description = "Operations for SeedsInDemand that delegate to SeedsInDemandFacade")
public interface SeedsInDemandControllerApi {

    @Operation(summary = "Create SeedsInDemand (delegates to SeedsInDemandFacade.save)", tags = {"SeedsInDemand"},
            description = "Creates a new resource using SeedsInDemandFacade.save with a SeedsInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "SeedsInDemand payload used for creation", required = true)
            @RequestBody SeedsInDemandDTO SeedsInDemandDTO);

    @Operation(summary = "Update SeedsInDemand (delegates to SeedsInDemandFacade.update)", tags = {"SeedsInDemand"},
            description = "Updates a resource using SeedsInDemandFacade.update with a SeedsInDemandDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = SeedsInDemandDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SeedsInDemandDTO> update(
            @Parameter(description = "SeedsInDemand payload used for update", required = true)
            @RequestBody SeedsInDemandDTO SeedsInDemandDTO);

    @Operation(summary = "Get SeedsInDemands page (delegates to SeedsInDemandFacade.getAllPage)", tags = {"SeedsInDemand"},
            description = "Returns a paginated list using SeedsInDemandFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<SeedsInDemandDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get SeedsInDemand by ID (delegates to SeedsInDemandFacade.getById)" , tags = {"SeedsInDemand"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = SeedsInDemandDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<SeedsInDemandDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete SeedsInDemand (delegates to SeedsInDemandFacade.delete)" , tags = {"SeedsInDemand"})
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

    @Operation(summary = "Get SeedsInDemand by IDs (delegates to SeedsInDemandFacade.getAllByIds)" , tags = {"SeedsInDemand"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<SeedsInDemandDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
