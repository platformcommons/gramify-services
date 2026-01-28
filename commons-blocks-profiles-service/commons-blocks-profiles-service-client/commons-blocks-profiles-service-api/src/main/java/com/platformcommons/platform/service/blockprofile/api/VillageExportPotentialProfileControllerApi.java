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

import com.platformcommons.platform.service.blockprofile.dto.VillageExportPotentialProfileDTO;

/**
 * API contract for VillageExportPotentialProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-export-potential-profiles")
@Tag(name = "VillageExportPotentialProfile", description = "Operations for VillageExportPotentialProfile that delegate to VillageExportPotentialProfileFacade")
public interface VillageExportPotentialProfileControllerApi {

    @Operation(summary = "Create VillageExportPotentialProfile (delegates to VillageExportPotentialProfileFacade.save)", tags = {"VillageExportPotentialProfile"},
            description = "Creates a new resource using VillageExportPotentialProfileFacade.save with a VillageExportPotentialProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageExportPotentialProfile payload used for creation", required = true)
            @RequestBody VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO);

    @Operation(summary = "Update VillageExportPotentialProfile (delegates to VillageExportPotentialProfileFacade.update)", tags = {"VillageExportPotentialProfile"},
            description = "Updates a resource using VillageExportPotentialProfileFacade.update with a VillageExportPotentialProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageExportPotentialProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageExportPotentialProfileDTO> update(
            @Parameter(description = "VillageExportPotentialProfile payload used for update", required = true)
            @RequestBody VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO);

    @Operation(summary = "Get VillageExportPotentialProfiles page (delegates to VillageExportPotentialProfileFacade.getAllPage)", tags = {"VillageExportPotentialProfile"},
            description = "Returns a paginated list using VillageExportPotentialProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageExportPotentialProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageExportPotentialProfile by ID (delegates to VillageExportPotentialProfileFacade.getById)" , tags = {"VillageExportPotentialProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageExportPotentialProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageExportPotentialProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageExportPotentialProfile (delegates to VillageExportPotentialProfileFacade.delete)" , tags = {"VillageExportPotentialProfile"})
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

    @Operation(summary = "Get VillageExportPotentialProfile by IDs (delegates to VillageExportPotentialProfileFacade.getAllByIds)" , tags = {"VillageExportPotentialProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageExportPotentialProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
