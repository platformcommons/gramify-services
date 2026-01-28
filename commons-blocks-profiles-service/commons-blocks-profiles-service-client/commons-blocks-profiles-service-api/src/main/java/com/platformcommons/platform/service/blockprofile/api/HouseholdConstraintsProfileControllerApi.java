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

import com.platformcommons.platform.service.blockprofile.dto.HouseholdConstraintsProfileDTO;

/**
 * API contract for HouseholdConstraintsProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/household-constraints-profiles")
@Tag(name = "HouseholdConstraintsProfile", description = "Operations for HouseholdConstraintsProfile that delegate to HouseholdConstraintsProfileFacade")
public interface HouseholdConstraintsProfileControllerApi {

    @Operation(summary = "Create HouseholdConstraintsProfile (delegates to HouseholdConstraintsProfileFacade.save)", tags = {"HouseholdConstraintsProfile"},
            description = "Creates a new resource using HouseholdConstraintsProfileFacade.save with a HouseholdConstraintsProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HouseholdConstraintsProfile payload used for creation", required = true)
            @RequestBody HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO);

    @Operation(summary = "Update HouseholdConstraintsProfile (delegates to HouseholdConstraintsProfileFacade.update)", tags = {"HouseholdConstraintsProfile"},
            description = "Updates a resource using HouseholdConstraintsProfileFacade.update with a HouseholdConstraintsProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HouseholdConstraintsProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HouseholdConstraintsProfileDTO> update(
            @Parameter(description = "HouseholdConstraintsProfile payload used for update", required = true)
            @RequestBody HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO);

    @Operation(summary = "Get HouseholdConstraintsProfiles page (delegates to HouseholdConstraintsProfileFacade.getAllPage)", tags = {"HouseholdConstraintsProfile"},
            description = "Returns a paginated list using HouseholdConstraintsProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HouseholdConstraintsProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HouseholdConstraintsProfile by ID (delegates to HouseholdConstraintsProfileFacade.getById)" , tags = {"HouseholdConstraintsProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HouseholdConstraintsProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HouseholdConstraintsProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HouseholdConstraintsProfile (delegates to HouseholdConstraintsProfileFacade.delete)" , tags = {"HouseholdConstraintsProfile"})
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

    @Operation(summary = "Get HouseholdConstraintsProfile by IDs (delegates to HouseholdConstraintsProfileFacade.getAllByIds)" , tags = {"HouseholdConstraintsProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HouseholdConstraintsProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
