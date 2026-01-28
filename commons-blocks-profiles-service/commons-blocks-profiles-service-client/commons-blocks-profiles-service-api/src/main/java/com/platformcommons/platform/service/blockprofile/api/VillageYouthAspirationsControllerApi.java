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

import com.platformcommons.platform.service.blockprofile.dto.VillageYouthAspirationsDTO;

/**
 * API contract for VillageYouthAspirationsController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-youth-aspirationses")
@Tag(name = "VillageYouthAspirations", description = "Operations for VillageYouthAspirations that delegate to VillageYouthAspirationsFacade")
public interface VillageYouthAspirationsControllerApi {

    @Operation(summary = "Create VillageYouthAspirations (delegates to VillageYouthAspirationsFacade.save)", tags = {"VillageYouthAspirations"},
            description = "Creates a new resource using VillageYouthAspirationsFacade.save with a VillageYouthAspirationsDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageYouthAspirations payload used for creation", required = true)
            @RequestBody VillageYouthAspirationsDTO VillageYouthAspirationsDTO);

    @Operation(summary = "Update VillageYouthAspirations (delegates to VillageYouthAspirationsFacade.update)", tags = {"VillageYouthAspirations"},
            description = "Updates a resource using VillageYouthAspirationsFacade.update with a VillageYouthAspirationsDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageYouthAspirationsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageYouthAspirationsDTO> update(
            @Parameter(description = "VillageYouthAspirations payload used for update", required = true)
            @RequestBody VillageYouthAspirationsDTO VillageYouthAspirationsDTO);

    @Operation(summary = "Get VillageYouthAspirationss page (delegates to VillageYouthAspirationsFacade.getAllPage)", tags = {"VillageYouthAspirations"},
            description = "Returns a paginated list using VillageYouthAspirationsFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageYouthAspirationsDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageYouthAspirations by ID (delegates to VillageYouthAspirationsFacade.getById)" , tags = {"VillageYouthAspirations"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageYouthAspirationsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageYouthAspirationsDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageYouthAspirations (delegates to VillageYouthAspirationsFacade.delete)" , tags = {"VillageYouthAspirations"})
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

    @Operation(summary = "Get VillageYouthAspirations by IDs (delegates to VillageYouthAspirationsFacade.getAllByIds)" , tags = {"VillageYouthAspirations"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageYouthAspirationsDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
