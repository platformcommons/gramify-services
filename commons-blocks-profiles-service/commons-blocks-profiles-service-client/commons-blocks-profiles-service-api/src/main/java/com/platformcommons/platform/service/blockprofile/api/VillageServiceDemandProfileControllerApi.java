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

import com.platformcommons.platform.service.blockprofile.dto.VillageServiceDemandProfileDTO;

/**
 * API contract for VillageServiceDemandProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-service-demand-profiles")
@Tag(name = "VillageServiceDemandProfile", description = "Operations for VillageServiceDemandProfile that delegate to VillageServiceDemandProfileFacade")
public interface VillageServiceDemandProfileControllerApi {

    @Operation(summary = "Create VillageServiceDemandProfile (delegates to VillageServiceDemandProfileFacade.save)", tags = {"VillageServiceDemandProfile"},
            description = "Creates a new resource using VillageServiceDemandProfileFacade.save with a VillageServiceDemandProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageServiceDemandProfile payload used for creation", required = true)
            @RequestBody VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO);

    @Operation(summary = "Update VillageServiceDemandProfile (delegates to VillageServiceDemandProfileFacade.update)", tags = {"VillageServiceDemandProfile"},
            description = "Updates a resource using VillageServiceDemandProfileFacade.update with a VillageServiceDemandProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageServiceDemandProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageServiceDemandProfileDTO> update(
            @Parameter(description = "VillageServiceDemandProfile payload used for update", required = true)
            @RequestBody VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO);

    @Operation(summary = "Get VillageServiceDemandProfiles page (delegates to VillageServiceDemandProfileFacade.getAllPage)", tags = {"VillageServiceDemandProfile"},
            description = "Returns a paginated list using VillageServiceDemandProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageServiceDemandProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageServiceDemandProfile by ID (delegates to VillageServiceDemandProfileFacade.getById)" , tags = {"VillageServiceDemandProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageServiceDemandProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageServiceDemandProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageServiceDemandProfile (delegates to VillageServiceDemandProfileFacade.delete)" , tags = {"VillageServiceDemandProfile"})
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

    @Operation(summary = "Get VillageServiceDemandProfile by IDs (delegates to VillageServiceDemandProfileFacade.getAllByIds)" , tags = {"VillageServiceDemandProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageServiceDemandProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
