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

import com.platformcommons.platform.service.blockprofile.dto.VillageWaterResourceProfileDTO;

/**
 * API contract for VillageWaterResourceProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-water-resource-profiles")
@Tag(name = "VillageWaterResourceProfile", description = "Operations for VillageWaterResourceProfile that delegate to VillageWaterResourceProfileFacade")
public interface VillageWaterResourceProfileControllerApi {

    @Operation(summary = "Create VillageWaterResourceProfile (delegates to VillageWaterResourceProfileFacade.save)", tags = {"VillageWaterResourceProfile"},
            description = "Creates a new resource using VillageWaterResourceProfileFacade.save with a VillageWaterResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageWaterResourceProfile payload used for creation", required = true)
            @RequestBody VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO);

    @Operation(summary = "Update VillageWaterResourceProfile (delegates to VillageWaterResourceProfileFacade.update)", tags = {"VillageWaterResourceProfile"},
            description = "Updates a resource using VillageWaterResourceProfileFacade.update with a VillageWaterResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageWaterResourceProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageWaterResourceProfileDTO> update(
            @Parameter(description = "VillageWaterResourceProfile payload used for update", required = true)
            @RequestBody VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO);

    @Operation(summary = "Get VillageWaterResourceProfiles page (delegates to VillageWaterResourceProfileFacade.getAllPage)", tags = {"VillageWaterResourceProfile"},
            description = "Returns a paginated list using VillageWaterResourceProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageWaterResourceProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageWaterResourceProfile by ID (delegates to VillageWaterResourceProfileFacade.getById)" , tags = {"VillageWaterResourceProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageWaterResourceProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageWaterResourceProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageWaterResourceProfile (delegates to VillageWaterResourceProfileFacade.delete)" , tags = {"VillageWaterResourceProfile"})
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

    @Operation(summary = "Get VillageWaterResourceProfile by IDs (delegates to VillageWaterResourceProfileFacade.getAllByIds)" , tags = {"VillageWaterResourceProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageWaterResourceProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
