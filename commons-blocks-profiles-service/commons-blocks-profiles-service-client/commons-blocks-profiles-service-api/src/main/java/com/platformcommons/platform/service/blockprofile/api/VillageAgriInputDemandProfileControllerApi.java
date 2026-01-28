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

import com.platformcommons.platform.service.blockprofile.dto.VillageAgriInputDemandProfileDTO;

/**
 * API contract for VillageAgriInputDemandProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-agri-input-demand-profiles")
@Tag(name = "VillageAgriInputDemandProfile", description = "Operations for VillageAgriInputDemandProfile that delegate to VillageAgriInputDemandProfileFacade")
public interface VillageAgriInputDemandProfileControllerApi {

    @Operation(summary = "Create VillageAgriInputDemandProfile (delegates to VillageAgriInputDemandProfileFacade.save)", tags = {"VillageAgriInputDemandProfile"},
            description = "Creates a new resource using VillageAgriInputDemandProfileFacade.save with a VillageAgriInputDemandProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageAgriInputDemandProfile payload used for creation", required = true)
            @RequestBody VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfileDTO);

    @Operation(summary = "Update VillageAgriInputDemandProfile (delegates to VillageAgriInputDemandProfileFacade.update)", tags = {"VillageAgriInputDemandProfile"},
            description = "Updates a resource using VillageAgriInputDemandProfileFacade.update with a VillageAgriInputDemandProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageAgriInputDemandProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageAgriInputDemandProfileDTO> update(
            @Parameter(description = "VillageAgriInputDemandProfile payload used for update", required = true)
            @RequestBody VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfileDTO);

    @Operation(summary = "Get VillageAgriInputDemandProfiles page (delegates to VillageAgriInputDemandProfileFacade.getAllPage)", tags = {"VillageAgriInputDemandProfile"},
            description = "Returns a paginated list using VillageAgriInputDemandProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageAgriInputDemandProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageAgriInputDemandProfile by ID (delegates to VillageAgriInputDemandProfileFacade.getById)" , tags = {"VillageAgriInputDemandProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageAgriInputDemandProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageAgriInputDemandProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageAgriInputDemandProfile (delegates to VillageAgriInputDemandProfileFacade.delete)" , tags = {"VillageAgriInputDemandProfile"})
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

    @Operation(summary = "Get VillageAgriInputDemandProfile by IDs (delegates to VillageAgriInputDemandProfileFacade.getAllByIds)" , tags = {"VillageAgriInputDemandProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageAgriInputDemandProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
