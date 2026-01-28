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

import com.platformcommons.platform.service.blockprofile.dto.HouseholdAspirationsProfileDTO;

/**
 * API contract for HouseholdAspirationsProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/household-aspirations-profiles")
@Tag(name = "HouseholdAspirationsProfile", description = "Operations for HouseholdAspirationsProfile that delegate to HouseholdAspirationsProfileFacade")
public interface HouseholdAspirationsProfileControllerApi {

    @Operation(summary = "Create HouseholdAspirationsProfile (delegates to HouseholdAspirationsProfileFacade.save)", tags = {"HouseholdAspirationsProfile"},
            description = "Creates a new resource using HouseholdAspirationsProfileFacade.save with a HouseholdAspirationsProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HouseholdAspirationsProfile payload used for creation", required = true)
            @RequestBody HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO);

    @Operation(summary = "Update HouseholdAspirationsProfile (delegates to HouseholdAspirationsProfileFacade.update)", tags = {"HouseholdAspirationsProfile"},
            description = "Updates a resource using HouseholdAspirationsProfileFacade.update with a HouseholdAspirationsProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HouseholdAspirationsProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HouseholdAspirationsProfileDTO> update(
            @Parameter(description = "HouseholdAspirationsProfile payload used for update", required = true)
            @RequestBody HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO);

    @Operation(summary = "Get HouseholdAspirationsProfiles page (delegates to HouseholdAspirationsProfileFacade.getAllPage)", tags = {"HouseholdAspirationsProfile"},
            description = "Returns a paginated list using HouseholdAspirationsProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HouseholdAspirationsProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HouseholdAspirationsProfile by ID (delegates to HouseholdAspirationsProfileFacade.getById)" , tags = {"HouseholdAspirationsProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HouseholdAspirationsProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HouseholdAspirationsProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HouseholdAspirationsProfile (delegates to HouseholdAspirationsProfileFacade.delete)" , tags = {"HouseholdAspirationsProfile"})
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

    @Operation(summary = "Get HouseholdAspirationsProfile by IDs (delegates to HouseholdAspirationsProfileFacade.getAllByIds)" , tags = {"HouseholdAspirationsProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HouseholdAspirationsProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
