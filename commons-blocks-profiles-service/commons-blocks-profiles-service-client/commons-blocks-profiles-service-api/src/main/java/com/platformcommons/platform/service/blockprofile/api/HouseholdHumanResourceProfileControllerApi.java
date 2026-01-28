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

import com.platformcommons.platform.service.blockprofile.dto.HouseholdHumanResourceProfileDTO;

/**
 * API contract for HouseholdHumanResourceProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/household-human-resource-profiles")
@Tag(name = "HouseholdHumanResourceProfile", description = "Operations for HouseholdHumanResourceProfile that delegate to HouseholdHumanResourceProfileFacade")
public interface HouseholdHumanResourceProfileControllerApi {

    @Operation(summary = "Create HouseholdHumanResourceProfile (delegates to HouseholdHumanResourceProfileFacade.save)", tags = {"HouseholdHumanResourceProfile"},
            description = "Creates a new resource using HouseholdHumanResourceProfileFacade.save with a HouseholdHumanResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HouseholdHumanResourceProfile payload used for creation", required = true)
            @RequestBody HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO);

    @Operation(summary = "Update HouseholdHumanResourceProfile (delegates to HouseholdHumanResourceProfileFacade.update)", tags = {"HouseholdHumanResourceProfile"},
            description = "Updates a resource using HouseholdHumanResourceProfileFacade.update with a HouseholdHumanResourceProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HouseholdHumanResourceProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HouseholdHumanResourceProfileDTO> update(
            @Parameter(description = "HouseholdHumanResourceProfile payload used for update", required = true)
            @RequestBody HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO);

    @Operation(summary = "Get HouseholdHumanResourceProfiles page (delegates to HouseholdHumanResourceProfileFacade.getAllPage)", tags = {"HouseholdHumanResourceProfile"},
            description = "Returns a paginated list using HouseholdHumanResourceProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HouseholdHumanResourceProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HouseholdHumanResourceProfile by ID (delegates to HouseholdHumanResourceProfileFacade.getById)" , tags = {"HouseholdHumanResourceProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HouseholdHumanResourceProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HouseholdHumanResourceProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HouseholdHumanResourceProfile (delegates to HouseholdHumanResourceProfileFacade.delete)" , tags = {"HouseholdHumanResourceProfile"})
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

    @Operation(summary = "Get HouseholdHumanResourceProfile by IDs (delegates to HouseholdHumanResourceProfileFacade.getAllByIds)" , tags = {"HouseholdHumanResourceProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HouseholdHumanResourceProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
