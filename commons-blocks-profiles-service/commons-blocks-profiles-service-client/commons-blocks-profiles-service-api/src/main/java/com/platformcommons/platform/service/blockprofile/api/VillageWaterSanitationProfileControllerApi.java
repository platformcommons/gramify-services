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

import com.platformcommons.platform.service.blockprofile.dto.VillageWaterSanitationProfileDTO;

/**
 * API contract for VillageWaterSanitationProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-water-sanitation-profiles")
@Tag(name = "VillageWaterSanitationProfile", description = "Operations for VillageWaterSanitationProfile that delegate to VillageWaterSanitationProfileFacade")
public interface VillageWaterSanitationProfileControllerApi {

    @Operation(summary = "Create VillageWaterSanitationProfile (delegates to VillageWaterSanitationProfileFacade.save)", tags = {"VillageWaterSanitationProfile"},
            description = "Creates a new resource using VillageWaterSanitationProfileFacade.save with a VillageWaterSanitationProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageWaterSanitationProfile payload used for creation", required = true)
            @RequestBody VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO);

    @Operation(summary = "Update VillageWaterSanitationProfile (delegates to VillageWaterSanitationProfileFacade.update)", tags = {"VillageWaterSanitationProfile"},
            description = "Updates a resource using VillageWaterSanitationProfileFacade.update with a VillageWaterSanitationProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageWaterSanitationProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageWaterSanitationProfileDTO> update(
            @Parameter(description = "VillageWaterSanitationProfile payload used for update", required = true)
            @RequestBody VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO);

    @Operation(summary = "Get VillageWaterSanitationProfiles page (delegates to VillageWaterSanitationProfileFacade.getAllPage)", tags = {"VillageWaterSanitationProfile"},
            description = "Returns a paginated list using VillageWaterSanitationProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageWaterSanitationProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageWaterSanitationProfile by ID (delegates to VillageWaterSanitationProfileFacade.getById)" , tags = {"VillageWaterSanitationProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageWaterSanitationProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageWaterSanitationProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageWaterSanitationProfile (delegates to VillageWaterSanitationProfileFacade.delete)" , tags = {"VillageWaterSanitationProfile"})
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

    @Operation(summary = "Get VillageWaterSanitationProfile by IDs (delegates to VillageWaterSanitationProfileFacade.getAllByIds)" , tags = {"VillageWaterSanitationProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageWaterSanitationProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
