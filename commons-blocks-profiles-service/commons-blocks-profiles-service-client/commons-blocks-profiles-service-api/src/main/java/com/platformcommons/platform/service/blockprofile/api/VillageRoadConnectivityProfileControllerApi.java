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

import com.platformcommons.platform.service.blockprofile.dto.VillageRoadConnectivityProfileDTO;

/**
 * API contract for VillageRoadConnectivityProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-road-connectivity-profiles")
@Tag(name = "VillageRoadConnectivityProfile", description = "Operations for VillageRoadConnectivityProfile that delegate to VillageRoadConnectivityProfileFacade")
public interface VillageRoadConnectivityProfileControllerApi {

    @Operation(summary = "Create VillageRoadConnectivityProfile (delegates to VillageRoadConnectivityProfileFacade.save)", tags = {"VillageRoadConnectivityProfile"},
            description = "Creates a new resource using VillageRoadConnectivityProfileFacade.save with a VillageRoadConnectivityProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageRoadConnectivityProfile payload used for creation", required = true)
            @RequestBody VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO);

    @Operation(summary = "Update VillageRoadConnectivityProfile (delegates to VillageRoadConnectivityProfileFacade.update)", tags = {"VillageRoadConnectivityProfile"},
            description = "Updates a resource using VillageRoadConnectivityProfileFacade.update with a VillageRoadConnectivityProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageRoadConnectivityProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageRoadConnectivityProfileDTO> update(
            @Parameter(description = "VillageRoadConnectivityProfile payload used for update", required = true)
            @RequestBody VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO);

    @Operation(summary = "Get VillageRoadConnectivityProfiles page (delegates to VillageRoadConnectivityProfileFacade.getAllPage)", tags = {"VillageRoadConnectivityProfile"},
            description = "Returns a paginated list using VillageRoadConnectivityProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageRoadConnectivityProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageRoadConnectivityProfile by ID (delegates to VillageRoadConnectivityProfileFacade.getById)" , tags = {"VillageRoadConnectivityProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageRoadConnectivityProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageRoadConnectivityProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageRoadConnectivityProfile (delegates to VillageRoadConnectivityProfileFacade.delete)" , tags = {"VillageRoadConnectivityProfile"})
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

    @Operation(summary = "Get VillageRoadConnectivityProfile by IDs (delegates to VillageRoadConnectivityProfileFacade.getAllByIds)" , tags = {"VillageRoadConnectivityProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageRoadConnectivityProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
