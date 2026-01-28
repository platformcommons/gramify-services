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

import com.platformcommons.platform.service.blockprofile.dto.VillageForestResourceProfileDTO;

/**
 * API contract for VillageForestResourceProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-forest-resource-profiles")
@Tag(name = "VillageForestResourceProfile", description = "Operations for VillageForestResourceProfile that delegate to VillageForestResourceProfileFacade")
public interface VillageForestResourceProfileControllerApi {

    @Operation(summary = "Create VillageForestResourceProfile (delegates to VillageForestResourceProfileFacade.save)", tags = {"VillageForestResourceProfile"},
            description = "Creates a new resource using VillageForestResourceProfileFacade.save with a VillageForestResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageForestResourceProfile payload used for creation", required = true)
            @RequestBody VillageForestResourceProfileDTO VillageForestResourceProfileDTO);

    @Operation(summary = "Update VillageForestResourceProfile (delegates to VillageForestResourceProfileFacade.update)", tags = {"VillageForestResourceProfile"},
            description = "Updates a resource using VillageForestResourceProfileFacade.update with a VillageForestResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageForestResourceProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageForestResourceProfileDTO> update(
            @Parameter(description = "VillageForestResourceProfile payload used for update", required = true)
            @RequestBody VillageForestResourceProfileDTO VillageForestResourceProfileDTO);

    @Operation(summary = "Get VillageForestResourceProfiles page (delegates to VillageForestResourceProfileFacade.getAllPage)", tags = {"VillageForestResourceProfile"},
            description = "Returns a paginated list using VillageForestResourceProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageForestResourceProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageForestResourceProfile by ID (delegates to VillageForestResourceProfileFacade.getById)" , tags = {"VillageForestResourceProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageForestResourceProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageForestResourceProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageForestResourceProfile (delegates to VillageForestResourceProfileFacade.delete)" , tags = {"VillageForestResourceProfile"})
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

    @Operation(summary = "Get VillageForestResourceProfile by IDs (delegates to VillageForestResourceProfileFacade.getAllByIds)" , tags = {"VillageForestResourceProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageForestResourceProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
