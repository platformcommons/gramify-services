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

import com.platformcommons.platform.service.blockprofile.dto.HouseholdLivelihoodProfileDTO;

/**
 * API contract for HouseholdLivelihoodProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/household-livelihood-profiles")
@Tag(name = "HouseholdLivelihoodProfile", description = "Operations for HouseholdLivelihoodProfile that delegate to HouseholdLivelihoodProfileFacade")
public interface HouseholdLivelihoodProfileControllerApi {

    @Operation(summary = "Create HouseholdLivelihoodProfile (delegates to HouseholdLivelihoodProfileFacade.save)", tags = {"HouseholdLivelihoodProfile"},
            description = "Creates a new resource using HouseholdLivelihoodProfileFacade.save with a HouseholdLivelihoodProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "HouseholdLivelihoodProfile payload used for creation", required = true)
            @RequestBody HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO);

    @Operation(summary = "Update HouseholdLivelihoodProfile (delegates to HouseholdLivelihoodProfileFacade.update)", tags = {"HouseholdLivelihoodProfile"},
            description = "Updates a resource using HouseholdLivelihoodProfileFacade.update with a HouseholdLivelihoodProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = HouseholdLivelihoodProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HouseholdLivelihoodProfileDTO> update(
            @Parameter(description = "HouseholdLivelihoodProfile payload used for update", required = true)
            @RequestBody HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO);

    @Operation(summary = "Get HouseholdLivelihoodProfiles page (delegates to HouseholdLivelihoodProfileFacade.getAllPage)", tags = {"HouseholdLivelihoodProfile"},
            description = "Returns a paginated list using HouseholdLivelihoodProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<HouseholdLivelihoodProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get HouseholdLivelihoodProfile by ID (delegates to HouseholdLivelihoodProfileFacade.getById)" , tags = {"HouseholdLivelihoodProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = HouseholdLivelihoodProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<HouseholdLivelihoodProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete HouseholdLivelihoodProfile (delegates to HouseholdLivelihoodProfileFacade.delete)" , tags = {"HouseholdLivelihoodProfile"})
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

    @Operation(summary = "Get HouseholdLivelihoodProfile by IDs (delegates to HouseholdLivelihoodProfileFacade.getAllByIds)" , tags = {"HouseholdLivelihoodProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<HouseholdLivelihoodProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
