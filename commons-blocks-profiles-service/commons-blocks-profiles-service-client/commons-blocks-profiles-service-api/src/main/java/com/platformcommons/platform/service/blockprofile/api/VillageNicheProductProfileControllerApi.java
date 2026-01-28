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

import com.platformcommons.platform.service.blockprofile.dto.VillageNicheProductProfileDTO;

/**
 * API contract for VillageNicheProductProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-niche-product-profiles")
@Tag(name = "VillageNicheProductProfile", description = "Operations for VillageNicheProductProfile that delegate to VillageNicheProductProfileFacade")
public interface VillageNicheProductProfileControllerApi {

    @Operation(summary = "Create VillageNicheProductProfile (delegates to VillageNicheProductProfileFacade.save)", tags = {"VillageNicheProductProfile"},
            description = "Creates a new resource using VillageNicheProductProfileFacade.save with a VillageNicheProductProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageNicheProductProfile payload used for creation", required = true)
            @RequestBody VillageNicheProductProfileDTO VillageNicheProductProfileDTO);

    @Operation(summary = "Update VillageNicheProductProfile (delegates to VillageNicheProductProfileFacade.update)", tags = {"VillageNicheProductProfile"},
            description = "Updates a resource using VillageNicheProductProfileFacade.update with a VillageNicheProductProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageNicheProductProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageNicheProductProfileDTO> update(
            @Parameter(description = "VillageNicheProductProfile payload used for update", required = true)
            @RequestBody VillageNicheProductProfileDTO VillageNicheProductProfileDTO);

    @Operation(summary = "Get VillageNicheProductProfiles page (delegates to VillageNicheProductProfileFacade.getAllPage)", tags = {"VillageNicheProductProfile"},
            description = "Returns a paginated list using VillageNicheProductProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageNicheProductProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageNicheProductProfile by ID (delegates to VillageNicheProductProfileFacade.getById)" , tags = {"VillageNicheProductProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageNicheProductProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageNicheProductProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageNicheProductProfile (delegates to VillageNicheProductProfileFacade.delete)" , tags = {"VillageNicheProductProfile"})
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

    @Operation(summary = "Get VillageNicheProductProfile by IDs (delegates to VillageNicheProductProfileFacade.getAllByIds)" , tags = {"VillageNicheProductProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageNicheProductProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
