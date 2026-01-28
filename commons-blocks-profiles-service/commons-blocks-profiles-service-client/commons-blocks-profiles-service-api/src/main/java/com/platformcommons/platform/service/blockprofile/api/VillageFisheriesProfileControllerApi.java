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

import com.platformcommons.platform.service.blockprofile.dto.VillageFisheriesProfileDTO;

/**
 * API contract for VillageFisheriesProfileController. Declares endpoints and Swagger documentation.
 */
@RequestMapping(value = "/api/v1/village-fisheries-profiles")
@Tag(name = "VillageFisheriesProfile", description = "Operations for VillageFisheriesProfile that delegate to VillageFisheriesProfileFacade")
public interface VillageFisheriesProfileControllerApi {

    @Operation(summary = "Create VillageFisheriesProfile (delegates to VillageFisheriesProfileFacade.save)", tags = {"VillageFisheriesProfile"},
            description = "Creates a new resource using VillageFisheriesProfileFacade.save with a VillageFisheriesProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> create(
            @Parameter(description = "VillageFisheriesProfile payload used for creation", required = true)
            @RequestBody VillageFisheriesProfileDTO VillageFisheriesProfileDTO);

    @Operation(summary = "Update VillageFisheriesProfile (delegates to VillageFisheriesProfileFacade.update)", tags = {"VillageFisheriesProfile"},
            description = "Updates a resource using VillageFisheriesProfileFacade.update with a VillageFisheriesProfileDTO payload.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = @Content(schema = @Schema(implementation = VillageFisheriesProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<VillageFisheriesProfileDTO> update(
            @Parameter(description = "VillageFisheriesProfile payload used for update", required = true)
            @RequestBody VillageFisheriesProfileDTO VillageFisheriesProfileDTO);

    @Operation(summary = "Get VillageFisheriesProfiles page (delegates to VillageFisheriesProfileFacade.getAllPage)", tags = {"VillageFisheriesProfile"},
            description = "Returns a paginated list using VillageFisheriesProfileFacade.getAllPage.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page retrieved",
                    content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    @GetMapping
    ResponseEntity<PageDTO<VillageFisheriesProfileDTO>> getAllPage(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size);

    @Operation(summary = "Get VillageFisheriesProfile by ID (delegates to VillageFisheriesProfileFacade.getById)" , tags = {"VillageFisheriesProfile"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = VillageFisheriesProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<VillageFisheriesProfileDTO> getById(
            @Parameter(description = "Resource ID", required = true)
            @PathVariable Long id);

    @Operation(summary = "Delete VillageFisheriesProfile (delegates to VillageFisheriesProfileFacade.delete)" , tags = {"VillageFisheriesProfile"})
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

    @Operation(summary = "Get VillageFisheriesProfile by IDs (delegates to VillageFisheriesProfileFacade.getAllByIds)" , tags = {"VillageFisheriesProfile"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(schema = @Schema(implementation = Set.class)))
    })
    @PostMapping(path = "/ids", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<VillageFisheriesProfileDTO>> getAllByIds(
            @Parameter(description = "Set of IDs", required = true)
            @RequestBody Set<Long> ids);
}
