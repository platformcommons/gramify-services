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

import com.platformcommons.platform.service.blockprofile.dto.VillageHumanResourceProfileDTO;

/**
 * API contract for VillageHumanResourceProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-human-resource-profiles")
@Tag(name = "VillageHumanResourceProfile", description = "Operations for VillageHumanResourceProfile that delegate to VillageHumanResourceProfileFacade")
public interface VillageHumanResourceProfileControllerApi {

    @Operation(summary = "Create VillageHumanResourceProfile (delegates to VillageHumanResourceProfileFacade.save)", tags = {"VillageHumanResourceProfile"},
            description = "Creates a new resource using VillageHumanResourceProfileFacade.save with a VillageHumanResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageHumanResourceProfile payload used for creation", required = true)
            @RequestBody VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO);

    @Operation(summary = "Update VillageHumanResourceProfile (delegates to VillageHumanResourceProfileFacade.update)", tags = {"VillageHumanResourceProfile"},
            description = "Updates a resource using VillageHumanResourceProfileFacade.update with a VillageHumanResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageHumanResourceProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageHumanResourceProfileDTO> update(
            @Parameter(description = "VillageHumanResourceProfile payload used for update", required = true)
            @RequestBody VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO);

    @Operation(summary = "Get VillageHumanResourceProfiles page (delegates to VillageHumanResourceProfileFacade.getAllPage)", tags = {"VillageHumanResourceProfile"},
            description = "Returns a paginated list using VillageHumanResourceProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageHumanResourceProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageHumanResourceProfile by ID (delegates to VillageHumanResourceProfileFacade.getById)" , tags = {"VillageHumanResourceProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageHumanResourceProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageHumanResourceProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageHumanResourceProfile (delegates to VillageHumanResourceProfileFacade.delete)" , tags = {"VillageHumanResourceProfile"})
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

    @Operation(summary = "Get VillageHumanResourceProfile by IDs (delegates to VillageHumanResourceProfileFacade.getAllByIds)" , tags = {"VillageHumanResourceProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageHumanResourceProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
